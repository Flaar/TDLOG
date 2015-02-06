package com.locolize.geoloc_project;

import android.location.Location;
import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;

public class Event extends LPOI{
	//public int id;
	//public String name;
	//public Location position;
	public String description;
	public Contact owner;
	public ArrayList<Contact> contacts_invited;	
	public Boolean public_event;
	public Date begining;
	public Date end;
	
	public void print_event(){
		System.out.println("id: " + id + System.getProperty("line.separator"));
		System.out.println("name: " + name + System.getProperty("line.separator"));
		System.out.println("description: " + description + System.getProperty("line.separator"));
		System.out.println("owner: " + owner.name + System.getProperty("line.separator"));
		System.out.println("position: " + position + System.getProperty("line.separator"));
		System.out.println("public_event: " + public_event + System.getProperty("line.separator"));
		System.out.println("date begining:" + begining + System.getProperty("line.separator"));
		System.out.println("date end:" + end + System.getProperty("line.separator"));
	}
	
	public void print_on_screen(){
	}
	
}
	

