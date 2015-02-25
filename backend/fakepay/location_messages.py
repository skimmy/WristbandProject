"""This module contains all the messages definition to be used by Google Cloud Endpoints service for the remote location"""

from protorpc import messages

class RemoteLocationOkMessage(messages.Message):
    """Message used to aknowledge reception of other messages. This messges is
sent / received by all participants (i.e., wristband, backend and tutor)."""
    tid =  messages.StringField(1, required=True)
    wbid = messages.StringField(2, required=True)
    info = messages.StringField(3)
    

class RemoteLocationUpdateMessage(messages.Message):
    """Message used by wristband to send a location update to the backend"""
    tid = messages.StringField(1, required=True)
    wbid = messages.StringField(2, required=True)
    latitude = messages.FloatField(3, required=True)
    longitude = messages.FloatField(4, required=True)
    accuracy = messages.FloatField(5, default=0.0)

class RemoteLocationRegisterTutor(messages.Message):
    """Message used by the tutor to register for a wristband updates"""
    tutid = messages.StringField(1, required=True)
    wbid = messages.StringField(2, required=True)
    gcmRegId = messages.StringField(3, required=True)

class RemoteLocationWristbandFence(messages.Message):
    """Message used to describe fence for wristbands"""
    wbid = messages.StringField(1, required=True)
    latitude = messages.FloatField(2, required=True)
    longitude = messages.FloatField(3, required=True)
    radius = messages.FloatField(4, required=True)
