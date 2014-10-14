"""This module contains abstract model classes for each of the objects exchanged between different parts of the distributed application (i.e. web service, client, ...). The models are used to represent in an abstract way the concepts and to supply helper functions to translate into different data types (i.e. datastore objects, endpoints messages, ..."""

import payment_messages as msg
import datastore_model as ds

# constants describing purchase type
PURCHASE_TYPE_GENERIC = 0
PURCHASE_TYPE_FOOD = 1
PURCHASE_TYPE_SERVICE = 2

# constants defininf states for the transactions
TS_UNKNOWN = 0
TS_MERCH_REQUEST = 11
TS_CUST_REQUEST = 12
TS_FULL_REQUEST = 10
TS_MERCH_AUTH = 21
TS_CUST_AUTH = 22
TS_FULL_AUTH = 20
TS_MERCH_CONFIRM = 31
TS_CUST_COONFIRM = 32
TS_CONFIRMED = 30

class PaymentDetail(object):

    def __init__(self, msg = None):
        if msg == None:
                    self.transId = ""
                    self.gateId = ""
                    self.wearId = ""
                    self.amount = 0.0
                    self.purchaseType = PURCHASE_TYPE_GENERIC
                    self.transState = TS_UNKNOWN
        else:
            self.transId = msg.transactionId
            self.wearId = msg.wearId
            self.gateId = msg.gateId
            self.amount = msg.amount
            self.purchaseType = msg.purchaseType

    def toMessage(self):
        return msg.PaymentDetailMessage(
            wearId = self.wearId,
            gateId = self.gateId,
            transactionId = self.transId,
            amount = self.amount,
            purchaseType = self.purchaseType
            )

    def toNdbModel(self):
        return ds.PaymentDetailDatastore(
            transactionId = self.transId,
            wearId = self.wearId,
            gateId = self. gateId,
            amount = self.amount,
            purchaseType = self.purchaseType,
            transactionState = self.transState
            )
        
    @staticmethod
    def fromNdbModel(ndbModel):
        detail = PaymentDetail()
        detail.transId = ndbModel.transactionId,
        detail.wearId = ndbModel.wearId,
        detail.gateId = ndbModel.gateId,
        detail.amount = ndbModel.amount,
        detail.purchaseType = ndbModel.purchaseType,
        detail.transState = ndbModel.transactionState
        return detail
            
