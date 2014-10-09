"""This module contains abstract model classes for each of the objects exchanged between different parts of the distributed application (i.e. web service, client, ...). The models are used to represent in an abstract way the concepts and to supply helper functions to translate into different data types (i.e. datastore objects, endpoints messages, ..."""

import payment_messages as msg
import datastore_model as ds

PURCHASE_TYPE_GENERIC = 0
PURCHASE_TYPE_FOOD = 1
PURCHASE_TYPE_SERVICE = 2

class PaymentDetail(object):
    def __init__(self):
        self.transId = ""
        self.gateId = ""
        self.wearId = ""
        self.amount = 0.0
        self.purchaseType = PURCHASE_TYPE_GENERIC

    def __init__(self, msg):
        self.transId = msg.transactionId
        self.wearId = msg.wearId
        self.gateId = msg.gateId
        self.amount = msg.amount
        self.purchaseType = msg.purchaseType

    def toMessage(self):
        msg.PaymentDetailMessage(
            wearId = self.wearId,
            gateId = self.gateId,
            transactionId = self.transId,
            amount = self.amount,
            purchaseType = self.purchaseType
            )

    def toNdbModel(self):
        ds.PaymentDetailDatastore(
            transactionId = self.transId,
            wearId = self.wearId,
            gateId = self. gateId,
            amount = self.amount,
            purchaseType = self.purchaseType
            )
        
