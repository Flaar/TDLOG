package com.Interface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class OptionsActivity extends Activity {
  
  private Button mOK = null;
  private String[] mOptions = null;
  private ListView mListOpt = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.optionslayout);
    

    
    mListOpt = (ListView) findViewById(R.id.listoptions);
    mOK = (Button) findViewById(R.id.ok);
    mOptions = new String[]{"C", "Java", "COBOL", "Perl"};


    mListOpt.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mOptions));
    
    //Que se passe-t-il d�s qu'on clique sur le bouton ?
    mOK.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        Toast.makeText(OptionsActivity.this, "Merci ! Les donn�es ont �t� envoy�es !", Toast.LENGTH_LONG).show();
        
        
        //On d�clare qu'on ne peut plus s�lectionner d'�l�ment
        mListOpt.setChoiceMode(ListView.CHOICE_MODE_NONE);
        //On affiche un layout qui ne permet pas de s�lection
        mListOpt.setAdapter(new ArrayAdapter<String>(OptionsActivity.this, android.R.layout.simple_list_item_1, mOptions));
        
        //On d�sactive le bouton
        mOK.setEnabled(false);
      }
    });
  }
}