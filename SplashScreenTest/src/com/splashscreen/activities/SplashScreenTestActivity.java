package com.splashscreen.activities;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class SplashScreenTestActivity extends Activity
{
	String mainStr = "CSS BRAND1 VTO~*~VTO/PROD & NAME~VTO/PROD & VAR1|CSS BRAND1 VTO~*~VTO/PROD & NAME~VTO/PROD & VAR2|CSS BRAND1 VTO~*~VTO/PROD & NAME~VTO/PROD & VAR3";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try
		{
			getMultiLineAttributeValue();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public StringBuffer getMultiLineAttributeValue() throws Exception
	{
		StringBuffer formatedString = new StringBuffer();
		try
		{
			String[] arr = mainStr.split("/");
			for (int i = 0; i < arr.length; i++)
			{
				if (arr[i].contains("~"))
				{
					int j = arr[i].indexOf("~");
					String str = arr[i].substring(0, j);
					formatedString.append(str);
//					if (str != null && str.trim().length() > 0)
//					{
//						str = str.replace("/", "\n");
//						formatedString.append(str);
//					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			return formatedString;
		}
	}

}