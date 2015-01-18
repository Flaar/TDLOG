package com.locolize.client_test;

import java.util.Date;

import com.locolize.client_test.MainActivity.connectTask;

import android.location.Location;

public class Utilisateur {
	public String name;
	public String surname;
	public int phone_number;
	public Boolean visible;
	public Boolean parameter_1;
	public Boolean parameter_2;
	public Boolean parameter_3;
	public Contact close_contacts;
	//public Event close_events;
	
	public void update_contact_list(){
		//actualise les contacts dans la zone
		public String msg;
		msg="actualisePosition";
		private Client mClient;
		//se connecte au serveur
		new connectTask().execute("");
		// envoie le msg
		if (mClient != null) {
            mClient.sendMessage(msg);
        // là il faut qu'on récupère le message qqpart !! où??
            //http://openclassrooms.com/forum/sujet/probleme-avec-methode-readline-avec-un-bufferedreader-18000
     
            
        }
		

		
		
		
	}

}
