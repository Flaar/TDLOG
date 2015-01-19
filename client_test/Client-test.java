package com.locolize.client_test;
import android.util.Log;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
 
public class Client-test {
 
    private String serverMessage;
    public static final String SERVERIP = "89.88.112.126"; //your computer IP address
    public static final int SERVERPORT = 6667;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
 
    PrintWriter out;
    BufferedReader in;
    

    ///////////////////////CLASSE UTILE///////////////////////////////
    
	public String requeteId;
	public String clientPosition;
	public String clientId;
	public int nombreContacts;	
	public int contactsNums;
   	public float rayon;
	public int contactId;
	public int evenementId;
	public String msg;
	
	
	public String actualisePosition(){
		// actualise clientPosition    		
		msg="actualisePosition";
		//...
		return(clientPosition);
		
	}
	
	public actualiseContacts(){
		//actualise les contacts dans la zone
		msg="actualiseContacts";
		msg2=nombreContacts;
			    		
		return(contactsNums)
		//format des contacts a définir
	}
	
	public positionContacts(){
		msg="positionContacts";
		msg2=rayon;
		return();//a définir
	}

	public positionEvenementsPublics(){
		msg="positionEvenementsPublics";
		msg2=rayon;
		return();//a définir
		
	}
	
	public etatContact(){
		msg="etatContact";
		msg2=contactId;
		return();//a définir
		
	}
	
	public etatEvenement()){
		msg="etatEvenement";
		msg2=contactId;
		return();//a définir
		
	}
	
	public joindreEvenement(){
		msg="joindreEvenement";
		msg2=evenementId
	}
	
	public creerEvenement(){
		msg="creerEvenement";	    		
		timestampDebut
		timestampFin
		positionGPS
		longueurTexte
		texte
	   	nombreInvites
		invitesId			
	}
	
	public inviterEvenement(){
		msg="inviterEvenement";
		evenementId
		nombreInvites
	}
	
}

    
    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public Client(OnMessageReceived listener) {
        mMessageListener = listener;
    }
 
    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }
 
    public void stopClient(){
        mRun = false;
    }
 
    public void run() {
 
        mRun = true;
 
        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
 
            Log.e("TCP Client", "C: Connecting...");
 
            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
 
            try {
 
                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
 
                Log.e("TCP Client", "C: Sent.");
 
                Log.e("TCP Client", "C: Done.");
 
                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
 
                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    serverMessage = in.readLine();
 
                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;
 
                }
 
                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
 
            } catch (Exception e) {
 
                Log.e("TCP", "S: Error", e);
 
            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }
 
        } catch (Exception e) {
 
            Log.e("TCP", "C: Error", e);
 
        }
 
    }
 
    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}