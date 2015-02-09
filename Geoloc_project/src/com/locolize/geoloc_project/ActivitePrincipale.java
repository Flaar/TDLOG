

package com.locolize.geoloc_project;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.math.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivitePrincipale extends Activity implements LocationListener{

	private int userIcon, foodIcon, drinkIcon, shopIcon, otherIcon, friendIcon;
	private GoogleMap theMap;
	private LocationManager locMan;
	private Marker userMarker;
	private Marker contactMarker;
	//points d'interet :
	private Marker[] placeMarkers;
	//nombre maximal de lieux retournes
	private final int nb_max_lieux = 20;//obtenus pour la plupart par l'API Google Places
	private MarkerOptions[] places;
	private MarkerOptions options = new MarkerOptions();
	Marker marqueur_rdv_courant;
	private boolean addMarker = false;
	public Utilisateur user=new Utilisateur();
	public boolean camCentering = true;
	public float defaultZoom = 15f;
	
@Override
public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_activite_principale);
	    
	    
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    user.client_id = 21;
	    
	    userIcon = R.drawable.yellow_point;
		foodIcon = R.drawable.red_point;
		drinkIcon = R.drawable.blue_point;
		shopIcon = R.drawable.green_point;
		otherIcon = R.drawable.purple_point;
		friendIcon = R.drawable.purple_point;
		
		
		//user.add_all_phone_contacts_to_database();
		
		/*
		DatabaseHandler database = new DatabaseHandler(this);
		System.out.println("apr�s database");
		Contact hubert= new Contact("Hubert", "Ruppert", "146746824");
		System.out.println("apr�s creation hubert");
		database.addContact(hubert);
		System.out.println("apr�s ajout hubert database");
		Contact hubert_bis=database.getContact(1);
		System.out.println(hubert_bis.name);
		database.updateMessage ("je suis travail",hubert_bis);
		System.out.println("apr�s ajout message");
		String message_to_print;
		message_to_print=database.getMessage(hubert_bis);
		System.out.println("apr�s getmessage");
		System.out.println(message_to_print);
		*/
		
		
		/*
		System.out.println("Avant database");
		//DatabaseHandler database = new DatabaseHandler(context);
		//database.create_table(db);
		
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
		
		/*
		DatabaseHandler database = new DatabaseHandler(this);
	    
	    if (database.getAllContacts().size() == 0 )
	    	add_all_phone_contacts_to_database(database);
	    
	    List<Contact> list_of_contacts;
	    list_of_contacts=database.getAllContacts();
	    
	    mContacts=new String[list_of_contacts.size()];
	    for(int i=0; i<list_of_contacts.size(); i++)
	    {
	    	mContacts[i]=list_of_contacts.get(i).name;
	    }*/
		
		
		
		ArrayList<Contact> contacts_tab = new ArrayList<Contact>(0);
		ArrayList<Event> events_tab = new ArrayList<Event>(0);
		//final Utilisateur user = new Utilisateur();
		double lat1 = 48.853;double lon1 = 2.35; //Notre Dame de Paris
		double lat2 = 48.855159;double lon2 = 2.361385; //Carrousel du Louvre
		double lat3 = 48.8583700999;double lon3 = 2.2944813000; //Tour Eiffel
		
		Contact c1 = new Contact(); 
		//Contact c2 = new Contact();
		//user.close_contacts.add(c2);
		//LatLng LaLo = new LatLng(lat1,lon1);
		//c1.setLatLng(LaLo);
		//c1.position.setLatitude(lat1);
		//c1.position.setLongitude(lon1);
		c1.latlng = new LatLng(lat1,lon1);c1.pseudo="ND de Paris"; //c1.name="a";c1.surname="a";c1.position= new Location("Paris");c1.id=1;c1.phone_number=0;
		c1.visible=true;
		//c2.position.setLatitude(lat2); c2.position.setLongitude(lon2); c2.pseudo="Carrousel du Louvre";
		//Contact c3 = new Contact(); c3.position.setLatitude(lat3); c3.position.setLongitude(lon3); c3.pseudo="Tour Eiffel";
		
		Event e1 = new Event();
		e1.latlng = new LatLng(lat2,lon2);
		
		user.close_contacts = contacts_tab;
		user.close_events = events_tab;
		user.close_contacts.add(c1);
		//user.close_contacts.add(c2);
		//user.close_contacts.add(c3);
		
		if(theMap==null){
			//on "recupere" la carte
			theMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
			//check in case map/ Google Play services not available
			if(theMap!=null){
				//ok - proceed
				theMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //on peut aussi mettre HYBRID, TERRAIN, ...
				theMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
				{
					@Override
					public void onMapLongClick(LatLng point)
					{
						if(addMarker == true){
						if(options==null)
						{
							options = new MarkerOptions()
							.anchor(0.5f, 0.5f)
							.position(point)
							.title("Marker")
							.draggable(true)
							//.icon(BitmapDescriptorFactory
							//		.fromResource(R.drawable.))
							;
							marqueur_rdv_courant = theMap.addMarker(options);
							Event evt = new Event();
							user.close_events.add(evt);
						}
						else{
							options.position(point);
							//theMap.clear();
							marqueur_rdv_courant = theMap.addMarker(options);
						}
						//new GetTask().execute(point);
						addMarker = false;
						//lancer activit� partager � : avec des checkboxes
						//
						Intent intent3 = new Intent(ActivitePrincipale.this, PartageRDV.class);
				  		startActivity(intent3);
						}
						
					}	
				});
				theMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
					
					@Override
					public boolean onMarkerClick(Marker arg0) { // deux cas : si c'est un Event on ouvre l'activité partager avec : PartagerRDV.class
						//et si c'est un contact on ouvre la conversation
						int evtORctct=2; // 0 si c'est un event 1 si c'est un contact, 2 sinon (soi-même)
						// TODO Auto-generated method stub
						//On affiche une bulle dans la même fenetre qui contient un ou plusieurs boutons
						Toast.makeText(getApplicationContext(), 
								String.valueOf(arg0.getPosition().latitude), Toast.LENGTH_LONG).show();
						
						for(Contact contact : user.close_contacts){
							if(arg0.getPosition()==contact.latlng){
								//Contact currContact = new Contact();
								//CURRENT_CONTACT = contact;
								evtORctct = 1;
								break;
							}
							else {for(Event event : user.close_events){
								if(arg0.getPosition()==event.latlng){
									//Event currEvent = new Event();
									//CURRENT_EVENT = event;
									evtORctct = 0;
									break;
							}
						}
							}
						 try {
							 Thread.sleep(3000);		
							  } catch (Exception e) {
							    System.out.println("Le thread s'est arrêté avant !");
							  }
						 Intent intent4 = new Intent();
						 if(evtORctct==0){
						intent4 = new Intent(ActivitePrincipale.this, PartageRDV.class);}
						 else if(evtORctct==1){
							 intent4 = new Intent(ActivitePrincipale.this, conversationActivity.class);
						 }
						 //IL FAUT CHANGER L ACTIVITE OUVERTE ICI PUIS DECOMMENTER startActivity
						 
			        	  final String POSITION="position";
			        	//intent4.putExtra(CURRENT_CONTACT, "Contact courant");
			        	  //intent4.putExtra("CURRENT_CONTACT", value);
			        	  
			        	  if(evtORctct==2){
			        		  return true;
			        	  }
			        	  else if(evtORctct==0 || evtORctct==1){
				  		startActivity(intent4);}
						}
						return false;
						}
				});			
				
				  

				user.printContacts(theMap);
				
				//mise a jour de la position de l'utilisateur et des points d'interets obtenus
				//par les requetes places API
				try {
					updatePlaces();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("Rien ne va plus","c'est la dèche");
					Log.e(ACTIVITY_SERVICE, "essaye encore");
				}
			}
		}
		
	    final Button contactsButton = (Button) findViewById(R.id.Contacts);
	    final Button optionsButton = (Button) findViewById(R.id.Options);
	    final Button addMarkerButton = (Button) findViewById(R.id.AddMarkerRDV);
	    final Button CreerCompteButton = (Button) findViewById(R.id.CreerCompte);
	    
	    contactsButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
	  
		  		Intent intent = new Intent(ActivitePrincipale.this, ContactsActivity.class);
		  		startActivity(intent);
		  		/*try {
					Utilisateur user=new Utilisateur("Hugues","le mecha shark", "007856784");
			  		Contact hug= new Contact();
			  		//hug=user.add_one_contact_to_server("+33604441320");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		  		//Utilisateur user=new Utilisateur(21);
		  		//user.test_requete(); // marche
		  		/*try {
					user.update_close_contact_list();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
		  		
		  		
		  		//Utilisateur user=new Utilisateur(1);
		  		//user.update_close_contact_list();
		  		//user.print_close_contact_list();
		  		
		  		/*
		  		locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		  		//obtention de la dernier position
		  		Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		  		double lat = lastLoc.getLatitude();
		  		double lng = lastLoc.getLongitude();
		  		//creation d'un objet LatLng
		  		LatLng lastLatLng = new LatLng(lat, lng);

		  		user.position= lastLoc;
		  		user.update_location();*/
	    		}
	    	});
	   
	    optionsButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
		  		Intent intent2 = new Intent(ActivitePrincipale.this, OptionsActivity.class);
		  		startActivity(intent2);
	    		  
	    		}
	    	});
	    
	    
	    CreerCompteButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
		  		Intent intent3 = new Intent(ActivitePrincipale.this, CreerCompteActivity.class);
		  		startActivity(intent3);
	    		  
	    		}
	    	});
	    
	    
	    
	    addMarkerButton.setOnClickListener(new OnClickListener(){
	    	@Override
	    	public void onClick(View v){
	    		//Mettre � un l'ajout de marqueur;
			    addMarker=true;
	    		
	    		//Afficher un toast
	    		Toast.makeText(getApplicationContext(), 
                         "Veuillez cliquer sur la carte pour ajouter le marqueur", Toast.LENGTH_LONG).show();
	    	}
	    });
	   
	    
	    
	   
	}
  

