package com.list.activities;

import java.util.ArrayList;

import com.list.adapter.ListDataAdapter;
import com.list.data.ListData;
import com.list.entity.ArrayLists;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

public class List extends Activity {
	private ListView listView;
	private ArrayLists getListData = new ArrayLists();
	private ArrayList<ListData> arr_data;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_main);

		listView = (ListView) findViewById(R.id.listView);
		Log.d("tag", " entered in oncreate");
		listView.setAdapter(new ListDataAdapter(getListData.getListData(), this));
		listView.setOnItemClickListener(moreItemClickListener);

	}

	private OnItemClickListener moreItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			if (ArrayLists.getListData_arr.get(position) != null) {
				Intent intent = new Intent(List.this, ListDetails.class);
				ArrayLists.listDataObject = ArrayLists.getListData().get(position);
				startActivity(intent);

			}
		}
	};

}
