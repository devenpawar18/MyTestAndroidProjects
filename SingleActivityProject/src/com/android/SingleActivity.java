package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SingleActivity extends Activity {
	private TextView facebook;
	private TextView twitter;
	private TextView youtube;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("IntentProjectActivity", "onCreate :: " + this.getApplicationContext().getResources().getText(R.string.app_name));

		facebook = (TextView) findViewById(R.id.facebook_text);
		twitter = (TextView) findViewById(R.id.twitter_text);
		youtube = (TextView) findViewById(R.id.youtube_text);

		facebook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String title = "FACEBOOK";
				String desc = "facejfjkdhkgdfklg";
				openDescriptionActivity(title, desc);

			}
		});

		twitter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String title = "TWITTER";
				String desc = "uguygug   gjh";
				openDescriptionActivity(title, desc);

			}
		});

		youtube.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				String title = "YOUTUBE";
				String desc = "YouTube is a video-sharing website on which users can upload, share, and view videos, created by three former PayPal employees in February 2005";
				openDescriptionActivity(title, desc);
			}
		});
	}

	private void openDescriptionActivity(String title, String desc) {
		Intent intent = new Intent(this, DescriptionActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("desc", desc);
		startActivity(intent);
	}

}
