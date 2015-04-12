package com.ezardlabs.cropop;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LogAdapter extends ArrayAdapter<String[]> {

	public LogAdapter(Context context, int resource, String[][] objects) {
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
