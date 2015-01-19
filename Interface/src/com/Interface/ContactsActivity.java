package com.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class ContactsActivity extends Activity {
  
  
  private ListView mListContacts = null;

  private String[] mContacts = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.contactslayout);
    

    mListContacts = (ListView) findViewById(R.id.listcontacts);
    mContacts = new String[]{"Hugues", "Vassily", "Benjamin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin"
    		, "Robin", "Charles", "Romain", "Robin", "Eric", "Robin", "Robin", "Claude", "Robin"};


    mListContacts.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mContacts));
    
   
  }
}
