"""Defines helper functions to parse received messages. It is also responsible
for (safely) reading and writing to the data storer using datastorer helper
functions"""

import payment_messages as msg
import payment_model as model
import datastore_helper as ds

# Return codes
RET_CODE_OK = 0
RET_CODE_GENERIC_ERROR = 1

RET_CODE_TRANS_FOUND = 2
RET_CODE_TRANS_NOT_FOUND = 3

# Strings associated with return codes
returnStrings = ["Ok", "Generic Error", "Found transaction",
                 "Transaction not found"]

def createNewTransaction(paymentDetails):
    """Creates a new transaction to be inserted in the datastore"""
    detailModel = model.PaymentDetail(paymentDetails)
    ds.insertNewTransaction(detailModel.toNdbModel())  
    

def parsePaymentRequestMerchant(paymentDetails):
    # Get the model object from the GCE message object
    detailModel = model.PaymentDetail(paymentDetails)
    transactionInfo = ds.retrieveTransactionInfo(detailModel.transId)
    # Check if the corresponding transaction has alredy been stored
    # if not we need to create a new transaction
    if transactionInfo == None:
        # register the merchant request and insert the a new transaction
        detailModel.transState = model.TS_MERCH_REQUEST
        transactionInfo = detailModel.toNdbModel()
        ds.insertNewTransaction(transactionInfo)
        retCode = RET_CODE_TRANS_NOT_FOUND
        return [retCode, returnStrings[retCode], detailModel]
    else:        
        retCode = RET_CODE_TRANS_FOUND        
        return [retCode, returnStrings[retCode], model.PaymentDetail.fromNdbModel(transactionInfo)]
    # Something unexpected happened and therefore we return a generic error
    return [RET_CODE_GENERIC_ERROR, returnStrings[RET_CODE_GENERIC_ERROR]]

def parseTransactionInfo(transactionId):
    # basic (default) initializations
    retCode = RET_CODE_OK
    retDetails = None

    # get (if any) the transaction information from the datastore
    transactionInfo = ds.retrieveTransactionInfo(transactionId.tid)
    if (transactionInfo == None):
        retCode = RET_CODE_TRANS_NOT_FOUND
    else:
        retCode = RET_CODE_TRANS_FOUND
        retDetails = (model.PaymentDetail.fromNdbModel(transactionInfo)).toMessage()
        transState = transactionInfo.transactionState

    return msg.ReplyInfoMessage(code=retCode, text=returnStrings[retCode], 
                                details=retDetails, state=transState)
