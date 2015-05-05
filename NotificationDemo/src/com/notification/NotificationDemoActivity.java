package com.notification;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class NotificationDemoActivity extends Activity
{
	private AlertDialog mDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String[] image_selection = getApplication().getResources()
				.getStringArray(R.array.image_selection_option);

		AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
		mBuilder.setTitle("Title");

		mBuilder.setItems(image_selection, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				which += 1;
			}
		});
		mBuilder.show();
		// mDialog = mBuilder.create();
		// mDialog.show();

		// Show Notification.
		NotificationManager nm = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int idForNotif = 0;
		Intent intent2 = new Intent(this, NotificationDemoActivity.class);
		CharSequence from = "MYHABIT";
		CharSequence message = "hello";
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, 0);
		Log.d("NotificationAlarmReceiver", "onReceive :: " + "Pending Intent");
		Notification notif = new Notification(R.drawable.ic_launcher, message,
				System.currentTimeMillis());
		notif.setLatestEventInfo(this, from, message, pendingIntent);
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		Log.d("NotificationAlarmReceiver", "onReceive :: " + "Notify NOW");
		nm.notify(idForNotif, notif);
	}
}