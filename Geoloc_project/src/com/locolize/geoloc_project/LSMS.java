package com.locolize.geoloc_project;

import android.telephony.SmsManager;

public class LSMS {

		public void envoi(Contact contact, String msg){
			SmsManager sms = SmsManager.getDefault();
			String s = String.valueOf(contact.phone_number);
			sms.sendTextMessage(s, null, msg, null, null);
		}

		public void envoi(String number, String msg){
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(number, null, msg, null, null);
		}
		
}