
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
import com.arbetsformedlingen.entity.JobEntity;


public class SingleItemAdapter extends BaseAdapter {

	private Context context;
	private int totalCount;
	private String listTitle;
	private ArrayList<JobEntity> jobEntityList = new ArrayList<JobEntity>();

	/**
	 * @param context
	 * @param c
	 */
	public SingleItemAdapter(Context m_context,String listTitle, ArrayList<JobEntity> arrayList) {
		this.context = m_context;
		this.listTitle = listTitle;

//		for(JobEntity entity: arrayList)
//		{
//			totalCount +=TextUtils.isEmpty(entity.getJobCount())?0:Integer.parseInt(entity.getJobCount());
//		}
//
//		if(arrayList.size()>0 && !TextUtils.isEmpty(listTitle)){
//			JobEntity titleEntity = new JobEntity();
//			String allofjobsText = (String)context.getResources().getText(R.string.allofjobsText);
//			titleEntity.setName(allofjobsText +" "+ listTitle);
//			titleEntity.setJobCount(String.valueOf(totalCount));
//			this.jobEntityList.add(titleEntity);
//		}
		
		this.jobEntityList.addAll(arrayList);
		 
	}


	@Override
	public int getCount() {

		return jobEntityList != null ? jobEntityList.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return jobEntityList.get(position);
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
			v = vi.inflate(R.layout.singleitem_row, null);
		}
		
		JobEntity entity = jobEntityList.get(position);
		TextView titleView = (TextView) v.findViewById(R.id.itemTextView);
		TextView locationView = (TextView) v.findViewById(R.id.valueTextView);
		
		titleView.setText(Html.fromHtml(entity.getName()));
		locationView.setText(Html.fromHtml(entity.getJobCount()));
		
		if(GlobalBaseActivity.currentActivityState == GlobalBaseActivity.ActivityState.ContactCountry
				|| TextUtils.isEmpty(listTitle))
			locationView.setVisibility(View.INVISIBLE);
		
		
		v.setTag(entity);
		return v;

	}

}
