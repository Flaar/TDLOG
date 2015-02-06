package com.Interface;

//N'oubliez pas de déclarer le bon package dans lequel se trouve le fichier !


import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;



public class MainActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_main);
	    
	    final Button contactsButton = (Button) findViewById(R.id.Contacts);
	    final Button optionsButton = (Button) findViewById(R.id.Options);
	    final Button CreerCompteButton = (Button) findViewById(R.id.CreerCompte);
	    final Button SMSButton = (Button) findViewById(R.id.SMSactivity);
	    
	    contactsButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
	  
		  		Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
		  		startActivity(intent);
	    		  
	    		}
	    	});
	   
	    optionsButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
		  		Intent intent2 = new Intent(MainActivity.this, OptionsActivity.class);
		  		startActivity(intent2);
	    		  
	    		}
	    	});
	    
	    CreerCompteButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
		  		Intent intent3 = new Intent(MainActivity.this, CreerCompteActivity.class);
		  		startActivity(intent3);
	    		  
	    		}
	    	});
	    
	    SMSButton.setOnClickListener(new OnClickListener() {
	    	  @Override
	    	  public void onClick(View v) {
		  		Intent intent = new Intent(MainActivity.this, SMS.class);
		  		startActivity(intent);
	    		  
	    		}
	    	});
	   
	}
}