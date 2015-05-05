package com.logindatabasedemo.activities;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logindatabasedemo.entity.Person;

public class LoginDatabaseDemoActivity extends Activity {

	private EditText email;
	private EditText password;
	private Button login;
	private Button reset;
	private Button register;
	private Cursor userCursor;
	private ArrayList<Person> person;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		email = (EditText) findViewById(R.id.emailET);
		password = (EditText) findViewById(R.id.pwdET);
		login = (Button) findViewById(R.id.login);
		reset = (Button) findViewById(R.id.reset);
		register = (Button) findViewById(R.id.register);
		person = new ArrayList<Person>();

		userCursor = managedQuery(Person.CONTENT_URI, null, null, null, null);
		userCursor.moveToFirst();

		for (int i = 0; i < userCursor.getCount(); i++) {
			person.add(Person.fromCursor(userCursor));
			userCursor.moveToNext();
		}
		userCursor.deactivate();
		userCursor.close();
		userCursor = null;

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String emailId = email.getText().toString();
				String pass = password.getText().toString();

				Iterator<Person> itr = person.iterator();
				while (itr.hasNext()) {
					Person p = itr.next();
					String email = p.getEmail();
					String password = p.getPassword();
					if (emailId.equals(email) && pass.equals(password)) {
						Bundle bundle = Person.getPersonBundle(p);
						Intent intent = new Intent(
								LoginDatabaseDemoActivity.this,
								LoginSuccess.class);
						intent.putExtras(bundle);
						intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						return;
					}

				}
				Toast.makeText(LoginDatabaseDemoActivity.this,
						"Invalid Credentials", Toast.LENGTH_SHORT).show();
			}

		});

		reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				email.setText("");
				password.setText("");
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginDatabaseDemoActivity.this,
						Register.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		email.setText("");
		password.setText("");

	}

}