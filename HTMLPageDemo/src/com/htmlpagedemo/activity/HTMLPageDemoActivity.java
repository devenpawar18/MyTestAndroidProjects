package com.htmlpagedemo.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.htmlpagedemo.HtmlPageDemoApplication;
import com.htmlpagedemo.entity.ProductDescription;
import com.htmlpagedemo.service.HTMLPageService;
import com.htmlpagedemo.service.HTMLPageService.GetHtmlPageServiceListener;

public class HTMLPageDemoActivity extends Activity implements
		GetHtmlPageServiceListener {
	private ProgressDialog pd;
	ArrayList<String> list;
	private WebView webview;
	StringBuilder strbldr;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pd = ProgressDialog.show(this, "", "Loading...");
		webview = (WebView) findViewById(R.id.contentWebView);
		getHtmlPageDetails();
	}

	public void getHtmlPageDetails() {
		HTMLPageService service = new HTMLPageService();
		service.setListener(this);
		HtmlPageDemoApplication.operationsQueue.execute(service);
	}

	@Override
	public void onGetHtmlPageFailed(String error) {
		if (pd != null && pd.isShowing())
			pd.cancel();
		Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onGetHtmlPageFinished(ProductDescription prodDescription) {
		if (pd != null && pd.isShowing())
			pd.cancel();
		setJobDetailOnWebView(prodDescription);

	}

	private String getJobHTMLContent() {
		StringBuilder contentBuffer = new StringBuilder();
		try {
			Resources myResources = getResources();
			InputStream instream = myResources
					.openRawResource(R.raw.detailtemplate);

			if (instream != null) { // prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream);
				BufferedReader buffreader = new BufferedReader(inputreader);
				String line; // read every line of the file into the
				// line-variable, on line at the time
				while ((line = buffreader.readLine()) != null) {
					contentBuffer.append(line);
					// Log.d("line", line);
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

	private void setJobDetailOnWebView(ProductDescription entity) {

		if (entity != null) {
			String htmlContent = "";
			htmlContent = getJobHTMLContent();
			Log.d("htmlContent :", getJobHTMLContent().toString());
			htmlContent = htmlContent.replace(
					"$description$",
					TextUtils.isEmpty(entity.getDescription()) ? "" : entity
							.getDescription() + "</br></br>");

			list = new ArrayList<String>();

			list = ProductDescription.bullets.get("function");
			htmlContent = htmlContent.replace("$functionTitle$", getResources()
					.getText(R.string.function));
			strbldr = getBullets();
			htmlContent = htmlContent.replace("$function$",
					TextUtils.isEmpty(strbldr) ? "" : strbldr);

			list = ProductDescription.bullets.get("materials");
			htmlContent = htmlContent.replace("$materialsTitle$",
					getResources().getText(R.string.materials));
			strbldr = getBullets();
			htmlContent = htmlContent.replace("$materials$",
					TextUtils.isEmpty(strbldr) ? "" : strbldr);

			list = ProductDescription.bullets.get("measurements");
			htmlContent = htmlContent.replace("$measurementsTitle$",
					getResources().getText(R.string.measurements));
			strbldr = getBullets();
			htmlContent = htmlContent.replace("$measurements$",
					TextUtils.isEmpty(strbldr) ? "" : strbldr);

			/*
			 * list = ProductDescription.bullets.get("item details");
			 * htmlContent = htmlContent.replace("$itemDetailsTitle$",
			 * getResources().getText(R.string.item_details)); strbldr =
			 * getBullets(); htmlContent = htmlContent.replace("$itemDetails$",
			 * TextUtils.isEmpty(strbldr) ? "" : strbldr);
			 */

			list = ProductDescription.bullets.get("country of origin");
			htmlContent = htmlContent.replace("$countryOfOriginTitle$",
					getResources().getText(R.string.country_of_origin));
			strbldr = getBullets();
			htmlContent = htmlContent.replace("$countryOfOrigin$",
					TextUtils.isEmpty(strbldr) ? "" : strbldr);

			list = ProductDescription.bullets.get("item number");
			htmlContent = htmlContent.replace("$itemNumberTitle$",
					getResources().getText(R.string.item_number));
			strbldr = getBullets();
			htmlContent = htmlContent.replace("$itemNumber$",
					TextUtils.isEmpty(strbldr) ? "" : strbldr);

			list = ProductDescription.bullets.get("shipping");
			htmlContent = htmlContent.replace("$shippingTitle$", getResources()
					.getText(R.string.shipping));
			strbldr = getBullets();
			htmlContent = htmlContent.replace("$shipping$",
					TextUtils.isEmpty(strbldr) ? "" : strbldr);

			htmlContent = htmlContent.replace("$prodAbout$",
					TextUtils.isEmpty(entity.getProductAbout()) ? "" : "</br>"
							+ entity.getProductAbout());

			Log.d("htmlContent :", htmlContent.toString());
			webview.loadDataWithBaseURL("", htmlContent.toString(),
					"text/html", "utf-8", "");
		}
	}

	public StringBuilder getBullets() {
		strbldr = new StringBuilder();
		System.out.println("list " + list);
		Iterator<String> itr = list.iterator();
		while (itr.hasNext()) {
			String str = itr.next();

			strbldr.append(str + "</br>");
		}
		return strbldr;

	}
}