"""FakePay API using Google Cloud Endpoints (GCE)

Written by Michele Schimd, 10/03/2014
"""

import endpoints

from protorpc import messages
from protorpc import message_types
from protorpc import remote

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

APPLICATION = endpoints.api_server([HelloFakePayAPI])
