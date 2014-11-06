#!/usr/bin/python

V_TAG="VERB     "

def logline(level, tag, message):
    print "%s[%s] - %s" % (level, tag, message)

def verbose(tag, message):
    logline(V_TAG, tag, message)

def v(tag, message):
    verbose(tag, message)

