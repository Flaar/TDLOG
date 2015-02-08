package com.locolize.geoloc_project;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

 
public class DatabaseHandler extends SQLiteOpenHelper {
	
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_MESSAGE = "message";
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
    }
 
    // Creating Tables
    public void create_table(SQLiteDatabase db) {
    	System.out.println("on rentre dans le create-table");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_SURNAME + " TEXT," + KEY_PH_NO + " TEXT,"+ KEY_MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        System.out.println("on sort du create-table");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    	System.out.println("on rentre dans le onCreate");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_SURNAME + " TEXT," + KEY_PH_NO + " TEXT,"+ KEY_MESSAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        System.out.println("on sort du create-table");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
        
    // Adding new contact
    void addContact(Contact contact) {
    	//System.out.println("before writable");
        SQLiteDatabase db = this.getWritableDatabase();
        //System.out.println("after writable");
        ContentValues values = new ContentValues();
        //System.out.println("after values");
        values.put(KEY_NAME, contact.name); // Contact Name
        values.put(KEY_SURNAME, contact.surname); 
        values.put(KEY_PH_NO, contact.phone_number); // Contact Phone
        values.put(KEY_MESSAGE, "");
        //System.out.println("after setting");
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //System.out.println("after insert");
        db.close(); // Closing database connection
        //System.out.println("after close");
    }
 
    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("after writable");
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_NAME, KEY_SURNAME, KEY_PH_NO, KEY_MESSAGE }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);// rajouter 2 null ?? parce que j'ai ajout� surname et message ??
        System.out.println("after query");
        System.out.println(cursor);
        if (cursor != null)
            cursor.moveToFirst();
        System.out.println("before contact");
        System.out.println(Integer.parseInt(cursor.getString(0)));
        System.out.println(cursor.getString(1));
        System.out.println(cursor.getString(2));
        System.out.println(cursor.getString(3));
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return contact
        return contact;
    }
     
    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return contactList;
    }
 
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.name);
        values.put(KEY_SURNAME, contact.surname);
        values.put(KEY_PH_NO, contact.phone_number);
 
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",new String[] { String.valueOf(contact.id)});
    }
    
    public int updateMessage (String text,Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("after writable");
        ContentValues values = new ContentValues();
        System.out.println("after values");
        values.put(KEY_MESSAGE, text);
        System.out.println("after put");
        System.out.println(String.valueOf(contact.id));
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",new String[] { String.valueOf(contact.id)});        
    }
    
    String getMessage(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("after writable");
        System.out.println(contact.id);
        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID, KEY_NAME, KEY_SURNAME, KEY_PH_NO, KEY_MESSAGE }, KEY_ID + "=?", new String[] { String.valueOf(contact.id) }, null, null, null, null);// rajouter 2 null ?? parce que j'ai ajout� surname et message ??
        System.out.println("before cursor");
        if (cursor != null)
            cursor.moveToFirst();
        System.out.println("after cursor");
        System.out.println(cursor.getString(4));
        String conversation = cursor.getString(4);
        // return contact
        return conversation;
    }
 
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.id) });
        db.close();
    }
 
 
    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }
 
}

