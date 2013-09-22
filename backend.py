from bottle import debug, response, request, route, run, post, get, HTTPResponse
import json
import MySQLdb as db
import time
import gc

## token ##
import hashlib
import random

## Cleaning up before we start##
gc.collect()

## Initiating the database connection##
con = db.Connect('localhost', 'sauadmin', 'dolly', 'prosjektsau', charset='utf8', use_unicode=True)

## Database functions ##

def db_read_single(unknown, table, key, value):
# db_read_single(id, bonder, token, some_token) vil gi
# id til bruker med some_token
    try:    
        cur = con.cursor()
        cur.execute("SELECT `%s` FROM `%s` WHERE `%s` = '%s'" % (unknown, table, key, value))
        data = cur.fetchone()
    except:
        return False
    return str(data[0])

def db_read_multiple(lifeform, userid=None):
    if lifeform == "farmer" and userid == None:
        sql = "SELECT * from `bonder`" 
    elif lifeform == "farmer" and userid != None:
        sql = "SELECT * from `bonder` WHERE `id` = '%s'" % (userid)
    elif lifeform == "sheep" and userid == None:
        sql = "SELECT * from `sauer`"
    elif lifeform == "sheep" and userid != None:
        sql = "SELECT * from `sauer` WHERE `owner` = '%s'" % (userid)
    
    try:
        cur = con.cursor()
        cur.execute(sql)
        data = cur.fetchall()
        if data == None:
            return "Empty"
        d = {}
        l = []
        for da in data:
          l.append(str(da))
        d[lifeform] = l
    except:
        return False
    return d

def db_insert(t, kwargs):
    sql = "INSERT INTO `%s`" % (t)
    sql += "(`" + "` ,`".join(kwargs.keys()) + "`)"
    sql += "VALUES ('" + "' ,'".join(kwargs.values()) + "')"
    try:
        cur = con.cursor()
        cur.execute(sql)
        con.commit()
        return True
    except:
        return False

def db_update_token(email, token):
    try: 
        cur = con.cursor()
        cur.execute("UPDATE bonder SET token=%s WHERE email=%s", (token, email))
        con.commit()
    except:
        return False
    return True

##########################
## General purpose      ##
##########################

@route('/sheep/<i:int>', method='GET')
def sheep(i):
    if validate_token(request.query.token):
        result = db_read_multiple('sheep', i)
        if result != False:
            return respond(200, 'Ok', result)
        else:
            return respond(131, 'Database-error', None)
    else:
        return respond(132,'No (such) token', None)


@route('/sheep/add/<i:int>', method='GET')
def add_sheep(i):
    if validate_token(request.query.token):
        

##########################
## Function for writing ##
## out the response     ##
##########################

def respond(co, ms, resp):
    response = 'application/json'
    return {'code':co , 'msg':ms , 'response':resp  }

#########################
## Register, login and ##
## token validation    ##
#########################

@route('/register')
def register():
    return'''
        <form action="/register" method="post">
            Email:    <input type="text" name="email"   />
            Name:     <input type="text" name="name"    />
            Password:  <input type="password" name="pswd"    />
            Phone:     <input type="text" name="phone"   />
            <input type="submit" value="Register" />
        </form>'''

@route('/register', method='POST')
def register():
    email = request.forms.get('email')
    name = request.forms.get('name')
    pswd = request.forms.get('pswd')
    phone = request.forms.get('phone')

    if email != None and name != None and pswd != None and phone != None:
      token = generate_token(email)
      d = {'name':name, 'email':email,'pswd':pswd, 'token':token,  'sheeps':'0', 'telephone':phone}
      if db_insert('bonder', d) == True:
        return respond(200, 'User Registration Complete', {'token':token})
      else:
        return respond(131, 'Database-error', None)
    else:
        return respond(134, 'Missing required fields', None)


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
    result = db_read_single("id", "bonder", "token", token)
    if result == None:
        return False
    else:
        return True

def check_credentials(em, p):
    data = db_read_single("pswd", "bonder", "email", em)
    psw = data
    if str(psw) == p:
        return True
    else:
        return False

def generate_token(name):
    #setup
    random.seed(13)
    h = hashlib.sha1()
    # Contains:
    t = str(time.time())
    num = str(random.random())
    name = name
    h.update(t + num + name)
    token = 'sheep$' + h.hexdigest()
    return token


if __name__ == '__main__':
    run(host='0.0.0.0', port=8081, debug=True)
