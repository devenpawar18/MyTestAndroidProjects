package com.lifecycle.activities;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity
{
	private Button homeBtn;
	private TextView detailTxt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.d("DetailActivity", "onCreate 2 called.::");

		detailTxt = (TextView) findViewById(R.id.text);
		detailTxt.setText("Detail Activity");
		homeBtn = (Button) findViewById(R.id.btn);
		homeBtn.setText("Home");
		homeBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(DetailActivity.this, Activity3.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});
	}

	@Override
	protected void onStart()
	{
		Log.d("DetailActivity", "onStart 2 called.::");
		super.onStart();
	}

	@Override
	protected void onPause()
	{
		Log.d("DetailActivity", "onPause 2 called.::");
		super.onPause();
	}

	@Override
	protected void onRestart()
	{
		Log.d("DetailActivity", "onRestart 2 called.::");
		super.onRestart();
	}

	@Override
	protected void onResume()
	{
		Log.d("DetailActivity", "onResume 2 called.::");
		super.onResume();
	}

	@Override
	protected void onStop()
	{
		Log.d("DetailActivity", "onStop 2 called.::");
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		Log.d("DetailActivity", "onDestroy 2 called.::");
		super.onDestroy();
	}
}