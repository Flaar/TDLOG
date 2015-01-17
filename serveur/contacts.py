#!/usr/bin/python3

import mysql.connector
import sys

#La classe contacts utilise la table SQL définie par ces contraintes :
#CREATE TABLE repertoire_final (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, telephone VARCHAR(12), nom VARCHAR(255), prenom VARCHAR(255), positionX FLOAT, positionY FLOAT, datePosition DATE, tempsPosition TIME, contactsIds TEXT,CONSTRAINT numero_unique UNIQUE (telephone) )

def split(chaine):
    liste=chaine.split()
    resultat=[]
    for compteur in range(len(liste)):
        resultat.append(int(liste[compteur]))
    return resultat

def concatene(liste):
    resultat=str(liste[0])
    for compteur in range(len(liste)-1):
        resultat+' '+str(liste(compteur))
    return resultat


def formatNumeros(numeros):
    return numeros

    


class contacts:
    def __init__(self):
        self.logs=''
        try:
            self.connnexion=mysql.connector.connect(user='root', password='projetdumythe', database='contacts', host='localhost');
            self.curseur=self.connexion.cursor()

    def actualiseListe(self, clientId, contactsNums):
        contactsNums=formatNumeros(contactsNums)
        contactsIds=[]
        for compteur in range(len(contactsNums)):
            num=contactsNums[compteur]
            query="SELECT id FROM contacts WHERE telephone="+num
            self.curseur.execute(query)
            contactId=self.curseur.fetchone()
            curseur.fetchall()
            contactId=contactId[0]
            if len(contactId)>0:
                contactsIds.append(int(contactId))
            elif len(contactsId)==0:
                self.logs=self.logs+str(num)+" pas dans DB"
            else:
                self.logs=self.logs+"erreur DB"
        query="SELECT contactsIds FROM contacts WHERE id="+clientId
        self.curseur.execute(query)
        ancienContactsIds=self.curseur.fetchone()[0]
        ancienContactsIds=split(ancienContactsIds)
        contactsIds=ancienContactsId+contactsIds
        contactsIds=concatene(contactsIds)
        query="UPDATE contacts SET contactsIds '"+contactsIds+"' WHERE id="+clientId
        self.logs=self.logs+'actualiseContacts OK'
        self.connexion.close()
        return contactsIds

    def position(self, clientId, rayon)
        query="SELECT contactsIds FROM contacts WHERE id="+clientId
        self.curseur.execute(query)
        contactsIds=self.curseur.fetchone()
        contactsIds=contactsIds[0]
        contactsIds=split(contactsIds)
        query="SELECT positionX FROM contacts WHERE id="+contactId
        self.curseur.execute(query)
        ClientPositionX=self.curseur.fetchone()[0]
        query="SELECT positionY FROM contacts WHERE id="+contactId
        self.curseur.execute(query)
        ClientPositionY=self.curseur.fetchone()[0]
        contactsPositionsX=[]
        contactsPositionsY=[]
        contactsProchesIds=[]
        for contactId in contactsIds:
            query="SELECT positionX FROM contacts WHERE id="+contactId
            self.curseur.execute(query)
            PositionX=self.curseur.fetchone()[0]
            query="SELECT positionY FROM contacts WHERE id="+contactId
            self.curseur.execute(query)
            PositionY=self.curseur.fetchone()[0]
            if clientPositionX-rayon<positionX and positionX<clientPositionX+rayon and clientPositionY-rayon<positionY and positionY<clientPositionY+rayon:
                contactsPositionsX.append(positionX)
                contactsPositionsY.append(positionY)
                contactsProchesIds.append(contactId)  
        self.connexion.close()
        return (contactsIds, contactsPositionsX, contactsPositionsY)
    
            
