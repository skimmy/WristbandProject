"""This module contains all the messages definition to be used by Google Cloud Endpoints service"""

from protorpc import messages

class ReplyInfoMessage(messages.Message):
    """This is the message that should be contained in every reply message. """
    # The return code is the only required field
    code = messages.IntegerField(1, required=True)    
    text = messages.StringField(2)

class PaymentDetailMessage(messages.Message):
    """This message contains details about the payment"""
    wearId = messages.StringField(1, required=True)
    gateId = messages.StringField(2, required=True)
    transactionId = messages.StringField(3, required=True)
    amount = messages.FloatField(4, required=True)
    purchaseType = messages.IntegerField(5, required=True)
    
class PaymentAuthorizedMerchantMessage(messages.Message):
    """This is the message returned to the client once the payment request merchant request has been parsed"""
    details = messages.MessageField(PaymentDetailMessage, 1, required=True)
    authorized = messages.BooleanField(2, required=True)
    info = messages.MessageField(ReplyInfoMessage, 3)
