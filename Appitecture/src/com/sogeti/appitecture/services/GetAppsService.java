package com.sogeti.appitecture.services;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sogeti.appitecture.HTTPRequest;
import com.sogeti.appitecture.entities.App;
import android.os.Handler;
import android.os.Message;

public class GetAppsService implements Runnable
{
	public interface GetAppsServiceListener
	{
		void onGetAppsFinished(ArrayList<App> apps);

		void onGetAppsFailed(String error);
	}

	private static final String GET_APPS_URL = Services.API_URL + "/androidmarkettopfreeapps.php";

	private GetAppsServiceListener listener;
	private ArrayList<App> apps;

	public void run()
	{
		HTTPRequest request = new HTTPRequest(GET_APPS_URL);

		try
		{
			request.execute(HTTPRequest.RequestMethod.GET);
			String response = request.getResponseString();
			JSONObject jsonObject = new JSONObject(response);
			JSONArray jsonArray = jsonObject.getJSONArray("topFreeApps");
			apps = new ArrayList<App>();

			for (int i = 0; i < jsonArray.length(); i++)
			{
				App app = new App();
				app.deserializeJSON(jsonArray.getJSONObject(i));
				apps.add(app);
			}
		}
		catch (Exception e)
		{
			apps = null;
		}

		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if (apps != null)
				listener.onGetAppsFinished(apps);
			else
				listener.onGetAppsFailed(null);
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