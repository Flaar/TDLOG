package com.locolize.geoloc_project;

import android.util.Log;
import java.util.ArrayList;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client {
 
   
    public static final String SERVERIP = "89.88.112.126"; //your computer IP address
	//public static final String SERVERIP = "89.156.23.227";
	//public static final int SERVERPORT = 6767;
	public static final int SERVERPORT = 7676;
    //private OnMessageReceived mMessageListener = null;
    //private boolean mRun = false;
    public ArrayList<String> message_to_send;
	public ArrayList<String> message_received;
	
	
    PrintWriter out;
    BufferedReader in;
    
    public Client(ArrayList<String> message_user){
    //constructeur de la classe lors de l'envoi d'un message
    	message_to_send=message_user;
    	System.out.println("le message a envoyer est:");
    	System.out.println(message_to_send);
    	new Thread(new ClientThread()).start();
    	
    }
    
    public ArrayList<String> get_message_received(){
    return(message_received);
    }
    
    class ClientThread implements Runnable {
    	@Override
    	public void run() {
    		 try {
    	            //here you must put your computer's IP address.
    	            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
    	 
    	            Log.e("TCP Client", "C: Connecting...");
    	            //readingThread = new Thread();
    	    		//readingThread.start(); 
    	            // remarque si on fait un thread, on est obligé de lancer un runable, donc il faut mettre ce qui suit dans un fonction run
    	            // comme c'est expliqué ici! http://www.mti.epita.fr/blogs/2011/09/29/utilisation-des-sockets-sous-android/
    	    		System.out.println("before socket ");
    	            //create a socket to make the connection with the server
    	            Socket socket = new Socket(serverAddr, SERVERPORT);
    	            System.out.println("after socket");
    		        
    		        
    			    try{
    		    	// nouveau try ici ???
    		            //send the message to the server
    		            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    					 //receive the message which the server sends back
    					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    					
    		            Log.e("TCP Client", "C: Sent."); 
    		            Log.e("TCP Client", "C: Done.");
    		            String outMessage="";
    		            
    		           for (String line : message_to_send) {
    		        	   	System.out.println("line to send is: " + line);
    		            	outMessage=outMessage+line+System.getProperty("line.separator");
    		            	}
    		            System.out.println("message to send is: ");
    		            System.out.println(outMessage);
    		            
    		            
    		            out.write(outMessage);
    		            out.flush();
    		
    		            Log.i("TcpClient", "sent: " + outMessage);
    		
    		            //accept server response
    		            String inMessage = in.readLine();
    		            message_received.add(inMessage);                
    		            System.out.println("message receive is: ");
    		            System.out.println(inMessage);
    		
    		            Log.i("TcpClient", "received: " + inMessage + System.getProperty("line.separator"));
    		
    		            //close connection
    		            socket.close();
    		        } catch (UnknownHostException e) {
    		            e.printStackTrace();
    		        } catch (IOException e) {
    		            e.printStackTrace();
    		        } catch (Exception e) {
    		            e.printStackTrace();
    		        }
    	        } catch (UnknownHostException e) {
    	            e.printStackTrace();
    	            System.out.println("La connection au serveur a echoue");
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	            System.out.println("La connection au serveur a echoue");
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	            System.out.println("La connection au serveur a echoue");
    	        }
    	        

    	}

    }
    
    /*
    public ArrayList<String> run() {
    	  
        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
 
            Log.e("TCP Client", "C: Connecting...");
            //readingThread = new Thread();
    		//readingThread.start(); 
            // remarque si on fait un thread, on est obligé de lancer un runable, donc il faut mettre ce qui suit dans un fonction run
            // comme c'est expliqué ici! http://www.mti.epita.fr/blogs/2011/09/29/utilisation-des-sockets-sous-android/
    		System.out.println("before socket ");
            //create a socket to make the connection with the server
            Socket socket = new Socket(serverAddr, SERVERPORT);
            System.out.println("after socket");
	        
	        
		    try{
	    	// nouveau try ici ???
	            //send the message to the server
	            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				 //receive the message which the server sends back
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
	            Log.e("TCP Client", "C: Sent."); 
	            Log.e("TCP Client", "C: Done.");
	            String outMessage="";
	            
	           for (String line : message_to_send) {
	        	   	System.out.println("on est dans le for");
	            	System.out.println("line to send is: " + line);
	            	outMessage=outMessage+line+System.getProperty("line.separator");
	            	}
	            System.out.println("message to send is: ");
	            System.out.println(outMessage);
	            
	            
	            out.write(outMessage);
	            out.flush();
	
	            Log.i("TcpClient", "sent: " + outMessage);
	
	            //accept server response
	            String inMessage = in.readLine();
	            message_received.add(inMessage);                
	            System.out.println("message receive is: ");
	            System.out.println(inMessage);
	
	            Log.i("TcpClient", "received: " + inMessage + System.getProperty("line.separator"));
	
	            //close connection
	            socket.close();
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("La connection au serveur a echoue");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("La connection au serveur a echoue");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("La connection au serveur a echoue");
        }
        return(message_received);
    }
    */
    
    
    
    
    
}
