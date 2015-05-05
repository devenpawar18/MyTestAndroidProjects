package com.arbetsformedlingen.network;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.arbetsformedlingen.entity.ContactDetailEntity;
import com.arbetsformedlingen.entity.ContactPersonEntity;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;
import com.arbetsformedlingen.entity.JobEntity;
import com.arbetsformedlingen.entity.SearchResultEntity;

public class Parser {

	public ArrayList<SearchResultEntity> parseSerachResults(String jsonData) {

		ArrayList<SearchResultEntity> items = null;
		try {
			items = new ArrayList<SearchResultEntity>();
			JSONObject jObject = new JSONObject(jsonData);
			Log.d("Json object", jObject.names().toString(2));
			JSONObject jListObject = jObject.getJSONObject("matchningslista");
			FilterEntity.getFilterInstance().jobCount = jListObject
					.getString("antal_platsannonser");
			Log.d("FilterEntity.getFilterInstance().jobCount",
					FilterEntity.getFilterInstance().jobCount);
			JSONArray jsonArray = jListObject.getJSONArray("matchningdata");
			for (int i = 0; i < jsonArray.length(); i++) {
				items.add(SearchResultEntity.toSearchResultEntity(jsonArray
						.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			FilterEntity.getFilterInstance().jobCount = "0";
		}
		return items;
	}

	public ArrayList<JobEntity> parseCountry(String jsonData) {

		ArrayList<JobEntity> items = null;
		try {
			items = new ArrayList<JobEntity>();
			JSONObject jObject = new JSONObject(jsonData);
			Log.d("Json object", jObject.names().toString(2));
			JSONObject jListObject = jObject.getJSONObject("soklista");
			JSONArray jsonArray = jListObject.getJSONArray("sokdata");
			for (int i = 0; i < jsonArray.length(); i++) {
				items.add(JobEntity.toCountryEntity(jsonArray.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return items;
	}

	public ArrayList<JobEntity> parseContactCity(String jsonData) {

		ArrayList<JobEntity> items = null;
		try {
			items = new ArrayList<JobEntity>();
			JSONObject jObject = new JSONObject(jsonData);
			Log.d("Json object", jObject.names().toString(2));
			JSONObject jListObject = jObject
					.getJSONObject("arbetsformedlingslista");
			JSONArray jsonArray = jListObject
					.getJSONArray("arbetsformedlingplatsdata");
			for (int i = 0; i < jsonArray.length(); i++) {
				items.add(JobEntity.toContactCityEntity(jsonArray
						.getJSONObject(i)));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return items;
	}

	public JobDetailEntity parseJobDetail(String jsonData) {
		JobDetailEntity item = null;
		try {
			item = new JobDetailEntity();
			JSONObject jObject = new JSONObject(jsonData);
			JSONObject jListObject = jObject.getJSONObject("platsannons");
			item = item
					.getAddToJobDetailEntity(!jListObject.isNull("annons") ? jListObject
							.getJSONObject("annons") : new JSONObject());
			item = item
					.getCarToJobDetailEntity(!jListObject.isNull("krav") ? jListObject
							.getJSONObject("krav") : new JSONObject());
			item = item.getConditionToJobDetailEntity(!jListObject
					.isNull("villkor") ? jListObject.getJSONObject("villkor")
					: new JSONObject());
			item = item.getApplicationToJobDetailEntity(!jListObject
					.isNull("ansokan") ? jListObject.getJSONObject("ansokan")
					: new JSONObject());
			if (!jListObject.isNull("arbetsplats")) {
				jObject = jListObject.getJSONObject("arbetsplats");
				item = item.getWorkplaceToJobDetailEntity(jObject);
				item.contactPersonsList = new ArrayList<ContactPersonEntity>();
				JSONObject personObject = !jObject.isNull("kontaktpersonlista") ? jObject
						.getJSONObject("kontaktpersonlista") : new JSONObject();
				JSONArray contactArray = !personObject
						.isNull("kontaktpersondata") ? personObject
						.getJSONArray("kontaktpersondata") : new JSONArray();
				for (int i = 0; i < contactArray.length(); i++) {
					item.contactPersonsList.add(ContactPersonEntity
							.toContactPersonEntity(contactArray
									.getJSONObject(i)));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				JSONObject jObject;
				jObject = new JSONObject(jsonData);
				JSONObject jListObject = jObject.getJSONObject("Error");
				return null;
			} catch (JSONException e1) {
				e1.printStackTrace();
				return null;
			}
		}
		return item;
	}

	public ContactDetailEntity parseContactDetail(String jsonData) {

		ContactDetailEntity item = null;
		try {
			item = new ContactDetailEntity();
			JSONObject jObject = new JSONObject(jsonData);
			Log.d("Json object", jObject.names().toString(2));
			item = ContactDetailEntity.toCountryEntity(jObject
					.getJSONObject("arbetsformedling"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return item;
	}

	public ArrayList<String> parseTranslatedResponse(String xmlData) {
		ArrayList<String> data = new ArrayList<String>();
		try {

			JobDetailEntity jobDetail = null;

			if (!TextUtils.isEmpty((CharSequence) xmlData)) {
				XmlPullParser parser = Xml.newPullParser();
				try {
					parser.setInput(
							new ByteArrayInputStream(xmlData.getBytes("UTF-8")),
							null);
					int eventType = parser.getEventType();
					boolean done = false;
					while (eventType != XmlPullParser.END_DOCUMENT && !done) {
						String name = null;
						switch (eventType) {
						case XmlPullParser.START_DOCUMENT:
							break;
						case XmlPullParser.START_TAG:
							name = parser.getName();
							if (name.equalsIgnoreCase("ArrayOfTranslateArrayResponse")) {
								jobDetail = new JobDetailEntity();
							} else if (jobDetail != null) {
								if (name.equalsIgnoreCase("TranslatedText")) {
									data.add(parser.nextText()
											.replace("&amp;", "&")
											.replace("&lt;", "<")
											.replace("&gt;.", ">"));
								}
							}
							break;
						case XmlPullParser.END_TAG:
							name = parser.getName();
							if (name.equalsIgnoreCase("ArrayOfTranslateArrayResponse")
									&& jobDetail != null) {

								jobDetail = null;
							}
							break;
						}
						eventType = parser.next();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else
				Log.d("RSSParser", "parseNews :: "
						+ "response not parsed as its blank");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

}
