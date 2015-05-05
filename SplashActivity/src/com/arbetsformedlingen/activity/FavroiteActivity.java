package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

import com.arbetsformedlingen.adapter.FavroiteAdapter;
import com.arbetsformedlingen.adapter.SearchResultAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;
import com.arbetsformedlingen.entity.SearchResultEntity;

public class FavroiteActivity extends GlobalBaseActivity {
	ListView itemList;
	private ActivityState oldState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favroitelayout);
		setTitleWithResource(R.string.favorites);
			
		oldState = currentActivityState;
		
		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		itemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent newIntent = null ;
				Cursor tempCursor;
				SearchResultEntity entity = (SearchResultEntity)arg1.getTag();
				switch (oldState) {
				case FavroiteJob:
					newIntent = new Intent(FavroiteActivity.this,JobDetailActivity.class);
					newIntent.putExtra("JobID", entity.getId());
					newIntent.putExtra("IsFavroite", true);
					startActivity(newIntent);
					break;
				case FavroiteSearch:
					Log.d("Filter Favroite", entity.getId()+"");
					tempCursor = getContentResolver().query(FilterEntity.FILTER_CONTENT_URI, null,FilterEntity.ID_Column + "=" + entity.getId()+" AND " + FilterEntity.FILTER_TYPE + "=1", null, null);
					tempCursor.moveToFirst();
					FilterEntity.newFilterInstance();
					if(tempCursor.getCount()>0)
					{
						FilterEntity fEntity = FilterEntity.getFilterInstance();
						fEntity.cityId = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_CITY_ID));
						fEntity.cityName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_CITY_NAME));
						fEntity.professionId= tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_PROF_ID));
						fEntity.professionName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_PROF_NAME));
						fEntity.filterSearchType = tempCursor.getInt(tempCursor.getColumnIndex(FilterEntity.FILTER_SEARCH_TYPE));
						
						if(fEntity.filterSearchType == FilterEntity.SERACH_BY_LOCATION)
							currentActivityState=ActivityState.WorkLocation;
						else if(fEntity.filterSearchType == FilterEntity.SERACH_BY_OCCUPATION)
							currentActivityState=ActivityState.SubProfession;
						
						
					}
					
					tempCursor.close();
					tempCursor = null;

					newIntent = new Intent(FavroiteActivity.this,ResultListActivity.class);
					newIntent.putExtra("IsFavroite", true);
					startActivity(newIntent);
					break;
				case FavroiteLastSearch:
					Log.d("Filter Latest Serach", entity.getId()+"");
					tempCursor = getContentResolver().query(FilterEntity.FILTER_CONTENT_URI, null,FilterEntity.ID_Column + "=" + entity.getId()+" AND " + FilterEntity.FILTER_TYPE + "=0", null, null);
					tempCursor.moveToFirst();
					FilterEntity.newFilterInstance();
					if(tempCursor.getCount()>0)
					{
						FilterEntity fEntity = FilterEntity.getFilterInstance();
						fEntity.cityId = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_CITY_ID));
						fEntity.cityName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_CITY_NAME));
						fEntity.professionId= tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_PROF_ID));
						fEntity.professionName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_PROF_NAME));
						fEntity.filterSearchType = tempCursor.getInt(tempCursor.getColumnIndex(FilterEntity.FILTER_SEARCH_TYPE));
						
						if(fEntity.filterSearchType == FilterEntity.SERACH_BY_LOCATION)
							currentActivityState=ActivityState.WorkLocation;
						else if(fEntity.filterSearchType == FilterEntity.SERACH_BY_OCCUPATION)
							currentActivityState=ActivityState.SubProfession;
							
					}
					tempCursor.close();
					tempCursor = null;
					

					newIntent = new Intent(FavroiteActivity.this,ResultListActivity.class);
					newIntent.putExtra("IsFavroite", true);
					startActivity(newIntent);
					break;
				default:
					break;
				}
				
			}
			
		});
		
		SegmentedControlButton classified = (SegmentedControlButton)findViewById(R.id.classified);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			classified.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		classified.setText(R.string.ads);
		classified.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					oldState = ActivityState.FavroiteJob;
					getFavoriteJob();
				}
				
			}
			
		});
		
		SegmentedControlButton searched = (SegmentedControlButton)findViewById(R.id.searched);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			searched.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		searched.setText(R.string.Searches);
		searched.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					oldState = ActivityState.FavroiteSearch;
					getFavoriteFilter();
				}
				
			}
			
		});
		
		SegmentedControlButton lastSearch = (SegmentedControlButton)findViewById(R.id.lastSearch);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			lastSearch.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		lastSearch.setText(R.string.recent);
		lastSearch.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(isChecked)
				{
					oldState = ActivityState.FavroiteLastSearch;
					getLatestFilter();
				}
				
			}
			
		});

		oldState = ActivityState.FavroiteJob;
		getFavoriteJob();

		registerForContextMenu(itemList);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
