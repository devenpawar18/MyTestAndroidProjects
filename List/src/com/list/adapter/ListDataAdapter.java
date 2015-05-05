package com.list.adapter;

import java.util.ArrayList;

import com.list.activities.R;
import com.list.data.ListData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListDataAdapter extends BaseAdapter {

	ArrayList<ListData> listArr = new ArrayList<ListData>();
	
	Context context;

	public ListDataAdapter(ArrayList<ListData> listArr, Context context) {
		super();
		this.listArr = listArr;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listArr.size();
	}

	@Override
	public Object getItem(int position) {
		return listArr.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView == null) {
			v = LayoutInflater.from(context).inflate(R.layout.list_contents, parent, false);
		} else {
			v = (View) convertView;
		}
		ListData data = listArr.get(position);

		TextView text1 = (TextView) v.findViewById(R.id.name);
		TextView text2 = (TextView) v.findViewById(R.id.address);
		TextView text3 = (TextView) v.findViewById(R.id.phone);
		text1.setText(data.getName());
		text2.setText(data.getAddress());
		text3.setText(data.getPhone());
		return v;
	}

}
