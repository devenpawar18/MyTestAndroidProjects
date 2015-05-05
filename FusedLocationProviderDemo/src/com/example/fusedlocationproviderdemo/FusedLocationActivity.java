package com.example.fusedlocationproviderdemo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.ActivityRecognitionClient;

public class FusedLocationActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener
{

	/**
	 * Store the PendingIntent used to send activity recognition events back to the app
	 */

	private PendingIntent mActivityRecognitionPendingIntent;
	/**
	 * Store the current activity recognition client
	 */
	private ActivityRecognitionClient mActivityClient;

	private TextView currentActivityTextView;
	public FusedLocationBroadcast fusedLocationBroadcast;
	public String activityName;
	public StringBuilder sb;
	private SimpleDateFormat dateFormat;
	public static final String FILTER_STRING = "Location Data Received";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fused_location);

		currentActivityTextView = (TextView) findViewById(R.id.text_view);
		mActivityClient = new ActivityRecognitionClient(this, this, this);
		mActivityClient.connect();

		dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		sb = new StringBuilder("Searching User's Activity" + "  " + dateFormat.format(new Date()));
		currentActivityTextView.setText(sb.toString());

		currentActivityTextView.setMovementMethod(new ScrollingMovementMethod());

		IntentFilter filter = new IntentFilter(FILTER_STRING);
		fusedLocationBroadcast = new FusedLocationBroadcast();
		registerReceiver(fusedLocationBroadcast, filter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_fused_location, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		Toast.makeText(FusedLocationActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		Intent intent = new Intent(this, ActivityRecognitionIntentService.class);
		mActivityRecognitionPendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mActivityClient.requestActivityUpdates(2000, mActivityRecognitionPendingIntent);

	}

	@Override
	public void onDisconnected()
	{
		Toast.makeText(FusedLocationActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();

	}

	/**
	 * Receives a Broadcast on Network Change.
	 * 
	 * @author devpawar
	 * 
	 */
	public class FusedLocationBroadcast extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			activityName = intent.getExtras().getString("key");
			sb.append("\n" + activityName + "  " + dateFormat.format(new Date()));
			currentActivityTextView.setText(sb.toString());
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		unregisterReceiver(fusedLocationBroadcast);
	}

}
