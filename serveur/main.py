#!/usr/bin/python3

#fichier debuggué et fonctionnel

import socket
import socketserver
import sys
import threading
import evenements
import contacts
import utilisateur

HOST = '' #représente toutes les interfaces réseau
PORT = 7777
#Port non prviligié à choisir

#Classe gérant les requètes reçues sous forme de fichiers. Ces fichiers seront au format XML ou JSON selon le choix retenu plus tard et seront traités
class traitementRequete(socketserver.StreamRequestHandler):
    print("Nouvelle requète traitée dans le thread n°", threading.current_thread())

    #la fonction handle est générique pour cette classe et contient toute la gestion de la requète. Elle est appellée à chaque requète
    def handle(self):
        #self.rfile est un fichier contenant le contenu de la requete

        
        clientId=int(self.rfile.readline().strip())
        print(clientId)
        requeteId=self.rfile.readline().strip().decode('utf_8')
        print(requeteId)

#requètes sur le client

        if requeteId=='nouvelUtilisateur':
            client=utilisateur.bddUtilisateur()
            nom=self.rfile.readline().strip().decode('utf_8')
            prenom=self.rfile.readline().strip().decode('utf_8')
            telephone=self.rfile.readline().strip().decode('utf_8')
            traitement=client.nouveau(nom, prenom, telephone)

        elif requeteId=='quiSuisJe':
            telephone=self.rfile.readline().strip().decode('utf_8')
            client=utilisateur.bddUtilisateur()
            traitement=client.identite(telephone)

        elif requeteId=='actualisePosition':
            client=utilisateur.bddUtilisateur()
            clientPositionX=self.rfile.readline().strip().decode('utf_8')
            clientPositionY=self.rfile.readline().strip().decode('utf_8')
            traitement=client.actualisePosition(clientId, clientPositionX, clientPositionY)

#requètes sur les contacts

        elif requeteId=='listeContacts':
            contact=contacts.bddContacts()
            traitement=contact.actualiseListe(clientId, 'None')

        
        elif requeteId=='actualiseContacts':
            contact=contacts.bddContacts()
            nombreContacts=int(self.rfile.readline().strip())
            contactsNums=[]
            for compteur in range(nombreContacts):
                contactsNums.append(self.rfile.readline().strip().decode('utf_8'))
            traitement=contact.actualiseListe(clientId, contactsNums)
            
        elif requeteId=='positionContacts':
            contact=contacts.bddContacts()
            rayon=float(self.rfile.readline().strip().decode('utf_8'))
            traitement=contact.position(clientId, rayon) #classe appropriée à définir
            
#requètes sur les évènements

        elif requeteId=='creerEvenement':     
            evenement=evenements.bddEvenements()
            titre=self.rfile.readline().strip().decode('utf_8')
            timestampDebut=self.rfile.readline().strip().decode('utf_8')
            timestampFin=self.rfile.readline().strip().decode('utf_8')
            positionX=self.rfile.readline().strip().decode('utf_8')
            positionY=self.rfile.readline().strip().decode('utf_8')
            longueurTexte=int(self.rfile.readline().strip())
            texte=self.rfile.readline().strip().decode('utf_8')
            nombreInvites=int(self.rfile.readline().strip())
            invitesId=[clientId]
            for compteur in range(nombreInvites):
                invitesId.append(int(self.rfile.readline().strip().decode('utf_8')))
            public=bool(self.rfile.readline().strip().decode('utf_8'))
            traitement=evenement.nouveau(clientId, titre, timestampDebut, timestampFin, positionX, positionY, texte, invitesId, public)
            
        elif requeteId=='positionEvenements':
            evenement=evenements.bddEvenements()
            traitement=evenement.position(clientId)
            
        elif requeteId=='joindreEvenement':
            evenement=evenements.bddEvenements()
            evenementId=int(self.rfile.readline().strip().decode('utf_8'))
            traitement=evenement.joindre(clientId, evenementId)
            

#requètes sur les modules complémentaires

        elif requeteId=='requeteModule':
            moduleId=self.rfile.readline().strip().decode('utf_8')
            longueurRequete=int(self.rfile.readline().strip().decode('utf_8'))
            requeteModule=self.rfile.readline(longueurRequete).strip().decode('utf_8')
            traitement=modules.module(moduleId, requeteModule)
            
        else:
            traitement='ERREUR : requete incomplete ou mal identifiee'


        self.wfile.write(bytes(traitement+'\nend\n','utf_8'))


#Classe implémentant le serveur TCP qui permet les échanges mis en place par la classe précédente. Ce serveur est threadé (et peut être aussi forké si on a un serveur multicoeur) afin de permettre de traiter plusieurs requètes simultanées)

class serveurTCPAsync(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass

if __name__=="__main__":
    serveur=serveurTCPAsync((HOST, PORT), traitementRequete)

    serveurThread=threading.Thread(target=serveur.serve_forever)
    serveurThread.daemon = True
    serveurThread.start()

    print("Boucle serveur tournant dans le thread n°", serveurThread.name)
    
