package com.ezardlabs.cropop;

public class Application extends android.app.Application {

	@Override
	public void onCreate() {
		super.onCreate();
		DBManager.init(this);
	}
}
