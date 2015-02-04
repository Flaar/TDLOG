package com.locolize.geoloc_project;

//import java.util.Date;
//import com.locolize.client_test.MainActivity.connectTask;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.GoogleMap;

import android.location.Location;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
//import android.location.Location;
import android.util.Log;
//ATTENTION rajouter  <uses-permission android:name="android.permission.READ_CONTACTS" /> dans les autorisations
public class Utilisateur extends Activity {//implements LPOI {
	// Cette classe correspond � l'utilisateur, elle permet la cr�ation d'un compte sur l'appli
	// l'enregistrement des contacts du t�l�phone dans le serveur
	// l'ajout de contacts (ajout � la base de donn�e SQLITE) et la r�cup�ration de tous les contacts du t�l�phone 
	// ATTENTION pb d'id ! (sur la base SQlite, les id sont choisis par la base, il peut il y avoir redondance des id sur le serveur de Benjamin
	// on peut enregistrer les conversations et les lire (elles sont sous forme de string.
	// Cette classe permet aussi d'actualiser la position GPS de l'utilisateur sur le serveur
	// et r�cup�rer les donn�es de ce qui se passe autour de lui
	// et d'int�ragir avec: join_event, etc...
	// cad la position de ses amis (close_contact) et les �venements (close_event)
	// Pour l'affichage (Robin et Hugues, ce qui est important:
	// > close_contacts est une ArrayList de Contact � afficher � l'�cran
	// > close_events est une ArrayList d'event � afficher � l'�cran
	// > contacts est une List de tous les contacts dans la base de donn�e SQLite que l'on peut ajouter via les fonction (add_contact_to_database
	// et add_all_phone_contacts_to_database, il faudra tout de m�me faire le tri entre les contacts ayant l'appli et ceux qui ne l'ont pas !)
	// FONCTION A AJOUTER:
	// >une fonction qui permet d'afficher tous les POI sur la map (close_contacts et close events).
	// elle se basera sur une fonction afficher_POI de la classe POI dont d�rive les classes Contact et Event
	// >une fonction qui ne rentre dans la base de donn�e que les contacts qui ont l'appli
	// >cr�er �venement
	// > inviter � un �venement
	// ...
	
	
	public DatabaseHandler database;
	public String name;
	public String surname;
	public String pseudo;
	public int phone_number;
	public int client_id;
	public Location position;
	public Boolean visible;
	public Boolean parameter_1;
	public Boolean parameter_2;
	public Boolean parameter_3;
	public ArrayList<Contact> close_contacts;
	public ArrayList<Event> close_events;
	public List<Contact> contacts;
	private Client myClient;
	private ArrayList<String> message_to_send;
	private ArrayList<String> message_received;
	
	public Utilisateur(){
		name= "NewUser";
		pseudo= "NewUser";
	}
	
	public Utilisateur(String this_name, String this_surname, int this_phone_number){
		/*System.out.println("Avant database");
		DatabaseHandler database = new DatabaseHandler(this);
		System.out.println("apr�s database");
		Contact hubert= new Contact("Hubert", "Ruppert", 146746824);
		System.out.println("apr�s creation hubert");
		database.addContact(hubert);
		System.out.println("apr�s ajout hubert database");
		database.updateMessage ("je suis travail",hubert);
		System.out.println("apr�s ajout message");
		String message_to_print;
		message_to_print=database.getMessage(hubert);
		System.out.println("apr�s getmessage");
		System.out.println(message_to_print);
		//contacts = database.getAllContacts();*/
		
		client_id=7;
		name=this_name;
		surname=this_surname;
		phone_number=this_phone_number;
		
		
		//Log.d("Reading: ", "Reading all contacts..");
		//envoyer ces contacts au serveur !!!
		message_to_send = new ArrayList<String>();
		
		message_to_send.add("nouvelUtilisateur");
		message_to_send.add(name);
		message_to_send.add(surname);
		message_to_send.add(Integer.toString(phone_number));
		System.out.println("juste avant l'envoi client");
		send_receive();
		/*
		myClient = new Client(message_to_send);// constructeur, client donne l'adresse IP, les principaux param�tres
		message_received=myClient.get_message_received();// fonction run prend l'array liste en entr�e, l'envoie et attend la r�ponse 
		System.out.println(message_received); // qu'il met sous forme d'array liste �galement
		*/
	}
	
	public Utilisateur(int this_id){
		client_id=this_id;
	}
	
	public void update_close_contact_list(){
		//actualise les contacts dans la zone
		message_to_send = new ArrayList<String>();
		message_to_send.add("positionContacts");
		message_to_send.add(Integer.toString(5));
		send_receive();
		System.out.println(!message_received.isEmpty());
		if (!message_received.isEmpty()){
			
			//int number_of_contacts= Integer.parseInt(message_received.get(1));
			int compteur = 2;
			
			while(compteur < message_received.size()-1) {
				System.out.println("Index: " + compteur + " - Item: " + message_received.get(compteur));//affichage de contr�le
				
				Contact contact = new Contact();
				contact.id=Integer.parseInt(message_received.get(compteur));
				contact.name=message_received.get(compteur+1);
				contact.surname=message_received.get(compteur+2);
				contact.phone_number=Integer.parseInt(message_received.get(compteur+3));
				double Longitude=Double.parseDouble(message_received.get(compteur+4));
				double Latitude=Double.parseDouble(message_received.get(compteur+5));
				//String sLongitude=Location.convert(Longitude, Location.FORMAT_SECONDS);
				//String sLatitude=Location.convert(Latitude, Location.FORMAT_SECONDS);
				contact.position.setLatitude(Latitude);
				contact.position.setLatitude(Longitude);
				
				close_contacts.add(contact);
				System.out.println("le contact re�u est:");
				System.out.println(contact.id);
				System.out.println(contact.name);
				System.out.println(contact.surname);
				System.out.println(contact.phone_number);
				System.out.println(contact.position.getLatitude());
				System.out.println(contact.position.getLongitude());
				compteur=compteur+6;				
				}
				
			}
			
		else{
			System.out.println("error: system failed to communicate with the server");}		           
      }
	
