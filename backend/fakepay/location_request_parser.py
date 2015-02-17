import datastore_model as dsm
import datastore_helper as dsh
import location_model as model
import gcm_helper as gcm


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
    # send message to the tutor
    gcm.gcm_send_location(locModel)


def parseRegisterTutorMessage(msg):
    # create the model...
    dsModel = dsm.RegisteredTutor(
        tutid = msg.tutid,
        wbid = msg.wbid,
        regid = msg.gcmRegId
        )
    # ...and store id
    dsh.storeRegIdForWB(dsModel)