//		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
			 SearchResultEntity entity = (SearchResultEntity)info.targetView.getTag();
			 switch (oldState) {
				case FavroiteJob:
					getContentResolver().delete(JobDetailEntity.JOBDETAIL_CONTENT_URI,JobDetailEntity.ID_COLUMN + "=" + entity.getId(), null);
					//Toast.makeText(this, "Deleted successfully.", 500).show();
					getFavoriteJob();
					break;
				case FavroiteSearch:
					getContentResolver().delete(FilterEntity.FILTER_CONTENT_URI,FilterEntity.ID_Column + "=" + entity.getId()+" AND " + FilterEntity.FILTER_TYPE + "=1", null);
					//Toast.makeText(this, "Deleted successfully.", 500).show();
					getFavoriteFilter();
					break;
				case FavroiteLastSearch:
					getContentResolver().delete(FilterEntity.FILTER_CONTENT_URI,FilterEntity.ID_Column + "=" + entity.getId()+" AND " + FilterEntity.FILTER_TYPE + "=0", null);
					//Toast.makeText(this, "Deleted successfully.", 500).show();
					getLatestFilter();
					break;
			 }
			break;
		case R.id.deleteall:
			 switch (oldState) {
				case FavroiteJob:
					getContentResolver().delete(JobDetailEntity.JOBDETAIL_CONTENT_URI,null, null);
					//Toast.makeText(this, "Deleted successfully.", 500).show();
					getFavoriteJob();
					break;
				case FavroiteSearch:
					getContentResolver().delete(FilterEntity.FILTER_CONTENT_URI,FilterEntity.FILTER_TYPE + "=1", null);
					//Toast.makeText(this, "Deleted successfully.", 500).show();
					getFavoriteFilter();
					break;
				case FavroiteLastSearch:
					getContentResolver().delete(FilterEntity.FILTER_CONTENT_URI,FilterEntity.FILTER_TYPE + "=0", null);
					//Toast.makeText(this, "Deleted successfully.", 500).show();
					getLatestFilter();
					break;
			 }
			break;
		}
		return super.onContextItemSelected(item);
	}
//	
//	@Override
//	protected void onResume() {
//		switch (currentActivityState) {
//		case FavroiteJob:
//			getFavoriteJob();
//			break;
//		case FavroiteSearch:
//			getFavoriteFilter();
//			break;
//		case FavroiteLastSearch:
//			getLatestFilter();
//			break;
//		default:
//			break;
//		}
//		super.onResume();
//	}
	
//	@Override
//	protected void onStart() {
//		super.onStart();
//		oldState = currentActivityState;
//	}

