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
PORT = 6767
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

#requètes sur le client

        if requeteId=='nouvelUtilisateur':
            client=utilisateur.bddUtilisateur()
            nom=self.rfile.readline().strip().decode('utf_8')
            prenom=self.rfile.readline().strip().decode('utf_8')
            telephone=self.rfile.readline().strip().decode('utf_8')
            traitement=client.nouveau(nom, prenom, telephone)

        elif requeteId=='whoAmI':
            client=utilisateur.bddUtilisateur()
            traitement=client.test(clientId)

        elif requeteId=='actualisePosition':
            client=utilisateur.bddUtilisateur()
            clientPositionX=self.rfile.readline().strip().decode('utf_8')
            clientPositionY=self.rfile.readline().strip().decode('utf_8')
            traitement=client.actualisePosition(clientId, clientPositionX, clientPositionY)

#requètes sur les contacts
        
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
            timestampDebut=self.rfile.readline().strip().decode('utf_8')
            timestampFin=self.rfile.readline().strip().decode('utf_8')
            positionGPS=self.rfile.readline().strip().decode('utf_8')
            longueurTexte=int(self.rfile.readline().strip()).decode('utf_8')
            texte=self.rfile.readline(longueurTexte).strip().decode('utf_8')
            nombreInvites=int(self.rfile.readline().strip()).decode('utf_8')
            invitesId=[clientId]
            for compteur in range(nombreInvites):
                invitesId.append(int(self.rfile.readline().strip().decode('utf_8')))
            public=bool(self.rfile.readline().strip().decode('utf_8'))
            traitement=evenements.creer(timestampDebut, timestampFin, positionGPS, texte, invitesId, public)
            
        elif requeteId=='positionEvenementsPublics':
            evenement=evenements.bddEvenements()
            rayon=float(self.rfile.readline().strip().decode('utf_8'))
            traitement=evenements.position(clientId, rayon) #classe appropriée encore a écrire
            
        elif requeteId=='joindreEvenement':
            evenement=evenements.bddEvenements()
            evenementId=int(self.rfile.readline().strip().decode('utf_8'))
            traitement=evenements.joindre(clientId, evenementId)
            
        elif requeteId=='inviterEvenement':
            evenement=evenements.bddEvenements()
            evenementId=int(self.rfile.readline().strip().decode('utf_8'))
            nombreInvites=int(self.rfile.readline().strip().decode('utf_8'))
            invitesId=[]
            for compteur in range(nombreInvites):
                invitesId.append(int(self.rfile.readline().strip().decode('utf_8')))
            traitement=evenements.ajouterInvites(evenementId, invitesId)
            

#requètes sur les modules complémentaires

        elif requeteId=='requeteModule':
            moduleId=self.rfile.readline().strip().decode('utf_8')
            longueurRequete=int(self.rfile.readline().strip().decode('utf_8'))
            requeteModule=self.rfile.readline(longueurRequete).strip().decode('utf_8')
            traitement=modules.module(moduleId, requeteModule)
            
        else:
            traitement='ERREUR : requete incomplete ou mal identifiee'


        self.wfile.write(bytes(traitement,'utf_8'))


#Classe implémentant le serveur TCP qui permet les échanges mis en place par la classe précédente. Ce serveur est threadé (et peut être aussi forké si on a un serveur multicoeur) afin de permettre de traiter plusieurs requètes simultanées)

class serveurTCPAsync(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass

if __name__=="__main__":
    serveur=serveurTCPAsync((HOST, PORT), traitementRequete)

    serveurThread=threading.Thread(target=serveur.serve_forever)
    serveurThread.daemon = True
    serveurThread.start()

    print("Boucle serveur tournant dans le thread n°", serveurThread.name)
    
