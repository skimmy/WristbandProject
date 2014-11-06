"""This module contains helper functions to access the datastorer. Note that the
integrity of data is here enforced only at low level, all application details
about data integrity and consisency should not be implemented in helper functions"""

from google.appengine.ext import ndb

import util.logutil as Log

import datastore_model as dsm

TAG="DS_HELPER"

# This is the name of the current database. Note that on the same datastore we
# may have different databases (i.e. different names) to identify different
# contexts of the same application (e.g. different payment circuits, ...)
CURRENT_DATABASE_NAME = "default-db"

# this is the name of the parent of all transactions in the datastore
TRANSACTIONS_PARENT_NAME = "transactions"

def transactionAncestor():
    ndb.Key(TRANSACTIONS_PARENT_NAME, CURRENT_DATABASE_NAME)


def blocking_entity_group_query():
    pass

def retrieveTransactionInfo(tid):
    Log.v(TAG, "Retrieve for id " + tid)
    # retrieve object from datastore
    tAncestor = transactionAncestor()
    storedEntities = dsm.PaymentDetailDatastore.query(
        dsm.PaymentDetailDatastore.transactionId == tid,
        ancestor=tAncestor).iter()
    # Based on the number of entities retrieved from the storer different behavior
    # may be defined. For simplicity we ignore now the possibility of multiple
    # entries (this should, in principle) be avoided even though the concurrency
    # is quite an issue here.
    
    # case 1 - No entry retrieved
    if (not storedEntities.has_next()):
        return None
    # case 2 - At least one enrity retrieved
    else:
        return storedEntities.next()
    

def insertNewTransaction(ndbDetail):
    ndbDetail.parent = transactionAncestor()
    ndbDetail.put()
