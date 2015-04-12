package com.ezardlabs.cropop.menu;

import android.content.Intent;
import android.os.Bundle;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.R;
import com.ezardlabs.cropop.history.History;
import com.ezardlabs.cropop.logs.Logs;

import butterknife.ButterKnife;
import butterknife.OnClick;

//TODO You need to add images to the ImageViews in R.layout.menu
//TODO Uncomment the following lines, but replace 'NextActivity.class' with the class of the next
//activity that you want to go to

public class Menu extends Activity {


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		ButterKnife.inject(this);
	}


	@OnClick(R.id.smsButton)
	public void smsClick() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.alertButton)
	public void alertClick() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.historyButton)
	public void historyClick() {
		startActivity(new Intent(this, History.class));
	}

	@OnClick(R.id.logsButton)
	public void logsClick() {
		startActivity(new Intent(this, Logs.class));
	}
}
