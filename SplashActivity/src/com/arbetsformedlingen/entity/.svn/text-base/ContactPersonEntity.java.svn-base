package com.arbetsformedlingen.entity;

import org.json.JSONObject;

import android.util.Log;



public class ContactPersonEntity {
	
	private String name ,title, mobileNumber,phoneNumber;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public static ContactPersonEntity toContactPersonEntity(JSONObject jsonObject) {
		ContactPersonEntity entity = new ContactPersonEntity();
		try {
			entity.setName(jsonObject.has("namn")?jsonObject.getString("namn"):"");
			Log.d("ContactPersonEntity", "toContactPersonEntity :: " + "entity.getName() :: "+entity.getName());
			entity.setTitle(jsonObject.has("titel")?jsonObject.getString("titel"):"");
			entity.setMobileNumber(jsonObject.has("mobilnummer")?jsonObject.getString("mobilnummer"):"");
			entity.setPhoneNumber(jsonObject.has("telefonnummer")?jsonObject.getString("telefonnummer"):"");

		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return entity;
	}
	
	public static JSONObject toContactPersonJSON(ContactPersonEntity entity) {
		JSONObject json = new JSONObject();
		try {
			json.put("namn", entity.getName()== null?"":entity.getName());
			json.put("titel", entity.getTitle()== null?"":entity.getTitle());
			json.put("mobilnummer", entity.getMobileNumber()== null?"":entity.getMobileNumber());
			json.put("telefonnummer", entity.getPhoneNumber()== null?"":entity.getPhoneNumber());

		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return json;
	}
}
