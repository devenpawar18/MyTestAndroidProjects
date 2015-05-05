package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.arbetsformedlingen.adapter.ResultListAdapter;
import com.arbetsformedlingen.adapter.SingleItemAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobEntity;
import com.arbetsformedlingen.entity.SearchResultEntity;

public class SearchActivity extends GlobalBaseActivity {

	private ListView itemList;
	private InputMethodManager imm;
	private EditText searchTextView;
	private View headerView;
	private int offset = 1;
	private ArrayList<SearchResultEntity> resultList;
	private View footerView;
	private ResultListAdapter resultListAdapter;
	private String previousSearchText="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textsearch);

		resultList = new ArrayList<SearchResultEntity>();

		resultListAdapter = new ResultListAdapter(this, resultList);

		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		headerView = LayoutInflater.from(this).inflate(R.layout.freetext_header, null, false);
		headerView.setEnabled(false);
		headerView.setFocusable(false);
		headerView.setFocusableInTouchMode(false);
		itemList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent newIntent;
				Log.d("Search text", currentActivityState + "");
				switch (currentActivityState) {
				case Search:
					SearchResultEntity searchEntity = (SearchResultEntity) arg1.getTag();
					if (searchEntity != null) {
						Log.d("SearchActivity", "onItemClick :: " + "searchEntity.getLocation() :: "+searchEntity.getLocation());
						newIntent = new Intent(SearchActivity.this, JobDetailActivity.class);
						newIntent.putExtra("JobID", searchEntity.getId());
						startActivity(newIntent);
					}
					break;
				case FilterProfession:
					JobEntity entity = (JobEntity) arg1.getTag();
					FilterEntity.getFilterInstance().professionId = entity.getId();
					FilterEntity.getFilterInstance().professionName = entity.getName();
					finish();
					break;
				}
			}
		});

		searchTextView = (EditText) findViewById(R.id.serchText);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null)
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

		searchTextView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					if (imm != null)
						imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
					if(searchTextView.getText()!=null&&!searchTextView.getText().toString().equals("")
							&&!searchTextView.getText().toString().equals(previousSearchText))
					{
						return searchText();
					}
				}
				return false;
			}
		});

		switch (currentActivityState) {
		case Search:
			setTitleWithResource(R.string.text_Search_Label);

			break;
		case FilterProfession:
			setTitleWithResource(R.string.search_Occupation);
			break;
		}

		switch (currentActivityState) {

		case FilterProfession:
			ArrayList<JobEntity> list = new ArrayList<JobEntity>();
			JobEntity entity = new JobEntity();
			entity.setId("");
			entity.setName((String) getResources().getText(R.string.all));
			entity.setJobCount("0");
			list.add(entity);
			itemList.setAdapter(new SingleItemAdapter(this, "", list));
			break;
		}

		footerView = LayoutInflater.from(this).inflate(R.layout.singleitem_row, null, false);

		TextView loadMoreView = (TextView) footerView.findViewById(R.id.itemTextView);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			loadMoreView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		loadMoreView.setText(R.string.load_More);
		loadMoreView.setGravity(Gravity.CENTER);
		footerView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				offset++;
				refreshList();
			}

		});

	}

	private boolean searchText()
	{
		AppController controller = new AppController(SearchActivity.this);
		switch (currentActivityState) {
		case Search:
			offset = 1;
			tracker.trackEvent("Searchwords", searchTextView.getText().toString() + "", "Android", 5);
			String url = SEARCH_URL + searchTextView.getText().toString() + "&sida=" + offset;
			controller.getSearchResult(url);
			return true;
		case FilterProfession:
			controller.getProfessions(FILTER_PROFESSIONS_URL + searchTextView.getText().toString());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onSearchRequested() {
		
		if(searchTextView.getText()!=null&&!searchTextView.getText().toString().equals("")
				&&!searchTextView.getText().toString().equals(previousSearchText))
		{
			searchText();
		}
		if (imm != null)
			imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
		return false;
	}
	
	@Override
	public void refreshList() {
		AppController controller = new AppController(SearchActivity.this);
		tracker.trackEvent("Searchwords", searchTextView.getText() + "", "Android", 5);
		String url = SEARCH_URL + searchTextView.getText() + "&sida=" + offset;
		controller.getSearchResult(url);
	}

	@Override
	public void refreshCompleted(Object data) {
		switch (currentActivityState) {
		case Search:
			TextView jobCount = (TextView) headerView.findViewById(R.id.jobCount);
			if (itemList.getHeaderViewsCount() == 0) {
				itemList.addHeaderView(headerView);
				itemList.setAdapter(resultListAdapter);
			}
			if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
				jobCount.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
			}
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_jobs), Toast.LENGTH_SHORT).show();
				jobCount.setText(R.string.no_Ads_Found);
				resultList.clear();
				resultListAdapter.updateResultList(resultList);
				itemList.removeFooterView(footerView);
				return;
			}
			previousSearchText = searchTextView.getText().toString();
			
			itemList.setVisibility(View.VISIBLE);

			ArrayList<SearchResultEntity> retList = null;
			if (data instanceof ArrayList) {
				retList = (ArrayList<SearchResultEntity>) data;
			}

			if (offset == 1) {
				resultList = new ArrayList<SearchResultEntity>();
			}

			

			if (itemList.getFooterViewsCount() == 0 && retList.size() != 0) {
				itemList.addFooterView(footerView);
			}

			resultList.addAll(retList);

			String t_noOfResults = FilterEntity.getFilterInstance().jobCount;

			
			if (itemList == null || itemList.getAdapter() == null || itemList.getAdapter().getCount() <= 0 || retList.size() == 0) {
				jobCount.setText(R.string.no_Ads_Found);
			} else {
				jobCount.setText(FilterEntity.getFilterInstance().jobCount + " " + (String) this.getResources().getText(R.string.ads_Found));
			}
			

			int t_items = 0;
			try {
				t_items = Integer.parseInt(t_noOfResults);
			} catch (IllegalArgumentException e) {
				Log.d("Exception", "while refreshing list t_noOfResults :: " + t_noOfResults);
			}

			if (resultList.size() == t_items) {
				itemList.removeFooterView(footerView);
			}
			Log.d("SearchActivity", "refreshCompleted :: " + "resultlist :: "+resultList);
			resultListAdapter.updateResultList(resultList);
			break;
		case FilterProfession:
			if (imm != null)
				imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
			ArrayList<JobEntity> list = new ArrayList<JobEntity>();
			JobEntity entity = new JobEntity();
			entity.setId("");
			entity.setName((String) getResources().getText(R.string.all));
			entity.setJobCount("0");
			list.add(entity);
			list.addAll((ArrayList<JobEntity>) data);
			itemList.setAdapter(new SingleItemAdapter(this, "", list));
			break;
		}
	}
}
