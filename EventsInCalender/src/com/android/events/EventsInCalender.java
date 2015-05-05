package com.android.events;

import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class EventsInCalender extends Activity {
//	String calendarUriBase = "content://calendar/calendars";

	// content://com.android.calendar/
	// content://calendar/calendars
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event);
		int str = Integer.parseInt(android.os.Build.VERSION.SDK);
//		Float.valueOf(android.os.Build.VERSION.RELEASE) <= 2.1
		if(Float.valueOf(android.os.Build.VERSION.RELEASE) <= 2.1){
			Toast.makeText(this, "below 2.2", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "above 2.2", Toast.LENGTH_SHORT).show();
		}
//		Float f = Float.valueOf(android.os.Build.VERSION.RELEASE);
//		int i = Integer.valueOf(android.os.Build.VERSION.SDK); 
	

	}
}