package com.locolize.geoloc_project;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;

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
 
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

public class ActivitePrincipale extends Activity {
	  static final LatLng HAMBURG = new LatLng(53.558, 9.927);
	  static final LatLng KIEL = new LatLng(53.551, 9.993);
	  private GoogleMap map;
	  private int userIcon, foodIcon, drinkIcon, shopIcon, otherIcon;
	  private LocationManager locMan;
	  private Marker userMarker;
	  
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_activite_principale);
	
	  	
	  	userIcon = R.drawable.yellow_point;
	  	foodIcon = R.drawable.red_point;
	  	drinkIcon = R.drawable.blue_point;
	  	shopIcon = R.drawable.green_point;
	  	otherIcon = R.drawable.purple_point;
	        
	  	if(map==null){
	  		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	                .getMap();}
	            
	  	
	            if (map!=null){
	            	
	            	map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	            	
	              Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
	                  .title("Hamburg"));
	              Marker kiel = map.addMarker(new MarkerOptions()
	                  .position(KIEL)
	                  .title("Kiel")
	                  .snippet("Kiel is cool")
	                  .icon(BitmapDescriptorFactory
	                      .fromResource(R.drawable.ic_launcher)));
	              
	              updatePlaces();
	        
	            }
	    }
	  
	  private void updatePlaces(){
      	//updates location
		  if(userMarker!=null) userMarker.remove();
		  locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		  Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		  double lat = lastLoc.getLatitude();
		  double lng = lastLoc.getLongitude();
		  LatLng lastLatLng = new LatLng(lat, lng);
		  userMarker = map.addMarker(new MarkerOptions()
          .position(lastLatLng)
          .title("You are here")
          .icon(BitmapDescriptorFactory.fromResource(userIcon))
          .snippet("Your last recorded location"));
		  
		  map.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 3000, null);
		  
		  String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
				    "json?location="+lat+","+lng+
				    "&radius=1000&sensor=true" +
				    "&types=food|bar|store|museum|art_gallery"+
				    "&key=AIzaSyDlg-eY0KiMruBtVXRcpiZRkD4qpmrn5C8";
		  
      	}
	  
	  private class GetPlaces extends AsyncTask<String, Void, String> {
		//fetches and parses place data
		  
		  @Override
		  protected String doInBackground(String... placesURL) {
		      //fetches places
			  StringBuilder placesBuilder = new StringBuilder();
			//process search parameter string(s)
			  for (String placeSearchURL : placesURL) {
			  //executes search
				  HttpClient placesClient = new DefaultHttpClient();
				  try {
					    //try to fetch the data
					  HttpGet placesGet = new HttpGet(placeSearchURL);
					  HttpResponse placesResponse = placesClient.execute(placesGet);
					  StatusLine placeSearchStatus = placesResponse.getStatusLine();
					  if (placeSearchStatus.getStatusCode() == 200) {
						//we have an OK response
						  HttpEntity placesEntity = placesResponse.getEntity();
						  InputStream placesContent = placesEntity.getContent();
						  InputStreamReader placesInput = new InputStreamReader(placesContent);
						  BufferedReader placesReader = new BufferedReader(placesInput);
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
		  
		 // protected void onPostExecute(String result) {
			    //parse place data returned from Google Places
		//	}
		  
		}
	  
	}
