package com.broaddemo.entities;

import org.json.JSONObject;

public class App implements JSONSerializable
{
	private String info;

	public App()
	{
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	@Override
	public JSONObject serializeJSON() throws Exception
	{
		return null;
	}

	@Override
	public void deserializeJSON(JSONObject jsonObject) throws Exception
	{
		this.info = jsonObject.getString("ContactUsMobileAppContent");
	}

}