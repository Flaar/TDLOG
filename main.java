package com.locolize.geoloc_project;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapController;
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
	        setContentView(R.layout.activity_activite_principale);
	        MapView mMapView = (MapView) findViewById(R.id.mapview);
	        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
	        mMapView.setBuiltInZoomControls(true);
	        MapController mMapController = (MapController) mMapView.getController();
	        mMapController.setZoom(15);
	        GeoPoint gPt = new GeoPoint(51500000, -150000);
	        mMapController.setCenter(gPt);
	    }
	}
