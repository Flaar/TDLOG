package com.locolize.geoloc_project;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
 
public class SMS_receiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent) 
       {	
		 //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String str = "";
        String phoneNumber="";
        String realMessage="";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                str += "SMS from " + msgs[i].getOriginatingAddress();                     
                str += " :";
                str += msgs[i].getMessageBody().toString();
                str += "\n";  
                phoneNumber=msgs[i].getOriginatingAddress();
                realMessage=msgs[i].getMessageBody().toString();
            }
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            
            DatabaseHandler database = new DatabaseHandler(context);
            
            List<Contact> list_of_contacts;
            list_of_contacts=database.getAllContacts();
            
            for(int i=0; i<list_of_contacts.size(); i++)
            {
            	Contact contact=list_of_contacts.get(i);
            	String real_number=contact.phone_number;
            	if (contact.phone_number.length() == 10)
            		real_number="+33" + real_number.substring(1);
            	
            	if (real_number.equals(phoneNumber))
            		database.updateMessage(database.getMessage(contact) + "STOP" + "Message reçu : \n" + realMessage, contact);

            }
        }
		
		
		
	}
}