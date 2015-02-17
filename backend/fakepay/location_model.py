import datetime

import location_messages as msg
import datastore_model as ds

class WristbandLocation(object):
    def __init__(self, msg = None):
        if msg == None:
            self.wbid = None
            self.latitude = 0.0
            self.longitude = 0.0
            self.accuracy = 0.0
            self.timestamp = None
        else:
            self.wbid = msg.wbid
            self.latitude = msg.latitude
            self.longitude = msg.longitude
            self.accuracy = msg.accuracy
            self.timestamp = None

    def toNdbModel(self):
        ndbModel = ds.LocationDatastore(
            wbid = self.wbid,
            longitude = self.longitude,
            latitude = self.latitude,
            accuracy = self.accuracy )
        return ndbModel

