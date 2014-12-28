#!/usr/bin/python3

import socket
import socketserver
import sys
import thread
import evenements as *
import contacts as *

HOST = '' #représente toutes les interfaces réseau
PORT = 8888 #Port non prviligié à choisir

#Classe gérant les requètes reçues sous forme de fichiers. Ces fichiers seront au format XML ou JSON selon le choix retenu plus tard et seront traités
class traitementRequete(socketserver.StreamRequestHandler):
    print("Nouvelle requète traitée dans le thread n°", threading.current_thread())
    #la fonction setup est générique pour cette classe et contient l'initialisation si nécessaire
    def setup(self):
        pass
    #la fonction handle est générique pour cette classe et contient toute la gestion de la requète. Elle est appellée à chaque requète
    def handle(self):
        #self.rfile est un fichier contenant le contenu de la requete
        clientId=int(self.rfile.readline().strip())
        requeteId=self.rfile.readline().strip()
        if requeteId=='actualisePosition':
            clientPosition=self.rfile.readline().strip()
            traitement=contacts.actualisePositionClient(clientId, clientPosition)
        elif requeteId=='actualiseContacts':
            nombreContacts=int(self.rfile.readline().strip()))
            contactsNums=[clientId]
            for compteur in range(nombreContacts):
                contactsNums.append(self.rfile.readline().strip())
            traitement=contacts.actualiseListe(clientId, contactsNums)
        elif requeteId=='positionContacts':
            rayon=float(self.rfile.readline().strip())
            traitement=contacts.position(clientId, rayon) #classe appropriée à définir
            reponse=traitement.resultat #renvoie la liste des positions des contacts dans un format à définir
        elif requeteId=='positionEvenementsPublics':
            rayon=float(self.rfile.readline().strip())
            traitement=evenements.position(clientId, rayon) #classe appropriée encore a écrire
            reponse=traitement.resultat #renvoie la liste des positions des contacts dans un format à définir
        elif requeteId=='etatContact':
            contactId=int(self.rfile.readline().strip())
            traitement=contacts.etat(clientId, contactId)
            reponse=traitement.resultat #renvoie la liste des positions des contacts dans un format à définir
        elif requeteId=='etatEvenement':
            evenementId=int(self.rfile.readline().strip())
            traitement=evenements.etat(clientId, evenementId)
            reponse=traitement.resultat #renvoie la liste des positions des contacts dans un format à définir
        elif requeteId=='joindreEvenement':
            evenementId=int(self.rfile.readline().strip())
            traitement=evenements.joindre(clientId, evenementId)
            reponse=traitement.resultat #renvoie la liste des positions des contacts dans un format à définir
        elif requeteId=='creerEvenement':
            timestampDebut=self.rfile.readline().strip()
            timestampFin=self.rfile.readline().strip()
            positionGPS=self.rfile.readline().strip()
            longueurTexte=int(self.rfile.readline().strip())
            texte=self.rfile.readline(longueurTexte).strip()
            nombreInvites=int(self.rfile.readline().strip())
            invitesId=[clientId]
            for compteur in range(nombreInvites):
                invitesId.append(int(self.rfile.readline().strip()))
            public=bool(self.rfile.readline().strip())
            traitement=evenements.creer(timestampDebut, timestampFin, positionGPS, texte, invitesId, public)
            reponse=traitement.evenementId
        elif requeteId=='inviterEvenement':
            evenementId=int(self.rfile.readline().strip())
            nombreInvites=int(self.rfile.readline().strip())
            invitesId=[]
            for compteur in range(nombreInvites):
                invitesId.append(int(self.rfile.readline().strip()))
            traitement=evenements.ajouterInvites(evenementId, invitesId)
            reponse=''
        elif requeteId=='requeteModule':
            moduleId=self.rfile.readline().strip()
            longueurRequete=int(self.rfile.readline().strip())
            requeteModule=self.rfile.readline(longueurRequete).strip())
            traitement=modules.module(moduleId, requeteModule)
            reponse=traitement.resultat
        else:
            reponse='ERREUR : requete incomplete ou mal identifiee'
        self.wfile.write(reponse)
    #la fonction finish est générique pour cette classe et contient le nettoyage si nécessaire
    def finish():
        pass

#Classe implémentant le serveur TCP qui permet les échanges mis en place par la classe précédente. Ce serveur est threadé (et peut être aussi forké si on a un serveur multicoeur) afin de permettre de traiter plusieurs requètes simultanées)

class serveurTCPAsync(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass

if __name__=="__main__":
    serveur=serveurTCPAsync((HOST, PORT), traitementRequete)

    serveurThread=threading.Thread(target=serveur.serve_forever)
    serveurThread.daemon = True
    serverThread.start()

    print("Boucle serveur tournant dans le thread n°", serveurThread.name)
    
