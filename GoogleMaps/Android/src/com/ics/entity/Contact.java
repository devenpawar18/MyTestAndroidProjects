package com.ics.entity;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

	private String email;
	private String catalogEmail;
	private String phone;
	private String messageEmail;
	private String address;
	private String postAddress;
	
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCatalogEmail() {
		return catalogEmail;
	}

	public void setCatalogEmail(String catalogEmail) {
		this.catalogEmail = catalogEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessageEmail() {
		return messageEmail;
	}

	public void setMessageEmail(String messageEmail) {
		this.messageEmail = messageEmail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public static Parcelable.Creator<Contact> getCreator() {
		return CREATOR;
	}

	private Contact(Parcel in) {
		readFromParcel(in);
	}

	public Contact() {
		// TODO Auto-generated constructor stub
	}

	public void writeToParcel(Parcel out, int arg1) {
		out.writeString(email);
		out.writeString(catalogEmail);
		out.writeString(phone);
		out.writeString(messageEmail);
		out.writeString(address);
		out.writeString(postAddress);
	}

	public void readFromParcel(Parcel in) {
		email = in.readString();
		catalogEmail = in.readString();
		phone = in.readString();
		messageEmail = in.readString();
		address = in.readString();
		postAddress = in.readString();
	}

	public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
		public Contact createFromParcel(Parcel in) {
			return new Contact(in);
		}

		public Contact[] newArray(int size) {
			return new Contact[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}