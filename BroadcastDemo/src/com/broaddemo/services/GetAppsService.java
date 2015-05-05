package com.broaddemo.services;

import org.json.JSONArray;
import android.os.Handler;
import android.os.Message;
import com.broaddemo.entities.App;
import com.broaddemo.network.HTTPRequest;

public class GetAppsService implements Runnable
{
	public interface GetAppsServiceListener
	{
		void onGetAppsFinished(App apps);

		void onGetAppsFailed(String error);
	}

	private App app;
	private static final String GET_APPS_URL = "http://www.nestle.com/ContactUsJson.ashx?device=android";

	private GetAppsServiceListener listener;

	public void run()
	{
		HTTPRequest request = new HTTPRequest(GET_APPS_URL);

		try
		{
			request.execute(HTTPRequest.RequestMethod.GET);
			String response = request.getResponseString();
			JSONArray jsonArray = new JSONArray(response);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				app = new App();
				app.deserializeJSON(jsonArray.getJSONObject(i));
			}
		}
		catch (Exception e)
		{
			app = null;
		}

		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (app != null)
				listener.onGetAppsFinished(app);
			else
				listener.onGetAppsFailed("error occurred.");
		}
	};

	public GetAppsServiceListener getListener()
	{
		return listener;
	}

	public void setListener(GetAppsServiceListener listener)
	{
		this.listener = listener;
	}
}