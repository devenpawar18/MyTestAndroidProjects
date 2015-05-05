package com.ics.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ics.ICSApplication;
import com.ics.entity.Contact;
import com.ics.services.ICSSampleService;
import com.ics.services.ICSSampleService.GetAppsServiceListener;

public class ICSActivity extends Activity implements GetAppsServiceListener {
	private TextView email;
	private TextView catalogEmail;
	private TextView phone;
	private TextView messageEmail;
	private TextView address;
	private TextView postAddress;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		email = (TextView) findViewById(R.id.email);
		catalogEmail = (TextView) findViewById(R.id.catalogEmail);
		phone = (TextView) findViewById(R.id.phone);
		messageEmail = (TextView) findViewById(R.id.messageEmail);
		address = (TextView) findViewById(R.id.address);
		postAddress = (TextView) findViewById(R.id.postAddress);
		getApps();

	}

	public void getApps() {
		ICSSampleService service = new ICSSampleService();
		service.setListener(this);
		ICSApplication.operationsQueue.execute(service);
	}

	@Override
	public void onGetAppsFinished(Contact apps) {
		email.setText(apps.getEmail());
		catalogEmail.setText(apps.getCatalogEmail());
		phone.setText(apps.getPhone());
		messageEmail.setText(apps.getMessageEmail());
		address.setText(apps.getAddress());
		postAddress.setText(apps.getPostAddress());

	}

	@Override
	public void onGetAppsFailed(String error) {
		// TODO Auto-generated method stub

	}
}