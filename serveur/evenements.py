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
#CREATE TABLE evenements(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, titre VARCHAR(255), positionX FLOAT, positionY FLOAT, dateDebut INT, dateFin INT, texte TEXT, invitesIds TEXT, public BOOL)


class bddEvenements:

    def __init__(self):
        self.logs=''
        self.connexion=mysql.connector.connect(user='tdlog', database='contacts', host='localhost')
        self.curseur=self.connexion.cursor()
        self.balayer()

    def balayer(self)
        query='DELETE FROM evenements WHERE tempsFin < UNIX_TIMESTAMP(NOW())'
        self.curseur.execute(query)

    def nouveau(self, clientId, titre, timestampDebut, timestampFin, positionX, positionY, texte, invitesIds, presentsIds, public):
        query="INSERT INTO evenements (titre, positionX, positionY, tempsDebut, tempsFin, texte, invitesIds, presentsIds, public) VALUES ('"+titre+"', '"+str(positionX)+"', '"+str(positionY)+"', '"+texte+"', '"+invitesIds+"', '"+clientId+"', '"+str(public)+"')"
        self.curseur.execute(query)
        self.connexion.commit()
        query="SELECT id FROM evenements WHERE (titre, positionX, positionY) = ('"+titre+"', '"+str(positionX)+"', '"+str(positionY)+"')"
        self.curseur.execute(query)
        eventId=int(self.curseur.fetchone()[0])
        self.connexion.commit()
        self.connexion.close()
        return '2\n'+'nouvelEvenementOk'+'\n'+str(eventId)

    def test(self,eventId):
        query="SELECT titre from evenements WHERE id = "+str(eventId)
        self.curseur.execute(query)
        reponse=self.curseur.fetchone()[0]
        return reponse
    
    def position(self, clientId):
        query="SELECT eventsIds FROM contacts WHERE id="+str(clientId)
        eventsIds=self.curseur.fetchone()[0]
        eventsIds=concatene(eventsIds)
        reponse='positionEvenementsOk\n'
        evenements=''
        for eventId in eventsIds:
            query="SELECT positionX, positionY, invitesIds, presentsIds, titre, texte, tempsDebut, tempsFin FROM evenements WHERE id="+str(eventId)+" AND tempsFin < UNIX_TIMESTAMP(NOW())
            try:
                self.curseur.execute(query)
                fetch=self.curseur.fetchone()
                evenements+=eventId+'\n'+fetch[3]+'\n'+fetch[0]+'\n'+fetch[1]+'\n'+fetch[2]+'\n'+fetch[4]+'\n'+fetch[5]+'\n'+fetch[6]+'\n'
            except:
                pass
        evenements=str(evenements.count('\n')/8)+evenements
        reponse+=evenements
        return reponse

    def joindre(self, clientId, eventId):
        query="SELECT presentsIds FROM evenements WHERE id="+str(eventId)
        self.curseur.execute(query)
        ancienContactsIds=self.curseur.fetchone()[0]
        ancienContactsIds=set(split(ancienContactsIds))
        contactsIds=ancienContactsIds.union(set([ClientId]))
        contactsIdstexte=concatene(contactsIds)
        query="UPDATE evenements SET contactsIds = '"+contactsIdstexte+"' WHERE id="+str(eventId)
        self.curseur.execute(query)
        self.connexion.commit()
        return 'joindreEvenementOk'

    

    

#!/usr/bin/python3

import sys
