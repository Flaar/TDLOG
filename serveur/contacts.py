#!/usr/bin/python3

import MySQLdb as mdb
import sys

class contacts:
    def __init__(self):
        self.logs=''
        try:
            self.connnexion=mdb.connect(read_default_file="dbId.conf");
            self.curseur=self.connexion.cursor()

    def actualisePositionClient(self, clientId, clientPosition):
        query="UPDATE contacts SET position '"+clientPosition+"' WHERE id="+clientId
        self.curseur.execute(query)
        self.connexion.close()
        self.logs=self.logs+'actualisePosition OK'

    def actualiseListe(self, clientId, contactsNums):
        contactsNums=self.formatNumeros(contactsNums)
        contactsIds=[]
        for compteur in range(len(contactsNums)):
            num=contactsNums[compteur]
            query="SELECT id FROM contacts WHERE num="+num
            self.curseur.execute(query)
            contactId=self.curseur.fetchall()
            if len(contactId)>0:
                contactsIds.append(int(contactId))
            elif len(contactsId)==0:
                self.logs=self.logs+str(num)+" pas dans DB"
            else:
                self.logs=self.logs+"erreur DB"
##        query="SELECT contactsIds FROM contacts WHERE id="+clientId
##        self.curseur.execute(query)
##        ancienContactsIds=self.curseur.fetchall()
##        contactsIds=ancienContactsId+contactsIds
        query="UPDATE contacts SET contactsIds '"+contactsIds+"' WHERE id="+clientId
        self.logs=self.logs+'actualiseContacts OK'
        self.connexion.close()
        return contactsIds

    def position(self, clientId, rayon)
        query="SELECT contactsIds FROM contacts WHERE id="+clientId
        self.curseur.execute(query)
        contactsIds=self.curseur.fetchall()
        contactsPosition=[]
        for contactId in contactsIds:
            query="SELECT position FROM contacts WHERE id="+contactId
            self.curseur.execute(query)
            contactsPositions.append(self.curseur.fetchall())
        self.connexion.close()
        return (contactsIds, contactsPositions)
    
            
