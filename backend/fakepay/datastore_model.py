"""This module contains the model objects to be used for storing and retrieving data in the Google App Engine datastore."""

from google.appengine.ext import ndb


"""Class used for storing payment details on the GAE datastore
"""
class PaymentDetailDatastore(ndb.Model):
    transactionId = ndb.StringProperty()
    gateId = ndb.StringProperty()
    wearId = ndb.StringProperty()
    amount = ndb.FloatProperty()
    purchaseType = ndb.IntegerProperty()
    transactionState = ndb.IntegerProperty()


class LocationDatastore(ndb.Model):
    wbid = ndb.StringProperty()
    longitude = ndb.FloatProperty()
    latitude = ndb.FloatProperty()
    accuracy = ndb.FloatProperty()
    timestamp = ndb.DateTimeProperty(auto_now=True)


class RegisteredTutor(ndb.Model):
    tutid = ndb.StringProperty()
    wbid = ndb.StringProperty()
    regid = ndb.StringProperty()
