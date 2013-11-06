#!/usr/local/bin/python
import time
import MySQLdb as mdb
from random import randint

con = mdb.connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')
cur = con.cursor()

cur.execute("SELECT * FROM sauer")
sheep = cur.fetchall()

def db_insert(owner, sheepid, level, msg, lat, lng):
    sql = "INSERT INTO `notifications` (`id`, `owner`, `sheepid`, `level`, `msg`, `lat`, `lng`, `datime`) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s')" % (owner, sheepid, level, msg, lat, lng, str(time.strftime("%d/%m/%y - %H:%M"))) 
    try:
        cur = con.cursor()
        cur.execute(sql)
        con.commit()
        return True
    except:
        return False

for she in sheep:
    sheepid = str(she[0])
    name =  str(she[1])
    age =  str(she[2])
    dead = str(she[3])
    lat = str(she[4])
    lng = str(she[5])
    hr = str(she[6])
    weight = str(she[7])
    temp =  str(she[8])
    respiration =  str(she[9])
    gender = str(she[10])
    owner = str(she[11])
    
    #output:
    msg = ""
    level = ""

    #if clusterfuck:
    if(dead != "0"):
        msg = name , "is now dead"
        level = "3"
        db_insert(owner, sheepid, level, msg, lat, lng)
    if(hr >= 90 or hr <= 61):
        msg = name + " has an irregular hearth-rate: " + hr
        level = "2"
        db_insert(owner, sheepid, level, msg, lat, lng)
    if(temp >= 40 or temp <= 36):
        msg = name + " has an irregular temperature: " + temp
        level = "1"
        db_insert(owner, sheepid, level, msg, lat, lng)
    if(respiration >= 20 or respiration <= 12):
        msg = name + " has an irregular respiration: " + respiration
        level = "2"
        db_insert(owner, sheepid, level, msg, lat, lng)


