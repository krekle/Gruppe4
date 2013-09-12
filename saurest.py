from bottle import debug, response, request, route, run, post, get, HTTPResponse
import gc
import json
import MySQLdb as mdb
import time
#### token:
import hashlib
import random

#Cleaning up before we start:
gc.collect()
con = mdb.Connect('localhost', 'sauadmin', 'dolly', 'prosjektsau', charset='utf8')

#### DataBase Usage ####
def db_read_multiple(sql, type):

    try: 
        cur = con.cursor(mdb.cursors.DictCursor)
        cur.execute(sql)
        data = cur.fetchall()
        if data == None:
            return None
        desc = cur.description
        dict = {}
        l = []
       
        for d in data:
            l.append(d)    
        dict[type] = l
    except:
          return False
    return dict

def db_read_single_dict(sql):
    try:
         cur = con.cursor(mdb.cursors.DictCursor)
         cur.execute(sql)
         data = cur.fetchone()
    except:
          return False
    return data

def db_read_single(sql):
    try:
        cur = con.cursor()
        cur.execute(sql)
        data = cur.fetchone()
    except:
        return False
    return data

def db_update_token(email, token): 
    try:
        cur = con.cursor()
        cur.execute("UPDATE bonder SET token=%s WHERE email=%s;",(token, email))
        con.commit()
    except:
        return False
    return True

def db_update_multiple(sql):
    try:
        cur = con.cursor()
        cur.execute(sql)
        con.commit()
    except:
        return False
    return True    

#### Writing out response correctly ####

def respond(co, ms, resp):
    response = 'application/json'
    return {'code':co , 'msg':ms , 'response':resp  }

#### Status and Check ####

@route('/hello')
def hello():
    return "Hello World!"

@route('/status')
def status():
    return respond(200, 'Api up and running','Server Time: '+ str (time.time()))


#### General purpose  ####

@route('/user/all', method = 'GET')
def users():
    if validate_token(request.query.token):
        result = db_read_multiple("SELECT name, email, sheeps, telephone, id FROM bonder;", 'bonder')
        if result != None:
            return respond(200, 'Ok', result)
        else:
            return respond(131, 'DATABASE-Error', None)
    else:
        return respond(132, 'No (such) token', None)

@route('/user/<id:int>', method = 'GET')
def get_user(id):
    if validate_token(request.query.token):
        result = db_read_single_dict("SELECT name, email, sheeps, telephone, id FROM users WHERE id = '%s';" %id)
        return respond(200, 'Ok', result)
    else: 
        return respond(132, 'No (such) token', None)


@route('/user/<action>/me', method = 'GET')
def user_me(action):
    if action == 'set':
        if validate_token(request.query.token):
            d = dict(request.query)
            sql = "UPDATE bonder SET "
            for key in d.iterkeys():
                if key != 'token' and key != 'pswd' and key != 'name' and key != 'email':
                  sql += key +"='" + d[key] + "'" + ","
            sql = sql[:-1]
            sql += ("WHERE token='%s';" %request.query.token)
            db_update_multiple(sql)
            return respond(200, 'Database updated', None)

    elif action == 'get':
        return respond(200, 'Ok', db_read_single_dict("SELECT name, email, sheeps, telephone, id FROM bonder WHERE token = '%s';" %request.query.token))

#### Login and token validation ####

@route('/register', method='POST')
def register():
    email = request.forms.get('email')
    name = request.forms.get('name')
    pswd = request.forms.get('pswd')
    result = db_update_multiple('INSERT INTO `prosjektsau`.`bonder` (`id`, `name`, `email`, `pswd`, `token`, `sheeps`, `telephone`) VALUES (NULL, ' + str(name) + ', ' +str(email) + ', ' + str(pswd) + ', NULL, NULL);')



@route('/login')
def login():
    return '''
        <form action="/login" method="post">
            Email:    <input type="text" name="email" />
            Password: <input type="password" name="pswd" />
            <input type="submit" value="Login" />
        </form>'''


@route('/login', method='POST')
def login():
    email = request.forms.get('email')
    pswd  = request.forms.get('pswd'  )
    token = request.forms.get('token')
    
    if email != None and pswd != None:
        if check_credentials(email, pswd):
            dict = {}
            tok = generate_token(email)
            dict['token'] = tok
            if db_update_token(email, tok):
                return respond(200, 'Ok', dict)
            else:
                return respond(132, 'Database Error', None)
        else:
            return respond(131, 'Wrong password/username', None)
    elif token != None:
        if validate_token(token):
            return respond(200, 'Token Valid', None)
        else:
            return respond(131, 'Invalid/Expired token', None)
    else:
        return respond(131, 'Invalid input', None)

def validate_token(token):
    result = db_read_single("SELECT id FROM bonder WHERE token = '%s';" %token)
    if result == None:
        return False
    else:
        return True
        
def check_credentials(n, p):
    data = db_read_single("SELECT pswd FROM bonder WHERE email = '%s';" %n)
    psw = data[0]
    if str(psw) == p:
        return True
    else:
        return False
  #TODO:
    

def generate_token(name):
    #setup
    random.seed(13)
    h = hashlib.sha1()
    # Contains:
    t = str(time.time())
    num = str(random.random())
    name = name
    h.update(t + num + name)
    token = 'att$' + h.hexdigest()
    return token


if __name__ == '__main__':
    run(host='0.0.0.0', port=8081, debug=True)
