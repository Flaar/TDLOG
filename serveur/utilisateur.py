#!/usr/bin/python3

import mysql.connector
import sys

class utilisateur:

    def __init__(self):
        self.logs=''
        try:
            self.connnexion=mysql.connector.connect(user='tdlog', password='projetdumythe', database='contacts', host='localhost');
            self.curseur=self.connexion.cursor()

    def nouveau(self, nom, prenom, telephone)
        query="INSERT INTO contacts (nom, prenom, telephone, positionX, positionY, tempsPosition, contactsIds) VALUES ('"+nom+"', '"+prenom+"', '"+telephone+"', 0, 0, NOW(), '')"
        self.curseur.execute(query)
        self.connexion.close()
        return 'nouveauClientOk'


    def actualisePosition(self, clientId, clientPositionX, clientPositionY):
        query="UPDATE contacts SET positionX ="+float(clientPositionX)+" WHERE id="+clientId
        self.curseur.execute(query)
        query="UPDATE contacts SET positionY ="+float(clientPositionY)+" WHERE id="+clientId
        self.curseur.execute(query)
        self.connexion.close()
        return 'actualisePosition OK'
