package com.android;

import java.util.logging.Logger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TestLoginActivity extends Activity {

	private EditText uname, pass;
	private Button login, reset;
	private AlertDialog m_dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		uname = (EditText) findViewById(R.id.un);
		pass = (EditText) findViewById(R.id.pd);
		login = (Button) findViewById(R.id.login);
		reset = (Button) findViewById(R.id.reset);

		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//used for debugging//	Log.d("", "onClick :: " + "code for submit");
				m_dialog = new AlertDialog.Builder(TestLoginActivity.this)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						uname.setText("");
						pass.setText("");
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						m_dialog.cancel();
					}
				})
				.setTitle("Are you sure you want to reset the fields?")
				.setCancelable(true)
				.create();
				m_dialog.show();
				
				
				
			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Login successful",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
