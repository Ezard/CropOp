package com.ezardlabs.cropop.contacts;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.DBManager;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ContactDetail extends Activity {
	@InjectView(R.id.name) TextView name;
	@InjectView(R.id.phone) TextView phone;
	@InjectView(R.id.list) ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_detail);
		ButterKnife.inject(this);
		name.setText(getIntent().getStringExtra("name"));
		phone.setText(getIntent().getStringExtra("phone"));
		DBManager.getLogs(getIntent().getIntExtra("id", -1));
	}


}
