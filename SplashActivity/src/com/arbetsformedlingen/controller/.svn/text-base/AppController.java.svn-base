package com.arbetsformedlingen.controller;

import java.io.InputStream;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.arbetsformedlingen.activity.GlobalMapActivity;
import com.arbetsformedlingen.activity.R;
import com.arbetsformedlingen.entity.ContactDetailEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;
import com.arbetsformedlingen.entity.JobEntity;
import com.arbetsformedlingen.entity.SearchResultEntity;
import com.arbetsformedlingen.network.HTTPConnection;
import com.arbetsformedlingen.network.HttpConnectionCallback;
import com.arbetsformedlingen.network.Parser;

public class AppController {

	private Context currentContext;
	private Parser parser;
	private ProgressDialog dialog;
	private HTTPConnection connection;

	public void getSearchResult(String url) {
		new SearchTask().execute(url);
	}

	public void getJobCount(String url) {
		new JobCountTask().execute(url);
	}

	public void getCountries(String url) {
		new CountryTask().execute(url);
	}

	public void getJobDetail(String url) {
		new JobDetailTask().execute(url);
	}

	public void getContactDetail(String url) {
		new ContactDetailTask().execute(url);
	}

	public void getProfessionsFilter(String url) {
		new ProfessionTask().execute(url);
	}

	public void getProfessions(String url) {
		new CountryTask().execute(url);
	}

	public void getContactCity(String url) {
		new ContactCityTask().execute(url);
	}

	public void getMapCordinates(String url) {
		new MapCordinateTask().execute(url);
	}

	public void getMicrosoftTranslateJobDetail(String... params) {
		new MicrosoftTranslateJobDetailTask().execute(params);
	}

	public void getShortenURL(String url) {
		new ShortenURL().execute(url);
	}

	public AppController(Context context) {
		this.currentContext = context;
		parser = new Parser();
		connection = new HTTPConnection();
	}

	private class SearchTask extends
			AsyncTask<String, Void, ArrayList<SearchResultEntity>> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected ArrayList<SearchResultEntity> doInBackground(
				final String... args) {
			args[0] = args[0].trim();
			args[0] = args[0].replaceAll(" ", "%20");
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			ArrayList<SearchResultEntity> itemList = parser
					.parseSerachResults(response);
			return itemList;
		}