@Override
public void onLocationChanged(Location location) {
	Log.v("ActivitePrincipale", "location changed");
	try {
		updatePlaces();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Override
public void onProviderDisabled(String provider){
	Log.v("ActivitePrincipale", "provider disabled");
}
@Override
public void onProviderEnabled(String provider) {
	Log.v("ActivitePrincipale", "provider enabled");
}
@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	Log.v("ActivitePrincipale", "status changed");
}



private void updatePlaces() throws Exception{
	//on obtient le gestionnaire de lieu "location manager"
	locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//obtention de la dernier position
	Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	double lat = lastLoc.getLatitude();
	double lng = lastLoc.getLongitude();
	//creation d'un objet LatLng
	LatLng lastLatLng = new LatLng(lat, lng);

	user.position= lastLoc;
	user.update_location();
	//suppression de tous les marqueurs deja existants
	if(userMarker!=null) userMarker.remove();
	//creation et initialisation des proprietes du marqueur de l'utilisateur
	userMarker = theMap.addMarker(new MarkerOptions()
	.position(lastLatLng)
	.title("You are here")
	.icon(BitmapDescriptorFactory.fromResource(userIcon))
	.snippet("Your last recorded location"));
	
	user.update_close_contact_list();
	for(Contact contact : user.close_contacts){
		/*contactMarker = */theMap.addMarker(new MarkerOptions()
		.position(contact.latlng)
		.title(contact.name)
		.icon(BitmapDescriptorFactory.fromResource(friendIcon))
		.snippet(contact.surname + " A.K.A " + contact.pseudo));;
	}
	
	//deplacement vers la position actuelle de l'utilisateur
	if(camCentering==true){			//on ne zoom automatiquement que lorsque l'utilisateur ouvre l'appli
		theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng,defaultZoom), 3000, null);
		camCentering=false;
		}
	//construction des strings des requetes de POI
	String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
			"json?location="+lat+","+lng+
			"&radius=1000&sensor=true" +
			"&types=food|bar|store|museum|art_gallery"+
			"&key=AIzaSyDlg-eY0KiMruBtVXRcpiZRkD4qpmrn5C8";
	
	//execution de la requete
	//new GetPlaces().execute(placesSearchStr);
	
	locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
}

