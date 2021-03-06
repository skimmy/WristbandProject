import datastore_model as dsm
import datastore_helper as dsh
import location_model as model
import gcm_helper as gcm

import fencing


TAG = "LocationRequestParser"

def parseLocationUpdateMessage(msg):
    # create model from message
    locModel = model.WristbandLocation(msg)

    # Retrieve last location for WBID from the DS
    wbid = locModel.wbid
    wblocation = dsh.readWBLocation(wbid)

    # refresh the location
    if wblocation == None:
        wblocation = locModel.toNdbModel()
    else:
        wblocation.latitude = locModel.latitude
        wblocation.longitude = locModel.longitude
        wblocation.accuracy = locModel.accuracy
    # store back the refreshed location on the DS
    dsh.writeWBLocation(wblocation)

    # check if new location is outside fences
    # 1. retieve the fences for current wb
    fences = dsh.getFencesForWristband(wbid)
    # 2. check if new location violate fences
    violatedFences = []
    if (fences != None):
        violatedFences = fencing.checkFences(wblocation, fences)
    # 3. generate alarms
    #    alarms = [str(fences[i]) for i in violatedFences]
    alarms = str(len(violatedFences))
    # retrieve all registration ids for the current wristband
    regIds = dsh.regIdsForWB(wbid)
    ids = [reg.regid for reg in regIds]
    print (ids)
    # send message to the tutor
    gcm.gcm_send_location(locModel, ids, alarms)


def parseRegisterTutorMessage(msg):
    # create the model...
    dsModel = dsm.RegisteredTutor(
        tutid = msg.tutid,
        wbid = msg.wbid,
        regid = msg.gcmRegId
        )
    # ...and store id
    dsh.storeRegIdForWB(dsModel)

def parseAddFenceMessage(msg):
    dsModel = dsm.WristbandFence(
        wbid = msg.wbid,
        longitude = msg.longitude,
        latitude = msg.latitude,
        radius = msg.radius
        )
    dsh.addFenceToWristband(dsModel)
