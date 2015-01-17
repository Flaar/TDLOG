#!/usr/bin/python3

import socketserver
import sys
import socket
import threading


class MyTCPHandler(socketserver.StreamRequestHandler):

    def handle(self):
        # self.rfile is a file-like object created by the handler;
        # we can now use e.g. readline() instead of raw recv() calls
        self.data='bite'
        while self.data!='end':
            self.data=self.rfile.readline().strip().decode('utf_8')
            # Likewise, self.wfile is a file-like object used to write back
            # to the client
            
            self.wfile.write(bytes(self.data+' toi meme ','utf_8'))
            print(self.data, threading.current_thread)
            

class serveurTCPAsync(socketserver.ThreadingMixIn, socketserver.TCPServer):
    pass




if __name__ == "__main__":
    HOST, PORT = '', 6667

    serveur=serveurTCPAsync((HOST, PORT), MyTCPHandler)

    serveurThread=threading.Thread(target=serveur.serve_forever)
    serveurThread.daemon = True
    serveurThread.start()


    # Create the server, binding to localhost on port 9999
    #server = socketserver.TCPServer((HOST, PORT), MyTCPHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    #server.serve_forever()
        
