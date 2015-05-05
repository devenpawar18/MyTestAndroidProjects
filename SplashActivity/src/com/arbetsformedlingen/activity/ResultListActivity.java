package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.adapter.ResultListAdapter;
import com.arbetsformedlingen.adapter.ResultListSectionAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.SearchResultEntity;

public class ResultListActivity extends GlobalBaseActivity {


	public static final int FILTER_RESULT_CODE = 143;
	private ListView itemList;
	private FilterEntity entity;
	private View headerView;
	private TextView jobcountText;
	private TextView jobFilterText;
	private int offset=1;
	private View footerView;
	private ArrayList<SearchResultEntity> nonrelatedResultList;
	private ArrayList<SearchResultEntity> relatedResultList;
	private ResultListSectionAdapter resultListAdapter;
	private ResultListAdapter nonrelatedResultListAdapter;
	private ResultListAdapter relatedResultListAdapter;
	private boolean isFromFav;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resultlayout);

		//setTitleText(RESULTLIST_TITLE);
		setTitleWithResource(R.string.job_Ads);
		setFavroiteImage();

		tracker.trackPageView("/Android/JobListViewed");

		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		nonrelatedResultList = new ArrayList<SearchResultEntity>();
		relatedResultList = new ArrayList<SearchResultEntity>();

		itemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {


				if(position!=0)
				{
					SearchResultEntity entity = (SearchResultEntity)arg1.getTag();
					Intent newIntent = new Intent(ResultListActivity.this, JobDetailActivity.class);
					newIntent.putExtra("JobID", entity.getId());
					Log.d("JobID :", entity.getId());
					newIntent.putExtra("JobLocation", entity.getLocality());
					startActivity(newIntent);
				}
			}

		});

		refreshList();
		
		isFromFav = getIntent().getBooleanExtra("IsFavroite", false); 

		final ImageView refreshView  = (ImageView ) findViewById(R.id.titleImage);
		boolean isFav = FilterEntity.getFilterInstance().isFavroiteFilter(ResultListActivity.this);

		if(isFav)
			refreshView.setImageResource(R.drawable.favourites_disable);
		else
			refreshView.setImageResource(R.drawable.favourites);
		
		refreshView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					GlobalBaseActivity.tracker.trackEvent("Favorite", "Search",  "Android", 10);    
					refreshView.setImageResource(R.drawable.favourites_disable);
					//FilterEntity.setFavoriteFilterValues(ResultListActivity.this);
					if(!FilterEntity.getFilterInstance().isFavroiteFilter(ResultListActivity.this)){
						FilterEntity.getFilterInstance().saveFavroiteFilter(ResultListActivity.this);
						//Toast.makeText(ResultListActivity.this, (String)getResources().getText(R.string.addToFavText), 500).show();
					}
					/*else
						Toast.makeText(ResultListActivity.this, (String)getResources().getText(R.string.alreadyFav), 500).show();*/
				}

			});
		
		headerView = LayoutInflater.from(this).inflate(R.layout.resultitem_row, null, false);
		Log.d("ResultListAdapter :: LayoutInflater = " ,"resultitem_row");
		
		TextView filterButton  = (TextView) headerView.findViewById(R.id.filter_button_image);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			filterButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		filterButton.setText(R.string.filter);
		filterButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(ResultListActivity.this, FilterActivity.class);
				((Activity) ResultListActivity.this).startActivityForResult(newIntent,ResultListActivity.FILTER_RESULT_CODE);
			}
			
		});
		if (currentLocale != null && currentLocale.equals("ru")) {
			filterButton.setBackgroundResource(R.layout.large_image_selector);
		}
		
		footerView = LayoutInflater.from(this).inflate(R.layout.singleitem_row, null, false);
		Log.d("ResultListAdapter :: LayoutInflater = " ,"singleitem_row");
		
		TextView loadMoreView  = (TextView) footerView.findViewById(R.id.itemTextView);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			loadMoreView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		loadMoreView.setText((String)getResources().getText(R.string.load_More));
		loadMoreView.setGravity(Gravity.CENTER);
		footerView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				offset++;
				refreshList();
			}
			
		});
		
		jobcountText = (TextView) headerView.findViewById(R.id.jobCount);
		jobFilterText = (TextView) headerView.findViewById(R.id.jobFilter);
		
		
		
		resultListAdapter = new  ResultListSectionAdapter(this);
		
		nonrelatedResultListAdapter = new ResultListAdapter(this,nonrelatedResultList);
		relatedResultListAdapter = new ResultListAdapter(this,nonrelatedResultList);
		
		resultListAdapter.addSection("", nonrelatedResultListAdapter);
		resultListAdapter.addSection(getResources().getString(R.string.related_matches), relatedResultListAdapter);
		
		
		if (itemList.getHeaderViewsCount() == 0) {
			itemList.addHeaderView(headerView);
			
		}
		itemList.setAdapter(resultListAdapter);
	}



	@Override
	public void refreshList() {

		AppController controller = new AppController(ResultListActivity.this);

		String url = RESULT_URL;
		entity = FilterEntity.getFilterInstance();
		if(!TextUtils.isEmpty(entity.cityId) && !TextUtils.isEmpty(entity.professionId))
			url += "kommunid=" + entity.cityId + "&yrkesid=" + entity.professionId;
		else if(!TextUtils.isEmpty(entity.cityId))
			url += "kommunid=" + entity.cityId;
		else if(!TextUtils.isEmpty(entity.professionId))
			url += "yrkesid=" + entity.professionId;
		
			url += "&sida=" + offset;

		controller.getSearchResult(url);
		Log.d("url :", url);

	}
	
	
	
