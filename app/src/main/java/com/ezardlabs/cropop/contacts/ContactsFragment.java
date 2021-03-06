package com.ezardlabs.cropop.contacts;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezardlabs.cropop.DBManager;
import com.ezardlabs.cropop.R;

public class ContactsFragment extends Fragment {
	private RecyclerView list;
	private boolean showMapRegionList = false;

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
		MenuItem temp;
		if (showMapRegionList) {
			temp = menu.add("Show contacts list").setIcon(R.drawable.ic_menu_allfriends).setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					showMapRegionList = false;
					getActivity().supportInvalidateOptionsMenu();
					list.setAdapter(new ContactAdapter(DBManager.getContacts()));
					return true;
				}
			});
			if (VERSION.SDK_INT >= 11) temp.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else {
			temp = menu.add("Show map region list").setIcon(android.R.drawable.ic_menu_mapmode).setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					showMapRegionList = true;
					getActivity().supportInvalidateOptionsMenu();
					list.setAdapter(new LocationsAdapter(DBManager.getContactLocations()));
					return true;
				}
			});
			if (VERSION.SDK_INT >= 11) temp.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		temp = menu.add("Add farmers to collection").setIcon(android.R.drawable.ic_menu_add).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				final View view = getActivity().getLayoutInflater().inflate(R.layout.contacts_add, null);
				new AlertDialog.Builder(getActivity()).setTitle("Add farmers to collection").setView(view).setPositiveButton("Add", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						new LocationFinder(((EditText) view.findViewById(R.id.name)).getText().toString(), ((EditText) view.findViewById(R.id.number)).getText().toString(),
								((EditText) view.findViewById(R.id.location)).getText().toString()).start();
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
			return new ViewHolder(getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_2, viewGroup, false), contacts[i]);
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

			public ViewHolder(View itemView, final Contact contact) {
				super(itemView);
				name = (TextView) itemView.findViewById(android.R.id.text1);
				location = (TextView) itemView.findViewById(android.R.id.text2);
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(getActivity(), ContactDetail.class).putExtra("name", contact.getName()).putExtra("location", contact.getLocation()).putExtra("id", contact.getId()));
					}
				});
			}
		}
	}

	public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> {
		private String[] locations;

		public LocationsAdapter(String[] locations) {
			this.locations = locations;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			return new ViewHolder(getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			holder.title.setText(locations[position]);
		}

		@Override
		public int getItemCount() {
			return locations.length;
		}

		public class ViewHolder extends RecyclerView.ViewHolder {
			TextView title;

			public ViewHolder(View itemView) {
				super(itemView);
				title = (TextView) itemView.findViewById(android.R.id.text1);
				itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						list.setAdapter(new ContactAdapter(DBManager.getContacts(title.getText().toString())));
					}
				});
			}
		}
	}

	class LocationFinder extends Thread {
		private final String name;
		private final String phone;
		private final String location;
		private ProgressDialog pd;

		public LocationFinder(String name, String phone, String location) {
			this.name = name;
			this.phone = phone;
			this.location = location;
		}

		public void run() {
			Looper.prepare();
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
					pd = ProgressDialog.show(getActivity(), "Calculating location from GPS, please wait...", null, true);
				}
			});
			LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			locationManager.requestSingleUpdate(criteria, new LocationListener() {
				@Override
				public void onLocationChanged(Location location) {
					DBManager.addContact(name, phone, LocationFinder.this.location, location.getLatitude(), location.getLongitude());
					pd.dismiss();
					list.setAdapter(new ContactAdapter(DBManager.getContacts()));
				}

				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					switch (status) {
						case LocationProvider.AVAILABLE:
							break;
						case LocationProvider.TEMPORARILY_UNAVAILABLE:
							Toast.makeText(getActivity(), "GPS temporarily unavailable", Toast.LENGTH_SHORT).show();
							break;
						case LocationProvider.OUT_OF_SERVICE:
							Toast.makeText(getActivity(), "GPS out of service", Toast.LENGTH_SHORT).show();
							break;
					}
				}

				@Override
				public void onProviderEnabled(String provider) {

				}

				@Override
				public void onProviderDisabled(String provider) {

				}
			}, Looper.getMainLooper());
		}
	}
}
