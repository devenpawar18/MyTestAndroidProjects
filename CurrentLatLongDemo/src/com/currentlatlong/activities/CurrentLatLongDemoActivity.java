package com.currentlatlong.activities;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentLatLongDemoActivity extends Activity
{
	Geocoder geocoder;
	String bestProvider;
	List<Address> user = null;
	double lat;
	double lng;
	TextView currentLatLong;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		currentLatLong = (TextView) findViewById(R.id.current_lat_long);
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		bestProvider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(bestProvider);
		if (location == null)
		{
			Toast.makeText(this, "Location Not found", Toast.LENGTH_LONG).show();
		}
		else
		{
			geocoder = new Geocoder(this);
			try
			{
				user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				lat = (double) user.get(0).getLatitude();
				lng = (double) user.get(0).getLongitude();
				// currentLatLong
				// .setText("Lattitude: " + Double.toString(lat) + " Longitude: " + Double
				// .toString(lng));

				StringBuffer sb = new StringBuffer();

				for (int i = 0; i < user.get(0).getMaxAddressLineIndex(); i++)
				{
					sb.append(user.get(0).getAddressLine(i));

				}
				currentLatLong.setText(sb.toString());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}

}