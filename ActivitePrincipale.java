package com.locolize.geoloc_project;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
//import org.osmdroid.views.*;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class ActivitePrincipale extends Activity {
	   public void onCreate(Bundle savedInstanceState){

	        super.onCreate(savedInstanceState);



	        MapView mapView = new MapView(this, 256); //constructor

	        mapView.setClickable(true);

	        mapView.setBuiltInZoomControls(true);

	        setContentView(mapView); //displaying the MapView

	        mapView.getController().setZoom(15); //set initial zoom-level, depends on your need

	        mapView.getController().setCenter(new GeoPoint(48.85, 2.3));
	        
	        mapView.setUseDataConnection(false); //keeps the mapView from loading online tiles using network connection.

	    }
	}