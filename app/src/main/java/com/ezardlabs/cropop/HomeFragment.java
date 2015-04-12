package com.ezardlabs.cropop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ezardlabs.cropop.history.History;
import com.ezardlabs.cropop.logs.Logs;

import butterknife.OnClick;

public class HomeFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.menu, container, false);
		v.findViewById(R.id.historyButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), History.class));
			}
		});
		v.findViewById(R.id.logsButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), Logs.class));
			}
		});
		return v;
	}

	@OnClick(R.id.smsButton)
	public void smsClick() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.alertButton)
	public void alertClick() {
//		startActivity(new Intent(this, NextActivity.class));
	}

	@OnClick(R.id.historyButton)
	public void historyClick() {
		startActivity(new Intent(getActivity(), History.class));
	}

	@OnClick(R.id.logsButton)
	public void logsClick() {
		startActivity(new Intent(getActivity(), Logs.class));
	}
}
