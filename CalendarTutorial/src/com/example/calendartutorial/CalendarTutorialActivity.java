package com.example.calendartutorial;

import java.util.Calendar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class CalendarTutorialActivity extends Activity
{
	private String id;
	public static final String[] EVENT_PROJECTION = new String[] { Calendars._ID, // 0
	Calendars.ACCOUNT_NAME, // 1
	Calendars.CALENDAR_DISPLAY_NAME, // 2
	Calendars.OWNER_ACCOUNT // 3
	};

	// The indices for the projection array above.
	private static final int PROJECTION_ID_INDEX = 0;
	private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
	private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_tutorial);
		// Construct event details
		long startMillis = 0;
		long endMillis = 0;
		int eventId = 222;

		// Cursor cur = null;
		// ContentResolver cr = getContentResolver();
		// Uri uri = Calendars.CONTENT_URI;
		// String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE
		// + " = ?) AND (" + Calendars.OWNER_ACCOUNT + " = ?))";
		// String[] selectionArgs = new String[] { "sampleuser@gmail.com", "com.google",
		// "sampleuser@gmail.com" };
		// // Submit the query and get a Cursor object back.
		// cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
		// cur.moveToFirst();
		// while (cur.moveToNext())
		// {
		// long calID = 0;
		// String displayName = null;
		// String accountName = null;
		// String ownerName = null;
		//
		// // Get the field values
		// calID = cur.getLong(PROJECTION_ID_INDEX);
		// displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
		// accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
		// ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
		//
		// }

		// String where = CalendarContract.Events._ID + "=" + eventId;
		String where = CalendarContract.Events._ID + "=?";
		Cursor cursor = getContentResolver().query(Events.CONTENT_URI, new String[] { "_id" }, null, null, null);
		Log.d("WhatsNewActivity", "cursor count::" + cursor.getCount());
		cursor.moveToFirst();
		for (int i = 0; i < cursor.getColumnCount(); i++)
		{
			Log.d("WhatsNewActivity", "column names::" + cursor.getColumnName(i));

		}
		int idColumn = cursor.getColumnIndexOrThrow("_id");
		String colName = cursor.getColumnName(idColumn);
		String[] arr = cursor.getColumnNames();
		for (int i = 0; i < cursor.getColumnCount(); i++)
		{
			Log.d("WhatsNewActivity", "column names::" + cursor.getColumnName(i));

		}
		for (int i = 0; i < cursor.getCount(); i++)
		{
			id = cursor.getString(idColumn);
			Log.d("CalendarTutorialActivity", "Calendar id values" + id);

		}

		if (cursor.getCount() > 0)
		{
			// event already exists
			Toast.makeText(CalendarTutorialActivity.this, "Event already exists in Calendar.", Toast.LENGTH_SHORT).show();
		}

		// Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
		// Intent intent1 = new Intent(Intent.ACTION_EDIT).setData(uri).putExtra(Events.TITLE,
		// "My New Title");
		// startActivity(intent1);

		// Uri uri = ContentUris.withAppendedId(Events.CONTENT_URI, eventId);
		// Cursor cursor = getContentResolver().query(uri, null, null, null, null);
		// String str = uri.toString();
		//
		// int i = str.lastIndexOf("/");
		// str = str.substring(i + 1, str.length());
		// long lo = Long.parseLong(str);

		// if (lo == eventId)
		// {
		// Toast.makeText(CalendarTutorialActivity.this, "Event already exists in Calendar.",
		// Toast.LENGTH_SHORT).show();
		// }
		// else
		// {

		// Insert Event with Intent.
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(2012, 8, 19, 7, 30);
		Calendar endTime = Calendar.getInstance();
		endTime.set(2012, 8, 19, 8, 30);
		Intent intent = new Intent(Intent.ACTION_INSERT).setData(Events.CONTENT_URI).putExtra(CalendarContract.Events._ID, eventId)
				.putExtra(Events.TITLE, "Event 1").putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
				.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis()).putExtra(Events.DTEND, endTime.getTimeInMillis())
				.putExtra(Events.DESCRIPTION, "Group class").putExtra(Events.EVENT_LOCATION, "The gym").putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
		// .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
		startActivity(intent);

		// Calendar beginTime = Calendar.getInstance();
		// beginTime.set(2012, 9, 14, 7, 30);
		// startMillis = beginTime.getTimeInMillis();
		// Calendar endTime = Calendar.getInstance();
		// endTime.set(2012, 9, 14, 8, 45);
		// endMillis = endTime.getTimeInMillis();

		// Insert Event
		// ContentResolver cr = getContentResolver();
		// ContentValues values = new ContentValues();
		// values.put(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
		// values.put(Events.DTSTART, startMillis);
		// values.put(Events.DTEND, endMillis);
		// values.put(Events.TITLE, "Walk The Dog");
		// values.put(Events.ALL_DAY, "1");
		// values.put(Events.DESCRIPTION, "My dog is bored, so we're going on a really long walk!");
		// values.put(Events.CALENDAR_ID, 1);
		// values.put(Events._ID, 22);
		// // values.put(Events.STATUS, 1);
		// // values.put(CalendarContract.Events.VISIBLE, 1);
		// TimeZone timeZone = TimeZone.getDefault();
		// values.put(Events.EVENT_TIMEZONE, timeZone.getID());
		// // Uri uri = cr.insert(Uri.parse("content://com.android.calendar/calendars/events"),
		// // values);
		//
		// // Retrieve ID for new event
		// // String eventID = uri.getLastPathSegment();
		//
		// Toast.makeText(this, "Event Added Successfully...", Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_calendar_tutorial, menu);
		return true;
	}
}
