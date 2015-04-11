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
		db.execSQL("CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name, phone, location, latitude, longitude)");
	}

	public static void addContact(String name, String phone, String location, double latitude, double longitude) {
		ContentValues cv = new ContentValues(3);
		cv.put("name", name);
		cv.put("phone", phone);
		cv.put("location", location);
		cv.put("latitude", latitude);
		cv.put("longitude", longitude);
		db.insert("contacts", null, cv);
	}

	public static Contact[] getContacts() {
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
