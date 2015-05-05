package com.ebay.rlsample;

import com.ebay.redlasersdk.BarcodeResult;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;

public class RLSample extends Activity {
	private boolean doUpce = true;
	private boolean doEan8 = false;
	private boolean doEan13 = false;
	private boolean doSticky = false;
	private boolean doQRCode = false;
	private boolean doCode128 = false;
	private boolean doCode39 = false;
	private boolean doCode93 = false;
	private boolean doDataMatrix = false;
	private boolean doRSS14 = false;
	private boolean doITF = false;
	private boolean doPDF417 = false;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("RedLaser Sample App");
		setContentView(R.layout.main);

		findViewById(R.id.btn_scan).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				launchScanner();
			}
		});

		final ToggleButton toggleUPC = (ToggleButton) findViewById(R.id.toggleUPC);
		toggleUPC.setChecked(doUpce);
		toggleUPC.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (toggleUPC.isChecked()) {
					doUpce = true;
					Toast.makeText(RLSample.this, "UPC On", Toast.LENGTH_SHORT).show();
				} else {
					doUpce = false;
					Toast.makeText(RLSample.this, "UPC Off", Toast.LENGTH_SHORT).show();
				}
			}
		});

		final ToggleButton toggleEAN = (ToggleButton) findViewById(R.id.toggleEAN);
		toggleEAN.setChecked(doEan8);
		toggleEAN.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (toggleEAN.isChecked()) {
					doEan8 = true;
					doEan13 = true;
					Toast.makeText(RLSample.this, "EAN On", Toast.LENGTH_SHORT).show();
				} else {
					doEan8 = false;
					doEan13 = false;
					Toast.makeText(RLSample.this, "EAN Off", Toast.LENGTH_SHORT).show();
				}
			}
		});

		final ToggleButton toggleQR = (ToggleButton) findViewById(R.id.toggleQR);
		toggleQR.setChecked(doQRCode);
		toggleQR.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (toggleQR.isChecked()) {
					doQRCode = true;
					doSticky = true;
					Toast.makeText(RLSample.this, "QR Code On", Toast.LENGTH_SHORT).show();
				} else {
					doQRCode = false;
					doSticky = false;
					Toast.makeText(RLSample.this, "QR Code Off", Toast.LENGTH_SHORT).show();
				}
			}
		});

		final ToggleButton toggle128 = (ToggleButton) findViewById(R.id.toggle128);
		toggle128.setChecked(doCode128);
		toggle128.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (toggle128.isChecked()) {
					doCode128 = true;
					Toast.makeText(RLSample.this, "Code 128 On", Toast.LENGTH_SHORT).show();
				} else {
					doCode128 = false;
					Toast.makeText(RLSample.this, "Code 128 Off", Toast.LENGTH_SHORT).show();
				}
			}
		});

		final ToggleButton toggle39 = (ToggleButton) findViewById(R.id.toggle39);
		toggle39.setChecked(doCode39);
		toggle39.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (toggle39.isChecked()) {
					doCode39 = true;
					Toast.makeText(RLSample.this, "Code 39 On", Toast.LENGTH_SHORT).show();
				} else {
					doCode39 = false;
					Toast.makeText(RLSample.this, "Code 39 Off", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// we came from a scanning activity
		// need to do something with it
		if (resultCode == RESULT_OK) {
			String barcode = data.getAction();
			String barcodeType = data.getStringExtra(BarcodeResult.BARCODE_TYPE);
			Log.d("RLSample", "BARCODE: " + barcode);

			new AlertDialog.Builder(this).setTitle("Scan Result").setMessage(barcodeType + ": " + barcode).setNegativeButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// ignore, just dismiss
				}
			}).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scan:
			launchScanner();
			return true;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void launchScanner() {
		try {
			Bundle bundle = buildBundle();
			Intent scanIntent = new Intent(RLSample.this, RedLaserSDK.class);
			scanIntent.putExtras(bundle);
			startActivityForResult(scanIntent, 1);
		} catch (Exception e) {
			Log.d("RLSample", e.getLocalizedMessage() + " " + e.getCause());
		}
	}

	public Bundle buildBundle() {
		Bundle bundle = new Bundle();
		bundle.putBoolean(RedLaserSDK.DO_UPCE, doUpce);
		bundle.putBoolean(RedLaserSDK.DO_EAN8, doEan8);
		bundle.putBoolean(RedLaserSDK.DO_EAN13, doEan13);
		bundle.putBoolean(RedLaserSDK.DO_STICKY, doSticky);
		bundle.putBoolean(RedLaserSDK.DO_QRCODE, doQRCode);
		bundle.putBoolean(RedLaserSDK.DO_CODE128, doCode128);
		bundle.putBoolean(RedLaserSDK.DO_CODE39, doCode39);
		bundle.putBoolean(RedLaserSDK.DO_CODE93, doCode93);
		bundle.putBoolean(RedLaserSDK.DO_DATAMATRIX, doDataMatrix);
		bundle.putBoolean(RedLaserSDK.DO_RSS14, doRSS14);
		bundle.putBoolean(RedLaserSDK.DO_ITF, doITF);
		bundle.putBoolean(RedLaserSDK.DO_PDF417, doPDF417);

		return bundle;
	}
}