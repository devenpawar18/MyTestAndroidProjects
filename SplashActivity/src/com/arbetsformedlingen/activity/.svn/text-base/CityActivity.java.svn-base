package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.arbetsformedlingen.adapter.SingleItemAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobEntity;

public class CityActivity extends GlobalBaseActivity {
	

	ListView itemList;
	String name;
	String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grouplayout);
		
		//setTitleText(KOMMUN_TITLE);
		//setTitleWithResource(R.string.localText);
		switch(currentActivityState)
		{
		case ContactCountry:
			setTitleWithResource(R.string.employment_Office);
			
		break;
		case WorkLocation:
			setTitleWithResource(R.string.municipality);
			break;
		
		}

		id = getIntent().getStringExtra("CountryID");
		name = getIntent().getStringExtra("CountryName");
		
		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		itemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent newIntent;
				JobEntity entity = (JobEntity)arg1.getTag();
				
				if(TextUtils.isEmpty(entity.getId())){
					return;
				}
				
				switch(currentActivityState)
				{
				case ContactCountry:
					newIntent = new Intent(CityActivity.this,OfficeDetailActivity.class);
					newIntent.putExtra("CityID", entity.getId());
					newIntent.putExtra("EmploymentOfficeName", entity.getName());
					Log.d("CityID :", entity.getId());
					startActivity(newIntent);
					
				break;
				case WorkLocation:
					FilterEntity.newFilterInstance();
					newIntent = new Intent(CityActivity.this,ResultListActivity.class);
					FilterEntity.getFilterInstance().cityId = entity.getId();
					FilterEntity.getFilterInstance().cityName = entity.getName();
					FilterEntity.getFilterInstance().filterSearchType = FilterEntity.SERACH_BY_LOCATION;
					startActivity(newIntent);
					break;
				
				}
			}
			
		});
		
		AppController controller = new AppController(CityActivity.this);
		switch(currentActivityState)
		{
		case ContactCountry:
			controller.getContactCity(CONTACT_CITY_URL + id);
			
		break;
		case WorkLocation:
			controller.getCountries(CITY_URL + id);
			break;
		
		}
	}


	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refreshCompleted(Object data) {
		
		
		
		switch(currentActivityState)
		{
		case ContactCountry:
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_Employment_offices), Toast.LENGTH_SHORT).show();
				break;
			}
			itemList.setAdapter(new SingleItemAdapter(this,"",(ArrayList<JobEntity>)data));
			
		break;
		case WorkLocation:
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_Municipalities), Toast.LENGTH_SHORT).show();
				break;
			}
			itemList.setAdapter(new SingleItemAdapter(this,name,(ArrayList<JobEntity>)data));
			break;
		}

//		if(itemList ==null || itemList.getAdapter()== null ||itemList.getAdapter().getCount()<=0)
//		{
//			TextView emptyText = (TextView)findViewById(R.id.emptyText);
//			emptyText.setVisibility(View.VISIBLE);
//			emptyText.setText(R.string.emptyValueText);
//		}
		
	}

}
