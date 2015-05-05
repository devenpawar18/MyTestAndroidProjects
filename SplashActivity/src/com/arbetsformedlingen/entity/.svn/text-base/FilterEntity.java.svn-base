package com.arbetsformedlingen.entity;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class FilterEntity {
	public static final String APP_FILTER_DATA = "Arbetsförmedlingen";
	public static final String FILTER_CITY_ID = "filtercityid";
	public static final String FILTER_CITY_NAME= "filtercityname";
	public static final String FILTER_PROF_ID = "filterprofid";
	public static final String FILTER_PROF_NAME= "filterprofname";
	private static final String APP_FAV_FILTER_CITY_ID = "ArbetsförmedlingenFavCityID";
	private static final String APP_FAV_FILTER_PROF_ID = "ArbetsförmedlingenFavProfID";
	private static final String APP_LAT_FILTER_CITY_ID = "ArbetsförmedlingenLatCityID";
	private static final String APP_LAT_FILTER_PROF_ID = "ArbetsförmedlingenLatProfID";
	public static final String FILTER_TABLENAME = "filer";
	public static final String FilterEntity= "filter";
	public static final String FilterTypeName = "vnd.android.cursor.item/vnd.com.arbetsformedlingen.controller.AppContentProvider.Filter";
	public static final Uri FILTER_CONTENT_URI = Uri.parse("content://com.arbetsformedlingen.controller.AppContentProvider/filter");
	public static final String ID_Column = "_id";
	public static final String FILTER_TYPE = "filtertype";
	public static final String FILTER_SEARCH_TYPE = "filtersearchtype";
	public static final String FILTER_DATE = "filterdate";
	public String cityId;
	public String cityName;
	public String professionId;
	public String professionName;
	public String jobCount;
	public int filterSearchType; // 1=job location, 2= occupation
	public static final int SERACH_BY_LOCATION=1;
	public static final int SERACH_BY_OCCUPATION=2;
	
	private static FilterEntity filterValues;
	
	public static FilterEntity getFilterInstance(){
		if(filterValues==null){
			filterValues = new FilterEntity();
			filterValues.cityId="";
			filterValues.cityName="";
			filterValues.professionName="";
			filterValues.professionId="";
			filterValues.jobCount ="";
			filterValues.filterSearchType =0;
		}
		return filterValues;
	}
	
	public static void newFilterInstance(){
			filterValues = new FilterEntity();
			filterValues.cityId="";
			filterValues.cityName="";
			filterValues.professionName="";
			filterValues.professionId="";
			filterValues.jobCount ="";
			filterValues.filterSearchType=0;
		return;
	}
	
	public static final String CREATE_TABLE_FILTER = "create table " + FILTER_TABLENAME + " ("
	+ ID_Column + " integer primary key AUTOINCREMENT, "
	+ FILTER_CITY_ID + " text , " +  FILTER_CITY_NAME  + " text null,"
	+ FILTER_TYPE + " int , " + FILTER_SEARCH_TYPE + " int , "
	+ FILTER_PROF_ID + " text , " +  FILTER_PROF_NAME  + " text null , "
	+ FILTER_DATE + " long null"
	+ ");";
	
	
	public static void getLatestFilterValues(Context context) {
		 SharedPreferences filter = context.getSharedPreferences(APP_FILTER_DATA, 0);       
		 
		 getFilterInstance().cityId =  filter.getString(APP_LAT_FILTER_CITY_ID, "");
		 Log.d("FilterEntity", "getFavoriteFilterValues :: " + "FilterEntity.cityId :: "+getFilterInstance().cityId); 

		 getFilterInstance().professionId = filter.getString(APP_LAT_FILTER_PROF_ID, "");
		 Log.d("FilterEntity", "getFavoriteFilterValues :: " + "FilterEntity.professionId :: "+getFilterInstance().professionId);
	}
	
	public static void setLatestFilterValues(Context context) {
		 SharedPreferences filter = context.getSharedPreferences(APP_FILTER_DATA, 0);       
		 SharedPreferences.Editor editor = filter.edit();
		 Log.d("FilterEntity", "setFavoriteFilterValues :: " + "FilterEntity.cityId :: "+getFilterInstance().cityId);
		 editor.putString(APP_LAT_FILTER_CITY_ID, getFilterInstance().cityId);
		 Log.d("FilterEntity", "setFavoriteFilterValues :: " + "FilterEntity.professionId :: "+getFilterInstance().professionId);
		 editor.putString(APP_LAT_FILTER_PROF_ID, getFilterInstance().professionId);
		 editor.commit();
	}
	
	public static void getFavoriteFilterValues(Context context) {
		 SharedPreferences filter = context.getSharedPreferences(APP_FILTER_DATA, 0);
		 
		 getFilterInstance().cityId =  filter.getString(APP_FAV_FILTER_CITY_ID, "");
		 Log.d("FilterEntity", "getFavoriteFilterValues :: " + "FilterEntity.cityId :: "+getFilterInstance().cityId); 

		 getFilterInstance().professionId = filter.getString(APP_FAV_FILTER_PROF_ID, "");
		 Log.d("FilterEntity", "getFavoriteFilterValues :: " + "FilterEntity.professionId :: "+getFilterInstance().professionId);
	}
	
	public static void setFavoriteFilterValues(Context context) {
		 SharedPreferences filter = context.getSharedPreferences(APP_FILTER_DATA, 0);       
		 SharedPreferences.Editor editor = filter.edit();
		 Log.d("FilterEntity", "setFavoriteFilterValues :: " + "FilterEntity.cityId :: "+getFilterInstance().cityId);
		 editor.putString(APP_FAV_FILTER_CITY_ID, getFilterInstance().cityId);
		 Log.d("FilterEntity", "setFavoriteFilterValues :: " + "FilterEntity.professionId :: "+getFilterInstance().professionId);
		 editor.putString(APP_FAV_FILTER_PROF_ID, getFilterInstance().professionId);
		 editor.commit();
	}

	public void saveFavroiteFilter(Context context)
	{
		ContentValues values = new ContentValues();
		values.put(FILTER_CITY_ID, this.cityId);
		values.put(FILTER_CITY_NAME, this.cityName);
		values.put(FILTER_TYPE, 1);
		values.put(FILTER_SEARCH_TYPE, this.filterSearchType);
		values.put(FILTER_PROF_ID, this.professionId);
		values.put(FILTER_PROF_NAME, this.professionName);
		values.put(FILTER_DATE, (new Date()).getTime());
		Log.d("saveFavroiteFilter", cityName + " , " + this.professionName);
		context.getContentResolver().insert(FILTER_CONTENT_URI,values);
	}
	
	public boolean isFavroiteFilter(Context context)
	{
		Log.d("isFavroiteFilter", cityId + " , " + professionId);
		Cursor tempCursor=context.getContentResolver().query(FILTER_CONTENT_URI, null, FILTER_CITY_ID +"='"+this.cityId+"' and "+FILTER_PROF_ID+"='"+this.professionId+"' and "+FILTER_TYPE +"=1", null, null);
		Log.d("isFavroiteFilter", tempCursor.getCount()+"");
		boolean retVal = tempCursor.getCount()>0;
		tempCursor.close();
		tempCursor=null;
		return retVal;
	}
	
	public void saveCurrentFilter(Context context)
	{
		ContentValues values = new ContentValues();
		values.put(FILTER_CITY_ID, this.cityId);
		values.put(FILTER_CITY_NAME, this.cityName);
		values.put(FILTER_TYPE, 0);
		values.put(FILTER_SEARCH_TYPE, this.filterSearchType);
		values.put(FILTER_PROF_ID, this.professionId);
		values.put(FILTER_PROF_NAME, this.professionName);
		values.put(FILTER_DATE, (new Date()).getTime());
		Log.d("saveCurrentFilter", cityName + " , " + this.professionName);
		context.getContentResolver().insert(FILTER_CONTENT_URI,values);
	}
}

