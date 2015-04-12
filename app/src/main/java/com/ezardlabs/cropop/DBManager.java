package com.ezardlabs.cropop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ezardlabs.cropop.contacts.Contact;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

public class DBManager {
	private static SQLiteDatabase db;

	static void init(Context ctx) {
		db = ctx.openOrCreateDatabase("Data", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS contacts(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name, phone, location, latitude, longitude)");
		db.execSQL("CREATE TABLE IF NOT EXISTS diseases(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, type, day, month, year, contact)");
		db.execSQL("CREATE TABLE IF NOT EXISTS incomes(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, amount, day, month, year, contact)");
		db.execSQL("CREATE TABLE IF NOT EXISTS harvests(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, weight, type, day, month, year, contact)");
		db.execSQL("CREATE TABLE IF NOT EXISTS pesticides(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, quantity, type, day, month, year, contact)");

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
		String id;
		if ((id = getIdFromPhoneNumber(phone)) != null) {
			ContentValues cv = new ContentValues();
			cv.put("type", type);
			cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
			cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
			cv.put("contact", id);
			db.insert("diseases", null, cv);
		}
	}

	public static void addIncome(String phone, float amount) {
		String id;
		if ((id = getIdFromPhoneNumber(phone)) != null) {
			ContentValues cv = new ContentValues();
			cv.put("amount", amount);
			cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
			cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
			cv.put("contact", id);
			db.insert("incomes", null, cv);
		}
	}

	public static void addHarvest(String phone, float weight, int type) {
		String id;
		if ((id = getIdFromPhoneNumber(phone)) != null) {
			ContentValues cv = new ContentValues();
			cv.put("weight", weight);
			cv.put("type", type);
			cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
			cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
			cv.put("contact", id);
			db.insert("harvests", null, cv);
		}
	}

	public static void addPesticide(String phone, float quantity, int type) {
		String id;
		if ((id = getIdFromPhoneNumber(phone)) != null) {
			ContentValues cv = new ContentValues();
			cv.put("quantity", quantity);
			cv.put("type", type);
			cv.put("day", Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
			cv.put("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
			cv.put("year", Calendar.getInstance().get(Calendar.YEAR));
			cv.put("contact", id);
			db.insert("pesticides", null, cv);
		}
	}

	private static String standardisePhoneNumber(String phone) {
		phone = phone.replace(" ", "");
		if (phone.matches("\\+44[0-9]{10}")) {
			return phone.substring(3, phone.length());
		} else {
			return phone.substring(1, phone.length());
		}
	}

	private static String getIdFromPhoneNumber(String phone) {
		Cursor c = db.rawQuery("SELECT id, phone FROM contacts", null);
		while (c.moveToNext()) {
			if (standardisePhoneNumber(c.getString(1)).equals(standardisePhoneNumber(phone))) {
				return c.getString(0);
			}
		}
		c.close();
		return null;
	}

	public static Contact[] getContacts(String location) {
		Cursor c = db.rawQuery("SELECT * FROM contacts" + (location == null ? "" : " WHERE location='" + location + "'"), null);
		Contact[] contacts = new Contact[c.getCount()];
		int count = 0;
		while (c.moveToNext()) {
			contacts[count++] = new Contact(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
		}
		c.close();
		return contacts;
	}

	public static String[] getContactLocations() {
		Cursor c = db.query(true, "contacts", new String[]{"location"}, null, null, null, null, "location", null);
		String[] locations = new String[c.getCount()];
		int count = 0;
		while (c.moveToNext()) {
			locations[count++] = c.getString(0);
		}
		c.close();
		return locations;
	}

	public static String[][] getLogs(int contactId) {
		Log.i("", "" + contactId);
		Cursor c1 = db.rawQuery("SELECT type, day, month, year FROM diseases WHERE contact=?", new String[]{String.valueOf(contactId)});
		Cursor c2 = db.rawQuery("SELECT amount, day, month, year FROM incomes WHERE contact=?", new String[]{String.valueOf(contactId)});
		Cursor c3 = db.rawQuery("SELECT weight, type, day, month, year FROM harvests WHERE contact=?", new String[]{String.valueOf(contactId)});
		String[][] logs = new String[c1.getCount() + c2.getCount() + c3.getCount()][2];
		int count = 0;
		while (c1.moveToNext()) {
			logs[count++] = new String[]{"Disease", c1.getString(0) + ": " + c1.getInt(1) + "/" + c1.getInt(2) + "/" + c1.getInt(3)};
		}
		while (c2.moveToNext()) {
			logs[count++] = new String[]{"Income", NumberFormat.getCurrencyInstance(Locale.UK).format(c2.getFloat(0)) + ": " + c2.getInt(1) + "/" + c2.getInt(2) + "/" + c2.getInt(3)};
		}
		while (c3.moveToNext()) {
			logs[count++] = new String[]{"Harvest", c3.getString(0) + "kg of " + c3.getString(1) + ": " + c3.getInt(2) + "/" + c3.getInt(3) + "/" + c3.getInt(4)};
		}
		c1.close();
		c2.close();
		c3.close();
		return logs;
	}

	public static String[][] getDiseaseLogs() {
		Cursor c = db.rawQuery("SELECT type, day, month, year, c.name as contact FROM diseases h LEFT JOIN contacts c ON c.id=h.contact", null);
		String[][] logs = new String[c.getCount()][2];
		int count = 0;
		while(c.moveToNext()) {
			logs[count++] = new String[]{c.getString(4), c.getString(0) + ": " + c.getInt(1) + "/" + c.getInt(2) + "/" + c.getInt(3)};
		}
		c.close();
		return logs;
	}

	public static String[][] getIncomeLogs() {
		Cursor c = db.rawQuery("SELECT amount, day, month, year, c.name as contact FROM incomes h LEFT JOIN contacts c ON c.id=h.contact", null);
		String[][] logs = new String[c.getCount()][2];
		int count = 0;
		while(c.moveToNext()) {
			logs[count++] = new String[]{c.getString(4), NumberFormat.getCurrencyInstance(Locale.UK).format(c.getFloat(0)) + ": " + c.getInt(1) + "/" + c.getInt(2) + "/" + c.getInt(3)};
		}
		c.close();
		return logs;
	}

	public static String[][] getHarvestLogs() {
		Cursor c = db.rawQuery("SELECT weight, type, day, month, year, c.name as contact FROM harvests h LEFT JOIN contacts c ON c.id=h.contact", null);
		String[][] logs = new String[c.getCount()][2];
		int count = 0;
		while(c.moveToNext()) {
			logs[count++] = new String[]{c.getString(5), c.getString(0) + "kg of " + c.getString(1) + ": " + c.getInt(2) + "/" + c.getInt(3) + "/" + c.getInt(4)};
		}
		c.close();
		return logs;
	}
}
