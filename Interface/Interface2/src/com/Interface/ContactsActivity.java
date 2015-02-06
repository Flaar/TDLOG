package com.Interface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

public class ContactsActivity extends Activity {
  
  
  private ListView mListContacts = null;
  private String[] mContacts = null;
  final String POSITION="position";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.contactslayout);
    

    mListContacts = (ListView) findViewById(R.id.listcontacts);
    mContacts = new String[]{"Hugues", "Vassily", "Benjamin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin"
    		, "Robin", "Charles", "Romain", "Robin", "Eric", "Robin", "Robin", "Claude", "Robin"};


    mListContacts.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mContacts));
    
    
    mListContacts.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        	Intent intent = new Intent(ContactsActivity.this, conversationActivity.class);
        	intent.putExtra(POSITION, position);
        	
	  		startActivity(intent);
        }
    });
    
   
    final Button addButton = (Button) findViewById(R.id.addContactButton);
    
    addButton.setOnClickListener(new OnClickListener() {
    	  @Override
    	  public void onClick(View v) {
  
	  		Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
	  		startActivity(intent);
    		  
    		}
    	});
    
    
    
  }
}
