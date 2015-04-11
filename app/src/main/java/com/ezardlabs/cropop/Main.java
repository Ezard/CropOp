package com.ezardlabs.cropop;

import android.content.Intent;
import android.os.Bundle;

import com.ezardlabs.cropop.contacts.Contacts;
import com.ezardlabs.cropop.data.Data;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ButterKnife.inject(this);
	}

	@OnClick(R.id.contacts)
	void contactsClick() {
		startActivity(new Intent(this, Contacts.class));
	}

	@OnClick(R.id.data)
	void dataClick() {
		startActivity(new Intent(this, Data.class));
	}
}