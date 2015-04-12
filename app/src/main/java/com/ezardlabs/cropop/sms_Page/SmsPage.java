package com.ezardlabs.cropop.sms_Page;

import android.os.Bundle;
import com.ezardlabs.cropop.R;
import com.ezardlabs.cropop.Activity;

import butterknife.ButterKnife;

/**
 * Created by Neel on 12/04/15.
 */
public class SmsPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_page);
        ButterKnife.inject(this);
    }


}
