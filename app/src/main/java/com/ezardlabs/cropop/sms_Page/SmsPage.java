package com.ezardlabs.cropop.sms_Page;

import android.content.Intent;
import android.os.Bundle;
import com.ezardlabs.cropop.R;
import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.history.History;
import com.ezardlabs.cropop.logs.Logs;

import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @OnClick(R.id.area1Button)
    public void historyClick() {
        startActivity(new Intent(this, AreaA.class));
    }

    @OnClick(R.id.logsButton)
    public void logsClick() {
        startActivity(new Intent(this, AreaB.class));
    }
}
