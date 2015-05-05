package com.android;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LatLongDemoActivity extends Activity {
	LocationManager locManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LocationManager locationManager;
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		updateWithNewLocation(location);

	}

	private void updateWithNewLocation(Location location) {
		String latLongString = "Unknown";
		String addressString = "No address found";
		TextView myLocationText;
		DecimalFormat df = new DecimalFormat("##.00");
		myLocationText = (TextView) findViewById(R.id.lat_long);
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + df.format(lat) + "\nLong:"
					+ df.format(lng);
			Geocoder gc = new Geocoder(LatLongDemoActivity.this,
					Locale.getDefault());
			try {
				List<Address> addresses = gc.getFromLocation(lat, lng, 1);
				if (addresses.size() == 1) {
					addressString = "";
					Address address = addresses.get(0);
					for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
						addressString = addressString
								+ address.getAddressLine(i) + "\n";
					}
					addressString = addressString + address.getCountryName()
							+ "\n";
				}
			} catch (IOException ioe) {
				Log.e("Geocoder IOException exception: ", ioe.getMessage());
			}
		}
		myLocationText.setText("Your Current Position is:\n" + latLongString
				+ "\n" + addressString);
	}
}