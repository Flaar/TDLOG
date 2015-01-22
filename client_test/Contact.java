package com.locolize.client_test;
import android.location.Location;

import java.util.Calendar;
import java.util.Date;

public class Contact {
	public int id;
	public int phone_number;
	public String surname;
	public String name;
	public Location GPS_coordinates;
	public Boolean visible;// je ne sais pas si le tri se fait à ce niveau ou plutôt sur le serveur
	public Date last_connection;
	
	public Contact(String new_name, String new_surname, int new_phone_number){
		phone_number=new_phone_number;
		name=new_name;
		surname=new_surname;
		last_connection = Calendar.getInstance().getTime();
		visible=true;
		}
	
	public Contact(int new_id,String new_name, String new_surname, int new_phone_number){
		phone_number=new_phone_number;
		id=new_id;
		name=new_name;
		surname=new_surname;
		last_connection = Calendar.getInstance().getTime();
		visible=true;
		}
	
	public Contact(){
		last_connection = Calendar.getInstance().getTime();
		visible=true;
		}
	
	public void update(Location new_GPS, Boolean is_visible, Date new_date){
		GPS_coordinates=new_GPS;
		visible=is_visible;
		last_connection=new_date;
	}
	
	public void print_contact(){
		System.out.println("id: " + id + System.getProperty("line.separator"));
		System.out.println("phone_number: " + phone_number + System.getProperty("line.separator"));
		System.out.println("surname: " + surname + System.getProperty("line.separator"));
		System.out.println("name: " + name + System.getProperty("line.separator"));
		System.out.println("GPS_coordinates: " + GPS_coordinates + System.getProperty("line.separator"));
		System.out.println("visible: " + visible + System.getProperty("line.separator"));
		System.out.println("last_connection:" + last_connection + System.getProperty("line.separator"));
	}
	
	public void print_on_screen(){
	}
	
}
	