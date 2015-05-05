package com.htmlpagedemo.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.htmlpagedemo.entity.ProductDescription;
import com.htmlpagedemo.network.HTTPRequest;
import com.htmlpagedemo.utils.HTMLDemoConstants;

public class HTMLPageService implements Runnable {
	ProductDescription prodDesc;

	public interface GetHtmlPageServiceListener {
		void onGetHtmlPageFinished(ProductDescription prodDescription);

		void onGetHtmlPageFailed(String error);

	}

	// private String GET_APPS_URL =
	// "http://www.endless.com/mDetail?v=1&asin=B004DERAPW&featureList=main,techSpec,fitGuide,sizeChart,customerReviews,description";
	private int errorCode = 0;

	private String GET_APPS_URL = "http://www.endless.com/mDetail?v=1&asin=B003OYJ6YU&featureList=main,techSpec,fitGuide,sizeChart,customerReviews,description";

	private GetHtmlPageServiceListener listener;

	public void run() {
		HTTPRequest request = new HTTPRequest(GET_APPS_URL);

		String response = null;
		try {
			errorCode = request.execute(HTTPRequest.RequestMethod.GET);
			response = request.getResponseString();
			System.out.println("response " + response);
			parseJson(response);
		} catch (Exception e) {

		}
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (errorCode == HTMLDemoConstants.SUCCESS)
				listener.onGetHtmlPageFinished(prodDesc);
			else
				listener.onGetHtmlPageFailed(null);
		}
	};

	public GetHtmlPageServiceListener getListener() {
		return listener;
	}

	public void setListener(GetHtmlPageServiceListener listener) {
		this.listener = listener;
	}

	public void parseJson(String jsonData) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject(jsonData)
					.getJSONObject("productDescription");
			prodDesc = new ProductDescription();
			prodDesc.deserializeJSON(jsonObject);
			Log.d("HTMLPageService", "parseJson::" + prodDesc);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}