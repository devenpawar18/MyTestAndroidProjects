package com.list.activities;

import com.list.adapter.ListDataAdapter;
import com.list.entity.ArrayLists;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class ListDetails extends Activity {

	private ListView listView;
	private ArrayLists getListData = new ArrayLists();
	private TextView name;
	private TextView address;
	private TextView phone;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_details);
		name = (TextView) findViewById(R.id.name);
		address = (TextView) findViewById(R.id.address);
		phone = (TextView) findViewById(R.id.phone);

		name.setText(ArrayLists.listDataObject.getName());
		address.setText(ArrayLists.listDataObject.getAddress());
		phone.setText(ArrayLists.listDataObject.getPhone());

	}

}
