#!/usr/bin/python3

import mysql.connector
import sys

class bddUtilisateur:

    def __init__(self):
        self.logs=''
        self.connexion=mysql.connector.connect(user='tdlog', database='contacts', host='localhost')
        self.curseur=self.connexion.cursor()

    def nouveau(self, nom, prenom, telephone):
        query="INSERT INTO repertoire_final (nom, prenom, telephone, positionX, positionY, tempsPosition, datePosition, contactsIds, eventsIds) VALUES ('"+nom+"', '"+prenom+"', '"+telephone+"', 0, 0, NOW(), NOW(), '', '12')"
        self.curseur.execute(query)
        self.connexion.commit()
        query="SELECT id from repertoire_final WHERE telephone = '"+telephone+"'"
        self.curseur.execute(query)
        clientId=int(self.curseur.fetchone()[0])
        self.connexion.close()
        return '1\n'+'nouveauClientOk'+'\n'+str(clientId)

    def test(self,clientId):
        query="SELECT nom from repertoire_final WHERE id = "+str(clientId)
        self.curseur.execute(query)
        reponse=self.curseur.fetchone()[0]
        return '1\n'+reponse


    def actualisePosition(self, clientId, clientPositionX, clientPositionY):
        query="UPDATE repertoire_final SET positionX ="+str(float(clientPositionX))+" WHERE id="+str(clientId)
        self.curseur.execute(query)
        query="UPDATE repertoire_final SET positionY ="+str(float(clientPositionY))+" WHERE id="+str(clientId)
        self.curseur.execute(query)
        self.connexion.commit()
        self.connexion.close()
        return '1\n'+'actualisePosition OK'
