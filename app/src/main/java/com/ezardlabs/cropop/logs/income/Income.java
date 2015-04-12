package com.ezardlabs.cropop.logs.income;

import android.os.Bundle;
import android.widget.ListView;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.DBManager;
import com.ezardlabs.cropop.LogAdapter;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Income extends Activity {
	@InjectView(R.id.list) ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diseases);
		ButterKnife.inject(this);
		getSupportActionBar().setTitle("Incomes");
		list.setAdapter(new LogAdapter(this, 0, DBManager.getIncomeLogs()));
	}
}