@Override
protected void onRestart() {
	final ImageView refreshView  = (ImageView ) findViewById(R.id.titleImage);
	boolean isFav = FilterEntity.getFilterInstance().isFavroiteFilter(ResultListActivity.this);

	if(isFav)
		refreshView.setImageResource(R.drawable.favourites_disable);
	else
		refreshView.setImageResource(R.drawable.favourites);
	super.onRestart();
}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode== FILTER_RESULT_CODE)
		{
			offset=1;
			refreshList();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void refreshCompleted(Object data) {
		
		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_jobs), Toast.LENGTH_SHORT).show();
			return;
		}
		
		ArrayList<SearchResultEntity> retList =  null;
		if (data instanceof ArrayList) {
			retList  = (ArrayList<SearchResultEntity>)data;
		}
		
		if(offset==1){
			nonrelatedResultList = new ArrayList<SearchResultEntity>();
			relatedResultList = new ArrayList<SearchResultEntity>();

			//FilterEntity.setLatestFilterValues(this);
			//if(!FilterEntity.getFilterInstance().isFavroiteFilter(this)){
			if(!isFromFav)
				FilterEntity.getFilterInstance().saveCurrentFilter(this);
			//}
				
		}
		
		
		
		if (itemList.getFooterViewsCount() == 0 && retList.size() != 0) {
			itemList.addFooterView(footerView);
		}
		
		int relevance =-1;
		for(int i =0 ;i <retList.size();i++)
		{
			relevance= -1;
			try{
			relevance = Integer.parseInt(retList.get(i).getRelavance());
			Log.d("Result Adapter", "relevance =" + relevance);
			
			}catch(Exception ex)
			{}
			
			if(relevance<100)
			{
				relatedResultList.add(retList.get(i));
			}else
				nonrelatedResultList.add(retList.get(i));
			
		}
		//resultList.addAll(retList);
		
		String t_noOfResults = FilterEntity.getFilterInstance().jobCount;
		
		int t_items = 0;
		try {
			t_items = Integer.parseInt(t_noOfResults);
		} catch (IllegalArgumentException e) {
			Log.d("Exception", "while refreshing list t_noOfResults :: "+t_noOfResults);
		}
