package com.search.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.search.R;
import com.search.entity.User;

public class UserSearchAdapter extends BaseAdapter {

	private ArrayList<User> offerList = new ArrayList<User>();
	private Context context;
	private ListView offersListView;

	public UserSearchAdapter(ArrayList<User> listArr, Context context,
			ListView listView) {
		super();
		this.offerList = listArr;
		this.context = context;
		this.offersListView = listView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return offerList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return offerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = null;
		if (convertView == null) {
			v = LayoutInflater.from(context).inflate(
					R.layout.search_for_member_row, parent, false);
		} else {
			v = convertView;
		}
		User UserData = offerList.get(position);
		v.setTag(UserData);

		TextView UserName = (TextView) v.findViewById(R.id.people_name);
		TextView countryName = (TextView) v.findViewById(R.id.country_name);

		UserName.setText(UserData.getEmpName());
		countryName.setText(UserData.getCountryName());

		return v;
	}
}
