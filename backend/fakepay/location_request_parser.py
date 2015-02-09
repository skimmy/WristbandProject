import datastore_model as dsm
import datastore_helper as dsh
import location_messages as msg

def parseLocationUpdateMessage(msg):
    # Retrieve last location for WBID from the DS
    wbid = msg.wbid
    # refresh the location
    wblocation = dsh.readWBLocation(wbid)
    if wblocation == None:
        pass
    # store back the refreshed location on the DS

    # send message to the tutor

