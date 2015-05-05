package com.arbetsformedlingen.activity;

import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class PlatsbankenActivity extends GlobalBaseActivity{



	private AlertDialog listDialog;
	private ArrayList<String> languageList;
	private Handler splashHandler;
	//private static boolean isLaunched=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        
        tracker = GoogleAnalyticsTracker.getInstance();      
        // Start the tracker in manual dispatch mode...     
        tracker.start(GOOGLE_TRACKER_KEY, this); 
        
//        if (!isLaunched) {
//			setContentView(R.layout.splash_screen);
//			isLaunched = true;
//			splashHandler = new Handler();
//
//			splashHandler.postDelayed(runnable, 3000);
//
//		} else
			initiate();
	}
	
	
//	Runnable runnable = new Runnable() {
//
//		@Override
//		public void run() {
//			initiate();
//
//		}
//	};
	private TextView locationButton;
	private TextView categoryButton;
	private TextView freeTextButton;
	private TextView titleTextView;
	
	private void initiate(){
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			
		}
		setContentView(R.layout.platsbanken);
        
		//setTitleText(PLATSBANKEN_TITLE);
        setTitleWithResource(R.string.platsbanken);
        setTranslateImage();
        setInfoImage();
        
        tracker.trackPageView("/Android/AppLaunch");



        titleTextView  = (TextView ) findViewById(R.id.titleText);
        if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			titleTextView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
        titleTextView.setText(getResources().getText(R.string.loading_Jobs));
        
		
		freeTextButton = (TextView) findViewById(R.id.freesTextButton);
		freeTextButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				currentActivityState = ActivityState.Search;
				Intent newIntent = new Intent(PlatsbankenActivity.this,SearchActivity.class);
				startActivity(newIntent);
			}
		});
		
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			freeTextButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		freeTextButton.setText(R.string.text_Search);
		

		categoryButton = (TextView) findViewById(R.id.categoryButton);
		categoryButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				currentActivityState = ActivityState.Profession;
				Intent newIntent = new Intent(PlatsbankenActivity.this,ProfessionActivity.class);
				startActivity(newIntent);
			}
			
		});
		
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			categoryButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		categoryButton.setText(R.string.search_By_Occupation);

		locationButton = (TextView) findViewById(R.id.workPlaceButton);
		locationButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				currentActivityState = ActivityState.WorkLocation;
				Intent newIntent = new Intent(PlatsbankenActivity.this,WorkLocationActivity.class);
				startActivity(newIntent);
			}
			
		});

		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			locationButton.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		locationButton.setText(R.string.search_By_Job_Location);
		
		languageList = new ArrayList<String>();
		languageList.add((String)getResources().getText(R.string.swedishLanguage));
		languageList.add((String)getResources().getText(R.string.arabicLanguage));
		languageList.add((String)getResources().getText(R.string.englishLanguage));
		languageList.add((String)getResources().getText(R.string.frenchLanguage));
		languageList.add((String)getResources().getText(R.string.russianLanguage));
		languageList.add((String)getResources().getText(R.string.spanishLanguage));


		ImageView refreshView  = (ImageView ) findViewById(R.id.titleImage);
		refreshView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
//				String translateText = (String)getResources().getText(R.string.translate);
//				showDialog(translateText,languageList,langItemClickListner);
				
				finish();
				Intent intent = new Intent(PlatsbankenActivity.this, LocalizationActivity.class);
				startActivity(intent);
			}
			
		});
		
		ImageView infoImage  = (ImageView ) findViewById(R.id.infoImage);
		infoImage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(PlatsbankenActivity.this,InfoDetailActivity.class);
				startActivity(newIntent);				
			}
			
		});
		
		
		AppController controller = new AppController(this);
		controller.getJobCount(COUNTRY_URL);
		
		Handler handler = new Handler();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
					titleTextView.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
					freeTextButton.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
					categoryButton.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
					locationButton.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
				}
				titleTextView.setText(getResources().getText(R.string.loading_Jobs));
				freeTextButton.setText(R.string.text_Search);
		        categoryButton.setText(R.string.search_By_Occupation);
		        locationButton.setText(R.string.search_By_Job_Location);
			}
		});
		
	}
	
	

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refreshCompleted(Object data) {

		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_total_number_of_jobs), Toast.LENGTH_SHORT).show();
			return;
		}

		if(data instanceof Long)
		{
	        TextView titleTextView  = (TextView ) findViewById(R.id.titleText);
	        String currently =  (String)getResources().getText(R.string.currently_s_Ads);
	        StringBuffer temp = new StringBuffer(data + "");
	        if (temp.length() > 3) {
	        	temp.insert(temp.length() - 3, ' ' );
	        }
	        
	        
	        
	        if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
	        	
				titleTextView.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
				freeTextButton.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
				categoryButton.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
				locationButton.setTypeface(Typeface.createFromAsset(PlatsbankenActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
				temp.insert(0, ' ');
		        temp.append(' ');
				titleTextView.setText("الآن " + temp + " الوظائف الشاغرة");
			} else {
				try {
					titleTextView.setText(String.format(currently , temp));
				} catch (Exception e) {
					
				}
			}
	        
	        freeTextButton.setText(R.string.text_Search);
	        categoryButton.setText(R.string.search_By_Occupation);
	        locationButton.setText(R.string.search_By_Job_Location);
		}
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//        tracker.trackPageView("/Android/AppStopped");
//		if(tracker!=null)
//			tracker.stop();
//		
//		isLaunched = false;
//	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			tracker.trackPageView("/Android/AppStopped");
			if(tracker!=null)
				tracker.stop();
			
//			isLaunched = false;
			FilterActivity.countyList = null;
			currentLocale = null;
		}
		return super.onKeyDown(keyCode, event);
	}


}
