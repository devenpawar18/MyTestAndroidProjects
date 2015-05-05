package com.edit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class EditTextProjectActivity extends Activity {
	private EditText et;
	private Spinner spinner;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_results_row);
//		spinner = (Spinner) findViewById(R.id.spinner);
//		et = (EditText) findViewById(R.id.et);
//		et.setError("Error Message");
//		spinner.setPrompt("world...");
	}
}