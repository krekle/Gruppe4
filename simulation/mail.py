import smtplib
import email.utils
from email.mime.text import MIMEText

# Create the message

def sendmail(toaddr, fromaddr, subject, msg):
    msg = MIMEText(msg)
    msg['To'] = email.utils.formataddr(('Recipient', toaddr))
    msg['From'] = email.utils.formataddr(('no-reply@sheepfarmer3000.no', fromaddr))
    msg['Subject'] = subject

    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.ehlo()
    server.starttls()
    server.ehlo()
    server.login('sheepfarmer3000@gmail.com', "dolly123")
    server.set_debuglevel(True) # show communication with the server
    try:
        server.sendmail('sheepfarmer3000@gmail.com', toaddr, msg.as_string())
    finally:
        server.quit()
