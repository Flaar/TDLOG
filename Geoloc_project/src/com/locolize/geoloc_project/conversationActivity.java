package com.locolize.geoloc_project;
import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.app.PendingIntent;
import android.telephony.SmsManager;
import java.util.List;
import java.util.ArrayList;

public class conversationActivity extends Activity {
  
  private ListView mlistMessages = null;
  private String[] mMessages = null;
  private String[] mNumbers=null;
  
  final String POSITION="position";
  int pos=0;
  List<String> texte;
  
  DatabaseHandler database;
  Contact contact_courant;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversationlayout);
    Intent intent = getIntent();
    
    
    database = new DatabaseHandler(this);
    
    List<Contact> list_of_contacts;
    list_of_contacts=database.getAllContacts();
    
    mNumbers=new String[list_of_contacts.size()];
    for(int i=0; i<list_of_contacts.size(); i++)
    {
    	mNumbers[i]=list_of_contacts.get(i).phone_number;
    }
    
    
    
    
    
    mlistMessages = (ListView) findViewById(R.id.listmessages);
    mMessages = new String[list_of_contacts.size()];

    texte = new ArrayList<String>();
    
    if (intent != null)
    	pos=intent.getIntExtra(POSITION,-1);
    
    
    //database.getMessage(database.getAllContacts().get(pos)))
    contact_courant=database.getAllContacts().get(pos);
    
    String[] texte2= database.getMessage(contact_courant).split("STOP");
    for(int i=0; i<texte2.length; i++)
    {
    	texte.add(texte2[i]);
    }
    
    
    mlistMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, texte));
    
    
    
    final Button btnSendSMS = (Button) findViewById(R.id.Send_button);
    final EditText txtmessage = (EditText) findViewById(R.id.nouveau_message);

    btnSendSMS.setOnClickListener(new View.OnClickListener() 
    {
        public void onClick(View v) 
        {                
            String phoneNo = mNumbers[pos];
            String message = txtmessage.getText().toString();                 
            if (phoneNo.length()>0 && message.length()>0)
            {
                sendSMS(phoneNo, message);
            	Toast.makeText(conversationActivity.this, "SMS envoyé", Toast.LENGTH_SHORT).show();
            	
            	database.updateMessage(database.getMessage(contact_courant) + "STOP" +message, contact_courant);
                texte.add(message);

                
                
                mlistMessages.setAdapter(new ArrayAdapter<String>(conversationActivity.this, android.R.layout.simple_list_item_1, texte));
            	
            	
            	//texte.add(message);
            	//mlistMessages.setAdapter(new ArrayAdapter<String>(conversationActivity.this, android.R.layout.simple_list_item_1, texte));
            	txtmessage.setText("");
            	//database.updateMessage(message, database.getAllContacts().get(pos));
            }
            else
                Toast.makeText(getBaseContext(), 
                    "Please enter both phone number and message.", 
                    Toast.LENGTH_SHORT).show();
        }
    });
    
      
  }
  
  private void sendSMS(String phoneNumber, String message)
  {        
      //PendingIntent pi = PendingIntent.getActivity(this, 0,new Intent(this, conversationActivity.class), 0);                
      SmsManager sms = SmsManager.getDefault();
      sms.sendTextMessage(phoneNumber, null, message, null, null);        
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
}