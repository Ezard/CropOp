package com.ezardlabs.cropop;

import android.os.Bundle;

import butterknife.OnClick;

//TODO You need to add images to the ImageViews in R.layout.menu
//TODO Uncomment the following lines, but replace 'NextActivity.class' with the class of the next
//activity that you want to go to

public class Menu extends Activity {
	@OnClick(R.id.image1)
	public void imageClick1() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.image2)
	public void imageClick2() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.image3)
	public void imageClick3() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.image4)
	public void imageClick4() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}
}
