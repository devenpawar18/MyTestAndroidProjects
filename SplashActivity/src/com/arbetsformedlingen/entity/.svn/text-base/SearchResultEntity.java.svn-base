package com.arbetsformedlingen.entity;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import android.util.Log;



public class SearchResultEntity {
	
	private String id;
	private String title;
	private String location;
	private String locality;
	private String pubDate;
	private String relavance;
	private String totalCount;

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getRelavance() {
		return relavance;
	}
	public void setRelavance(String relavance) {
		this.relavance = relavance;
	}
	public static SearchResultEntity toSearchResultEntity(JSONObject jsonObject) {
		SearchResultEntity entity = new SearchResultEntity();
		try {
			entity.setId(jsonObject.getString("annonsid"));
			entity.setTitle(jsonObject.getString("annonsrubrik"));

			try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS+mm:SS"); 
			//Date dt = sdf.parse(jsonObject.getString("publiceraddatum"));
			entity.setPubDate(jsonObject.getString("publiceraddatum"));
			}catch(Exception ex){
				
			}
			entity.setLocation(jsonObject.getString("arbetsplatsnamn"));
			entity.setLocality(jsonObject.getString("kommunnamn"));
			entity.setRelavance(jsonObject.getString("relevans"));

		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return entity;
	}
}
