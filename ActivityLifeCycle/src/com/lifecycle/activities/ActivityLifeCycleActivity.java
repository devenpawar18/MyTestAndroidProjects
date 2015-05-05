package com.lifecycle.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityLifeCycleActivity extends Activity {
	private Button detailBtn;
	private TextView homeTxt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("ActivityLifeCycleActivity", "onCreate 1 called.");

		homeTxt = (TextView) findViewById(R.id.text);
		homeTxt.setText("Home Activity");
		detailBtn = (Button) findViewById(R.id.btn);
		detailBtn.setText("Detail");
		detailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog();

				// Intent intent = new Intent(ActivityLifeCycleActivity.this,
				// DetailActivity.class);
				// intent.putParcelableArrayListExtra("key", (ArrayList<?
				// extends Parcelable>) list);
				// Bundle b = new Bundle();
				// b.putParcelable("key", (Parcelable) list);
				// intent.putExtra("bundle", b);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(intent);

			}
		});
	}

	public void showDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				ActivityLifeCycleActivity.this);
		dialog.setCancelable(false);
		dialog.setTitle("Hello");
		dialog.setCancelable(true);
		dialog.setMessage("Deven");
		dialog.show();
	}

	@Override
	protected void onPause() {
		Log.d("ActivityLifeCycleActivity", "onPause 1 called.");
		super.onPause();
	}

	@Override
	protected void onStart() {
		Log.d("ActivityLifeCycleActivity", "onStart 1 called.");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		Log.d("ActivityLifeCycleActivity", "onRestart 1 called.");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d("ActivityLifeCycleActivity", "onResume 1 called.");
		super.onResume();
	}

	@Override
	protected void onStop() {
		Log.d("ActivityLifeCycleActivity", "onStop 1 called.");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d("ActivityLifeCycleActivity", "onDestroy 1 called.");
		super.onDestroy();
	}
}