package com.arbetsformedlingen.entity;

import org.json.JSONObject;

import android.util.Log;



public class ContactDetailEntity {
	
	private String officeCode;
	private String officeName;
	private String cityManager;
	private String address;
	private String place;
	private String postalAddress;
	private String zipCode;
	private String postalCity;
	private String emailAddress;
	private String faxNumber;
	private String telNumber;
	private String telID;
	private String selfServiceTime;
	private String personalServiceTime;
	private String extraInformation;
	
	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String cityCode) {
		this.officeCode = cityCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String cityName) {
		this.officeName = cityName;
	}

	public String getCityManager() {
		return cityManager;
	}

	public void setCityManager(String cityManager) {
		this.cityManager = cityManager;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPostalCity() {
		return postalCity;
	}

	public void setPostalCity(String postalCity) {
		this.postalCity = postalCity;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getSelfServiceTime() {
		return selfServiceTime;
	}

	public void setSelfServiceTime(String selfServiceTime) {
		this.selfServiceTime = selfServiceTime;
	}

	public String getPersonalServiceTime() {
		return personalServiceTime;
	}

	public void setPersonalServiceTime(String personalServiceTime) {
		this.personalServiceTime = personalServiceTime;
	}
	
	public String getExtraInformation() {
		return extraInformation;
	}

	public void setExtraInformation(String extraInformation) {
		this.extraInformation = extraInformation;
	}
	
	
	public void setTelID(String telID) {
		this.telID = telID;
	}
	
	public String getTelID() {
		return telID;
	}
	
	public static ContactDetailEntity toCountryEntity(JSONObject jsonObject) {
		ContactDetailEntity entity = new ContactDetailEntity();
		try {
			entity.setOfficeCode(jsonObject.has("afplatskod")?jsonObject.getString("afplatskod"):"");
			//entity.setOfficeName(jsonObject.has("afplatsnamn")?jsonObject.getString("afplatsnamn"):"");
			entity.setCityManager(jsonObject.has("afplatschef")?jsonObject.getString("afplatschef"):"");
			entity.setAddress(jsonObject.has("besoksadress")?jsonObject.getString("besoksadress"):"");
			entity.setPlace(jsonObject.has("besoksort")?jsonObject.getString("besoksort"):"");
			entity.setPostalAddress(jsonObject.has("postadress")?jsonObject.getString("postadress"):"");
			entity.setZipCode(jsonObject.has("postnummer")?jsonObject.getString("postnummer"):"");
			entity.setTelID(jsonObject.has("telefontid")?jsonObject.getString("telefontid"):"");
			entity.setPostalCity(jsonObject.has("postort")?jsonObject.getString("postort"):"");
			entity.setEmailAddress(jsonObject.has("epostadress")?jsonObject.getString("epostadress"):"");
			entity.setFaxNumber(jsonObject.has("faxnummer")?jsonObject.getString("faxnummer"):"");
			entity.setTelNumber(jsonObject.has("kontakttelefonnummer")?jsonObject.getString("kontakttelefonnummer"):"");
			entity.setSelfServiceTime(jsonObject.has("sjalvservicetid")?jsonObject.getString("sjalvservicetid"):"");
			entity.setPersonalServiceTime(jsonObject.has("personligservicetid")?jsonObject.getString("personligservicetid"):"");
			entity.setExtraInformation(jsonObject.has("extra_information")?jsonObject.getString("extra_information"):"");
		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return entity;
	}
}
