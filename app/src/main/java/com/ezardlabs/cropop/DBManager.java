package com.ezardlabs.cropop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ezardlabs.cropop.contacts.Contact;

import java.util.Calendar;

public class DBManager {
	private static SQLiteDatabase db;

	static void init(Context ctx) {
		db = ctx.openOrCreateDatabase("Data", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name, phone, location, latitude, longitude)");
		db.execSQL("CREATE TABLE IF NOT EXISTS diseases(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, type, day, month, year, contact)");
		db.execSQL("CREATE TABLE IF NOT EXISTS incomes(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, amount, day, month, year, contact)");
		db.execSQL("CREATE TABLE IF NOT EXISTS harvests(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, weight, type, day, month, year, contact)");
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
		while (c.moveToNext()) {
			contacts[count++] = new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
		}
		c.close();
		return contacts;
	}

	public static void addDisease(String phone, String type) {
		phone = standardisePhoneNumber(phone);
		Cursor c = db.rawQuery("SELECT id, phone FROM contacts", null);
		while (c.moveToNext()) {
			if (standardisePhoneNumber(c.getString(1)).equals(phone)) {
				ContentValues cv = new ContentValues();
				cv.put("type", type);
				cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
				cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
				cv.put("contact", c.getInt(c.getInt(0)));
				db.insert("diseases", null, cv);
				break;
			}
		}
		c.close();
	}

	public static void addIncome(String phone, float amount) {
		phone = standardisePhoneNumber(phone);
		Cursor c = db.rawQuery("SELECT id, phone FROM contacts", null);
		while (c.moveToNext()) {
			if (standardisePhoneNumber(c.getString(1)).equals(phone)) {
				ContentValues cv = new ContentValues();
				cv.put("amount", amount);
				cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
				cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
				cv.put("contact", c.getInt(c.getInt(0)));
				db.insert("incomes", null, cv);
				break;
			}
		}
		c.close();
	}

	public static void addHarvest(String phone, float weight, int type) {
		phone = standardisePhoneNumber(phone);
		Cursor c = db.rawQuery("SELECT id, phone FROM contacts", null);
		while (c.moveToNext()) {
			if (standardisePhoneNumber(c.getString(1)).equals(phone)) {
				ContentValues cv = new ContentValues();
				cv.put("weight", weight);
				cv.put("type", type);
				cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
				cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
				cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
				cv.put("contact", c.getInt(c.getInt(0)));
				db.insert("harvests", null, cv);
				break;
			}
		}
		c.close();
	}

	private static String standardisePhoneNumber(String phone) {
		phone = phone.replace(" ", "");
		if (phone.matches("\\+44[0-9]{10}")) {
			return phone.substring(3, phone.length());
		} else {
			return phone.substring(1, phone.length());
		}
	}
}
