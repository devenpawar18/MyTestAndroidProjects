package com.broaddemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.broaddemo.entities.App;
import com.broaddemo.network.ApplicationEx;
import com.broaddemo.services.GetAppsService;
import com.broaddemo.services.GetAppsService.GetAppsServiceListener;

public class BroadcastDemoActivity extends Activity implements GetAppsServiceListener
{
	private TextView info;
	private ProgressDialog pd;
	private Button btn;
	public static final String FILTER_STRING = "com.data";
	private MyBroadcast r;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		IntentFilter filter = new IntentFilter(FILTER_STRING);
		r = new MyBroadcast();
		registerReceiver(r, filter);

		info = (TextView) findViewById(R.id.info);
		btn = (Button) findViewById(R.id.button);
		pd = ProgressDialog.show(this, "", "Loading...");
		btn.setText("Detail");
		btn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(BroadcastDemoActivity.this, DetailActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

		getApps();
	}

	public void getApps()
	{
		GetAppsService service = new GetAppsService();
		service.setListener(this);
		ApplicationEx.operationsQueue.execute(service);
	}

	@Override
	public void onGetAppsFinished(App apps)
	{
		if (pd.isShowing() && pd != null)
		{
			pd.cancel();
		}
		info.setText(apps.getInfo());

	}

	@Override
	public void onGetAppsFailed(String error)
	{
		if (pd.isShowing() && pd != null)
		{
			pd.cancel();
		}
		Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
		info.setText("No Data");

	}

	public class MyBroadcast extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{
//			String str = intent.getStringExtra("key");
			getApps();
			// abortBroadcast();
		}
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(r);
	}
}