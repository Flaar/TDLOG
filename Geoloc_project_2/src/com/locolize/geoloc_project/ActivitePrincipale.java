package com.locolize.geoloc_project;

import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class ActivitePrincipale extends Activity implements LocationListener{

	private int userIcon, foodIcon, drinkIcon, shopIcon, otherIcon;
	private GoogleMap theMap;
	private LocationManager locMan;
	private Marker userMarker;
	//points d'interet :
	private Marker[] placeMarkers;
	//nombre maximal de lieux retournes
	private final int nb_max_lieux = 20;//obtenus pour la plupart par l'API Google Places
	private MarkerOptions[] places;
	private MarkerOptions options = new MarkerOptions();
	
	
	 //static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	 //static final LatLng KIEL = new LatLng(53.551, 9.993);
	
@Override
public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_activite_principale);

		userIcon = R.drawable.yellow_point;
		foodIcon = R.drawable.red_point;
		drinkIcon = R.drawable.blue_point;
		shopIcon = R.drawable.green_point;
		otherIcon = R.drawable.purple_point;

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
							theMap.addMarker(options);
						}
						else{
							options.position(point);
							//theMap.clear();
							theMap.addMarker(options);
						}
						//new GetTask().execute(point);
					}	
				});
				  /*Marker hamburg = theMap.addMarker(new MarkerOptions().position(HAMBURG)
						  	                  .title("Hamburg"));
						  	              Marker kiel = theMap.addMarker(new MarkerOptions()
						  	                  .position(KIEL)
						  	                  .title("Kiel")
						  	                  .snippet("Kiel is cool")
						  	                  .icon(BitmapDescriptorFactory
						  	                      .fromResource(R.drawable.ic_launcher)));*/
				
				
				//mise a jour de la position de l'utilisateur et des points d'interets obtenus
				//par les requetes places API
				updatePlaces();
			}
		}
		
	    final Button contactsButton = (Button) findViewById(R.id.Contacts);
	    final Button optionsButton = (Button) findViewById(R.id.Options);
	    
	    
	    contactsButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
	  
		  		Intent intent = new Intent(ActivitePrincipale.this, ContactsActivity.class);
		  		startActivity(intent);
	    		  
	    		}
	    	});
	   
	    optionsButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
		  		Intent intent2 = new Intent(ActivitePrincipale.this, OptionsActivity.class);
		  		startActivity(intent2);
	    		  
	    		}
	    	});
	   
	}

@Override
public void onLocationChanged(Location location) {
	Log.v("ActivitePrincipale", "location changed");
	updatePlaces();
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

private void updatePlaces(){
	//on obtient le gestionnaire de lieu "location manager"
	locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	//obtention de la dernier position
	Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	double lat = lastLoc.getLatitude();
	double lng = lastLoc.getLongitude();
	//creation d'un objet LatLng
	LatLng lastLatLng = new LatLng(lat, lng);

	//suppression de tous les marqueurs deja existants
	if(userMarker!=null) userMarker.remove();
	//create and set marker properties
	userMarker = theMap.addMarker(new MarkerOptions()
	.position(lastLatLng)
	.title("You are here")
	.icon(BitmapDescriptorFactory.fromResource(userIcon))
	.snippet("Your last recorded location"));
	//move to location
	theMap.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 3000, null);
	
	//build places query string
	String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
			"json?location="+lat+","+lng+
			"&radius=1000&sensor=true" +
			"&types=food|bar|store|museum|art_gallery"+
			"&key=AIzaSyDlg-eY0KiMruBtVXRcpiZRkD4qpmrn5C8";
	
	//execute query
	new GetPlaces().execute(placesSearchStr);
	
	locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
}

private class GetPlaces extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... placesURL) {
		//fetch places
		
		//build result as string
		StringBuilder placesBuilder = new StringBuilder();
		//process search parameter string(s)
		for (String placeSearchURL : placesURL) {
			HttpClient placesClient = new DefaultHttpClient();
			try {
				//try to fetch the data
				
				//HTTP Get receives URL string
				HttpGet placesGet = new HttpGet(placeSearchURL);
				//execute GET with Client - return response
				HttpResponse placesResponse = placesClient.execute(placesGet);
				//check response status
				StatusLine placeSearchStatus = placesResponse.getStatusLine();
				//only carry on if response is OK
				if (placeSearchStatus.getStatusCode() == 200) {
					//get response entity
					HttpEntity placesEntity = placesResponse.getEntity();
					//get input stream setup
					InputStream placesContent = placesEntity.getContent();
					//create reader
					InputStreamReader placesInput = new InputStreamReader(placesContent);
					//use buffered reader to process
					BufferedReader placesReader = new BufferedReader(placesInput);
					//read a line at a time, append to string builder
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
		//parse place data returned from Google Places
		//remove existing markers
		if(placeMarkers!=null){
			for(int pm=0; pm<placeMarkers.length; pm++){
				if(placeMarkers[pm]!=null)
					placeMarkers[pm].remove();
			}
		}
		try {
			//parse JSON
			
			//create JSONObject, pass stinrg returned from doInBackground
			JSONObject resultObject = new JSONObject(result);
			//get "results" array
			JSONArray placesArray = resultObject.getJSONArray("results");
			//marker options for each place returned
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
					Log.v("PLACES", "missing value");
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
