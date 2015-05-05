package com.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.search.adapter.UserSearchAdapter;
import com.search.entity.User;

public class SearchFromSpinnerActivity extends Activity {
	private ListView searchList;
	private ArrayList<User> userData;
	private ArrayList<User> resultList = new ArrayList<User>();
	private ArrayList<String> countryList;
	private UserSearchAdapter userSearchAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		countryList = new ArrayList<String>();
		countryList.add("All");
		countryList.add("India");
		countryList.add("New Zealand");
		countryList.add("England");
		countryList.add("Japan");
		countryList.add("Austrailia");
		countryList.add("USA");
		countryList.add("France");

		userData = new ArrayList<User>();
		userData.add(new User("India", "Deven Pawar"));
		userData.add(new User("Austrailia", "Deven Pawar"));
		userData.add(new User("Japan", "Deven Pawar"));
		userData.add(new User("England", "Deven Pawar"));
		userData.add(new User("New Zealand", "Deven Pawar"));
		userData.add(new User("USA", "Deven Pawar"));
		userData.add(new User("France", "Deven Pawar"));
		userData.add(new User("India", "Swapnil Pawar"));
		userData.add(new User("Austrailia", "Swapnil Pawar"));
		userData.add(new User("Japan", "Swapnil Pawar"));
		userData.add(new User("England", "Swapnil Pawar"));
		userData.add(new User("New Zealand", "Swapnil Pawar"));
		userData.add(new User("USA", "Swapnil Pawar"));
		userData.add(new User("France", "Swapnil Pawar"));
		userData.add(new User("India", "Gaurang"));
		userData.add(new User("Austrailia", "Gautam"));
		userData.add(new User("Japan", "Rajesh"));
		userData.add(new User("England", "Ramesh"));
		userData.add(new User("New Zealand", "Jalaluddin Akbar"));
		userData.add(new User("USA", "Mangesh"));
		userData.add(new User("France", "Suraj"));
		userData.add(new User("India", "Pooja"));
		userData.add(new User("Austrailia", "Yogini"));
		userData.add(new User("Japan", "Sachin"));
		userData.add(new User("England", "Abhay"));
		userData.add(new User("New Zealand", "Siddharth"));
		userData.add(new User("USA", "Sanjay"));
		userData.add(new User("France", "Amar"));
		userData.add(new User("India", "yuvraj"));
		userData.add(new User("Austrailia", "Rahul"));
		userData.add(new User("Japan", "Sehwag"));
		userData.add(new User("England", "Virat Kohli"));
		userData.add(new User("New Zealand", "Sehwag"));
		userData.add(new User("USA", "Sunil"));
		userData.add(new User("France", "Aniket Chavan"));
		userData.add(new User("India", "Snehal Chavan"));
		userData.add(new User("Austrailia", "Sonal Chavan"));
		userData.add(new User("Japan", "Amol Pawar"));
		userData.add(new User("England", "Milind Pawar"));
		userData.add(new User("New Zealand", "Deven Pawar"));
		userData.add(new User("USA", "Swapnil Pawar"));
		userData.add(new User("France", "Deven Pawar"));

		searchList = (ListView) findViewById(R.id.searchListView);
		userSearchAdapter = new UserSearchAdapter(resultList, this, searchList);
		searchList.setAdapter(userSearchAdapter);

		Collections.sort(countryList);
		Spinner spinner = (Spinner) findViewById(R.id.searchSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				countryList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

	}

	public class MyOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			resultList.clear();
			if (!countryList.get(pos).trim().equalsIgnoreCase("all")) {
				Iterator<User> result_list_iterator = userData.iterator();
				while (result_list_iterator.hasNext()) {

					User u = result_list_iterator.next();
					if (u.getCountryName().equals(countryList.get(pos))) {
						resultList.add(u);
					}
				}
				userSearchAdapter.notifyDataSetChanged();
			} else {
				Iterator<User> result_list_iterator = userData.iterator();
				while (result_list_iterator.hasNext()) {
					User u = result_list_iterator.next();
					resultList.add(u);
				}
				userSearchAdapter.notifyDataSetChanged();
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
	}

}