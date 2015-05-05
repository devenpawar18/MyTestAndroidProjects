/**
 * 
 */
package com.arbetsformedlingen.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


/**
 * @author aramesan
 * 
 */
public class InfoDetailActivity extends GlobalDetailActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView titleTextView = (TextView) findViewById(R.id.heading);
		if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
			titleTextView.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		titleTextView.setText(R.string.platsbanken);
		String content =getInfoHTMLContent();
		
		content = content.replace("$infoText$", getInfoTextContent());
		content=content.replace("$u25CF$", "\u25cf");
		webview.loadDataWithBaseURL("",content, "text/html", "UTF-8", "");
		
	}

	private String getInfoHTMLContent() {
		StringBuilder contentBuffer = new StringBuilder();
		try {
			Resources myResources = getResources();
			InputStream instream = myResources.openRawResource(R.raw.infotemplate);

			if (instream != null) { // prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream,"utf-8");
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line; // read every line of the file into the
				// line-variable, on line at the time
				while ((line = buffreader.readLine())!=null) {
					contentBuffer.append(line);
					Log.d("line", line);
				}
			}
			instream.close();
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuffer.toString();
	}

	private String getInfoTextContent() {
		StringBuilder contentBuffer = new StringBuilder();
		try {
			Resources myResources = getResources();
			InputStream instream;
			String locale =myResources.getConfiguration().locale.getLanguage();
			Log.d("Locale", locale);
			if(locale.equals("es")){
				instream = myResources.openRawResource(R.raw.spanish);
			}else if(locale.equals("fr"))
				instream = myResources.openRawResource(R.raw.french);
			else if(locale.equals("ru")) {
				instream = myResources.openRawResource(R.raw.russian);
				if (instream != null) { // prepare the file for reading
					InputStreamReader inputreader = new InputStreamReader(instream, "UTF-8");
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line; // read every line of the file into the
					// line-variable, on line at the time
					while ((line = buffreader.readLine())!=null) {
						contentBuffer.append(line);
						Log.d("line", line);
					}
				}
				instream.close();
				return contentBuffer.toString();
			} else if(locale.equals("sv"))
				instream = myResources.openRawResource(R.raw.swedish);
			else if (locale.equals("ar")) {
				instream = myResources.openRawResource(R.raw.arabic);
				if (instream != null) { // prepare the file for reading
					InputStreamReader inputreader = new InputStreamReader(instream, "UTF-8");
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line; // read every line of the file into the
					// line-variable, on line at the time
					while ((line = buffreader.readLine())!=null) {
						contentBuffer.append(line);
						Log.d("line", line);
					}
				}
				instream.close();
				return contentBuffer.toString();
			}
			else 
				instream = myResources.openRawResource(R.raw.english);

			if (instream != null) { // prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream, "ISO-8859-1");
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line; // read every line of the file into the
				// line-variable, on line at the time
				while ((line = buffreader.readLine())!=null) {
					contentBuffer.append(line);
					Log.d("line", line);
				}
			}
			instream.close();
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuffer.toString();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem item=null;
		 item = menu.findItem(R.id.geoPoint);
	     item.setVisible(false);
		 item = menu.findItem(R.id.contact);
	     item.setVisible(false);
		 item = menu.findItem(R.id.mail);
	     item.setVisible(false);
		 item = menu.findItem(R.id.addfavorite);
	     item.setVisible(false);
	     item = menu.findItem(R.id.share);
	     item.setVisible(false);
	     item = menu.findItem(R.id.browser);
	     item.setVisible(false);
	     item = menu.findItem(R.id.delete);
	     item.setVisible(false);
	     
		return true;
	}

	@Override
	public void refreshCompleted(Object data) {
		// TODO Auto-generated method stub
		
	}
	
}

