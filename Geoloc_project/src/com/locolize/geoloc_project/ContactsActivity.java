package com.locolize.geoloc_project;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import java.util.List;


public class ContactsActivity extends Activity {
  
  
  private ListView mListContacts = null;
  private String[] mContacts = null;
  final String POSITION="position";

  DatabaseHandler database;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.contactslayout);
    
    database = new DatabaseHandler(this);
    
    if (database.getAllContacts().size() == 0 )
    	add_all_phone_contacts_to_database(database);
    
    List<Contact> list_of_contacts;
    list_of_contacts=database.getAllContacts();
    
    mContacts=new String[list_of_contacts.size()];
    for(int i=0; i<list_of_contacts.size(); i++)
    {
    	mContacts[i]=list_of_contacts.get(i).name;
    }
    //System.out.println("la taille du répertoire est: "+ list_of_contacts.size());
    
    
    mListContacts = (ListView) findViewById(R.id.listcontacts);
    //mContacts = new String[]{"Hugues", "Vassily", "Benjamin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin"
    //, "Robin", "Charles", "Romain", "Robin", "Eric", "Robin", "Robin", "Claude", "Robin"};


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
  
  public void add_all_phone_contacts_to_database(DatabaseHandler database){
		//System.out.println("P1");
	  	Log.d("Insert: ", "Inserting all the contacts ..");
	  	Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	  	//System.out.println("P2");
	      while (phones.moveToNext())
	      {
	      	String contact_name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	      	String contact_phone_number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	      	//System.out.println("p3");
	      	System.out.println(contact_name);
	      	//System.out.println(contact_phone_number);

	      	Contact this_contact=new Contact(contact_name, "dit Schilton", contact_phone_number);
	      	database.addContact(this_contact);

	      }
	      phones.close();    
	  }
  
  
  class ContactsThread implements Runnable {
	  @Override
  	  public void run() {
		  DatabaseHandler database2=new DatabaseHandler(ContactsActivity.this);
		  Log.d("Insert: ", "Inserting all the contacts ..");
		  	Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
		  	//System.out.println("P2");
		      while (phones.moveToNext())
		      {
		      	String contact_name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		      	String contact_phone_number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		      	//System.out.println("p3");
		      	System.out.println(contact_name);
		      	//System.out.println(contact_phone_number);

		      	Contact this_contact=new Contact(contact_name, "dit Schilton", contact_phone_number);
		      	database2.addContact(this_contact);

		      }
		      phones.close();
		      
		      
	  }
}
   
}

