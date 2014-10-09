"""Defines helper functions to parse received messages. It is also responsible
for (safely) reading and writing to the data storer using datastorer helper
functions"""

import payment_messages as msg
import payment_model as model
import datastore_helper as ds

# Return codes
RET_CODE_OK = 0
RET_CODE_GENERIC_ERROR = 1

RET_CODE_DUPLICATE_TID = 2
RET_CODE_SPURIOUS = 3

# Strings associated with return codes
returnStrings = ["Ok", "Generic Error", "Duplicated ID for the same transaction",
                 "Unrecognized transaction ID"]

def parsePaymentRequestMerchant(paymentDetails):
    # Get the model object from the GCE message object
    detailModel = model.PaymentDetail(paymentDetails)
    transactionInfo = ds.retrieveTransactionInfo(detailModel.transId)
    # Check if the corresponding transaction has alredy been stored
    # if not we need to create a new transaction
    if transactionInfo == None:
        retCode = RET_CODE_OK
        return [retCode, returnStrings[retCode]]
    else:
        # if it is alredy present one of the 3 following things can be happened
        # 1 - We have received the customer request for the same transaction
        
        # 2 - We have received a second request for the same transaction
        
        # 3 - We have received a 'spurious' transaction id (either error or attack)
        pass
        
    # Something unexpected happened and therefore we return a generic error
    return [RET_CODE_GENERIC_ERROR, returnStrings[RET_CODE_GENERIC_ERROR]]
