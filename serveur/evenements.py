#!/usr/bin/python3

import mysql.connector
import sys

def split(chaine):
    liste=chaine.split()
    resultat=[]
    for compteur in range(len(liste)):
        resultat.append(int(liste[compteur]))
    return resultat

def concatene(set):
    liste=list(set)
    resultat=str(liste[0])
    for compteur in range(len(liste)-1):
        resultat+=' '+str(liste[compteur+1])
    return resultat

#La classe bddEvenements  utilise la table SQL définie par ces contraintes :
#CREATE TABLE evenements(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(255), positionX FLOAT, positionY FLOAT, dateDebut VARCHAR(255), dateFin VARCHAR(255), texte TEXT, contactsIds TEXT, public BOOL)


class bddEvenements:

    def __init__(self):
        self.logs=''
        self.connexion=mysql.connector.connect(user='tdlog', database='contacts', host='localhost')
        self.curseur=self.connexion.cursor()

    def nouveau(self, titre, timestampDebut, timestampFin, positionX, positionY, texte, invitesId, public):
        query="INSERT INTO evenements (titre, positionX, positionY, tempsDebut, tempsFin, texte, contactsIds) VALUES ('"+nom+"', '"+prenom+"', '"+telephone+"', 0, 0, NOW(), NOW(), '')"
        self.curseur.execute(query)
        self.connexion.commit()
        query="SELECT id from evenements WHERE (nom, prenom) = ('"+nom+"', '"+prenom+"')"
        self.curseur.execute(query)
        clientId=int(self.curseur.fetchone()[0])
        self.connexion.commit()
        query="UPDATE evenements SET contactsId = '"+str(clientId)+"' WHERE id ="+str(clientId)
        self.connexion.commit()
        self.connexion.close()
        return 'nouveauClientOk'+'\n'+str(clientId)

    def test(self,clientId):
        query="SELECT nom from evenements WHERE id = "+str(clientId)
        self.curseur.execute(query)
        reponse=self.curseur.fetchone()[0]
        return reponse


    def actualisePosition(self, clientId, clientPositionX, clientPositionY):
        query="UPDATE evenements SET positionX ="+str(float(clientPositionX))+" WHERE id="+str(clientId)
        self.curseur.execute(query)
        query="UPDATE evenements SET positionY ="+str(float(clientPositionY))+" WHERE id="+str(clientId)
        self.curseur.execute(query)
        self.connexion.commit()
        self.connexion.close()
        return 'actualisePosition OK'
#!/usr/bin/python3

import sys
