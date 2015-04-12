package com.ezardlabs.cropop;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ezardlabs.cropop.contacts.ContactsFragment;
import com.ezardlabs.cropop.notifications.NotificationsFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Main extends Activity {
	@InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;
	@InjectView(R.id.listView) ListView drawerListView;
	private ActionBarDrawerToggle drawerToggle;
	private int position = 0;
	private String[] items = {"Home", "Contacts", "Notifications", "Settings"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ButterKnife.inject(this);
		drawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
		drawerListView = ((ListView) findViewById(R.id.listView));
		drawerListView.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, items));
		drawerListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Main.this.selectItem(position);
			}
		});
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View drawerView) {
				Main.this.supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				Main.this.supportInvalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		selectItem(0);
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		DrawerLayout localDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ListView localListView = (ListView) findViewById(R.id.listView);
		if (localDrawerLayout.isDrawerOpen(localListView)) localDrawerLayout.closeDrawer(localListView);
		else localDrawerLayout.openDrawer(localListView);
		return true;
	}

	void selectItem(int position) {
		this.position = position;
		Fragment fragment = null;
		switch (position) {
			case 0:
				fragment = new HomeFragment();
				break;
			case 1:
				fragment = new ContactsFragment();
				break;
			case 2:
				fragment = new NotificationsFragment();
				break;
		}
		if (fragment != null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
		}
		drawerListView.setItemChecked(position, true);
		getSupportActionBar().setTitle(items[position]);
		drawerLayout.closeDrawer(drawerListView);
	}
}