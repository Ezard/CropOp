package com.ezardlabs.cropop.history;

import android.os.Bundle;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;

public class History extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        ButterKnife.inject(this);
    }

}
