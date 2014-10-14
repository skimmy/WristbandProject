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
