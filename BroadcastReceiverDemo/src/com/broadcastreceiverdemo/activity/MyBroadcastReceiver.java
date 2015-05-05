package com.broadcastreceiverdemo.activity;

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
		Intent i = new Intent(BroadcastReceiverDemoActivity.FILTER_STRING);
		context.sendBroadcast(i);

	}

}