#!/usr/bin/python3

import mysql.connector
import sys

class bddUtilisateur:

    def __init__(self):
        self.logs=''
        self.connexion=mysql.connector.connect(user='tdlog', password='projetdumythe', database='contacts', host='localhost')
        self.curseur=self.connexion.cursor()

    def nouveau(self, nom, prenom, telephone):
        query="INSERT INTO repertoire_final (nom, prenom, telephone, positionX, positionY, tempsPosition, contactsIds) VALUES ('"+nom+"', '"+prenom+"', '"+telephone+"', 0, 0, NOW(), '')"
        self.curseur.execute(query)
        self.connexion.close()
        return 'nouveauClientOk'


    def actualisePosition(self, clientId, clientPositionX, clientPositionY):
        query="UPDATE repertoire_final SET positionX ="+str(float(clientPositionX))+" WHERE id="+str(clientId)
        self.curseur.execute(query)
        query="UPDATE repertoire_final SET positionY ="+str(float(clientPositionY))+" WHERE id="+str(clientId)
        self.curseur.execute(query)
        self.connexion.close()
        return 'actualisePosition OK'
