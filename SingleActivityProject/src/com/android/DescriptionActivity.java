package com.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DescriptionActivity extends Activity{
	
	private Button b1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second);

		Log.d("DescriptionActivity", "onCreate :: " + getIntent().getExtras().get("title"));
		Log.d("DescriptionActivity", "onCreate :: " + getIntent().getExtras().get("desc"));
		TextView title, desc;
		title = (TextView) findViewById(R.id.title);
		desc = (TextView) findViewById(R.id.desc);
		title.setText(getIntent().getExtras().get("title").toString());
		desc.setText(getIntent().getExtras().get("desc").toString());
		
	b1=(Button)findViewById(R.id.back);
	b1.setOnClickListener(new OnClickListener()
	{
	@Override
	public void onClick(View v) {

	Intent intent = new Intent(DescriptionActivity.this,SingleActivity.class);
	intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(intent);
	}
		
	});
	
	}
	
    
	

}
