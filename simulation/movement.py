#!/usr/local/bin/python

import MySQLdb as mdb
from random import randint

con = mdb.connect('localhost', 'sauadmin', 'dolly', 'prosjektsau')
cur = con.cursor()
query = """UPDATE `sauer` SET `hr`=%s, `lat`=%s, `long`=%s;"""
#Ca midt i Trondheim
cur.execute(query, ((randint(120, 210)) , ("63." + str(randint(400000, 500000))) , ("10." + str(randint(400000,500000))) ))
con.commit()
