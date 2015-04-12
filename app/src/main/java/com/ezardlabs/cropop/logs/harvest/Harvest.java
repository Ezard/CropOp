package com.ezardlabs.cropop.logs.harvest;

import android.content.Intent;
import android.os.Bundle;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.R;


import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Neel on 12/04/15.
 */
public class Harvest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.harvest);
        ButterKnife.inject(this);
    }

}