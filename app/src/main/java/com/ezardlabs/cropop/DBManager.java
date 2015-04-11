package com.ezardlabs.cropop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ezardlabs.cropop.contacts.Contact;

public class DBManager {
	private static SQLiteDatabase db;

	static void init(Context ctx) {
		db = ctx.openOrCreateDatabase("Data", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE contacts(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name, phone, location)");
	}

	public void addContact(String name, String number, String location) {
		ContentValues cv = new ContentValues(3);
		cv.put("name", name);
		cv.put("number", number);
		cv.put("location", location);
		db.insert("contacts", null, cv);
	}

	public Contact[] getContacts() {
		Cursor c = db.rawQuery("SELECT * FROM contacts", null);
		Contact[] contacts = new Contact[c.getCount()];
		int count = 0;
		while(c.moveToNext()) {
			contacts[count++] = new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
		}
		c.close();
		return contacts;
	}
}
