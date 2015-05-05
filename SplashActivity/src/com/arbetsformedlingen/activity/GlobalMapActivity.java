package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class GlobalMapActivity extends MapActivity{
	
	private GeoPoint point;
	private LocationManager mLocationManager;
	private Location mLocation;
	private MapView mapView;
	private Drawable marker;
	private MapController mMapController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.maplayout);
		mapView = (MapView) findViewById(R.id.mapView);
		mMapController = mapView.getController();
		
		String address = getIntent().getStringExtra("location");
		TextView titleTextView = (TextView) findViewById(R.id.heading);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			titleTextView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		titleTextView.setText(getResources().getString(R.string.map));
		
		address = address.replace(" ", "%20");

		AppController controller = new AppController(this);
		controller.getMapCordinates("http://maps.google.com/maps/geo?q=" + address +"&output=csv");
		
		
	}
	
	private void fetchCurrentLocation() {
		mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		try {
			if (LocationManager.NETWORK_PROVIDER != null && mLocationManager != null && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				setLocation();
			} else {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage(R.string.enable_location_service);

				dialog.setPositiveButton(R.string.go, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				});
				dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				dialog.show();
				
			}
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}
	}
	
	private void setLocation()
	{
		mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(mLocation!=null)	{
			int tLatitude = (int)(mLocation.getLatitude() * 1E6);
			int tLongitude = (int)(mLocation.getLongitude() * 1E6);
			GeoPoint tPoint = new GeoPoint(tLatitude, tLongitude);
			marker = getResources().getDrawable(R.drawable.pinblue);
			marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
			mapView.getOverlays().add(new MapItemizedOverlay(marker, this, tPoint));
		}
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		if (LocationManager.NETWORK_PROVIDER != null && mLocationManager != null && mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			setLocation();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void refreshCompleted(Object data) {
		
		String[] cords = (String[]) data;
		double lat = 0l, lng= 0l;
		if(cords.length>0&&cords.length==4) {
			lat = Double.parseDouble(cords[2]);
			lng = Double.parseDouble(cords[3]);
		}
			
		if(lat==0 && lng==0) {
			GeoPoint centerPoint = mapView.getMapCenter();
			lng =((double)centerPoint.getLongitudeE6()/1000000);
			lat =((double)centerPoint.getLatitudeE6()/1000000);
			Toast.makeText(this, "Not able to locate this location.", 500).show();
		}
		
        point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
        mMapController.animateTo(point);
        mMapController.setZoom(10);
        marker = getResources().getDrawable(R.drawable.pinorange);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
        mapView.getOverlays().add(new MapItemizedOverlay(marker, this, point));
        fetchCurrentLocation();
	}


	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public class MapItemizedOverlay extends ItemizedOverlay {
		
		private ArrayList<OverlayItem> listOverLayItems = new ArrayList<OverlayItem>(); 
		
		public MapItemizedOverlay(Drawable defaultMarker) {
			super(defaultMarker);
		}

		public MapItemizedOverlay(Drawable defaultMarker, Context context, GeoPoint point) {
			super(boundCenterBottom(defaultMarker));
			listOverLayItems.add(new OverlayItem(point, null, null));
			populate(); // very very important function not sure what it does but if you comment this, you will get a exception on onDraw();
		}
		
		@Override
		protected OverlayItem createItem(int i) {
			return listOverLayItems.get(i);
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, false);
		}

		@Override
		public int size() {
			return listOverLayItems.size();
		}
	}
	
}
