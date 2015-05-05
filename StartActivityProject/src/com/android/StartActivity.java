package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StartActivity extends Activity {
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textView = (TextView)findViewById(R.id.tv);

		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra("sampleData", "This is Sample Data");
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 1) {
			String msg = data.getStringExtra("returnedData");
			textView.setText(msg);
		}
	}
}
