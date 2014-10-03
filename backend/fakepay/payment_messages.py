"""This module contains all the messages definition to be used by Google Cloud Endpoints service"""

from protorpc import messages

"""This is the message sent back by the backend service as reply of (any) recieved
request."""
class ReplyMessage(messages.Message):
    # The return code is the only required field
    code = messages.IntegerField(1, required=True)    
    text = messages.StringField(2)
    transactionId = messages.StringField(3)
    # This is a fake challange
    challange = messages.StringField(4)

"""This message contains details about the payment"""
class PaymentDetailMessage(messages.Message):
    wearId = messages.StringField(1, required=True)
    gateId = messages.StringField(2, required=True)
    transactionId = messages.StringField(3, required=True)
    amount = messages.FloatField(4, required=True)
    purchaseType = messages.IntegerField(5, required=True)
    
