#!/usr/local/bin/python

import MySQLdb as mdb
from random import randint

con = mdb.connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')
cur = con.cursor()

cur.execute("SELECT * FROM sauer")
sheep = cur.fetchall()

for she in sheep:
    shee = str(she[0])
    dead = str(she[3])

    query = "UPDATE `sauer` SET `hr`=%s, `lat`=%s, `long`=%s, `temp`=%s, `respiration`=%s  WHERE `id`=%s;"
    #Ca midt i Trondheimi
    if(dead != 1):
        cur.execute(query, ((randint(60, 110)) , ("63." + str(randint(400000, 500000))) , ("10." + str(randint(400000,500000))), (38+randint(0,2)), (10+randint(2,8)) , shee ))

con.commit()

