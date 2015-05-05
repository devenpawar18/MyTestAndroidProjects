package com.lifecycle.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Activity3 extends Activity {
	private Button homeBtn;
	private TextView detailTxt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("Activity3", "onCreate 3 called.::");

		detailTxt = (TextView) findViewById(R.id.text);
		detailTxt.setText("Detail Activity");
		homeBtn = (Button) findViewById(R.id.btn);
		homeBtn.setText("Home");
		homeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity3.this,
						ActivityLifeCycleActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onStart() {
		Log.d("Activity3", "onStart 3 called.::");
		super.onStart();
	}

	@Override
	protected void onPause() {
		Log.d("Activity3", "onPause 3 called.::");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.d("Activity3", "onRestart 3 called.::");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d("Activity3", "onResume 3 called.::");
		super.onResume();
	}

	@Override
	protected void onStop() {
		Log.d("Activity3", "onStop 3 called.::");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d("Activity3", "onDestroy 3 called.::");
		super.onDestroy();
	}
}