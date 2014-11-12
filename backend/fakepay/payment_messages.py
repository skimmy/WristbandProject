"""This module contains all the messages definition to be used by Google Cloud Endpoints service"""

from protorpc import messages

class PaymentDetailMessage(messages.Message):
    """This message contains details about the payment"""
    wearId = messages.StringField(1, required=True)
    gateId = messages.StringField(2, required=True)
    transactionId = messages.StringField(3, required=True)
    amount = messages.FloatField(4, required=True)
    purchaseType = messages.IntegerField(5, required=True)

class TransactionIdMessage(messages.Message):
    """This message is used to query the service for a specific transaction"""
    tid = messages.StringField(1, required=True)

class ReplyInfoMessage(messages.Message):
    """This is the message that should be contained in every reply message. """
    # The return code is the only required field
    code = messages.IntegerField(1, required=True)    
    text = messages.StringField(2)
    details = messages.MessageField(PaymentDetailMessage, 3)
    state = messages.IntegerField(4)
   
class PaymentRequestMerchantMessage(messages.Message):
    transactionId = messages.StringField(1, required=True)
    senderId = messages.StringField(2)
    receiverId = messages.StringField(3)
    details = messages.MessageField(PaymentDetailMessage, 4, required=True)

class PaymentRequestCustomerMessage(messages.Message):
    transactionId = messages.StringField(1, required=True)
    senderId = messages.StringField(2)
    receiverId = messages.StringField(3)
    details = messages.MessageField(PaymentDetailMessage, 4, required=True)

class PaymentAuthorizedMerchantMessage(messages.Message):
    """This is the message returned to the client once the payment request merchant request has been parsed"""
    details = messages.MessageField(PaymentDetailMessage, 1, required=True)
    authorized = messages.BooleanField(2, required=True)
    info = messages.MessageField(ReplyInfoMessage, 3)

class PaymentAuthorizedCustomerMessage(messages.Message):
    """This is the message returned to the client once the payment request customer request has been parsed"""
    details = messages.MessageField(PaymentDetailMessage, 1, required=True)
    authorized = messages.BooleanField(2, required=True)
    info = messages.MessageField(ReplyInfoMessage, 3)    
        
