package com.checksdcard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class CheckSDCardPresenceActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (isSdPresent())
			Toast.makeText(this, "sd card present.", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(this, "sd card not present.", Toast.LENGTH_SHORT).show();
	}

	public static boolean isSdPresent()
	{
		return android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED);
	}
}