//	@Override
//	protected void onPause() {
//		Log.d("currentActivityState", currentActivityState.toString());
//		oldState = currentActivityState;
//		super.onPause();
//		
//	}
//	
//	@Override
//	protected void onRestart() {
//		super.onRestart();
//		Log.d("oldState", oldState.toString());
//		currentActivityState = oldState;
//	}

	private void getFavoriteFilter(){

		ArrayList<SearchResultEntity> data = new ArrayList<SearchResultEntity>();
		Cursor tempCursor = getContentResolver().query(FilterEntity.FILTER_CONTENT_URI, null, FilterEntity.FILTER_TYPE + "=1", null, null);
		tempCursor.moveToFirst();
		SearchResultEntity entity;
		
		Log.d("getFavoriteFilter", tempCursor.getCount() + " ");
		if(tempCursor.getCount()>0){
			while(!tempCursor.isAfterLast())
			{
				entity = new SearchResultEntity();
				entity.setId(tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.ID_Column)));
				String cityName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_CITY_NAME));
				String profName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_PROF_NAME));
				int searchType = tempCursor.getInt(tempCursor.getColumnIndex(FilterEntity.FILTER_SEARCH_TYPE));
				Log.d("getFavoriteFilter", cityName + " , " + profName);
				if(!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(profName))
				{
					if(searchType == FilterEntity.SERACH_BY_LOCATION)
					{
						entity.setTitle(cityName);
						entity.setLocation(profName);
					}else if(searchType == FilterEntity.SERACH_BY_OCCUPATION)
					{
						entity.setTitle(profName);
						entity.setLocation(cityName);
					}
					
				}
				else if(!TextUtils.isEmpty(cityName))
					entity.setTitle(cityName);
				else if(!TextUtils.isEmpty(profName))
					entity.setTitle(profName);
				data.add(entity);
				
				tempCursor.moveToNext();
			}
		}
		tempCursor.close();
		tempCursor = null;
		
		refreshCompleted(data);
		
	}
	
	private void getLatestFilter(){
		
		ArrayList<SearchResultEntity> data = new ArrayList<SearchResultEntity>();
		Cursor tempCursor = getContentResolver().query(FilterEntity.FILTER_CONTENT_URI, null, FilterEntity.FILTER_TYPE + "=0", null, null);
		tempCursor.moveToFirst();
		SearchResultEntity entity;
		Log.d("getLatestFilter", tempCursor.getCount() + " ");
		if(tempCursor.getCount()>0){
		while(!tempCursor.isAfterLast())
		{
			entity = new SearchResultEntity();
			entity.setId(tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.ID_Column)));
			String cityName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_CITY_NAME));
			String profName = tempCursor.getString(tempCursor.getColumnIndex(FilterEntity.FILTER_PROF_NAME));
			int searchType = tempCursor.getInt(tempCursor.getColumnIndex(FilterEntity.FILTER_SEARCH_TYPE));
			if(!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(profName)){

				if(searchType == FilterEntity.SERACH_BY_LOCATION)
				{
					entity.setTitle(cityName);
					entity.setLocation(profName);
				}else if(searchType == FilterEntity.SERACH_BY_OCCUPATION)
				{
					entity.setTitle(profName);
					entity.setLocation(cityName);
				}
			}
			else if(!TextUtils.isEmpty(cityName))
				entity.setTitle(cityName);
			else if(!TextUtils.isEmpty(profName))
				entity.setTitle(profName);
			data.add(entity);
			tempCursor.moveToNext();
		}
		}
		tempCursor.close();
		tempCursor = null;
		
		refreshCompleted(data);
	}
	
	private void getFavoriteJob() {
		ArrayList<SearchResultEntity> valueList = new ArrayList<SearchResultEntity>();
		Cursor cursor = getContentResolver().query(JobDetailEntity.JOBDETAIL_CONTENT_URI,null,null,null,null);
		cursor.moveToFirst();
		SearchResultEntity item;
		Log.d("getLatestFilter", cursor.getCount() + " ");
		if(cursor.getCount()>0)
		{
			while(!cursor.isAfterLast()){
				item = new SearchResultEntity();
				item.setId(cursor.getString(cursor.getColumnIndex(JobDetailEntity.ID_COLUMN)));
				item.setTitle(cursor.getString(cursor.getColumnIndex(JobDetailEntity.TITLE_COLUMN)));
				item.setLocation(cursor.getString(cursor.getColumnIndex(JobDetailEntity.POSTCOUNTRY_COLUMN)));
				item.setLocality(cursor.getString(cursor.getColumnIndex(JobDetailEntity.DISTRICT_COLUMN)));
				item.setPubDate(cursor.getString(cursor.getColumnIndex(JobDetailEntity.DATE_COLUMN)));
				valueList.add(item);
				cursor.moveToNext();
			}
		}
		cursor.close();
		cursor =null;
		
		refreshCompleted(valueList);
	}

	@Override
	public void refreshList() {
		AppController controller = new AppController(FavroiteActivity.this);
		
		String url = RESULT_URL;
		FilterEntity entity = FilterEntity.getFilterInstance();
		if(!TextUtils.isEmpty(entity.cityId) && !TextUtils.isEmpty(entity.professionId))
			url += "kommunid=" + entity.cityId + "&yrkesid=" + entity.professionId;
		else if(!TextUtils.isEmpty(entity.cityId))
			url += "kommunid=" + entity.cityId;
		else if(!TextUtils.isEmpty(entity.professionId))
			url += "yrkesid=" + entity.professionId;
		
        controller.getSearchResult(url);
        Log.d("url :", url);
	}


	@Override
	public void refreshCompleted(Object data) {
		
		switch (oldState) {
		case FavroiteJob:
			itemList.setAdapter(new SearchResultAdapter(this,(ArrayList<SearchResultEntity>)data));
			break;
		case FavroiteSearch:
		case FavroiteLastSearch:
			itemList.setAdapter(new FavroiteAdapter(this,(ArrayList<SearchResultEntity>)data));
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			currentActivityState = oldState;
//			return true;
//		}
		return false;
	}

}


