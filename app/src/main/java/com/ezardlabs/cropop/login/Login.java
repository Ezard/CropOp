package com.ezardlabs.cropop.login;

import android.content.Intent;
import android.os.Bundle;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.Main;
import com.ezardlabs.cropop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.loginButton)
    void loginClick() {
        startActivity(new Intent(this, Main.class));
    }

}