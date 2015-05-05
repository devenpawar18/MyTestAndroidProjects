package com.googlemaps.demo;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDemoActivity extends Activity implements OnInfoWindowClickListener, OnMarkerDragListener
{
	private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
	private static final LatLng MELBOURNE = new LatLng(-37.81319, 144.96298);
	private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
	private static final LatLng ADELAIDE = new LatLng(-34.92873, 138.59995);
	private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);

	public static List<LatLng> locationsList = new ArrayList<LatLng>();
	private Marker mPerth;
	private Marker mSydney;
	private Marker mBrisbane;
	private Marker mAdelaide;
	private Marker mMelbourne;
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_demo);

		getLocations();

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

		// Hide the zoom controls as the button panel will cover it.
		map.getUiSettings().setZoomControlsEnabled(false);

		// Add lots of markers to the map.
		addMarkersToMap();

		// Setting an info window adapter allows us to change the both the contents and look of the
		// info window.
		map.setInfoWindowAdapter(new CustomInfoWindowAdapter());

		// Set listeners for marker events. See the bottom of this class for their behavior.
		map.setOnInfoWindowClickListener(this);

		// Move the camera instantly to hamburg with a zoom of 15.
		// map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

		// Zoom in, animating the camera.
		// map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		map.setOnInfoWindowClickListener(this);
		map.setOnMarkerDragListener(this);
		map.setOnMarkerClickListener(new OnMarkerClickListener()
		{

			@Override
			public boolean onMarkerClick(final Marker marker)
			{
				if (marker.equals(mPerth))
				{
					bounceMarker(map, marker, PERTH.longitude, PERTH.latitude, PERTH);
				}
				else if (marker.equals(mSydney))
				{
					bounceMarker(map, marker, SYDNEY.longitude, SYDNEY.latitude, SYDNEY);
				}
				else if (marker.equals(mBrisbane))
				{
					bounceMarker(map, marker, BRISBANE.longitude, BRISBANE.latitude, BRISBANE);
				}
				else if (marker.equals(mAdelaide))
				{
					bounceMarker(map, marker, ADELAIDE.longitude, ADELAIDE.latitude, ADELAIDE);
				}
				else if (marker.equals(mMelbourne))
				{
					bounceMarker(map, marker, MELBOURNE.longitude, MELBOURNE.latitude, MELBOURNE);
				}
				return false;
			}
		});

		// Pan to see all markers in view.
		// Cannot zoom to bounds until the map has a size.
		final View mapView = getFragmentManager().findFragmentById(R.id.map).getView();
		if (mapView.getViewTreeObserver().isAlive())
		{
			mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
			{
				@SuppressWarnings("deprecation")
				// We use the new method when supported
				@SuppressLint("NewApi")
				// We check which build version we are using.
				@Override
				public void onGlobalLayout()
				{
					LatLngBounds bounds = new LatLngBounds.Builder().include(PERTH).include(SYDNEY).include(ADELAIDE).include(BRISBANE).include(MELBOURNE)
							.build();
					if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
					{
						mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}
					else
					{
						mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
					map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));
					map.animateCamera(CameraUpdateFactory.zoomTo(2), 3000, null);
				}
			});
		}
	}

	private void bounceMarker(GoogleMap map, final Marker marker, final double longitude, final double latitude, LatLng latLng)
	{
		// This causes the marker at Perth to bounce into position when it is clicked.
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		Projection proj = map.getProjection();
		Point startPoint = proj.toScreenLocation(latLng);
		startPoint.offset(0, -100);
		final LatLng startLatLng = proj.fromScreenLocation(startPoint);
		final long duration = 1500;

		final Interpolator interpolator = new BounceInterpolator();

		handler.post(new Runnable()
		{
			@Override
			public void run()
			{
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed / duration);
				double lng = t * longitude + (1 - t) * startLatLng.longitude;
				double lat = t * latitude + (1 - t) * startLatLng.latitude;
				marker.setPosition(new LatLng(lat, lng));

				if (t < 1.0)
				{
					// Post again 16ms later.
					handler.postDelayed(this, 16);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map_demo, menu);
		return true;
	}

	@Override
	public void onInfoWindowClick(Marker marker)
	{
		Toast.makeText(MapDemoActivity.this, marker.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
	}

	private void getLocations()
	{
		locationsList.add(BRISBANE);
		locationsList.add(MELBOURNE);
		locationsList.add(SYDNEY);
		locationsList.add(ADELAIDE);
		locationsList.add(PERTH);

	}

	private void addMarkersToMap()
	{
		// Uses a colored icon.
		mBrisbane = map.addMarker(new MarkerOptions().position(BRISBANE).title("Brisbane").snippet("Population: 2,074,200"));

		// Uses a custom icon.
		mSydney = map.addMarker(new MarkerOptions().position(SYDNEY).title("Sydney").snippet("Population: 4,627,300"));

		// Creates a draggable marker. Long press to drag.
		mMelbourne = map.addMarker(new MarkerOptions().position(MELBOURNE).title("Melbourne").snippet("Population: 4,137,400").draggable(true));

		// A few more markers for good measure.
		mPerth = map.addMarker(new MarkerOptions().position(PERTH).title("Perth").snippet("Population: 1,738,800"));
		mAdelaide = map.addMarker(new MarkerOptions().position(ADELAIDE).title("Adelaide").snippet("Population: 1,213,000"));

		// Creates a marker rainbow demonstrating how to create default marker icons of different
		// hues (colors).
		// int numMarkersInRainbow = 12;
		// for (int i = 0; i < numMarkersInRainbow; i++)
		// {
		// map.addMarker(new MarkerOptions()
		// .position(new LatLng(-30 + 10 * Math.sin(i * Math.PI / (numMarkersInRainbow - 1)), 135 -
		// 10 * Math
		// .cos(i * Math.PI / (numMarkersInRainbow - 1)))).title("Marker " + i)
		// .icon(BitmapDescriptorFactory.defaultMarker(i * 360 / numMarkersInRainbow)));
		// }
	}

	/** Demonstrates customizing the info window and/or its contents. */
	class CustomInfoWindowAdapter implements InfoWindowAdapter
	{
		// These a both viewgroups containing an ImageView with id "badge" and two TextViews with id
		// "title" and "snippet".
		private final View mWindow;

		CustomInfoWindowAdapter()
		{
			mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
		}

		@Override
		public View getInfoWindow(Marker marker)
		{
			render(marker, mWindow);
			return mWindow;
		}

		private void render(Marker marker, View view)
		{
			int badge;
			badge = R.drawable.badge_nsw;
			((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

			String title = marker.getTitle();
			TextView titleUi = ((TextView) view.findViewById(R.id.title));
			if (title != null)
			{
				// Spannable string allows us to edit the formatting of the text.
				SpannableString titleText = new SpannableString(title);
				titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
				titleUi.setText(titleText);
			}
			else
			{
				titleUi.setText("");
			}

			String snippet = marker.getSnippet();
			TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
			if (snippet != null && snippet.length() > 12)
			{
				SpannableString snippetText = new SpannableString(snippet);
				snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
				snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
				snippetUi.setText(snippetText);
			}
			else
			{
				snippetUi.setText("");
			}
		}

		@Override
		public View getInfoContents(Marker marker)
		{
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public void onMarkerDrag(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragEnd(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragStart(Marker marker)
	{
		// TODO Auto-generated method stub

	}
}
