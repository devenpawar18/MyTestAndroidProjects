package com.arbetsformedlingen.activity;

import java.util.ArrayList;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LocalizationActivity extends GlobalBaseActivity {

	private ArrayList<String> languageList;
	private ListView listView;
	private AlertDialog listDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		languageList = new ArrayList<String>();
		languageList.add((String) getResources().getText(
				R.string.swedishLanguage));
		languageList.add((String) getResources().getText(
				R.string.arabicLanguage));
		languageList.add((String) getResources().getText(
				R.string.englishLanguage));
		languageList.add((String) getResources().getText(
				R.string.frenchLanguage));
		languageList.add((String) getResources().getText(
				R.string.russianLanguage));
		languageList.add((String) getResources().getText(
				R.string.spanishLanguage));

		setContentView(R.layout.localization);

		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(new ListAdapter(getApplicationContext(),
				languageList));
		listView.setOnItemClickListener(langItemClickListner);
		listView.setDivider(null);
		String translateText = (String) getResources().getText(
				R.string.language);
		setTitleText(translateText);
	}

	private class ListAdapter extends BaseAdapter {

		private ArrayList<String> listArr;
		private Context context;

		public ListAdapter(Context context, ArrayList<String> listArr) {
			this.context = context;
			this.listArr = listArr;
		}

		@Override
		public int getCount() {
			return listArr.size();
		}

		@Override
		public Object getItem(int position) {
			return listArr.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		// @Override
		// public boolean isEnabled(int position) {
		// if(position==1)
		// return false;
		// return super.isEnabled(position);
		// }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String item = listArr.get(position);
			String[] codeLangArr = item.split("#");
			View view = null;
			try {
				TextView textView = null;
				if (convertView == null) {
					view = (View) LayoutInflater.from(context).inflate(
							R.layout.list_item_row, parent, false);
				} else {
					view = convertView;
				}
				textView = (TextView) view.findViewById(R.id.dialog_text);
				view.setTag(codeLangArr[1]);

				textView.setTypeface(Typeface.createFromAsset(
						LocalizationActivity.this.getAssets(),
						"fonts/DejaVuSans.ttf"));

				textView.setText(codeLangArr[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}
	}

	public OnItemClickListener langItemClickListner = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			currentLocale = (String) view.getTag();
			if (isAvailableLocale(currentLocale)) {
				Log.d("LOCALE", "currentLocale :: " + currentLocale);
				Locale locale = new Locale(currentLocale);
				Locale.setDefault(locale);
				Configuration config = new Configuration();
				getBaseContext().getResources().updateConfiguration(config,
						getBaseContext().getResources().getDisplayMetrics());
				TextView textView = (TextView) view
						.findViewById(R.id.dialog_text);
				tracker.trackEvent("Language", (String) textView.getText(),
						"Android", 6);
				startMainActivity();
			} else {
				Toast.makeText(LocalizationActivity.this,
						"Locale not supported", 500).show();
			}
		}
	};

	private void startMainActivity() {

		finish();
		Intent intent = new Intent(LocalizationActivity.this,
				PlatsbankenActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startMainActivity();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void refreshCompleted(Object data) {
		// Implementation not required.
	}

	@Override
	public void refreshList() {
		// Implementation not required.
	}
}
