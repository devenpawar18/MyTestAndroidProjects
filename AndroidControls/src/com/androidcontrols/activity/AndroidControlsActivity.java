package com.androidcontrols.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AndroidControlsActivity extends Activity {

	private ListView list;
	private UserAdapter userAdapter;
	private ArrayList<AndroidControls> controls = new ArrayList<AndroidControls>();
	ToggleButton tgbutton;
	Button button;
	TextView userName;
	Spinner spinner;
	CheckBox checkBox;
	EditText editText;
	RadioButton radioButton;
	private String sMenuExampleNames[] = { "Title only", "Title and Icon", "Submenu", "Groups", "Checkable", "Shortcuts", "Order", "Category and Order", "Visible", "Disabled" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baselayout);
		controls.add(new AndroidControls("Toggle Button"));
		controls.add(new AndroidControls("Drop Down List"));
		controls.add(new AndroidControls("Button"));
		controls.add(new AndroidControls("Check Box"));
		controls.add(new AndroidControls("Radio"));
		controls.add(new AndroidControls("Edit Box"));

		list = (ListView) findViewById(R.id.lvfeeds);
		userAdapter = new UserAdapter(controls, this);
		list.setAdapter(userAdapter);
		list.setDivider(null);

	}

	public class UserAdapter extends BaseAdapter {

		Context context;

		public UserAdapter(ArrayList<AndroidControls> listControls, Context context) {

			this.context = context;
		}

		@Override
		public int getCount() {
			return controls.size();
		}

		@Override
		public Object getItem(int position) {
			return controls.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				if (position == 0) {
					convertView = LayoutInflater.from(context).inflate(R.layout.listfeedsdata, parent, false);

					userName = (TextView) convertView.findViewById(R.id.feedtitle);
					tgbutton = (ToggleButton) convertView.findViewById(R.id.toggleButton);

				} else if ((position == 1)) {
					convertView = LayoutInflater.from(context).inflate(R.layout.dropdowndata, parent, false);

					userName = (TextView) convertView.findViewById(R.id.tv_maxClipLength);
					spinner = (Spinner) convertView.findViewById(R.id.sp_settings);
				} else if ((position == 2)) {
					convertView = LayoutInflater.from(context).inflate(R.layout.main, parent, false);

					userName = (TextView) convertView.findViewById(R.id.clickMeTtile);
					button = (Button) convertView.findViewById(R.id.clickMe);
				}else if ((position == 3)) {
					convertView = LayoutInflater.from(context).inflate(R.layout.checkbox, parent, false);

					userName = (TextView) convertView.findViewById(R.id.checkTitle);
					checkBox = (CheckBox) convertView.findViewById(R.id.chekbox);
				}else if ((position == 4)) {
					convertView = LayoutInflater.from(context).inflate(R.layout.radio, parent, false);

					userName = (TextView) convertView.findViewById(R.id.radioTitle);
					radioButton = (RadioButton) convertView.findViewById(R.id.widget2);
				}else if ((position == 5)) {
					convertView = LayoutInflater.from(context).inflate(R.layout.editbox, parent, false);

					userName = (TextView) convertView.findViewById(R.id.editTitle);
					editText = (EditText) convertView.findViewById(R.id.tv_viuName_value);
				}

			} else {

			}
			AndroidControls data = controls.get(position);
			if (position == 0) {
				userName.setText(data.getControls());
				Log.d("holder", "===tgbutton===" + tgbutton);
				tgbutton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (tgbutton.isChecked()) {

							Toast.makeText(AndroidControlsActivity.this, "ON", Toast.LENGTH_SHORT).show();
						} else {

							Toast.makeText(AndroidControlsActivity.this, "OFF", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
			if (position == 1) {
				userName.setText(data.getControls());
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(AndroidControlsActivity.this, android.R.layout.simple_spinner_item, sMenuExampleNames);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				spinner.setAdapter(adapter);
			}
			if (position == 2) {
				Log.d("data.getControls()", data.getControls());
				Log.d("data.getControls()", userName+"");
				userName.setText(data.getControls());

			}
			
			if (position == 3) {
				Log.d("data.getControls()", data.getControls());
				Log.d("data.getControls()", userName+"");
				userName.setText(data.getControls());

			}

			if (position == 4) {
				Log.d("data.getControls()", data.getControls());
				Log.d("data.getControls()", userName+"");
				userName.setText(data.getControls());

			}
			if (position == 5) {
				Log.d("data.getControls()====5", data.getControls());
				Log.d("data.getControls()", userName+"");
				userName.setText(data.getControls());

			}
			return convertView;

		}
	}

}