	public void update_close_event_list(){
		// a faire en regardant le code de benjo ! mais la structure est l� !
		message_to_send = new ArrayList<String>();
		message_to_send.add("actualiseEvenement");
		send_receive();
		
		if (!message_received.isEmpty()){
			
			//int number_of_contacts= Integer.parseInt(message_received.get(1));
			int compteur = 2;
			
			while(compteur < message_received.size()-1) {
				System.out.println("Index: " + compteur + " - Item: " + message_received.get(compteur));//affichage de contr�le
				
				Event event = new Event();
				event.id=Integer.parseInt(message_received.get(compteur));
				event.name=message_received.get(compteur+1);
				event.owner.id=Integer.parseInt(message_received.get(compteur+2));
				double Longitude=Double.parseDouble(message_received.get(compteur+3));
				double Latitude=Double.parseDouble(message_received.get(compteur+4));
				//String sLongitude=Location.convert(Longitude, Location.FORMAT_SECONDS);
				//String sLatitude=Location.convert(Latitude, Location.FORMAT_SECONDS);
				event.position.setLatitude(Latitude);
				event.position.setLatitude(Longitude);
				event.description=message_received.get(compteur+5);
				event.public_event=Boolean.valueOf(message_received.get(compteur+6));
				//event.contacts_invited co
				//event.begining=message_received.get(compteur+8)
				//event.end=message_received.get(compteur+9)
				
				close_events.add(event);
					
				compteur=compteur+6;				
				}
				
			}
			
		else{
			System.out.println("error: system failed to communicate with the server");}		           
      }
	
	public void update_location(){
		message_to_send = new ArrayList<String>();
		message_to_send.add("actualisePosition");
		message_to_send.add(Double.toString(position.getLatitude()));
		message_to_send.add(Double.toString(position.getLongitude()));
		send_receive();
	}
	
	public void join_event(int evenementId){
		message_to_send = new ArrayList<String>();
		message_to_send.add("joindreEvenement");
		message_to_send.add(Integer.toString(evenementId));
		send_receive();
	}
	
	private void send_receive(){
		 //ArrayList<String> message_received = new ArrayList<String>();// cr�e un array vide que la classe Client va remplir apr�s appel au serveur
		 message_to_send.add(0,Integer.toString(client_id));// on ajoute en premi�re ligne l'id du clien..
		 System.out.println(message_to_send);
		 myClient = new Client(message_to_send);// constructeur, client donne l'adresse IP, les principaux param�tres
		 message_received=myClient.get_message_received();// fonction run prend l'array liste en entr�e, l'envoie et attend la r�ponse 
		 // qu'il met sous forme d'array liste �galement
	}
	
	public void test_requete(){
		message_to_send = new ArrayList<String>();
		message_to_send.add("whoAmI");
		send_receive();
		System.out.println(message_received);		
	}
	
	public void test_client(){
		message_to_send = new ArrayList<String>();
		message_to_send.add("testcouf");
		message_to_send.add("c'est pas ouf");
		myClient = new Client(message_to_send);// constructeur, client donne l'adresse IP, les principaux param�tres
		message_received=myClient.get_message_received();
		System.out.println(message_received);		
	}
	
	public void print_close_contact_list(){
		for (Contact this_contact : close_contacts) {
			this_contact.print_contact();
			}
	}
	
	public void print_close_event_list(){
		for (Event this_event : close_events) {
			this_event.print_event();
			}
	}
	
	public void add_contact_to_database(){
  Log.d("Insert: ", "Inserting ..");
  database.addContact(new Contact());// a travailler, mais pas s�r que cette fonction soit utile
	}
	

  public void add_contacts_in_current_memory(){
  	contacts = database.getAllContacts();
  	Log.d("Reading: ", "Reading all contacts..");
  	 for (Contact this_contact : contacts) {
  	        String log = "Id: "+this_contact.id+" ,Name: " + this_contact.name + " ,Surname: " + this_contact.surname +" ,Phone: " + this_contact.phone_number;
  	        // Writing Contacts to log
  	        Log.d("Name: ", log);
  	        }    	    
  	}
  
  public void addEvents(){
	  
  }
  
  public void add_all_phone_contacts_to_database(){
  	Log.d("Insert: ", "Inserting all the contacts ..");
  	Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
      while (phones.moveToNext())
      {
      	String contact_name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
      	String contact_phone_number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
      	Contact this_contact=new Contact(contact_name, "", Integer.parseInt(contact_phone_number));
      	database.addContact(this_contact);

      }
      phones.close();    
  }
  
  public String getMessage(Contact contact){
  String msg=database.getMessage(contact);
  return(msg);
  }
  
  public void putMessage(Contact contact, String message_to_send){
  	database.updateMessage (message_to_send,contact);
  }
  
  
  public void printContacts(GoogleMap theMap){
		for(Contact contact : this.close_contacts)
		{theMap.addMarker(contact.createMarker());}
	}
  
  
  //*************************ACCESSEURS ET MUTATEURS******************************************************
  public void setName(String nom){this.name=nom;}
  public void setSurname(String prenom){this.surname=prenom;}
  public void setPseudo(String alias){this.pseudo=alias;}
  public void setNumber(int numero){this.phone_number=numero;}
  public void setLocation(Location pos){this.position=pos;}
  
	}
