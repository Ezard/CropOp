package com.ezardlabs.cropop.contacts;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		list.setAdapter(new Adapter(this, 0, DBManager.getLogs(getIntent().getIntExtra("id", -1))));
	}

	class Adapter extends ArrayAdapter<String[]> {

		public Adapter(Context context, int resource, String[][] objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(android.R.layout.simple_list_item_2, parent, false);
			((TextView) v.findViewById(android.R.id.text1)).setText(getItem(position)[0]);
			((TextView) v.findViewById(android.R.id.text1)).setTextColor(Color.RED);
			((TextView) v.findViewById(android.R.id.text2)).setText(getItem(position)[1]);
			return v;
		}
	}
}
