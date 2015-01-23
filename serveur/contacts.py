#!/usr/bin/python3

import mysql.connector
import sys

#La classe repertoire_final  utilise la table SQL définie par ces contraintes :
#CREATE TABLE repertoire_final  (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, telephone VARCHAR(12), nom VARCHAR(255), prenom VARCHAR(255), positionX FLOAT, positionY FLOAT, datePosition DATE, tempsPosition TIME, contactsIds TEXT,CONSTRAINT numero_unique UNIQUE (telephone) )

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

def ligneParLigne(liste):
    retour=''
    for compteur in range(len(liste)-1):
        retour=retour+str(liste)+'\n'
    retour=retour+str(liste(len(liste)-1))
    return retour
   


class bddContacts:
    
    def __init__(self):
        self.logs=''
        self.connexion=mysql.connector.connect(user='tdlog', password='projetdumythe', database='contacts', host='localhost');
        self.curseur=self.connexion.cursor()

    def actualiseListe(self, clientId, contactsNums):
        contactsNums=formatNumeros(contactsNums)
        #Si on nous fournit des numéros de téléphone de nouveaux contacts, on les rentre dans la base de données
        if contactsNums!="None":
            nouveauxContactsIds=[]
            nouveauxContactsNums=[]
            for compteur in range(len(contactsNums)):
                num=contactsNums[compteur]
                query="SELECT id FROM repertoire_final WHERE telephone="+num
                self.curseur.execute(query)
                contactId=self.curseur.fetchone()[0]
                if len(contactId)>0:
                    nouveauxContactsIds.append(int(contactId))
                    nouveauxContactsNums.append(num)
                elif len(contactsId)==0:
                    self.logs=self.logs+str(num)+" pas dans DB "
                else:
                    self.logs=self.logs+str(num)+" erreur DB "
            query="SELECT contactsIds FROM repertoire_final WHERE id="+str(clientId)
            self.curseur.execute(query)
            ancienContactsIds=self.curseur.fetchone()[0]
            ancienContactsIds=split(ancienContactsIds)
            contactsIds=ancienContactsId+nouveauxContactsIds
            contactsIdstexte=concatene(contactsIds)
            query="UPDATE repertoire_final SET contactsIds '"+contactsIdstexte+"' WHERE id="+clientId
            self.curseur.execute(query)
        reponse='actualiseListeOk\n'
        #affichage de la liste des contacts, nom, prenom
        for compteur in range(len(contactsIds)):
            query="SELECT nom, prenom, telephone FROM repertoire_final WHERE id="+contactsIds(compteur)
            self.curseur.execute(query)
            contact=self.curseur.fetchone()
            reponse=reponse+str(contactsIds[compteur])+'\n'+contact[0]+'\n'+contact[1]+'\n'+contact[2]+'\n'
        self.connexion.close()
        return reponse

    def position(self, clientId, rayon):
        query="SELECT contactsIds FROM repertoire_final WHERE id="+clientId
        self.curseur.execute(query)
        contactsIds=self.curseur.fetchone()[0]
        contactsIds=split(contactsIds)
        query="SELECT positionX FROM repertoire_final WHERE id="+contactId
        self.curseur.execute(query)
        ClientPositionX=self.curseur.fetchone()[0]
        query="SELECT positionY FROM repertoire_final WHERE id="+contactId
        self.curseur.execute(query)
        ClientPositionY=self.curseur.fetchone()[0]
        contactsPositionsX=[]
        contactsPositionsY=[]
        contactsProchesIds=[]
        for contactId in contactsIds:
            query="SELECT positionX FROM repertoire_final WHERE id="+contactId
            self.curseur.execute(query)
            PositionX=self.curseur.fetchone()[0]
            query="SELECT positionY FROM repertoire_final WHERE id="+contactId
            self.curseur.execute(query)
            PositionY=self.curseur.fetchone()[0]
            if clientPositionX-rayon<positionX and positionX<clientPositionX+rayon and clientPositionY-rayon<positionY and positionY<clientPositionY+rayon:
                contactsPositionsX.append(positionX)
                contactsPositionsY.append(positionY)
                contactsProchesIds.append(contactId)
        reponse='positionContactsOk\n'
        #affichage de la liste de contacts, nom, prenom
        for compteur in range(len(contactsIds)):
            query="SELECT nom, prenom, telephone FROM repertoire_final WHERE id="+contactsIds[compteur]
            self.curseur.execute(query)
            contact=self.curseur.fetchone()
            reponse=reponse+str(contactsIds[compteur])+'\n'+contact[0]+'\n'+contact[1]+'\n'+contact[2]+'\n'+contactsPositionsX[compteur]+'\n'+contactsPositionY[compteur]+'\n'
        self.connexion.close()
        return reponse

            

