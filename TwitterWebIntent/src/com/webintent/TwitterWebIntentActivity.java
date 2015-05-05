package com.webintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;

public class TwitterWebIntentActivity extends Activity
{
	private WebView webView;

	// private String url = "http://twitter.com/intent/tweet?text=hello world";

	private String url = "http://www.developer.android.com";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Intent shareIntent = new Intent(Intent.ACTION_SEND);
		// // shareIntent.setType("plain/text");
		// shareIntent.setType("application/twitter");
		// shareIntent.putExtra(Intent.EXTRA_TEXT, "testing");
		// startActivity(Intent.createChooser(shareIntent, "Share using"));

		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_TEXT, url);
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Message"); 
		startActivity(Intent.createChooser(sharingIntent, "Share using"));

		// webView = (WebView) findViewById(R.id.WebView);
		// webView.setBackgroundColor(0);
		// webView.loadUrl(url);
	}
}