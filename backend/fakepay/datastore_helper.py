"""This module contains helper functions to access the datastorer. Note that the
integrity of data is here enforced only at low level, all application details
about data integrity and consisency should not be implemented in helper functions"""

import datastore_model as dsm

def blocking_entity_group_query():
    pass

def retrieveTransactionInfo(tid):
    transactionRegistered = False
    dsQueryObj = dsm.PaymentDetailDatastore(transactionId=tid)
    if transactionRegistered:
        pass
    else:
        # no transaction registered with given ID, return None
        return None

