#-*- coding: utf-8 -*-

from bottle import debug, response, request, route, run, post, get, HTTPResponse, static_file
import json
import MySQLdb as db
import time
import gc
import os

## token ##
import hashlib
import random

## Cleaning up before we start##
gc.collect()

## Initiating the database connection##
con = db.Connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')

## Database functions ##

def db_delete_single(table, itemid):
    result = ""
    try:
        cur = con.cursor()
        cur.execute("DELETE FROM `%s`WHERE id='%s'" % (table, itemid))
        result = "deleted " + str(itemid)
        con.commit()
    except:
        result = "Nothing deleted"
    return result

def db_delete_all(table, key, value):
    result = ""
    try:
        cur = con.cursor()
        cur.execute("DELETE FROM `%s`WHERE `%s`='%s'" % (table, key, value))
        result = "deleted all from %s where %s = %s" % (table, key, value) 
        con.commit()
        return result
    except:
        result = "Nothing deleted"
        return result

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

def db_read_multiple(lifeform, userid, sheepid):
    if lifeform == "farmer" and userid == None:
        sql = "SELECT * from `bonder`" 
    elif lifeform == "farmer" and userid != None:
        sql = "SELECT * from `bonder` WHERE `id` = '%s'" % (userid)
    elif lifeform == "sheep" and userid == None:
        sql = "SELECT * from `sauer`"
    elif lifeform == "sheep" and userid != None:
        sql = "SELECT * from `sauer` WHERE `owner` = '%s'" % (userid)
    elif lifeform == "notification" and userid != None:
        sql = "SELECT * from `notifications` WHERE `owner`= '%s'" % (userid)
    elif lifeform == "notification" and userid == None and sheepid != None:
        sql = "SELECT * from `notifications` WHERE `sheepid` = '%s'" % (sheepid)
    elif lifeform == "chat"  and userid == None:
        sql = "SELECT * from `chat`"
    elif lifeform == "chat" and userid != None:
        sql = "SELECT * from `chat` WHERE `uid` = '%s'" % (userid)
        
    try:
        cur = con.cursor()
        cur.execute(sql)
        result = []
        finalres = {}
        columns = tuple( [d[0].decode('utf8') for d in cur.description])
        for row in cur:
            result.append(dict(zip(columns, row)))
        newresult = []
        
        for d in result:
            keys = [str(k) for k in d.keys()]
            values = [str(v) for v in d.values()]
            newresult.append(dict(zip(keys, values)))
        finalres[lifeform] = newresult

    except:
        return False
    return finalres

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

def db_update(table, kwargs, uid):
    sql = "UPDATE %s SET" % (table)
    for k in kwargs:
        sql = k + kwargs[k] + " "
    sql = "WHERE id=%s" % uid
    try:
        cur = con.cursor()
        cur.execute(sql)
        con.commit()
    except:
        return False
    return True


def db_update_token(email, token):
    try: 
        cur = con.cursor()
        cur.execute("UPDATE bonder SET token=%s WHERE email=%s", (token, email))
        con.commit()
    except:
        return False
    return True
	
##########################
## Css and js work      ##
##########################
	
# Static Routes
@get('/<filename:re:.*\.js>')
def javascripts(filename):
    return static_file(filename, root=os.path.join(os.path.dirname(__file__), 'static/js'))

@get('/css/<filename:re:.*\.css>')
def stylesheets(filename):
    return static_file(filename, root=os.path.join(os.path.dirname(__file__), 'static/css'))

@get('/images/<filename:re:.*\.(jpg|png|gif|ico)>')
def images(filename):
    return static_file(filename, root=os.path.join(os.path.dirname(__file__), 'static/images'))

@get('/fonts/<filename:re:.*\.(eot|ttf|woff|svg)>')
def fonts(filename):
    return static_file(filename, root=os.path.join(os.path.dirname(__file__), 'static/css/fonts'))

##########################
## General purpose      ##
##########################
@route('/')
def default():
    return static_file('api.html', root=os.path.join(os.path.dirname(__file__), 'static'))

@route('/status', method='GET')
def status():
    return respond(200, 'Server up and runnning', None)

@route('/sheep', method='GET')
def sheep():
    token = request.query.token
    current_user = str(db_read_single('id', 'bonder', 'token', token))
    if validate_token(token):
        result = db_read_multiple('sheep', current_user, None)
        if result != False:
            return respond(200, 'Ok', result)
        else:
            return respond(131, 'Database-error', None)
    else:
        return respond(132,'No (such) token', None)

@route('/sheep/add', method='GET')
def add_sheep():
    token = request.query.token
    if validate_token(token):
        name = request.query.name
        age = request.query.age
        gender = request.query.gender
        weight = request.query.weight
        owner = str(db_read_single('id', 'bonder', 'token', token))
        #TODO: hr, lat, long
        try:
            db_insert('sauer', {'name':name, 'age':age, 'gender':gender, 'owner':owner, 'weight':weight, 'lat':'63.43', 'long':'10.12' })
            return respond(200, 'Sheep added', None)
        except:
            return respond(131, 'Database-error', None)
    else:
        return respond(132, 'No (such) token', None)

