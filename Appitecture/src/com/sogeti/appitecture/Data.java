package com.sogeti.appitecture;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.sogeti.appitecture.entities.App;

public class Data
{
	private static final String TAG = "Data";
	private static final String APPS_CACHE_FILE_NAME = "apps.json"; 
	 
	private ArrayList<App> apps;
	private Context context;
	
	private static Data data = null; 
	public static Data getInstance()
	{ 
		if(data == null)
			data = new Data(); 

		return data; 
	}
	
	public Context getContext()
	{
		return context;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
		apps = new ArrayList<App>();
		loadApps();
	}
	
	public void saveApps()
	{
		try
		{
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < apps.size(); i++)
            	jsonArray.put(apps.get(i).serializeJSON());

            String jsonString = jsonArray.toString(); 
            FileOutputStream fis = context.openFileOutput(APPS_CACHE_FILE_NAME, Context.MODE_PRIVATE);
            fis.write(jsonString.getBytes());
            fis.close();
            
            Log.d(TAG, "saveApps: " + apps.size() + " apps saved");
		}
		catch(Exception e)
		{
            Log.e(TAG, "saveApps failed: " + e.getLocalizedMessage());
		}
	}
	
	public void loadApps()
	{
		try
		{
			FileInputStream fis = context.openFileInput(APPS_CACHE_FILE_NAME);
            InputStreamReader inputReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            String jsonString = bufferedReader.readLine();
            bufferedReader.close();
            fis.close();
            
            JSONArray jsonArray = new JSONArray(jsonString);
            apps.clear();
            for(int i = 0; i < jsonArray.length(); i++)
            {
            	JSONObject jsonObject = jsonArray.getJSONObject(i);
                App app = new App();
                app.deserializeJSON(jsonObject);
                apps.add(app);
            }
            
            Log.d(TAG, "loadApps: " + apps.size() + " apps loaded");
		}
		catch(Exception e)
		{
			Log.e(TAG, "loadApps failed: " + e.getLocalizedMessage());
		}
	}
	
	public ArrayList<App> getApps()
	{
		return apps;
	}
	
	public void setApps(ArrayList<App> apps)
	{
		this.apps = apps;
	}
}
