package com.logindatabasedemo.activities;

import com.logindatabasedemo.entity.Person;

import android.Manifest.permission_group;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginSuccess extends Activity {
	private TextView name;
	private TextView phone;
	private TextView email;
	private Button logout;
	private TextView welcomeUser;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_success);

		name = (TextView) findViewById(R.id.nameText);
		phone = (TextView) findViewById(R.id.phoneText);
		email = (TextView) findViewById(R.id.emailText);
		logout = (Button) findViewById(R.id.logout);
		welcomeUser = (TextView) findViewById(R.id.welcomeUser);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		welcomeUser.setText("Welcome "+ bundle.getString(Person.PERSON_NAME));
		name.setText(bundle.getString(Person.PERSON_NAME));
		phone.setText(bundle.getString(Person.PERSON_PHONE));
		email.setText(bundle.getString(Person.PERSON_EMAIL));

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginSuccess.this,
						LoginDatabaseDemoActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

	}
}