private class GetPlaces extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... placesURL) {
		//obtention des POI
		
		//construction du resultat en tant que string
		StringBuilder placesBuilder = new StringBuilder();
		//process search parameter string(s)
		for (String placeSearchURL : placesURL) {
			HttpClient placesClient = new DefaultHttpClient();
			try {
				//essaie d'obtenir les donnees
				
				//HTTP Get re�oit la string URL
				HttpGet placesGet = new HttpGet(placeSearchURL);
				//execution GET avec le Client - retour de la reponse
				HttpResponse placesResponse = placesClient.execute(placesGet);
				//verification du statut de la reponse
				StatusLine placeSearchStatus = placesResponse.getStatusLine();
				//on ne continue que si la reponse est positive
				if (placeSearchStatus.getStatusCode() == 200) {
					//obtention de l'entite de reponse
					HttpEntity placesEntity = placesResponse.getEntity();
					//get input stream setup
					InputStream placesContent = placesEntity.getContent();
					//creation du lecteur
					InputStreamReader placesInput = new InputStreamReader(placesContent);
					//utilisation du lecteur bufferise pour traiter l'information
					BufferedReader placesReader = new BufferedReader(placesInput);
					//lecture ligne par ligne, append to string builder
					String lineIn;
					while ((lineIn = placesReader.readLine()) != null) {
						placesBuilder.append(lineIn);
					}
				}
			}
			catch(Exception e){ 
				e.printStackTrace(); 
			}
		}
		return placesBuilder.toString();
	}
	//process data retrieved from doInBackground
	protected void onPostExecute(String result) {
		//analyse des des donnees de lieux retournes par l'API Google Places
		//suppression des marqueurs existants
		if(placeMarkers!=null){
			for(int pm=0; pm<placeMarkers.length; pm++){
				if(placeMarkers[pm]!=null)
					placeMarkers[pm].remove();
			}
		}
		try {
			//parse JSON
			
			//creation du JSONObject, pass string returned from doInBackground
			JSONObject resultObject = new JSONObject(result);
			//obtention du tableau de resultats
			JSONArray placesArray = resultObject.getJSONArray("results");
			//les MarkerOptions de chaque lieu retournes
			places = new MarkerOptions[placesArray.length()];
			//on boucle sur les lieux
			for (int p=0; p<placesArray.length(); p++) {
				//analyse chaque lieu
				//s'il manque des valeurs on ne montre pas le marqueur
				boolean missingValue=false;
				LatLng placeLL=null;
				String placeName="";
				String vicinity="";
				int currIcon = otherIcon;
				try{
					//essaie de recuperer les valeurs des donnees du lieu
					missingValue=false;
					//on obtient le lieu a cet indice
					JSONObject placeObject = placesArray.getJSONObject(p);
					//on obtient la geometrie du lieu
					JSONObject loc = placeObject.getJSONObject("geometry")
							.getJSONObject("location");
					//on lit lat lng
					placeLL = new LatLng(Double.valueOf(loc.getString("lat")), 
							Double.valueOf(loc.getString("lng")));	
					//on recupere les types
					JSONArray types = placeObject.getJSONArray("types");
					//on boucle pour tous les types
					for(int t=0; t<types.length(); t++){
						//quel type est-ce
						String thisType=types.get(t).toString();
						//on cherche pour des types particuliers puis on etablit les icones
						if(thisType.contains("food")){
							currIcon = foodIcon;
							break;
						}
						else if(thisType.contains("bar")){
							currIcon = drinkIcon;
							break;
						}
						else if(thisType.contains("store")){
							currIcon = shopIcon;
							break;
						}
					}
					//voisinage
					vicinity = placeObject.getString("vicinity");
					//nom du lieu
					placeName = placeObject.getString("name");
				}
				catch(JSONException jse){
					Log.v("PLACES", "missing value"); //on ecrit un message d'erreur dans la stacktrace si les infos d'un lieu sont incompletes
					missingValue=true;
					jse.printStackTrace();
				}
				//l'affichage ne se fait que si aucune valeur n'est NULL
				if(missingValue)	places[p]=null;
				else
					places[p]=new MarkerOptions()
				.position(placeLL)
				.title(placeName)
				.icon(BitmapDescriptorFactory.fromResource(currIcon))
				.snippet(vicinity);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if(places!=null && placeMarkers!=null){
			for(int p=0; p<places.length && p<placeMarkers.length; p++){
				//sera NULL s'il manque une valeur
				if(places[p]!=null)
					placeMarkers[p]=theMap.addMarker(places[p]);
			}
		}		
	}
}
@Override
protected void onResume() {
	super.onResume();
	if(theMap!=null){
		locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
	}
}
@Override
protected void onPause() {
	super.onPause();
	if(theMap!=null){
		locMan.removeUpdates(this);
	}
}
}
