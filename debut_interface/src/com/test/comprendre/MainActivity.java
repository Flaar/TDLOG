package com.test.comprendre;

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
	    
	    final Button optionsButton = (Button) findViewById(R.id.Contacts);
	    optionsButton.setOnClickListener(new OnClickListener() {
			
	    	  @Override
	    	  public void onClick(View v) {
	    		Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
	    		startActivity(intent);
	    		}
	    	});
	   
	    
	    	//setContentView(R.layout.activity_main);
	   
	}
}