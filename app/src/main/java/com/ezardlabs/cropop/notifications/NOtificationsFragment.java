package com.ezardlabs.cropop.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezardlabs.cropop.DBManager;
import com.ezardlabs.cropop.R;
import com.ezardlabs.cropop.contacts.Contact;

public class NotificationsFragment extends Fragment {
	RecyclerView list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.list, container, false);
		list = (RecyclerView) v.findViewById(R.id.list);
		list.setLayoutManager(new LinearLayoutManager(getActivity()));
		list.setAdapter(new ContactAdapter(DBManager.getContacts()));
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuItem temp = menu.add("Clear all notifications").setIcon(android.R.drawable.ic_menu_delete).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				final View view = getActivity().getLayoutInflater().inflate(R.layout.contacts_add, null);
				new AlertDialog.Builder(getActivity()).setTitle("Add peasant to collection").setView(view).setPositiveButton("Add", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setNegativeButton("Cancel", null).show();
				return true;
			}
		});
		if (VERSION.SDK_INT >= 11) temp.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	}

	public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
		private Contact[] contacts;

		public ContactAdapter(Contact[] contacts) {
			this.contacts = contacts;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			return new ViewHolder(getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_2, viewGroup, false));
		}

		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int i) {
			viewHolder.name.setText(contacts[i].getName());
			viewHolder.location.setText(contacts[i].getLocation());
		}

		@Override
		public int getItemCount() {
			return contacts.length;
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			TextView name;
			TextView location;

			public ViewHolder(View itemView) {
				super(itemView);
				name = (TextView) itemView.findViewById(android.R.id.text1);
				location = (TextView) itemView.findViewById(android.R.id.text2);
			}
		}
	}
}
