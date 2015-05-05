package com.logindatabasedemo.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.logindatabasedemo.controller.UserProvider;

public class Person {
	private String id;
	private String name;
	private String pnone;
	private String password;
	private String email;
	

	public final static String PERSON_ID = "id";
	public final static String PERSON_NAME = "name";
	public final static String PERSON_PHONE = "phone";
	public final static String PERSON_PASSWORD = "password";
	public final static String PERSON_EMAIL = "email";

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ UserProvider.AUTHORITY + "/person");
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.logindatabasedemo.person";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.logindatabasedemo.person";

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

	public String getPnone() {
		return pnone;
	}

	public void setPnone(String pnone) {
		this.pnone = pnone;
	}
	
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ContentValues getPersonContentValue() {
		ContentValues values = new ContentValues();
		values.put(PERSON_ID, getId());
		values.put(PERSON_NAME, getName());
		values.put(PERSON_PHONE, getPnone());
		values.put(PERSON_PASSWORD, getPassword());
		values.put(PERSON_EMAIL, getEmail());
		return values;
	}

	public static Person fromCursor(Cursor cursor) {
		Person person = new Person();
		try {
			person.setId(cursor.getString(cursor.getColumnIndex(PERSON_ID)));
			person.setName(cursor.getString(cursor
					.getColumnIndex(PERSON_NAME)));
			person.setPnone(cursor.getString(cursor
					.getColumnIndex(PERSON_PHONE)));
			person.setPassword(cursor.getString(cursor
					.getColumnIndex(PERSON_PASSWORD)));
			person.setEmail(cursor.getString(cursor
					.getColumnIndex(PERSON_EMAIL)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return person;
	}

	public static Bundle getPersonBundle(Person person) {
		Bundle bundle = new Bundle();
		bundle.putString(PERSON_ID, person.getId());
		bundle.putString(PERSON_NAME, person.getName());
		bundle.putString(PERSON_PHONE, person.getPnone());
		bundle.putString(PERSON_PASSWORD, person.getPassword());
		bundle.putString(PERSON_EMAIL, person.getEmail());
		return bundle;
	}

}
