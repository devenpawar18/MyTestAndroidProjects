package com.arbetsformedlingen.activity;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbetsformedlingen.network.HttpConnectionCallback;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public abstract class GlobalBaseActivity extends Activity implements HttpConnectionCallback {
	
	public static final String GOOGLE_TRACKER_KEY = "UA-22524300-1";
//	public static final String ENGLISH_LANGUAGE = "Engelska/English,en";
//	public static final String FRENCH_LANGUAGE = "Franska/français,fr";
//	public static final String ARABIC_LANGUAGE = "Arabic,ar";
//	public static final String SPANISH_LANGUAGE = "Spanska/español,es";
//	public static final String RUSSIAN_LANGUAGE = "Russian,ru";
//	public static final String SWEDISH_LANGUAGE = "Swedish,sv";
	

	//public static   String APP_TITLE = "Sök bland %s lediga jobb";
//	public static   String APP_TITLE = "Search %s vacancies";
//	public static   String LOADING_APP_TITLE = "Loading jobb";
	public static   String ADDED_TO_FAVORITE = "Added to favorite";
	public static   String NO_PHONE_NUMBERS_FOUND = "No contact numbers";
	
	public static String CALLBACK_URL = "setting://callbackurl";
	public static final String CITY_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/kommuner?lanid=";
	public static final String CONTACT_CITY_URL = "http://api.arbetsformedlingen.se/arbetsformedling/platser?lanid=";
	public static final String OCCUPATION_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/yrkesomraden";
	public static final String COUNTRIES_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/kommuner";
	public static final String RESULT_URL = "http://api.arbetsformedlingen.se/platsannons/matchning?";
	public static final String PROFESSION_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/yrkesgrupper?yrkesomradeid=";
	public static final String COUNTRY_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/lan";
	public static final String CONTACT_COUNTRY_URL = "http://api.arbetsformedlingen.se/arbetsformedling/soklista/lan";
	public static final String SUBPROFESSION_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/yrken?yrkesgruppid=";
	public static final String JOB_DETAIL_URL = "http://api.arbetsformedlingen.se/platsannons/";
	public static final String CONTACT_DETAIL_URL = "http://api.arbetsformedlingen.se/arbetsformedling/";
	public static final String FILTER_PROFESSIONS_URL = "http://api.arbetsformedlingen.se/platsannons/soklista/yrken/";
	public static final String SEARCH_URL = "http://api.arbetsformedlingen.se/platsannons/matchning?nyckelord=";
	
//	private static final String LOGIN = "PlatsbankenMobil";
//	private static final String API_KEY = "R_9855ba184d8ef74cbe6538c85a194da1";
	
	private static String API_KEY = "R_e9b43fbb5f1e8ec6102bc0ade8e4ecf6";
	private static String LOGIN = "idgnederland";
	
	public static String SHORTEN_URL = "http://api.bit.ly/shorten?version=2.0.1&format=xml&login=" + LOGIN + "&apiKey=" + API_KEY + "&longUrl=";
	
	public static enum ActivityState{
		Occupation,
		Profession,
		SubProfession,
		Search,
		FilterProfession,
		ContactCountry,
		WorkLocation,
		FavroiteJob,
		FavroiteSearch,
		FavroiteLastSearch,
		JobDetail,
		OfficeDetail
	}
	
	public static ActivityState currentActivityState;
	
	public static  GoogleAnalyticsTracker tracker;
	public static  String currentLocale; 
	
	public abstract void refreshList();
	
	public void setTitleText(String titleText) {
		TextView titleTextView = (TextView) findViewById(R.id.heading);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			titleTextView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		titleTextView.setText(titleText);
	}
	
	public void setTitleWithResource(int resId) {
		TextView titleTextView = (TextView) findViewById(R.id.heading);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			titleTextView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		titleTextView.setText(resId);
	}
	
	public void setFavroiteImage() {
		ImageView refreshView  = (ImageView ) findViewById(R.id.titleImage);
		refreshView.setVisibility(View.VISIBLE);
		refreshView.setImageResource(R.drawable.favourites);
	}
	
	public void setInfoImage() {
		ImageView infoImage  = (ImageView ) findViewById(R.id.infoImage);
		infoImage.setVisibility(View.VISIBLE);
		infoImage.setImageResource(R.drawable.infoicon);
	}
	
	public void setTranslateImage() {
		ImageView refreshView  = (ImageView ) findViewById(R.id.titleImage);
		refreshView.setVisibility(View.VISIBLE);
		refreshView.setImageResource(R.drawable.globe);
	}

	public String getTitleText() {
		TextView titleTextView = (TextView) findViewById(R.id.heading);
		return (String) titleTextView.getText();
	}
	
	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	protected void onResume() {
		
		super.onResume();
		Log.d("GlobalBaseActivity", "onResume :: " + "currentLocale :: "+currentLocale);
		if(isAvailableLocale(currentLocale))
			setLocale(currentLocale);
		else 
			setLocale("sv");
	}
	
	
	private void setLocale(String langCode) {
		Log.d("GlobalBaseActivity", "setLocale :: " + "langCode :: "+langCode);
		if(!TextUtils.isEmpty(langCode))
		{
			Locale locale = new Locale(langCode);
			//Locale.setDefault(locale); 
			Configuration config = new Configuration(); 
			config.locale = locale; 
			getBaseContext().getResources().updateConfiguration(config, 
			      getBaseContext().getResources().getDisplayMetrics());
		}
	}
	
	public boolean isAvailableLocale(String langCode)
	{
		for(Locale locale : Locale.getAvailableLocales())
		{
			if(locale.getLanguage().equals(langCode))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.search:
			if (!(this instanceof PlatsbankenActivity)) {
				intent = new Intent(this, PlatsbankenActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(intent);
			}
			break;
		case R.id.favorite:

			if (!(this instanceof FavroiteActivity)) {
				intent = new Intent(this, FavroiteActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(intent);
			}
			break;
		case R.id.contact:
			if (!(this instanceof ContactActivity)) {
				intent = new Intent(this, ContactActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				this.startActivity(intent);
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}