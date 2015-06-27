package com.mmcontacts;

/**
 * Created by Rahul Raj on 24-Jun-15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ContactDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE contacts ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "path TEXT, "+
                "status TEXT )";

        // create  table
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older photos table if existed
        db.execSQL("DROP TABLE IF EXISTS contacts");

        this.onCreate(db);
    }

    private static final String TABLE_CONTACTS = "contacts";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PATH = "path";
    private static final String KEY_STATUS = "status";

    private static final String[] COLUMNS = {KEY_ID,KEY_PATH,KEY_STATUS};

    public void addPhoto(ContactLocal contact) {

        Log.d("addPhoto", contact.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_PATH, contact.getPath());
        values.put(KEY_STATUS, contact.getStatus());


        db.insert(TABLE_CONTACTS, null,values); // key/value -> keys = column names/ values = column values
        db.close();
    }





    public ArrayList<ContactLocal> getAllContacts() {

        ArrayList<ContactLocal> contacts = new ArrayList<>();

        String query = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ContactLocal contactLocal = null;
        if (cursor.moveToFirst()) {
            do {

                contactLocal=getContactObject(cursor);
                contacts.add(contactLocal);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPhotos()", contacts.toString());
        return contacts;
    }

    private ContactLocal getContactObject(Cursor cursor){

        String path=cursor.getString(1);
        String status=cursor.getString(2);


        ContactLocal contact=new ContactLocal(path,status);
        return contact;

    }

    public void deleteContact(ContactLocal contactLocal) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS,
                KEY_PATH+ " = ?",
                new String[]{contactLocal.getPath()});

        // 3. close
        db.close();

        Log.d("deletePhoto", contactLocal.toString());

    }

    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_CONTACTS, null, null);
       // db.delete(DatabaseHelper.TAB_USERS_GROUP, null, null);
    }




}

