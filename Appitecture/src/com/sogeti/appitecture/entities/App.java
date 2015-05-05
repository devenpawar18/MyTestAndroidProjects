package com.sogeti.appitecture.entities;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class App implements Parcelable, JSONSerializable
{
	private String name;
	private String description;
	private String developer;
	private String icon;
	
	public App() {}

	@Override
	public JSONObject serializeJSON() throws Exception {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("name", name);
		jsonObject.put("description", description);
		jsonObject.put("developer", developer);
		jsonObject.put("icon", icon.replace("http", "https"));
		
		return jsonObject;
	}

	@Override
	public void deserializeJSON(JSONObject jsonObject) throws Exception
	{
		this.name = jsonObject.getString("name");
		this.description = jsonObject.getString("description");
		this.developer = jsonObject.getString("developer");
		this.icon = jsonObject.getString("icon").replace("https", "http");
	}

	public int describeContents()
	{
        return 0;
    }

    public void writeToParcel(Parcel out, int flags)
    {
    	out.writeString(name);
    	out.writeString(description);
    	out.writeString(developer);
    	out.writeString(icon);
    }

    public static final Parcelable.Creator<App> CREATOR = new Parcelable.Creator<App>()
    {
        public App createFromParcel(Parcel in)
        {
            return new App(in);
        }

        public App[] newArray(int size)
        {
            return new App[size];
        }
    };
    
    private App(Parcel in)
    {
    	name = in.readString();
    	description = in.readString();
    	developer = in.readString();
    	icon = in.readString();
    }

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDeveloper()
	{
		return developer;
	}

	public void setDeveloper(String developer)
	{
		this.developer = developer;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}
}