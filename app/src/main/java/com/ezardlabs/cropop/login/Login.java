package com.ezardlabs.cropop;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;

import com.ezardlabs.cropop.contacts.Contacts;
import com.ezardlabs.cropop.data.Data;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.inject(this);
    }


    /*public void onButtonClick(View v){
        if(v.getId() == R.id.loginButton){
            Intent i = new Intent(login.this, Login.class);
        }
    }
    */

    @OnClick(R.id.loginButton)
    void loginClick() {
        startActivity(new Intent(this, Main.class));
    }

}