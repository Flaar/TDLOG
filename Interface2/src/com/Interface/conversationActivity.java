package com.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

public class conversationActivity extends Activity {
  
  private ListView mlistMessages = null;
  private String[] mMessages = null;
  
  final String POSITION="position";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.conversationlayout);
    Intent intent = getIntent();
    
    mlistMessages = (ListView) findViewById(R.id.listmessages);
    mMessages = new String[]{"Bonjour", "Ca va?", "Bien et toi?", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin", "Robin"
    		, "Robin", "Charles", "Romain", "Robin", "Eric", "Robin", "Robin", "Claude", "Robin"};

    String[] mSpecificMessage = new String [1];
    
    if (intent != null) 
    	mSpecificMessage[0]=mMessages[intent.getIntExtra(POSITION,-1)];

    mlistMessages.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mSpecificMessage));
    
  }
}
