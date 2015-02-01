package com.locolize.geoloc_project;

import com.google.android.gms.maps.model.LatLng;

import android.location.Location;

public class LPOI {
	
	public int id;
	public Location position;
	public LatLng latlng;
	public String name;
	
	//*****ACCESSEURS ET MUTATEURS
	public void setId(int ident){this.id = ident;}
	public void setPosition(Location pos){this.position = pos;}
	public void setLatLng(LatLng lalo){this.latlng=lalo;}
	public void setName(String nom){this.name=nom;}
	
	public int getId(){return this.id;}
	public Location getPosition(){return this.position;}
	public LatLng getLatLng(){return this.latlng;}
	public String getName(){return this.name;}

}
