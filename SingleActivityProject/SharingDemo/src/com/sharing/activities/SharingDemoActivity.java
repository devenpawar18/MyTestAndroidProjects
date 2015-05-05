package com.sharing.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SharingDemoActivity extends Activity
{
	private Button share;
	private TextView share_text;
	private Dialog dialog;
	private String message;
	private String to = "devenpawar18@gmail.com";
	private String SHARE_URL = "http://www.google.com";

	// private String SHARE_URL =
	// "http://www.arbetsformedlingen.se/4.1799db4911df80d2fa9800024.html?id=";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		share_text = (TextView) findViewById(R.id.share_text);
		share = (Button) findViewById(R.id.share);
		message = share_text.getText().toString();

		// share_text.setText ( "78" + (char) 0x00B0 );

		share.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				dialog = new Dialog(SharingDemoActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.dialog);
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				double width = (dm.widthPixels * 0.80);
				int widthI = (int) width;
				Log.d("GlobalDetailActivity", "onOptionsItemSelected :: " + "widthI :: " + widthI);
				dialog.getWindow().setLayout(widthI, LayoutParams.WRAP_CONTENT);
				dialog.show();

				TextView dialogText = (TextView) dialog.findViewById(R.id.dialog_title_text);
				dialogText.setText(R.string.share);

				Button twitterButton = (Button) dialog.findViewById(R.id.twitter);
				twitterButton.setText(R.string.twitter);
				twitterButton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent settingIntent = new Intent(SharingDemoActivity.this,
								SharingActivity.class);
						settingIntent.putExtra("Type", (String) getResources()
								.getText(R.string.twitter));
						settingIntent.putExtra("Message", message);
						settingIntent.putExtra("URL", SHARE_URL);
						startActivity(settingIntent);
						dialog.dismiss();

					}

				});

				Button facebookButton = (Button) dialog.findViewById(R.id.facebook);
				facebookButton.setText(R.string.facebook);
				facebookButton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent settingIntent = new Intent(SharingDemoActivity.this,
								SharingActivity.class);
						settingIntent.putExtra("Type", (String) getResources()
								.getText(R.string.facebook));
						settingIntent.putExtra("Message", message);
						settingIntent.putExtra("URL", SHARE_URL);
						startActivity(settingIntent);
						dialog.dismiss();
					}

				});

				Button linkedInButton = (Button) dialog.findViewById(R.id.linkedin);
				linkedInButton.setText(R.string.linkedIn);
				linkedInButton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						Intent settingIntent = new Intent(SharingDemoActivity.this,
								SharingActivity.class);
						settingIntent.putExtra("Type", (String) getResources()
								.getText(R.string.linkedIn));
						settingIntent.putExtra("Message", message);
						settingIntent.putExtra("URL", SHARE_URL);
						startActivity(settingIntent);
						dialog.dismiss();
					}

				});

				Button mailButton = (Button) dialog.findViewById(R.id.mail);
				mailButton.setText(R.string.mail);
				mailButton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
						emailIntent.setType("plain/text");
						emailIntent
								.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { to });
						emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "mySubject");
						emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "myBodyText");
						startActivity(Intent
								.createChooser(emailIntent, "Select email application."));
						dialog.dismiss();
					}

				});

				Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
				cancelButton.setText(R.string.cancel);
				cancelButton.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
					}

				});

			}
		});
	}
}