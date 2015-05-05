package com.broaddemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Toast.makeText(context, "network changed.", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(BroadcastDemoActivity.FILTER_STRING);

		// sending data to activity.
		// i.putExtra("key", "value");

		context.sendBroadcast(i);
		// context.sendOrderedBroadcast(i, null);

	}

}