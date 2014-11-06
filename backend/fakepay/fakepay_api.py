"""FakePay API using Google Cloud Endpoints (GCE)

Written by Michele Schimd, 10/03/2014
"""

import endpoints

from protorpc import messages
from protorpc import message_types
from protorpc import remote

import payment_messages as msg
import request_parser as parser

import util.logutil as Log

package ='FakePay'

HTTP_DEFAULT_METHOD="GET"

TAG="ROOT"

"""This is the main service API for the payment system. It is developed to conform
to the custom payment protocol designed for the WristbandProject."""
@endpoints.api(name="paymentremote", version="0.1")
class PaymentRemoteService(remote.Service):
    
    # ---------------------------------- PAYMENT REQUEST MERCHANT ----------------------------------
    # PaymentRequestMerchant: the merchante (i.e. gate) issues a pyment to the 
    #    authority (i.e. the backend payment service, a.k.a. this service)
    @endpoints.method(msg.PaymentRequestMerchantMessage, msg.PaymentAuthorizedMerchantMessage,
                      path="payreqmerch", http_method=HTTP_DEFAULT_METHOD,
                      name="paymentrequestmerchant")
    def payment_request_merchant(self, request):
        # upon receiving a message we should
        # 1. Check message validity (ids, amount, ...)
        [returnCode, returnText, newDetails]  = parser.parsePaymentRequestMerchant(request.details)
        replyInfo = msg.ReplyInfoMessage(code=returnCode, text=returnText)
        if returnCode == parser.RET_CODE_TRANS_FOUND:
            pass
        elif returnCode == parser.RET_CODE_TRANS_NOT_FOUND:
            pass
        elif returnCode == parser.RET_CODE_GENERIC_ERROR:
            pass
        # 2a. create a new transaction or...
        replyMsg = msg.PaymentAuthorizedMerchantMessage(
            details=request.details, authorized=True, info=replyInfo)
        # 2b. ...retrieve if it is already activated (but this may be an error!)

        # 3. Store the new transaction

        # 4. Create the reply
        return replyMsg

    
    # ----------------------------------- TRANSACTION INFORMATION ----------------------------------
    # TransactionInfo: returns all information about transaction such as: id, amount, gate,
    #    wear, ...) be carefull that this method may be exploited to perform malicious attakcs
    #    so it should not be used to transfer important information (like challenges, identities,
    #   ...). The main purpose of this method is to give remote devices an way of querying remote
    #   authority service about the current state of a transaction (e.g. has been authorized, is
    #   completed, ...)
    @endpoints.method(msg.TransactionIdMessage, msg.ReplyInfoMessage,
                      path="transinfo", http_method=HTTP_DEFAULT_METHOD,
                      name="transactioninformation")
    def transaction_info(self, request):
        Log.v(TAG, "TransactionInfo (" + str(request.tid) + ")")
        # Just call the parser and return what it returns
        # Here we can possibly log some information about the calls whenever they will be needed
        replyInfo = parser.parseTransactionInfo(request)
        return replyInfo
    

# run all the services
APPLICATION = endpoints.api_server([PaymentRemoteService])
