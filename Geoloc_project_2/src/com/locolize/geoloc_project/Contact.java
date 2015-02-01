package com.locolize.geoloc_project;

import android.location.Location;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Contact extends LPOI{
	//public int id;
	public int phone_number;
	public String surname;
	//public String name;
	public String pseudo;
	//public Location position;
	//public LatLng latlng;
	public Boolean visible;// je ne sais pas si le tri se fait à ce niveau ou plutôt sur le serveur
	public Date last_connection;
	//public Bitmap marqueurIcone; //l'icone du contact;
	
	public Contact(String new_name, String new_surname, int new_phone_number){
		phone_number=new_phone_number;
		name=new_name;
		surname=new_surname;
		last_connection = Calendar.getInstance().getTime();
		visible=true;}
	
	public Contact(int new_id,String new_name, String new_surname, int new_phone_number){
		phone_number=new_phone_number;
		id=new_id;
		name=new_name;
		surname=new_surname;
		last_connection = Calendar.getInstance().getTime();
		visible=true;}
	
	public Contact(){
		last_connection = Calendar.getInstance().getTime();
		visible=true;
		//this.position = new Location();
		}
	
	public void update(Location new_GPS, Boolean is_visible, Date new_date){
		position=new_GPS;
		visible=is_visible;
		last_connection=new_date;}
	
	public void print_contact(){
		System.out.println("id: " + id + System.getProperty("line.separator"));
		System.out.println("phone_number: " + phone_number + System.getProperty("line.separator"));
		System.out.println("surname: " + surname + System.getProperty("line.separator"));
		System.out.println("name: " + name + System.getProperty("line.separator"));
		System.out.println("position: " + position + System.getProperty("line.separator"));
		System.out.println("visible: " + visible + System.getProperty("line.separator"));
		System.out.println("last_connection:" + last_connection + System.getProperty("line.separator"));
	}
	
	//cette méthode permet de creer un marqueur à la position du contact
	//ensuite on va faire un theMap.addMarker(createMarker(Contact contact));
	public MarkerOptions createMarker(){
		//double lat = this.position.getLatitude();
		//double lng = this.position.getLongitude();
		
		//LatLng latlng = new LatLng(lat,lng);
		MarkerOptions options = new MarkerOptions()
					.title(this.pseudo)
					.position(this.latlng)
					;
		return options;
	}
	
	
	//*****************ACCESSEURS ET MUTATEURS**************
	public void setId(int ident){this.id=ident;}
	public void setNumber(int num){this.phone_number=num;}
	public void setSurname(String prenom){this.surname=prenom;}
	public void setName(String nom){this.name=nom;}
	public void setPseudo(String alias){this.pseudo=alias;}
	public void setPosition(Location pos){this.position = pos;}
	public void setLatLng(LatLng latlong){this.position.setLatitude(latlong.latitude); this.position.setLongitude(latlong.longitude);}
	public int getId(){return this.id;}
	public int getNum(){return this.phone_number;}
	public String getSurname(){return this.surname;}
	public String getName(){return this.name;}
	public String getPseudo(){return this.pseudo;}
	public Location getPosition(){return this.position;}
	public LatLng getLatLng(){double lat = this.position.getLatitude(); double lon=this.position.getLongitude(); LatLng lalo = new LatLng(lat,lon); return lalo;}
}	
