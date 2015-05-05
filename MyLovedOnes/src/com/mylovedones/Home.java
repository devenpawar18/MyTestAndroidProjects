package com.mylovedones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends Activity {
	private ImageView img;
	private TextView msg;
	private TextView msg1;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		img = (ImageView) findViewById(R.id.frndimg);
		msg = (TextView) findViewById(R.id.frndmsg);
		msg1=(TextView)findViewById(R.id.frndmsg1);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, CoverFlow.class);
				startActivity(intent);

			}

		});

		msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, CoverFlow.class);
				startActivity(intent);

			}

		});
		msg1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Home.this, CoverFlow.class);
				startActivity(intent);

			}

		});

	}

}
