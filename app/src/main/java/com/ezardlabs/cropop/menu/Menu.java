package com.ezardlabs.cropop.menu;

import android.os.Bundle;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;

/**
 * Created by Neel on 12/04/15.
 */
public class Menu extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ButterKnife.inject(this);
    }
}
