package com.logindatabasedemo.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.logindatabasedemo.entity.Person;

public class Register extends Activity {
	private EditText name;
	private EditText password;
	private EditText phone;
	private EditText email;
	private Button save;
	private Button reset;
	
	//private ContentResolver ctr;
	Person person = new Person();
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		
		name = (EditText) findViewById(R.id.nameET);
		password = (EditText) findViewById(R.id.passET);
		phone = (EditText) findViewById(R.id.phoneET);
		email = (EditText) findViewById(R.id.emailET);
		save = (Button) findViewById(R.id.save);
		reset = (Button) findViewById(R.id.resetData);
		
		
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				person.setName(name.getText().toString());
				person.setPassword(password.getText().toString());
				person.setPnone(phone.getText().toString());
				person.setEmail(email.getText().toString());
				
				final ArrayList<Person> user = new ArrayList<Person>();
				
				user.add(person);
				
//				if (user != null && user.size() > 0) {
//					getContentResolver().delete(Person.CONTENT_URI, null, null);
//				}
				for (int i = 0; i < user.size(); i++) {
					getContentResolver().insert(Person.CONTENT_URI, user.get(i).getPersonContentValue());
				}
				Toast.makeText(Register.this, "data saved",Toast.LENGTH_SHORT ).show();
				Intent intent = new Intent(Register.this,LoginDatabaseDemoActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				name.setText("");
				password.setText("");
				phone.setText("");
				email.setText("");
				
			}
		});
		
	}
}