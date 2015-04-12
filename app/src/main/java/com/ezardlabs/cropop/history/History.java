package com.ezardlabs.cropop.history;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;

public class History extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        ButterKnife.inject(this);
        ((RadioGroup)findViewById(R.id.radioGroup)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radio09:
                        ((ImageView)findViewById(R.id.image)).setImageDrawable(getResources().getDrawable(R.drawable.graph1));
                        break;
                    case R.id.radio10:
                        ((ImageView)findViewById(R.id.image)).setImageDrawable(getResources().getDrawable(R.drawable.graph2));
                        break;
                    case R.id.radio11:
                        ((ImageView)findViewById(R.id.image)).setImageDrawable(getResources().getDrawable(R.drawable.graph3));
                        break;
                }
            }
        });
    }

}