//		itemList.setAdapter(new ResultListAdapter(this, resultList));
		
		if((relatedResultList.size() + nonrelatedResultList.size())  == t_items)
		{
			itemList.removeFooterView(footerView);
		}
		
		nonrelatedResultListAdapter.updateResultList(nonrelatedResultList);
		relatedResultListAdapter.updateResultList(relatedResultList);
		resultListAdapter.notifyDataSetChanged();

		jobFilterText.setVisibility(View.GONE);
		
//		int t_noOfResults = 0;
//		if (!resultList.isEmpty()) {
//			t_noOfResults = resultList.size();
//		}
		
		
		
		// Set Header data separately
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			jobcountText.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
			jobFilterText.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		if(!TextUtils.isEmpty(FilterEntity.getFilterInstance().cityName) &&!TextUtils.isEmpty(FilterEntity.getFilterInstance().professionName))
		{
			if(FilterEntity.getFilterInstance().filterSearchType == FilterEntity.SERACH_BY_OCCUPATION){
				
			jobcountText.setText(t_noOfResults + " " + (String)this.getResources().getText(R.string.ads_For)+ " " + FilterEntity.getFilterInstance().professionName);
			jobFilterText.setText((String)this.getResources().getText(R.string.filter)+ ": " +	FilterEntity.getFilterInstance().cityName);
			jobFilterText.setVisibility(View.VISIBLE);
			}else if(FilterEntity.getFilterInstance().filterSearchType == FilterEntity.SERACH_BY_LOCATION)
			{
				
				jobcountText.setText(t_noOfResults + " " + (String)this.getResources().getText(R.string.ads_In)+ " " + FilterEntity.getFilterInstance().cityName);
				jobFilterText.setText((String)this.getResources().getText(R.string.filter)+ ": " +	FilterEntity.getFilterInstance().professionName);
				jobFilterText.setVisibility(View.VISIBLE);
			}
		}else if(!TextUtils.isEmpty(FilterEntity.getFilterInstance().cityName))
		{
			jobcountText.setText(t_noOfResults + " " + (String)this.getResources().getText(R.string.ads_In)+ " " + FilterEntity.getFilterInstance().cityName);
			jobFilterText.setVisibility(View.GONE);
		}else if(!TextUtils.isEmpty(FilterEntity.getFilterInstance().professionName))
		{
			jobcountText.setText(t_noOfResults + " " + (String)this.getResources().getText(R.string.ads_For)+ " " + FilterEntity.getFilterInstance().professionName);
			jobFilterText.setVisibility(View.GONE);
		}
		
//		
//		if(GlobalBaseActivity.currentActivityState == GlobalBaseActivity.ActivityState.SubProfession){
//			jobcountText.setText(t_noOfResults + " " + (String)this.getResources().getText(R.string.jobsForText)+ " " + FilterEntity.getFilterInstance().professionName);
//			if (FilterEntity.getFilterInstance().cityName.length() > 0) {
//				jobFilterText.setText((String)this.getResources().getText(R.string.filter)+ ": " +	FilterEntity.getFilterInstance().cityName);
//				jobFilterText.setVisibility(View.VISIBLE);
//			}
//		} else {
//			jobcountText.setText(t_noOfResults + " " + (String)this.getResources().getText(R.string.jobsForText) + " " + FilterEntity.getFilterInstance().cityName);
//			if (FilterEntity.getFilterInstance().professionName.length() > 0) {
//				jobFilterText.setText((String)this.getResources().getText(R.string.filter)+ ": " +	FilterEntity.getFilterInstance().professionName);
//				jobFilterText.setVisibility(View.VISIBLE);
//			}
//		}
//		if (itemList ==null || itemList.getAdapter()== null || itemList.getAdapter().getCount() <= 0) {
//			TextView emptyText = (TextView) findViewById(R.id.emptyText);
//			emptyText.setVisibility(View.VISIBLE);
//			emptyText.setText(R.string.emptyValueText);
//		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FilterEntity.newFilterInstance();
			return true;
		}
		return false;
	}




}
