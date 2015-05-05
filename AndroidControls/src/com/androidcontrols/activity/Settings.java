package com.androidcontrols.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class Settings extends Activity {
	/** Called when the activity is first created. */

	public ProgressDialog mDialog;
	private Spinner settingsSpinner;

	private TextView maxClipLength;

	private String mMaxClipLength="English";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);

		maxClipLength = (TextView) findViewById(R.id.tv_maxClipLength_value);

		maxClipLength.setOnClickListener(spinnerMaxClipLengthListener);

	}

	private OnClickListener spinnerMaxClipLengthListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			settingsSpinner = (Spinner) findViewById(R.id.sp_settings);
			ArrayAdapter<CharSequence> networkadapter = ArrayAdapter.createFromResource(Settings.this, R.array.language, android.R.layout.simple_spinner_item);

			networkadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			settingsSpinner.setAdapter(networkadapter);
			int spinnerPosition = networkadapter.getPosition(mMaxClipLength);
			settingsSpinner.setSelection(spinnerPosition);
	

			settingsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

					Log.d("Spinner", "mMaxClipLength :: " + mMaxClipLength);
					mMaxClipLength = settingsSpinner.getSelectedItem().toString();
					maxClipLength.setText(settingsSpinner.getSelectedItem().toString());
				}

				@Override
				public void onNothingSelected(AdapterView<?> parentView) {

				}
			});

			settingsSpinner.performClick();

		}

	};

}
