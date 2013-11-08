from mailtest import sendmail as mail


toaddr = str(raw_input("recipient: "))
fromaddr = "no-replay@sheepfarmer3000.com"
subject = str(raw_input("subject: "))
msg = str(raw_input("msg: "))

mail(toaddr, fromaddr, subject, msg)


