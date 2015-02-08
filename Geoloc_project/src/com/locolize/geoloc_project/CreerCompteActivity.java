package com.locolize.geoloc_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CreerCompteActivity extends Activity {
  

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.creer_compte_layout);
    
    final Button create_account_button = (Button) findViewById(R.id.create_account);
    
    final EditText first_name = (EditText) findViewById(R.id.first_name);
    final EditText last_name = (EditText) findViewById(R.id.last_name);
    final EditText phone_number = (EditText) findViewById(R.id.phone_number);

    create_account_button.setOnClickListener(new View.OnClickListener() 
    {
        public void onClick(View v) 
        {                
            String firstName = first_name.getText().toString();
            String lastName = last_name.getText().toString();
            String phoneNumber = phone_number.getText().toString();
            
            if (firstName.length()>0 && lastName.length()>0 && phoneNumber.length()>0)
            {
            	try {
					Utilisateur User=new Utilisateur(firstName,lastName, phoneNumber);
					System.out.println("l'id de l'utilisateur est " + User.client_id);
			  		//hug=user.add_one_contact_to_server("+33604441320");
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
            else
                Toast.makeText(getBaseContext(), 
                    "Précisez tous les détails s'il vous plaît.", 
                    Toast.LENGTH_SHORT).show();
        }
    });
    
    
    
    
    
  }
}