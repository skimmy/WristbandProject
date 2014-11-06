#!/usr/bin/python

import logging

def v(tag, message):
    logging.info("[%s] - %s", tag, message)

def d(tag, message):
    logging.debug("[%s] - %s", tag, message)

def e(tag, message):
    logging.error("[%s] - %s", tag, message)

def w(tag, message):
    logging.warning("[%s] - %s", tag, message)
