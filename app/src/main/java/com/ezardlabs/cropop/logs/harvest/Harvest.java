package com.ezardlabs.cropop.logs.harvest;

import android.os.Bundle;
import android.widget.ListView;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.DBManager;
import com.ezardlabs.cropop.LogAdapter;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Harvest extends Activity {
	@InjectView(R.id.list) ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.harvest);
		ButterKnife.inject(this);
		getSupportActionBar().setTitle("Harvests");
		list.setAdapter(new LogAdapter(this, 0, DBManager.getHarvestLogs()));
	}
}