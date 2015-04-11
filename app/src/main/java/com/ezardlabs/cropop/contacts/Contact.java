package com.ezardlabs.cropop.contacts;

public class Contact {
	private int id;
	private String name;
	private String phone;
	private String location;

	public Contact(int id, String name, String phone, String location) {
		this.id = id;
		this.name = name;
		this.phone = phone;
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

	public String getPhone() {
		return phone;
	}
}
