package com.customcomponents.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyCustomViewsActivity extends Activity
{
	private CustomControl control;
	LinearLayout layout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		layout = (LinearLayout) findViewById(R.id.linearLayout);
		control = new CustomControl(this, null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		control.setLayoutParams(params);

		layout.addView(control);
		control.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Toast.makeText(MyCustomViewsActivity.this, "hello world!!!", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
}