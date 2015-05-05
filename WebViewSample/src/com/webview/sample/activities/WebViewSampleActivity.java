package com.webview.sample.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.webview.sample.data.Response;

public class WebViewSampleActivity extends Activity {
	private WebView webview;
	ProgressDialog pd;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview_main);
		String url = getIntent().getStringExtra("url");
		webview = (WebView) findViewById(R.id.contentWebView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(url);
		webview.setWebViewClient(new AdvancedWebClient());

	}

	/**
	 * This code will work after webview is loaded for the first time. After
	 * that the back key functionality will be handled properly by this code.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			Log.d("tag1", "onkeydown");
			webview.goBack();
			pd = ProgressDialog.show(WebViewSampleActivity.this, "",
					"Loading...");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class AdvancedWebClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, final String url) {
			super.onPageFinished(view, url);
			if (pd != null && pd.isShowing())
				pd.cancel();

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);

		}

		public boolean shouldOverrideUrlLoading(final WebView view,
				final String url) {
			int indexOfColon = url.indexOf(":");
			if (indexOfColon <= 0)
				return false;

			String commandString = url.substring(0, indexOfColon);
			/**
			 * for sending Emails.
			 */
			if (commandString.equals("mailto")) {

				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/html");
				commandString = url.replace(commandString + ":", "");
				i.putExtra(Intent.EXTRA_EMAIL, new String[] { commandString });
				startActivity(Intent.createChooser(i,
						"Select email application"));
				return false;

			}

			/**
			 * for phonecall.
			 */
			if (commandString.equals("tel")) {
				Intent callIntent = new Intent();
				callIntent.setAction(Intent.ACTION_DIAL);
				String telephoneNumber = url.replace("tel://", "");
				callIntent.setData(Uri.parse("tel:"
						+ Uri.encode(telephoneNumber)));
				startActivity(callIntent);
				return false;

			}

			/**
			 * for opening the links.
			 */
			if (commandString.equals("http")) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						WebViewSampleActivity.this);

				// dialog.setTitle();
				dialog.setMessage("Do you want to open the Link in Browser ?");

				dialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								/**
								 * for opening the links in browser.
								 */
								Intent myIntent = new Intent(
										Intent.ACTION_VIEW, Uri.parse(url));
								startActivity(myIntent);
							}
						});
				dialog.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								/**
								 * for opening the links in the webview itself.
								 * 
								 */
								pd = ProgressDialog.show(
										WebViewSampleActivity.this, "",
										"Loading...");
								view.loadUrl(url);
								dialog.cancel();

							}
						});
				dialog.show();

			}
			// view.loadUrl(url);
			return true;
		}
	}

}