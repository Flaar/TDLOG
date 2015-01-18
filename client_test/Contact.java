package com.locolize.client_test;
import android.location.Location;

import java.util.Date;

public class Contact {
	public int id;
	public int phone_number;
	public String surname;
	public String name;
	public Location GPS_coordinates;
	public Boolean visible;// je ne sais pas si le tri se fait à ce niveau ou plutôt sur le serveur
	public Date last_connection;
	
	public void update(Location new_GPS, Boolean is_visible, Date new_date){
		GPS_coordinates=new_GPS;
		visible=is_visible;
		last_connection=new_date;
	}
	public void print_on_screen(){
	}
	
}
	