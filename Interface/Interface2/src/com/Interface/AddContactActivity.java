package com.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class AddContactActivity extends Activity {
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_contact_layout);
    
    
	final Button add_Button = (Button) findViewById(R.id.add_button);
	
	
	
	add_Button.setOnClickListener(new OnClickListener() {
    	  @Override
    	  public void onClick(View v) {
  
	  		
    		  
    		}
    	});
    
    
    
    
    
    
    
  }
}
