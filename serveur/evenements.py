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
        #self.balayer()

    def balayer(self):
        query='DELETE FROM evenements WHERE dateFin < UNIX_TIMESTAMP(NOW())'
        self.curseur.execute(query)
        self.connexion.commit()

    def nouveau(self, clientId, titre, timestampDebut, timestampFin, positionX, positionY, texte, invitesIds, public):
        query="INSERT INTO evenements (titre, positionX, positionY, dateDebut, dateFin, texte, invitesIds, presentsIds, public) VALUES ('"+titre+"', "+str(positionX)+", "+str(positionY)+", "+str(timestampDebut)+", "+str(timestampFin)+", '"+texte+"', '"+concatene(invitesIds)+"', '"+str(clientId)+"', '"+str(public)+"')"
        print(query)
        self.curseur.execute(query)
        self.connexion.commit()
        query="SELECT id FROM evenements WHERE (titre, texte, invitesIds) = ('"+titre+"', '"+texte+"', '"+concatene(invitesIds)+"')"
        print(query)
        self.curseur.execute(query)
        eventId=int(self.curseur.fetchone()[0])
        print(invitesIds)
        for inviteId in invitesIds:
            query="SELECT eventsIds FROM repertoire_final WHERE id="+str(inviteId)
            print(query)
            self.curseur.execute(query)
            inviteEventsIds=self.curseur.fetchone()[0]
            inviteEventsIds=set(split(inviteEventsIds))
            nouveauEventsIds=inviteEventsIds.union({eventId})
            nouveauEventsIds=concatene(nouveauEventsIds)
            query="UPDATE repertoire_final SET eventsIds = '"+nouveauEventsIds+"' WHERE id="+str(inviteId)
            self.curseur.execute(query)
            self.connexion.commit()
        return 'nouvelEvenementOk'+'\n'+str(eventId)

    def test(self,eventId):
        query="SELECT titre from evenements WHERE id = "+str(eventId)
        self.curseur.execute(query)
        reponse=self.curseur.fetchone()[0]
        return reponse
    
    def position(self, clientId):
        query="SELECT eventsIds FROM repertoire_final WHERE id="+str(clientId)
        self.curseur.execute(query)
        eventsIds=self.curseur.fetchone()[0]
        reponse='positionEvenementsOk\n'
        evenements=''
        eventsIds=split(eventsIds)
        print(eventsIds)
        for eventId in eventsIds:
            print(eventId)
            query="SELECT titre, positionX, positionY, dateDebut, dateFin, texte, invitesIds, presentsIds FROM evenements WHERE id="+str(eventId)
            try:
                print(query)
                self.curseur.execute(query)
                fetch=self.curseur.fetchone()
                evenements+=str(eventId)+'\n'+str(fetch[0])+'\n'+str(fetch[1])+'\n'+str(fetch[2])+'\n'+str(fetch[3])+'\n'+str(fetch[4])+'\n'+str(fetch[5])+'\n'+str(fetch[6])+'\n'+str(fetch[7])+'\n'
            except:
                print('erreur')
        evenements=str(evenements.count('\n')/8)+evenements
        reponse+=evenements
        return reponse

    def joindre(self, clientId, eventId):
        query="SELECT presentsIds FROM evenements WHERE id="+str(eventId)
        self.curseur.execute(query)
        ancienContactsIds=self.curseur.fetchone()[0]
        ancienContactsIds=set(split(ancienContactsIds))
        contactsIds=ancienContactsIds.union(set([clientId]))
        contactsIdstexte=concatene(contactsIds)
        query="UPDATE evenements SET presentsIds = '"+contactsIdstexte+"' WHERE id="+str(eventId)
        self.curseur.execute(query)
        self.connexion.commit()
        return 'joindreEvenementOk'
