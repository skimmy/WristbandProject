"""FakePay API using Google Cloud Endpoints (GCE)

Written by Michele Schimd, 10/03/2014
"""

import endpoints

from protorpc import messages
from protorpc import message_types
from protorpc import remote

import payment_messages as msg
import request_parser as parser

package ='FakePay'

class Greeting(messages.Message):
    message = messages.StringField(1)

class GreetingCollection(messages.Message):
    items = messages.MessageField(Greeting, 1, repeated=True)

STORED_GREETINGS = GreetingCollection(items=[
        Greeting(message='Welcome on FakePay'),
        Greeting(message='Goodbye from FakePay')
])

@endpoints.api(name='hellofakepay', version="v1")
class HelloFakePayAPI(remote.Service):
    """Hello FakePay API v1"""
    
    @endpoints.method(message_types.VoidMessage, GreetingCollection,
                      path="hello", http_method="GET",
                      name="greetings.listGreeting")
    def greetings_list(self, unused_request):
        return STORED_GREETINGS

    ID_RESOURCE = endpoints.ResourceContainer(
        message_types.VoidMessage,
        id=messages.IntegerField(1, variant=messages.Variant.INT32))

    @endpoints.method(ID_RESOURCE, Greeting,
                      path="hello/{id}", http_method="GET",
                      name="greetings.getGreeting")
    def greeting_get(self, request):
        try:
            return STORED_GREETINGS.items[request.id]
        except(IndexError, TypeError):
            raise endpoints.NotFoundException("Greeting %s not found." % (request.id))

"""This is the main service API for the payment system. It is developed to conform
to the custom payment protocol designed for the WristbandProject."""
@endpoints.api(name="paymentremote", version="0.1")
class PaymentRemoteService(remote.Service):
    # PaymentRequestMerchant: the merchante (i.e. gate) issues a pyment to the 
    #    authority (i.e. the backend payment service, a.k.a. this service)
    @endpoints.method(msg.PaymentDetailMessage, msg.PaymentAuthorizedMerchantMessage,
                      path="payreqmerch", http_method="GET",
                      name="paymentrequestmerchant")
    def payment_request_merchant(self, request):
        # upon receiving a message we should
        # 1. Check message validity (ids, amount, ...)
        returnCode,  returnText = parser.parsePaymentRequestMerchant(request)
        replyInfo = msg.ReplyInfoMessage(code=returnCode, text=returnText)
        if returnCode == parser.RET_CODE_TRANS_FOUND:
            pass
        elif returnCode == parser.RET_CODE_TRANS_NOT_FOUND:
            pass
        elif returnCode == parser.RET_CODE_GENERIC_ERROR:
            pass
        # 2a. create a new transaction or...
        replyMsg = msg.PaymentAuthorizedMerchantMessage(
            details=request, authorized=True, info=replyInfo)
        # 2b. ...retrieve if it is already activated (but this may be an error!)

        # 3. Store the new transaction

        # 4. Create the reply
        return replyMsg

# run all the services
APPLICATION = endpoints.api_server([HelloFakePayAPI, PaymentRemoteService])
