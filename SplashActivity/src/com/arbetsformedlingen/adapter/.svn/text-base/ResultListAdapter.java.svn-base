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

public class ResultListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<SearchResultEntity> resultArrayList;

	/**
	 * @param context
	 * @param c
	 */
	public ResultListAdapter(Context m_context, ArrayList<SearchResultEntity> list) {
		this.context = m_context;
		// this.resultArrayList.add(new SearchResultEntity());
		this.resultArrayList = list;

	}

	public void updateResultList(ArrayList<SearchResultEntity> list) {
		this.resultArrayList = list;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {

		return resultArrayList != null ? resultArrayList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return resultArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.multipleitem_row, null);
		}
		
		SearchResultEntity entity = resultArrayList.get(position);
		TextView titleView = (TextView) v.findViewById(R.id.titleTextView);
		TextView dateView = (TextView) v.findViewById(R.id.dateTextView);
		TextView localityView = (TextView) v.findViewById(R.id.localityTextView);
		TextView locationView = (TextView) v.findViewById(R.id.locationTextView);
		
		titleView.setText(TextUtils.isEmpty(entity.getTitle())?"":Html.fromHtml(entity.getTitle()));
		if(TextUtils.isEmpty(entity.getPubDate()))
		{
			dateView.setVisibility(View.GONE);
		}else
		{
			dateView.setVisibility(View.VISIBLE);
			dateView.setText(Html.fromHtml(entity.getPubDate().substring(0,entity.getPubDate().indexOf("T"))));
		}
		
		if(TextUtils.isEmpty(entity.getLocality()))
		{
			localityView.setVisibility(View.GONE);
		}else
		{
			localityView.setVisibility(View.VISIBLE);
			localityView.setText(Html.fromHtml(entity.getLocality()));
		}
		
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
