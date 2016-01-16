#Tea Preparer server
#Copyright (C) 2016 ResonantWave
#This program is free software: you can redistribute it and/or modify
#it under the terms of the GNU General Public License as published by
#the Free Software Foundation, either version 3 of the License, or
#(at your option) any later version.
#This program is distributed in the hope that it will be useful,
#but WITHOUT ANY WARRANTY; without even the implied warranty of
#MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#GNU General Public License for more details.
#You should have received a copy of the GNU General Public License
#along with this program.  If not, see <http://www.gnu.org/licenses/>.

from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import RPi.GPIO as GPIO
import time
import cgi

GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.OUT)

def stir():
   global pwm
   for i in range(180): # Move in one direction
      pwm.ChangeDutyCycle(float(i)/10.0 + 2.5) 
      time.sleep(0.01)
   for i in range(180, 0, -1): # Aaaand in the other
      pwm.ChangeDutyCycle(float(i)/10.0 + 2.5)
      time.sleep(0.01)


class MainHandler(BaseHTTPRequestHandler):
   def do_GET(self):
     p = self.path.split("?")
     path = p[0][1:].split("/")
     params = {}

     self.send_response(200)
     self.send_header('Content-type', 'text/html')
     self.end_headers()
     if len(p) > 1:
       params = cgi.parse_qs(p[1], True, True)
       if 'time' in params: # check for 'time' in the GET request
          moveTime = str(params['time']).replace('\'', '').replace('[', '').replace(']', '') # remove the ['']
          if moveTime.isdigit():
             endTime = time.time() + float(moveTime)

             global pwm
             pwm = GPIO.PWM(18, 100)
             pwm.start(5)
             pwm.ChangeDutyCycle(float(0)/10.0 + 2.5) # move to initial position
             while time.time() < endTime:
                stir()
          else:
             self.wfile.write('0')
try:
  server = HTTPServer(('', 8000), MainHandler)
  server.serve_forever()
except KeyboardInterrupt:
  server.socket.close()

