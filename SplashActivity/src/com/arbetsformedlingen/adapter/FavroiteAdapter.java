
package com.arbetsformedlingen.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arbetsformedlingen.activity.GlobalBaseActivity;
import com.arbetsformedlingen.activity.R;
import com.arbetsformedlingen.entity.SearchResultEntity;


public class FavroiteAdapter extends BaseAdapter {

	private Context context;
	public int currentYearCount = 20;
	private ArrayList<SearchResultEntity> favArrayList = new ArrayList<SearchResultEntity>();

	/**
	 * @param context
	 * @param c
	 */
	public FavroiteAdapter(Context m_context, ArrayList<SearchResultEntity> list) {
		this.context = m_context;
		this.favArrayList = list;
	}



	@Override
	public int getCount() {

		return favArrayList != null ? favArrayList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return favArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			v = LayoutInflater.from(context).inflate(R.layout.favroiteitem_row, parent, false);
		}
		
		SearchResultEntity entity = favArrayList.get(position);
		TextView titleView = (TextView) v.findViewById(R.id.titleTextView);
		TextView locationView = (TextView) v.findViewById(R.id.locationTextView);
		
		titleView.setText(TextUtils.isEmpty(entity.getTitle())?"":Html.fromHtml(entity.getTitle()));
		
		if(TextUtils.isEmpty(entity.getLocation()))
		{
			locationView.setVisibility(View.GONE);
		}else
		{
			locationView.setVisibility(View.VISIBLE);
			locationView.setText(Html.fromHtml(entity.getLocation()));
		}
		
		v.setTag(entity);
		return v;

	}

}
