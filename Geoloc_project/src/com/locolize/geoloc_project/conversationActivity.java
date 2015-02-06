package com.locolize.geoloc_project;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
import android.app.PendingIntent;
import android.telephony.SmsManager;


public class conversationActivity extends Activity {
  
  private ListView mlistMessages = null;
  private String[] mMessages = null;
  private String[] mNumbers=null;
  
  final String POSITION="position";
  int pos=0;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversationlayout);
    Intent intent = getIntent();
    
    mlistMessages = (ListView) findViewById(R.id.listmessages);
    mMessages = new String[]{"Bonjour \nComment vas-tu?", "Ca va?", "Bien et toi?", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin"
    		, "Robin", "Charles", "Romain", "Robin", "Eric", "Robin", "Robin", "Claude", "Robin"};

    String[] mSpecificMessage = new String [1];
    
    if (intent != null)
    {
    	pos=intent.getIntExtra(POSITION,-1);
    	mSpecificMessage[0]=mMessages[pos];
    }

    mlistMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mSpecificMessage));
    
    mNumbers=new String[] {"0662598022"};
    
    
    
    
    
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
            	mMessages[pos]=mMessages[pos].concat("\n").concat(message);
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
  
}