		protected void onPostExecute(ArrayList<SearchResultEntity> list) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			((HttpConnectionCallback) currentContext).refreshCompleted(list);
		}

	}

	private class CountryTask extends
			AsyncTask<String, Void, ArrayList<JobEntity>> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected ArrayList<JobEntity> doInBackground(final String... args) {
			args[0] = args[0].trim();
			args[0] = args[0].replaceAll(" ", "%20");
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			ArrayList<JobEntity> itemList = parser.parseCountry(response);
			return itemList;
		}

		protected void onPostExecute(ArrayList<JobEntity> list) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			((HttpConnectionCallback) currentContext).refreshCompleted(list);
		}

	}

	private class ProfessionTask extends
			AsyncTask<String, Void, ArrayList<JobEntity>> {

		protected void onPreExecute() {

		}

		protected ArrayList<JobEntity> doInBackground(final String... args) {
			args[0] = args[0].trim();
			args[0] = args[0].replaceAll(" ", "%20");
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			ArrayList<JobEntity> itemList = parser.parseCountry(response);
			return itemList;
		}

		protected void onPostExecute(ArrayList<JobEntity> list) {
			((HttpConnectionCallback) currentContext).refreshCompleted(list);
		}

	}

	private class JobCountTask extends AsyncTask<String, Void, Long> {
		protected void onPreExecute() {
		}

		protected Long doInBackground(final String... args) {
			Long totalCount = 0l;
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return totalCount;
			}
			String response = connection.convertStreamToString(stream);
			ArrayList<JobEntity> itemList = parser.parseCountry(response);

			for (JobEntity entity : itemList) {
				totalCount += TextUtils.isEmpty(entity.getJobCount()) ? 0
						: Integer.parseInt(entity.getJobCount());
			}

			return totalCount;
		}

		protected void onPostExecute(Long count) {
			((HttpConnectionCallback) currentContext).refreshCompleted(count);
		}

	}

	private class ContactCityTask extends
			AsyncTask<String, Void, ArrayList<JobEntity>> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected ArrayList<JobEntity> doInBackground(final String... args) {
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			ArrayList<JobEntity> itemList = parser.parseContactCity(response);
			return itemList;
		}

		protected void onPostExecute(ArrayList<JobEntity> list) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			((HttpConnectionCallback) currentContext).refreshCompleted(list);
		}

	}

	private class JobDetailTask extends
			AsyncTask<String, Void, JobDetailEntity> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected JobDetailEntity doInBackground(final String... args) {
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			Log.d("AppController.JobDetailTask", "doInBackground :: "
					+ "response :: " + response);
			JobDetailEntity item = parser.parseJobDetail(response);
			return item;
		}

		protected void onPostExecute(JobDetailEntity item) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			((HttpConnectionCallback) currentContext).refreshCompleted(item);
		}

	}

	private class ContactDetailTask extends
			AsyncTask<String, Void, ContactDetailEntity> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected ContactDetailEntity doInBackground(final String... args) {
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			Log.d("AppController.ContactDetailTask", "doInBackground :: "
					+ "response :: " + response);
			ContactDetailEntity item = parser.parseContactDetail(response);
			return item;
		}

		protected void onPostExecute(ContactDetailEntity item) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			((HttpConnectionCallback) currentContext).refreshCompleted(item);
		}

	}

	private class MapCordinateTask extends AsyncTask<String, Void, String[]> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();

		}

		protected String[] doInBackground(final String... args) {
			InputStream stream = connection.OpenHttpConnection(args[0]);
			if (stream == null) {
				return null;
			}
			String response = connection.convertStreamToString(stream);
			String[] retVal = response.split(",");
			return retVal;
		}

		protected void onPostExecute(String[] list) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			((GlobalMapActivity) currentContext).refreshCompleted(list);
		}

	}

	private class MicrosoftTranslateJobDetailTask extends
			AsyncTask<String, Void, ArrayList<String>> {

		protected void onPreExecute() {
			dialog = new ProgressDialog(currentContext);
			dialog.setMessage((String) currentContext.getResources().getText(
					R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}

		protected ArrayList<String> doInBackground(final String... args) {
			ArrayList<String> values = new ArrayList<String>();
			final String appId = "EBC7D8D843029BE46115AAFE8B6C68E43F017B5E";
			String url = "http://api.microsofttranslator.com/v2/Http.svc/TranslateArray?appId="
					+ appId;

			String swriter = new String();
			StringBuilder xwriter = new StringBuilder(swriter);
			xwriter.append("<TranslateArrayRequest>");
			xwriter.append("<AppId>");
			xwriter.append(appId);
			xwriter.append("</AppId>");
			xwriter.append("<From>");
			xwriter.append("sv");
			xwriter.append("</From>");
			xwriter.append("<Texts>");
			for (int i = 1; i < args.length; i++) {

				String text = args[i].replace("<", "&lt;")
						.replace(">", "&gt;.").replace("&", "&amp;");
				xwriter.append("<string ");
				xwriter.append("xmlns=");
				xwriter.append("\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\">");
				xwriter.append(text);
				xwriter.append("</string>");
			}
			xwriter.append("</Texts>");
			xwriter.append("<To>");
			xwriter.append(args[0]);
			xwriter.append("</To>");
			xwriter.append("</TranslateArrayRequest>");

			String stream = connection.translate(url, xwriter.toString());
			if (stream == null) {
				return null;
			}
			values = parser.parseTranslatedResponse(stream);
			return values;
		}

		protected void onPostExecute(ArrayList<String> values) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (values == null)
				values = new ArrayList<String>();
			((HttpConnectionCallback) currentContext).refreshCompleted(values);
		}

	}

	private class ShortenURL extends AsyncTask<String, Void, String> {

		protected void onPreExecute() {
		}

		protected String doInBackground(final String... args) {
			InputStream stream = connection.shortenUrl(args[0]);
			if (stream == null) {
				return null;
			}
			 String response = connection.convertStreamToString(stream);
			return response;
		}

		protected void onPostExecute(String response) {
			((HttpConnectionCallback) currentContext)
					.refreshCompleted(response);
		}

	}

}
