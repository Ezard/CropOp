package com.ezardlabs.cropop.contacts;

public class Contact {
	private int id;
	private String name;
	private String number;
	private String location;

	public Contact(int id, String name, String number, String location) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.location = location;
	}

	public int getId() {
		return id;
	}

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}
}
