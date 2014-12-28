#!/usr/bin/python3

import MySQLdb as mdb
import sys

try:
    connnexion=mdb.connect('localhost', 'tdlog', 'motdepasse', 'evenements');
    
