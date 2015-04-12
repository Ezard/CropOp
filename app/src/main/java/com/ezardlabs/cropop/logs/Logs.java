package com.ezardlabs.cropop.logs;

import android.content.Intent;
import android.os.Bundle;

import com.ezardlabs.cropop.Activity;
import com.ezardlabs.cropop.R;
import com.ezardlabs.cropop.logs.disease.Disease;
import com.ezardlabs.cropop.logs.harvest.Harvest;
import com.ezardlabs.cropop.logs.income.Income;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Logs extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logs);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.harvestButton)
    void harvestClick() {
        startActivity(new Intent(this, Harvest.class));
    }

    @OnClick(R.id.diseaseButton)
    void diseaseClick() {
        startActivity(new Intent(this, Disease.class));
    }

    @OnClick(R.id.incomeButton)
    void incomeClick() {
        startActivity(new Intent(this, Income.class));
    }

}
