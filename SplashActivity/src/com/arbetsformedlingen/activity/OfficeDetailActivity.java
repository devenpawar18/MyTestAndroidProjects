/**
 * 
 */
package com.arbetsformedlingen.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.ContactDetailEntity;
import com.arbetsformedlingen.entity.ContactPersonEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;


/**
 * @author aramesan
 * 
 */
public class OfficeDetailActivity extends GlobalDetailActivity {

	private String employmentOfficeDetailName;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String cityId = getIntent().getStringExtra("CityID");
		employmentOfficeDetailName = getIntent().getStringExtra("EmploymentOfficeName");
		
		GlobalBaseActivity.tracker.trackPageView("/Android/OfficeListViewed");
		
		AppController controller = new AppController(OfficeDetailActivity.this);
		controller.getContactDetail(GlobalBaseActivity.CONTACT_DETAIL_URL + cityId);
		
	}

	private String getContactHTMLContent() {
		StringBuilder contentBuffer = new StringBuilder();
		try {
			Resources myResources = getResources();
			InputStream instream = myResources.openRawResource(R.raw.officetemplate);

			if (instream != null) { // prepare the file for reading
				InputStreamReader inputreader = new InputStreamReader(instream);
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
		
		item = menu.findItem(R.id.contact);
		ContactDetailEntity tempEntity = (ContactDetailEntity)entity;
    	if(TextUtils.isEmpty(tempEntity.getTelNumber())){
				item.setEnabled(false);
		}else{
			item.setEnabled(true);
		}
    	
    	item = menu.findItem(R.id.mail);

    	if(TextUtils.isEmpty(tempEntity.getEmailAddress())){
			item.setEnabled(false);
		}else{
			item.setEnabled(true);
		}
    	
    	item = menu.findItem(R.id.geoPoint);
	    item.setEnabled(false);
		    if ((tempEntity.getAddress() != null && tempEntity.getAddress().length() > 3) &&  
	    		(tempEntity.getPlace() != null && tempEntity.getPlace().length() > 3)) {
		    	item.setEnabled(true);
		    }
	
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
	

	public void refreshCompleted(Object data) {
		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_employment_office), Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(data instanceof ContactDetailEntity)
		{
			entity = data;
			setContactDetailOnWebView((ContactDetailEntity)data);
			((ContactDetailEntity)entity).setOfficeName(employmentOfficeDetailName);
		}

	}

	private void setContactDetailOnWebView(ContactDetailEntity entity) {

		if(entity!=null){

			TextView titleTextView = (TextView) findViewById(R.id.heading);
			titleTextView.setText(employmentOfficeDetailName);

			String htmlContent = "";
			htmlContent = getContactHTMLContent();
			Log.d("OfficeDetailActivity :", htmlContent.toString());
			htmlContent = htmlContent.replace("$logo$","");
			htmlContent = htmlContent.replace("$title$", employmentOfficeDetailName==null?"":employmentOfficeDetailName);
		
			htmlContent = htmlContent.replace("$visitorAdressTitle$", " <b>"+ (String)getResources().getText(R.string.visiting_Address)+"</b><br>");
			htmlContent = htmlContent.replace("$address$", entity.getAddress()==null?"":entity.getAddress());
			htmlContent = htmlContent.replace("$city$", entity.getPlace()==null?"":entity.getPlace());
//			htmlContent = htmlContent.replace("$phoneNumber$", entity.getTelNumber()==null?"":entity.getTelNumber());
//			htmlContent = htmlContent.replace("$faxNumber$", entity.getFaxNumber()==null?"":entity.getFaxNumber());
			htmlContent = htmlContent.replace("$telephoneTitle$"," <b>"+ (String)getResources().getText(R.string.phone_Hours)+"</b> <br>");
			htmlContent = htmlContent.replace("$telephonehours$", entity.getTelID()==null?"":entity.getTelID());
			htmlContent = htmlContent.replace("$postAdressTitle$"," <b>"+  (String)getResources().getText(R.string.postal_Address)+"</b> <br>");
			htmlContent = htmlContent.replace("$postAddress$", entity.getPostalAddress()==null?"":entity.getPostalAddress());
			htmlContent = htmlContent.replace("$managerTitle$", " <b>"+ (String)getResources().getText(R.string.head_Of_Employment_Office)+"</b> <br>");
			htmlContent = htmlContent.replace("$manager$", entity.getCityManager()==null?"":entity.getCityManager());
			//htmlContent = htmlContent.replace("$emailAddress$", entity.getEmailAddress()==null?"":entity.getEmailAddress());
			htmlContent = htmlContent.replace("$selfServiceTitle$"," <b>"+ (String)getResources().getText(R.string.self_Service)+"</b> <br>");
			htmlContent = htmlContent.replace("$selfServiceHours$", entity.getSelfServiceTime()==null?"":entity.getSelfServiceTime());
			htmlContent = htmlContent.replace("$serviceTitle$", " <b>"+ (String)getResources().getText(R.string.personal_Service)+"</b> <br>");
			htmlContent = htmlContent.replace("$serviceHours$", entity.getPersonalServiceTime()==null?"":entity.getPersonalServiceTime());
			htmlContent = htmlContent.replace("$information$", entity.getExtraInformation()==null?"":entity.getExtraInformation());
			
			Log.d("htmlContent :", htmlContent.toString());
			webview.loadDataWithBaseURL("",htmlContent.toString(), "text/html", "utf-8", "");
		}
	}

}

