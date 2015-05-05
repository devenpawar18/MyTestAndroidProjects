package com.sharing.services;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.sharing.network.HTTPRequest;
import com.sharing.utils.SharingUtils;

public class ShortenUrlService extends Application implements Runnable {
	String response = null;

	public interface GetShortenURLServiceListener {
		void onGetShortenURLFinished(String apps);

		void onGetShortenURLFailed(String error);
	}

	private String GET_APPS_URL = SharingUtils.NEW_URL;

	private GetShortenURLServiceListener listener;

	public void run() {
		HTTPRequest request = new HTTPRequest(GET_APPS_URL);
//		request.addHeader("content-Type", "application/x-www-form-urlencoded");
//		request.addHeader("From", "PBIphone@arbetsformedlingen.se");
//		request.addHeader("Accept", "application/json");
//		request.addHeader("Accept-Language", "SE");

		try {
			request.execute(HTTPRequest.RequestMethod.GET);
			response = request.getResponseString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putString("response", response);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String resp = msg.getData().getString("response");
			if (resp == null && TextUtils.isEmpty(resp)) {
				listener.onGetShortenURLFailed(null);
			} else {
				listener.onGetShortenURLFinished(resp);
			}
		}
	};

	public GetShortenURLServiceListener getListener() {
		return listener;
	}

	public void setListener(GetShortenURLServiceListener listener) {
		this.listener = listener;
	}

}