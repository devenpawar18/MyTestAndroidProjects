package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arbetsformedlingen.adapter.SingleItemAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.JobEntity;

public class WorkLocationActivity extends GlobalBaseActivity {
	

	ListView itemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grouplayout);
		
		//setTitleText(WORK_LOCATION_TITLE);
		setTitleWithResource(R.string.County);
		
		itemList = (ListView)findViewById(R.id.itemList);
		itemList.setDivider(null);
		itemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				JobEntity entity = (JobEntity)arg1.getTag();
				if(!TextUtils.isEmpty(entity.getId())){
				Intent newIntent = new Intent(WorkLocationActivity.this, CityActivity.class);
				newIntent.putExtra("CountryID", entity.getId());
				newIntent.putExtra("CountryName", entity.getName());
				startActivity(newIntent);
				}
			}
			
		});
		
		AppController controller = new AppController(WorkLocationActivity.this);
		switch(currentActivityState)
		{
		case ContactCountry:
			GlobalBaseActivity.tracker.trackPageView("/Android/OfficeListViewed");
			controller.getCountries(CONTACT_COUNTRY_URL);
			
		break;
		case WorkLocation:
	        controller.getCountries(COUNTRY_URL);
			break;
		
		}

	}

	@Override
	public void refreshList() {
		
	}

	@Override
	public void refreshCompleted(Object data) {
		switch(currentActivityState)
		{
		case ContactCountry:
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_counties), Toast.LENGTH_SHORT).show();
				break;
			}
			itemList.setAdapter(new SingleItemAdapter(this,"",(ArrayList<JobEntity>)data));
			
		break;
		case WorkLocation:
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_counties), 500).show();
				break;
			}
			itemList.setAdapter(new SingleItemAdapter(this,"Sweden",(ArrayList<JobEntity>)data));
			break;
		
		}

//		if(itemList ==null || itemList.getAdapter()== null || itemList.getAdapter().getCount()<=0)
//		{
//			TextView emptyText = (TextView)findViewById(R.id.emptyText);
//			emptyText.setVisibility(View.VISIBLE);
//			emptyText.setText(R.string.emptyValueText);
//		}
	}

}
