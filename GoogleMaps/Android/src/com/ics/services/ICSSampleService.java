package com.ics.services;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.provider.Contacts;

import com.ics.entity.Contact;
import com.ics.network.HTTPRequest;
import com.ics.utils.ICSConstants;

public class ICSSampleService implements Runnable {
	public interface GetAppsServiceListener {
		void onGetAppsFinished(Contact apps);

		void onGetAppsFailed(String error);
	}

	private static final String GET_APPS_URL = ICSConstants.getRequestUrl;

	private GetAppsServiceListener listener;
	private Contact contact;

	// private ArrayList<App> apps;

	public void run() {
		HTTPRequest request = new HTTPRequest(GET_APPS_URL);

		try {
			request.execute(HTTPRequest.RequestMethod.GET);
			String response = request.getResponseString();
			getContact(response);
		} catch (Exception e) {
			contact = null;
		}

		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (contact != null)
				listener.onGetAppsFinished(contact);
			else
				listener.onGetAppsFailed(null);
		}
	};

	public GetAppsServiceListener getListener() {
		return listener;
	}

	public void setListener(GetAppsServiceListener listener) {
		this.listener = listener;
	}

	public void getContact(String response) {
		contact = new Contact();
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject jsonObjectPeople = jsonObject.getJSONObject("d");
			contact.setEmail(jsonObjectPeople.getString("Email"));
			contact.setCatalogEmail(jsonObjectPeople.getString("CatalogEmail"));
			contact.setPhone(jsonObjectPeople.getString("Phone"));
			contact.setMessageEmail(jsonObjectPeople.getString("MessageEmail"));
			contact.setAddress(jsonObjectPeople.getString("Address"));
			contact.setPostAddress(jsonObjectPeople.getString("PostAddress"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}