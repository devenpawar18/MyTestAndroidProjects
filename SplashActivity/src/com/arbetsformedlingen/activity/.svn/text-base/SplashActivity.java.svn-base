package com.arbetsformedlingen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class SplashActivity extends GlobalBaseActivity {
	private Handler splashHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		splashHandler = new Handler();
		splashHandler.postDelayed(runnable, 3000);
	}
	
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			Intent intent = new Intent(SplashActivity.this, PlatsbankenActivity.class);
			SplashActivity.this.startActivity(intent);
			finish();
		}
	};

	@Override
	public void refreshCompleted(Object data) {
		// Not required.
		
	}

	@Override
	public void refreshList() {
		// Not required.
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return true;
	}
}	
