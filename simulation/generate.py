#!/usr/local/bin/python
import time
import MySQLdb as mdb
from random import randint
from mail import sendmail as mailer

con = mdb.connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')
cur = con.cursor()

def db_insert(name, age, dead, lat, lng, hr, weight, temp, respiration,  gender, owner):
    sql = "INSERT INTO `sauer` (`id`, `name`, `age`, `dead`, `lat`, `long`, `hr`, `weight`, `temp`, `respiration`, `gender`, `owner`) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')" % (name, age, dead, lat, lng, hr, weight, temp, respiration, gender, owner) 
    print(sql)
    try:
        cur = con.cursor()
        cur.execute(sql)
        con.commit()
        print("Sheep added")
        return True
    except:
        return False

def getGender():
    gender = randint(0, 1)
    if(gender == 0):
        return "Male"
    else:
        return "Female"

for i in range(200):
    name =  "sheep" + str(i)
    age =  randint(3, 12)
    dead = "0"
    lat = "63.30" + str(randint(3000, 40000))
    lng = "10.30" + str(randint(4000, 5000))
    hr = str(randint(60,90))
    weight = str(randint(50, 70))
    temp =  "37"
    respiration =  "13"
    gender = getGender()
    owner = str("39")
    db_insert(name, age, dead, lat, lng, hr, weight, temp, respiration, gender, owner)
