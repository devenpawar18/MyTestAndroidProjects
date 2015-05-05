package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.adapter.SingleItemAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobEntity;

public class SearchProfessionActivity extends GlobalBaseActivity {

	private ListView itemList;
	private InputMethodManager imm;
	private EditText searchTextView;
	private Handler searchProfessionFilter;
	private Runnable searchProfessionFilterRunnable;
	private ProgressBar titleProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textsearch);
		titleProgressBar = (ProgressBar) findViewById(R.id.title_progress_bar);
		setTitleWithResource(R.string.search_Occupation);
		
		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		itemList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (imm != null) {
					imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
				}
				JobEntity entity = (JobEntity) arg1.getTag();
				FilterEntity.getFilterInstance().professionId = entity.getId();
				if (entity.getId().length() != 0) {
					FilterEntity.getFilterInstance().professionName = entity.getName();
				} else {
					FilterEntity.getFilterInstance().professionName = "";
				}
				finish();
			}
		});

		searchTextView = (EditText) findViewById(R.id.serchText);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null)
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		
		searchTextView.addTextChangedListener(new TextWatcher() {
			String text = "";

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				text = s.toString();
				Log.d("SearchProfessionActivity", "beforeTextChanged :: " + "text :: "+text);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
//				if (text.length() > 0) {
//					if (text.length() > s.toString().length()) {
//						
//					} else {
//						if (s.toString().length() > 2) {
//							makeNetworkCall(s.toString());
//						} else {
//							clearList();
//						}
//					}
//				} 
				String text = s.toString();
				text = stripSpecialChar(text);
				if (text.length() > 2) {
					makeNetworkCall(text);
				} else {
					clearList();
				}
			}
		});
		
		searchTextView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					if (imm != null)
						imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
				}
				return false;
			}
		});
		ArrayList<JobEntity> list = new ArrayList<JobEntity>();
		JobEntity entity = new JobEntity();
		entity.setId("");
		entity.setName((String) getResources().getText(R.string.all));
		entity.setJobCount("0");
		list.add(entity);
		itemList.setAdapter(new SingleItemAdapter(this, "", list));

	}

	@Override
	public boolean onSearchRequested() {
		if (imm != null)
			imm.hideSoftInputFromWindow(searchTextView.getWindowToken(), 0);
		return false;
	}
	@Override
	public void refreshList() {
		// TODO Auto-generated method stub

	}
	
	
	
	@Override
	public void refreshCompleted(Object data) {
		titleProgressBar.setVisibility(View.GONE);
		
		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_occupations), Toast.LENGTH_SHORT).show();
			return;
		}


		ArrayList<JobEntity> list = new ArrayList<JobEntity>();
		JobEntity entity = new JobEntity();
		entity.setId("");
		entity.setName((String) getResources().getText(R.string.all));
		entity.setJobCount("0");
		list.add(entity);
		if (searchTextView.getText().length() > 2) {
			list.addAll((ArrayList<JobEntity>) data);
		} else {
			if (searchProfessionFilter != null) {
				searchProfessionFilter.removeCallbacks(searchProfessionFilterRunnable);
				searchProfessionFilter = null;
			}
		}
		itemList.setAdapter(new SingleItemAdapter(this, "", list));

//		if (itemList ==null || itemList.getAdapter()== null ||itemList.getAdapter().getCount() <= 0) {
//			TextView emptyText = (TextView) findViewById(R.id.emptyText);
//			emptyText.setVisibility(View.VISIBLE);
//			emptyText.setText(R.string.emptyValueText);
//		}

	}
	
	private void makeNetworkCall(final String text) {
		Log.d("SearchProfessionActivity", "makeNetworkCall :: " + "text :: "+text);
		if (searchProfessionFilter == null) {
			searchProfessionFilter = new Handler();
			searchProfessionFilterRunnable = new Runnable() {
				@Override
				public void run() {
					titleProgressBar.setVisibility(View.VISIBLE);
					AppController controller = new AppController(SearchProfessionActivity.this);
					controller.getProfessionsFilter(FILTER_PROFESSIONS_URL + text);
				}
			};
		} else {
			searchProfessionFilter.removeCallbacks(searchProfessionFilterRunnable);
			searchProfessionFilterRunnable = new Runnable() {
				@Override
				public void run() {
					titleProgressBar.setVisibility(View.VISIBLE);
					AppController controller = new AppController(SearchProfessionActivity.this);
					controller.getProfessionsFilter(FILTER_PROFESSIONS_URL + text);
				}
			};
		}
		searchProfessionFilter.postDelayed(searchProfessionFilterRunnable, 1000);
	}

	
	private String stripSpecialChar(String text) {
		text = text.replaceAll("[=\\*@£$%#())\\?<>|,;.:-_´`^~+§½¤&]", " ");
		return text;
	}
	
	private void clearList() {
		if (searchProfessionFilter == null) {
			return;
		}
		if(searchProfessionFilter != null) { 
			searchProfessionFilter.removeCallbacks(searchProfessionFilterRunnable);
		}
		searchProfessionFilter = null;
		ArrayList<JobEntity> list = new ArrayList<JobEntity>();
		JobEntity entity = new JobEntity();
		entity.setId("");
		entity.setName((String) getResources().getText(R.string.all));
		entity.setJobCount("0");
		list.add(entity);
		itemList.setAdapter(new SingleItemAdapter(this, "", list));
	}
}
