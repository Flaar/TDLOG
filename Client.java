package com.example.locolize_easy;
package com.javacodegeeks.android.androidsocketclient;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
 
public class Client extends Activity {
		 
	    private Socket socket;
	 
	    private static final int SERVERPORT = 5000;
	    private static final String SERVER_IP = "10.0.2.2";
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);     
	 
	        new Thread(new ClientThread()).start();
	    }
	 
	    /*public void onClick(View view) {
	        try {
	            EditText et = (EditText) findViewById(R.id.EditText01);
	            String str = et.getText().toString();
	            PrintWriter out = new PrintWriter(new BufferedWriter(
	                    new OutputStreamWriter(socket.getOutputStream())),
	                    true);
	            out.println(str);
	        } catch (UnknownHostException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }*/
	    // je n'ai pas encore choisi où je mettais ces fonction, dans la classe client ou la classe clientThread
	    // D'ailleurs pas sûr non plus que celle-ci soit indispensable.
	  
	    	clientId int
	    	requeteId 
	    	public String clientPosition;
	    	public String clientId;
	    	public int nombreContacts;	
	    	public int contactsNums;
	       	public float rayon;
	    	public int contactId;
	    	public int evenementId
	    	public String msg;
	    
	    	
	    	public String actualisePosition(){
	    		// actualise clientPosition    		
	    		msg='actualisePosition';
	    		//...
	    		return(clientPosition);
	    		
	    	}
	    	
	    	public actualiseContacts(){
	    		//actualise les contacts dans la zone
	    		msg='actualiseContacts';
	    		msg2=nombreContacts;
	    			    		
	    		return(contactsNums)
	    		//format des contacts a définir
	    	}
	    	
	    	public positionContacts(){
	    		msg='positionContacts';
	    		msg2=rayon;
	    		return();//a définir
	    	}
	    
	    	public positionEvenementsPublics(){
	    		msg='positionEvenementsPublics';
	    		msg2=rayon;
	    		return();//a définir
	    		
	    	}
	    	
	    	public etatContact(){
	    		msg='etatContact';
	    		msg2=contactId;
	    		return();//a définir
	    		
	    	}
	    	
	    	public etatEvenement()){
	    		msg='etatEvenement';
	    		msg2=contactId;
	    		return();//a définir
	    		
	    	}
	    	
	    	public joindreEvenement(){
	    		msg='joindreEvenement';
	    		msg2=evenementId
	    	}
	    	
	    	public creerEvenement(){
	    		msg='creerEvenement';	    		
	    		timestampDebut
	    		timestampFin
	    		positionGPS
	    		longueurTexte
	    		texte
	    	   	nombreInvites
	    		invitesId			
	    	}
	    	
	    	public inviterEvenement(){
	    		msg='inviterEvenement';
	    		evenementId
	    		nombreInvites
	    	}
	    	    
	 
	    class ClientThread implements Runnable {
	 
	        @Override
	        public void run() {
	 
	            try {
	                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);	 
	                socket = new Socket(serverAddr, SERVERPORT);
	                
	                //outgoing stream redirect to socket
	                OutputStream out = socket.getOutputStream();	               
	                PrintWriter output = new PrintWriter(out);
	                output.println("Hello Android!");
	                
	                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	               
	                //read line(s)
	                String st = input.readLine();
	                
	                //Close connection
	                socket.close();
	 
	            } catch (UnknownHostException e1) {
	                e1.printStackTrace();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	 
	        }
	 
	    }
}

