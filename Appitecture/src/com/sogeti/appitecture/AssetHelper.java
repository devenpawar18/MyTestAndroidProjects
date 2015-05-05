package com.sogeti.appitecture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

public class AssetHelper
{
	private static final String TAG = "AssetHelper";
	
	private static String convertStreamToString(InputStream is) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = is.read();
		while(i != -1)
		{
			baos.write(i);
			i = is.read();
		}
		return baos.toString();
	}
        
	public static String getStringFromAssetFile(Activity activity, String path)
	{
		AssetManager am = activity.getAssets();
		InputStream is;
		String s = null;
		try
		{
			is = am.open(path);     
			s = convertStreamToString(is);
			is.close();
		}
		catch(IOException e)
		{
			Log.e(TAG, e.toString());
		}
		return s;
	}
}
