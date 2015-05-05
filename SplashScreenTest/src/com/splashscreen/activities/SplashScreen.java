package com.splashscreen.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

public class SplashScreen extends Activity
{
	private static final int DELAY = 2000;
	private static final int START_HOMEACTIVITY = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		Message msg = new Message();
		msg.what = START_HOMEACTIVITY;
		mHandler.sendMessageDelayed(msg, DELAY);
	}

	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			if (msg.what == START_HOMEACTIVITY)
			{
				Intent intent = new Intent();
				intent.setClass(SplashScreen.this, SplashScreenTestActivity.class);
				startActivity(intent);
				finish();
			}
		};
	};

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
			return true;
		else
			return super.onKeyDown(keyCode, event);
	}

}
