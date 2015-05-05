package com.calendarapp;

import java.util.Calendar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class CalendarAppActivity extends Activity
{
	String calendarUriBase = "content://calendar/";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// get calendar
		Calendar cal = Calendar.getInstance();
		Uri EVENTS_URI = Uri.parse(getCalendarUriBase(this) + "events");
		ContentResolver cr = getContentResolver();

		String where = "calendar_id=" + "2";
		Cursor cursor = getContentResolver().query(Uri.parse(getCalendarUriBase(this) + "events"), null, null, null, null);
		Log.d("WhatsNewActivity", "cursor count::" + cursor.getCount());
		for (int i = 0; i < cursor.getColumnCount(); i++)
		{
			Log.d("WhatsNewActivity", "column names::" + cursor.getColumnName(i));

		}
		cursor.moveToFirst();
		while (cursor.moveToNext())
		{
			Uri deleteUri = ContentUris.withAppendedId(Uri.parse(getCalendarUriBase(this) + "events"), cursor.getInt(0));
			getContentResolver().delete(deleteUri, null, null);
		}
		// if (cursor.getCount() > 0)
		// {
		// // event already exists
		// Toast.makeText(CalendarAppActivity.this, "Event already exists in Calendar.",
		// Toast.LENGTH_SHORT).show();
		// }
		// else
		// {
		//
		// // event insert
		// ContentValues values = new ContentValues();
		//
		// values.put("calendar_id", 2); // id, We need to choose from our mobile for primary its 1
		// values.put("title", "Birthday");
		// values.put("description", "Go home at 2pm");
		// values.put("eventLocation", "Home");
		// long startTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24; // Next day
		//
		// values.put("dtstart", startTime);
		// values.put("dtend", startTime);
		// values.put("allDay", 1);
		// values.put("eventStatus", 1);
		// values.put("visibility", 3);
		// values.put("transparency", 0);
		// values.put("hasAlarm", 1);
		//
		// Uri event = cr.insert(EVENTS_URI, values);
		// //
		// // reminder insert
		// Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(this) + "reminders");
		// values = new ContentValues();
		// values.put("event_id", Long.parseLong(event.getLastPathSegment()));
		// values.put("method", 1);
		// values.put("minutes", 7 * 24 * 60);
		// cr.insert(REMINDERS_URI, values);
		// Toast.makeText(this, "Event Added Successfully...", Toast.LENGTH_SHORT).show();
		// }

	}

	private String getCalendarUriBase(Activity act)
	{
		String calendarUriBase = null;
		Uri calendars = Uri.parse("content://calendar/calendars");
		Cursor managedCursor = null;
		try
		{
			managedCursor = act.managedQuery(calendars, null, null, null, null);
		}
		catch (Exception e)
		{

		}
		if (managedCursor != null)
		{
			calendarUriBase = "content://calendar/";
		}
		else
		{
			calendars = Uri.parse("content://com.android.calendar/calendars");
			try
			{
				managedCursor = act.managedQuery(calendars, null, null, null, null);
			}
			catch (Exception e)
			{

			}
			if (managedCursor != null)
			{
				calendarUriBase = "content://com.android.calendar/";
			}
		}
		return "content://calendar/";
	}
}