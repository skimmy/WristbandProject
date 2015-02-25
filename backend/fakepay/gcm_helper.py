"""This package contais helper functions to interact with Cloud Connection Server
(CCS) used for interacting with devices via Goggle Cloud Messaging (GCM)"""

import urllib2
import json

# address of CCS
CCS_SERVER = "gcm-preprod.googleapis.com"
CCS_PORT = 5236
# URL of HTTP GCM server
HTTP_SERVER_URL = "https://android.googleapis.com/gcm/send"

USERNAME = "WristbandProject.Backend"
API_KEY = "AIzaSyC22aO3e5BzTTzXZZycXuORDtOBJpH0_m4"
REGISTRATION_ID = "WristbandProject.Tutor"
# "gcm.googleapis.com:5235" <-- Production address

def getHttpRequestHeader():
    hds = {
        'Authorization': 'key=' + API_KEY,
        'Content-Type': 'application/json'
        }
    return hds

def getHttpRequestData(locModel, regIds, alarms):
    data = {
#    "registration_ids" : ("[ " + (", ".join(regIds)) + " ]")
        "registration_ids" :  regIds,
        "data" : {
            "content" : "update",
            "latitude" : str(locModel.latitude),
            "longitude" : str(locModel.longitude),
            "alarms" : alarms
        }
     }
    return json.dumps(data)

def sendGcmMessage(header, data):
    req = urllib2.Request(HTTP_SERVER_URL,  data, header)
    print("[DATA] " + str(data))
    try:
        reply = urllib2.urlopen(req)
        print("[REPLY] " + repr(reply.read()))
    except urllib2.URLError, e:
        print("[ERROR] " + repr(e.reason))

def gcm_send_location(locModel, regIds, alarms):
    header = getHttpRequestHeader()
    data = getHttpRequestData(locModel, regIds, alarms)
#    print ("[HEADER] " + str(header))
#    print ("[DATA]   " + str(data))
    sendGcmMessage(header,data)
