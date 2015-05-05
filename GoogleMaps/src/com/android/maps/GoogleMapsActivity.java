package com.android.maps;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class GoogleMapsActivity extends MapActivity {

	private MapView mapView;
	private MapController mc;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		mapView = (MapView) findViewById(R.id.map);
		mc = mapView.getController();

		// String coordinates[] = {"40.747778", "-73.985556"};
		// double lat = Double.parseDouble(coordinates[0]);
		// double lng = Double.parseDouble(coordinates[1]);

		GeoPoint p = new GeoPoint((int) (40.747778 * 1E6),
				(int) (73.985556 * 1E6));
		Drawable marker = getResources().getDrawable(R.drawable.map_marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		mapView.getOverlays().add(new MapItemizedOverlay(marker));

		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(true);
		mapView.setStreetView(true);
		mc.animateTo(p);
		mc.setZoom(7);
		mapView.invalidate();

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_3:
			mc.zoomIn();
			break;
		case KeyEvent.KEYCODE_1:
			mc.zoomOut();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class MapItemizedOverlay extends ItemizedOverlay {

		private List<OverlayItem> items = new ArrayList();

		public MapItemizedOverlay(Drawable defaultMarker) {
			super(defaultMarker);
//			GeoPoint p1 = new GeoPoint((int) (44.747778 * 1E6),
//					(int) (71.985556 * 1E6));
//			GeoPoint p2 = new GeoPoint((int) (45.747778 * 1E6),
//					(int) (72.985556 * 1E6));
//			items.add(new OverlayItem(p1, null, null));
//			items.add(new OverlayItem(p2, null, null));

			for (int i = 0; i < GlobalData.agentItems.length; i++) {
				items.add(new OverlayItem(GlobalData.agentItems[i]
						.getGeoPoint(),
						GlobalData.agentItems[i].getAgentName(), null));
			}
			populate();
		}

		public MapItemizedOverlay(Drawable defaultMarker, Context context) {
			super(defaultMarker);
		}

		@Override
		protected OverlayItem createItem(int i) {
			return ((OverlayItem) items.get(i));
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			boundCenterBottom(getResources().getDrawable(R.drawable.map_marker));
		}

		@Override
		public int size() {
			return (items.size());
		}

		@Override
		protected boolean onTap(final int index) {
			final AgentDetails agentItems = GlobalData.agentItems[index];
			AlertDialog.Builder dialog = new AlertDialog.Builder(
					GoogleMapsActivity.this);

			dialog.setTitle(agentItems.getAgentName());
			// dialog.setMessage(findUsListItem.getSubTitle());

			dialog.setPositiveButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Intent intent = new Intent(GoogleMapsActivity.this,
					// FindUsDetailActivity.class);
					// intent.putExtra("index", index);
					// startActivity(intent);

				}
			});
			dialog.setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			dialog.show();
			return true;
		}
	}

}