#TODO: Create notifications  table
@route('/notification', method='GET')
def notification():
    token = request.query.token
    sheepid = request.query.sheepid
    if validate_token(token):
        result = None
        if(sheepid == None or sheepid == ""):
            current_user = str(db_read_single('id', 'bonder', 'token', token))
            result = db_read_multiple('notification', current_user, None)
        elif(sheepid != None):
            result = db_read_multiple('notification', None, sheepid)
        else:
            return respond(117, 'InvalidArgument-error', None)
        if(result != None and result != False):
            return respond(200, 'Ok', result)
        else:
            return respond(131, 'Database-error', None)
    else:
        return respond(132, 'No (such) token', None)

@route('/delete/sheep', method='GET')
def delete_sheep():
    token = request.query.token
    sheepid = request.query.sheepid
    if validate_token(token): 
        if sheepid!= None and sheepid != "":
            resp = db_delete_single('sauer', sheepid)
            return respond(200, 'Deletion Complete', resp)
        else:
            return respond(144, 'Missing id', 'No sheepid in request')
    else:
        return respond(132, 'No (such) token', None)

@route('/delete/notification', method='GET')
def delete_notification():
    token = request.query.token
    notid = request.query.notid
    if validate_token(token):
        if notid != None and notid!= "":
            resp = db_delete_single('notification', notid)
            return respond(200, 'Deleteion Complete', resp)
        else:
            user = str(db_read_single('id', 'bonder', 'token', token))
            resp = db_delete_all('notification', 'owner', user)
            return respond(200, 'Deletion Complete', resp)
    else:
        return respond(131,'No (such) token', None)

@route('/user', method='GET')
def get_user():
    token = request.query.token
    if validate_token(token):
        user = str(db_read_single('id', 'bonder', 'token', token))
        resp = db_read_multiple('farmer', user, None)
        if resp != None and resp != False:
            return respond(200, 'Ok', resp)
        else:
            return respond(117, 'Database-Error', None)
    else:
        return respond(131, 'No (such) token', None)

@route('/edit', method='GET')
def edit_user():
    token = request.query.token
    sheepid = request.query.uid
    #User
    #sheep
    if validate_token(token):
        d = {}
        if sheepid != "" or sheepid != None:
            user = str(db_read_single('id', 'bonder', 'token', token))
            d['name'] = request.query.name
            d['mail'] = request.query.mail
            d['vara'] = request.query.vara
            d['telephone'] = request.query.phone
            d['address'] = request.query.address
            for k in d:
                if(d[k] == None or d[k] == ""):
                    del d[k]
            result = db_update("bonder", d, user)
            if(result == True):
                return respond(200, "Sheep updated", None)
            else:
                return respond(133, "DataBase-Error", None)
        elif sheepid == "" or sheepid == None:
            d['name'] = request.query.name
            d['age'] = request.query.name
            d['weight'] = request.query.name
            d['Gender'] = request.query.name
            for k in d:
                if(d[k] == None or d[k] == ""):
                    del d[k]
            result = db_update("sauer", d, sheepid)
            if(result == True):
                return respond(200, "Sheep updated", None)
            else:
                return respond(131, "DataBase-Error", None)
    else:
        return respond(131, "No (such) token", None)


@route('/add/sheepchat', method='GET')
def sheepchat():
    d = {}
    d['msg'] = request.query.msg
    token = request.query.token
    if validate_token(token):
        d['uid'] = str(db_read_single('id', 'bonder', 'token', token))
        d['uname'] = str(db_read_single('name', 'bonder', 'token', token))
        result = db_insert('chat', d)
        if (result != False):
            return respond(200, 'ChatLog updated', None)
        else:
            return respond(133, 'DataBase-Err', d)
    else:
        return respond(131, 'No (such) token', None)

@route('/get/sheepchat', method='GET')
def sheepchat():
    token = request.query.token
    user = request.query.token
    if validate_token(token):
        uid = str(db_read_single('id', 'bonder', 'token', token))
        result = db_read_multiple("chat", None, None) 
        if (result != False and result != None):
            return respond(200, 'ChatLog updated', result)
        else:
            return respond(133, 'DataBase-Err', None)
    else:
        return respond(131, 'No (such) token', None)
        

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
    return static_file('registration.html', root=os.path.join(os.path.dirname(__file__), 'static'))

@route('/register', method='POST')
def register():
    email = request.forms.get('email')
    name = request.forms.get('name')
    pswd = request.forms.get('pswd')
    phone = request.forms.get('phone')
    address = request.forms.get('address')
    vara = request.forms.get('vara')

    if email != None and name != None and pswd != None and phone != None and address != None and vara != None:
      token = generate_token(email)
      d = {'name':name, 'email':email,'pswd':pswd, 'token':token,  'sheeps':'0', 'telephone':phone, 'address':address, 'vara':vara}
      if db_insert('bonder', d) == True:
        return respond(200, 'User Registration Complete', {'token':token})
      else:
        return respond(131, 'Database-error', None)
    else:
        return respond(134, 'Missing required fields', None)


@route('/login')
def login():
    return static_file('registration.html', root=os.path.join(os.path.dirname(__file__), 'static'))

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
    result = db_read_single('id', 'bonder', 'token', token)
    if result == None:
        return False
    else:
        return True

def check_credentials(em, p):
    psw = db_read_single("pswd", "bonder", "email", em)
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
