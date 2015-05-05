package com.dialogdemo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DialogDemoActivity extends Activity
{
	private Button button;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

			}
		});
	}
}