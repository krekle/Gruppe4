#!/usr/local/bin/python
import time
import MySQLdb as mdb
from random import randint
from mail import sendmail as mailer

con = mdb.connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')
cur = con.cursor()

cur.execute("SELECT * FROM sauer")
sheep = cur.fetchall()

def db_insert_not(owner, sheepid, level, msg, lat, lng):
    sql = "INSERT INTO `notifications` (`id`, `owner`, `sheepid`, `level`, `msg`, `lat`, `lng`, `datime`) VALUES (NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s')" % (owner, sheepid, level, msg, lat, lng, str(time.strftime("%d/%m/%y - %H:%M"))) 
    try:
        cur = con.cursor()
        cur.execute(sql)
        con.commit()
        return True
    except:
        return False
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
    
    kill = randint(0, 10)
    msg = ""
    level = ""
    
    if(kill == 2):
        email = db_read_single('email', 'bonder', 'id', owner)
        vara = db_read_single('vara', 'bonder', 'email', email)
        msgN = str(name) + " was attacked, but is alright."
        msgM = str(name) + """ was attacked, but is alright.\n
        Location of this attack can be seen at:\n
        https://maps.google.no/?q=loc:%s,%s+(This+is+where+%s+was+attacked)&z=19&output=embed
        """ % (str(lat), str(lng), str(name))
        level = "3"
        death = randint(0,5)
        if(death == 2):
            msgN = str(name) + " was attacked and killed!"
            msgM = str(name) + """ was attacked and killed!/n 
            Location of this attack can be seen at:\n  
            https://maps.google.no/?q=loc:%s,%s+(This+is+where+%s+was+attacked+and+killed&z=19&output=embed
            """ % (str(lat), str(lng), str(name))
            level = "4"
        
        #Mail to owner
        mailer(str(email), 'no-replay@sheepfarmer3000.no', 'Sheep Attack[' + str(name) + ']', str(msgM))
        #Mail to vara
        if(vara != None and vara != ""):
            mailer(str(vara), 'no-replay@sheepfarmer3000.no', 'Sheep Attack['+str(name)+']', str(msgM))
        db_insert_not(owner, sheepid, level, msgN, lat, lng)
        ## TODO: insert dead=1 in sheep.



