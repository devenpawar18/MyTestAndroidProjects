package com.arbetsformedlingen.entity;

import org.json.JSONObject;

import android.util.Log;



public class JobEntity {
	
	private String id;
	private String name;
	private String jobCount;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobCount() {
		return jobCount;
	}
	public void setJobCount(String jobCount) {
		this.jobCount = jobCount;
	}
	
	public static JobEntity toCountryEntity(JSONObject jsonObject) {
		JobEntity entity = new JobEntity();
		try {
			entity.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
			entity.setName(jsonObject.has("namn")?jsonObject.getString("namn"):"");
			entity.setJobCount(jsonObject.has("antal_platsannonser")?jsonObject.getString("antal_platsannonser"):"");
		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return entity;
	}
	
	public static JobEntity toContactCityEntity(JSONObject jsonObject) {
		JobEntity entity = new JobEntity();
		try {
			entity.setId(jsonObject.has("afplatskod")?jsonObject.getString("afplatskod"):"");
			entity.setName(jsonObject.has("afplatsnamn")?jsonObject.getString("afplatsnamn"):"");
			entity.setJobCount("");
		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return entity;
	}
}
