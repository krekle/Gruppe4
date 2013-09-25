#!/usr/local/bin/python

import MySQLdb as mdb
from random import randint

con = mdb.connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')
cur = con.cursor()

cur.execute("SELECT id FROM sauer")
sheep = cur.fetchall()

for she in sheep:
    shee = str(she[0])
    query = "UPDATE `sauer` SET `hr`=%s, `lat`=%s, `long`=%s WHERE `id`=%s;"
    #Ca midt i Trondheim
    cur.execute(query, ((randint(120, 210)) , ("63." + str(randint(400000, 500000))) , ("10." + str(randint(400000,500000))), shee ))

con.commit()
