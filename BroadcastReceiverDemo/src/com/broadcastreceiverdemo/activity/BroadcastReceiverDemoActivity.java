package com.broadcastreceiverdemo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class BroadcastReceiverDemoActivity extends Activity
{
	public static final String FILTER_STRING = "network changed successfully.";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		IntentFilter filter = new IntentFilter(FILTER_STRING);
		MyBroadcast r = new MyBroadcast();
		registerReceiver(r, filter);
	}

	public class MyBroadcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
			Toast.makeText(BroadcastReceiverDemoActivity.this, "My Broadcast received.", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
	}

}