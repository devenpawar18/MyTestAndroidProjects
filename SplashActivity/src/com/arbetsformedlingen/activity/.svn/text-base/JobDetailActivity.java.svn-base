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
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.ContactPersonEntity;
import com.arbetsformedlingen.entity.JobDetailEntity;

/**
 * @author aramesan
 * 
 */
public class JobDetailActivity extends GlobalDetailActivity {

	private boolean isFromFav;
	TextView titleTextView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GlobalBaseActivity.tracker.trackPageView("/Android/JobViewed");

		String jobId = getIntent().getStringExtra("JobID");
		isFromFav = getIntent().getBooleanExtra("IsFavroite", false);
		titleTextView = (TextView) findViewById(R.id.heading);
		Cursor tempCursor = managedQuery(JobDetailEntity.JOBDETAIL_CONTENT_URI, null, JobDetailEntity.ID_COLUMN + "='" + jobId + "'", null, null);

		if (tempCursor != null && tempCursor.getCount() > 0) {
			isFavroite = true;
		}
		else {
			isFavroite = false;
		}
		AppController controller = new AppController(JobDetailActivity.this);
		controller.getJobDetail(GlobalBaseActivity.JOB_DETAIL_URL + jobId);
	}

	private String getJobHTMLContent() {
		StringBuilder contentBuffer = new StringBuilder();
		try {
			Resources myResources = getResources();
			InputStream instream = myResources.openRawResource(R.raw.jobtemplate);

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

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		MenuItem item = null;
		item = menu.findItem(R.id.addfavorite);
		item.setEnabled(!isFavroite);
		item = menu.findItem(R.id.delete);
		item.setVisible(false);
		if (entity instanceof JobDetailEntity) {
			JobDetailEntity tempEntity = (JobDetailEntity) entity;
			item = menu.findItem(R.id.mail);
			if (tempEntity != null && TextUtils.isEmpty(tempEntity.getEmail())) {
				item.setEnabled(false);
			}
			item = menu.findItem(R.id.contact);
			if (tempEntity.contactPersonsList != null && tempEntity.contactPersonsList.size() > 0) {
				ArrayList<String> numbers = new ArrayList<String>();
				for (ContactPersonEntity person : tempEntity.contactPersonsList) {
					if (!TextUtils.isEmpty(person.getPhoneNumber()))
						numbers.add(person.getName() + "," + person.getPhoneNumber());
				}
				if (numbers.size() == 0) {
					item.setEnabled(false);
				}
			}
			else {
				item.setEnabled(false);
			}

			item = menu.findItem(R.id.geoPoint);
			item.setEnabled(false);
			if ((tempEntity.getVisitorAddress() != null && tempEntity.getVisitorAddress().length() > 3) && (tempEntity.getVisitorTown() != null && tempEntity.getVisitorTown().length() > 3)) {
				item.setEnabled(true);
			}
		}

		return true;
	}

	public void refreshCompleted(Object data) {

		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_job_info), Toast.LENGTH_SHORT).show();
			return;
		}

		if (data instanceof JobDetailEntity) {
			entity = data;
			titleTextView.setText(((JobDetailEntity) entity).getTitle());
			setJobDetailOnWebView((JobDetailEntity) data);
		}
		else {
			setTranlatedTextOnWebView((ArrayList<String>) data);
		}

	}

	private void setTranlatedTextOnWebView(ArrayList<String> transValues) {

		if (entity instanceof JobDetailEntity) {
			if (transValues != null && transValues.size() >= 4) {
				mLocale = GlobalDetailActivity.mTmpLocale;

				JobDetailEntity jobEntity = null;
				try {
					jobEntity = (JobDetailEntity) (((JobDetailEntity) entity).clone());
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				jobEntity.setTitle(transValues.get(0) != null ? transValues.get(0) : "");
				jobEntity.setJobText(transValues.get(1) != null ? transValues.get(1).replaceAll("och", "&") : "'");
				jobEntity.setWorkDuration(transValues.get(2) != null ? transValues.get(2) : "");
				jobEntity.setMisc(transValues.get(3) != null ? transValues.get(3) : "");
				jobEntity.setPayroll(transValues.get(4) != null ? transValues.get(4) : "");
				setJobDetailOnWebView(jobEntity);
			}
			else {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_translate), 500).show();
			}
		}
	}

	private void setJobDetailOnWebView(JobDetailEntity entity) {

		if (entity != null) {
			String htmlContent = "";
			htmlContent = getJobHTMLContent();
			Log.d("htmlContent :", getJobHTMLContent().toString());
			htmlContent = htmlContent.replace("$logo$", TextUtils.isEmpty(entity.getEmpLogo()) ? "" : "<img src='http://" + entity.getEmpLogo() + "' align='right' width='150' height='150'>");
			htmlContent = htmlContent.replace("$title$", TextUtils.isEmpty(entity.getTitle()) ? "" : entity.getTitle());
			htmlContent = htmlContent.replace("$employer$", TextUtils.isEmpty(entity.getEmployer()) ? "SAKNAS" : entity.getEmployer());
			htmlContent = htmlContent.replace("$publicationDate$", TextUtils.isEmpty(entity.getPublicationDate()) ? "" : "<span class='publicerad'>"+ (String) getResources().getText(R.string.published) + entity.getPublicationDate().substring(0, entity.getPublicationDate().indexOf("T"))+"</span>");
			htmlContent = htmlContent.replace("$jobName$", TextUtils.isEmpty(entity.getJobName()) ? "" : "<br><b><span class='label'>" + (String) getResources().getText(R.string.occupation) + ": " + entity.getJobName() + "</b></span>, ");

			if (!TextUtils.isEmpty(entity.getSpots())) {
				try {
					String spots = entity.getSpots();
					int t_spots = Integer.parseInt(spots);
					if (t_spots > 1) {
						htmlContent = htmlContent.replace("$spots$", "<span class='label'>"+spots + " " + (String) getResources().getText(R.string.vacancies)+"</span>");
					}
					else {
						htmlContent = htmlContent.replace("$spots$","<span class='label'>"+ spots + " " + (String) getResources().getText(R.string.vacancy)+"</span>");
					}
				} catch (NumberFormatException nfe) {
					htmlContent = htmlContent.replace("$spots$", "");
				}

			}
			else {
				htmlContent = htmlContent.replace("$spots$", "");
			}

			htmlContent = htmlContent.replace("$workLocation$", TextUtils.isEmpty(entity.getDistrict()) ? "" : "<br><span class='label'><b>" + (String) getResources().getText(R.string.work_Location) + "</b> " + entity.getDistrict()+"</span>");

			String description = TextUtils.isEmpty(entity.getJobText()) ? "" : "<br><br>" + entity.getJobText();
			description = description.replaceAll("/n", "<br>");
			description = description.replaceAll("/r", "<br>");

			htmlContent = htmlContent.replace("$jobText$", description);

			String license = "";
			if (entity.driverLicensesList != null && entity.driverLicensesList.size() > 0) {
				for (int i = 0; i < entity.driverLicensesList.size(); i++) {
					Log.d("JobDetailActivity", "setJobDetailOnWebView :: " + "entity.driverLicensesList.get(i) :: " + entity.driverLicensesList.get(i));
					if (i < entity.driverLicensesList.size() - 1) {
						license += (entity.driverLicensesList.get(i) == null ? "" : entity.driverLicensesList.get(i) + ", ");
					}
					else {
						license += (entity.driverLicensesList.get(i) == null ? "" : entity.driverLicensesList.get(i));
					}
				}
			}

			htmlContent = htmlContent.replace("$drivingLicense$", license == "" ? "" : "<br><br><b>" + (String) getResources().getText(R.string.driving_License) + "</b> " + license);
			htmlContent = htmlContent.replace("$car$", TextUtils.isEmpty(entity.getCar()) ? " " : "<br><span class='label'>" + (String) getResources().getText(R.string.access_To_Own_Car) + "</span> ");

			htmlContent = htmlContent.replace("$workDuration$", TextUtils.isEmpty(entity.getWorkDuration()) ? "" : "<br><br><b>" + (String) getResources().getText(R.string.duration_Or_WorkingHours) + "</b><br> " + entity.getWorkDuration());
			htmlContent = htmlContent.replace("$payroll$", TextUtils.isEmpty(entity.getPayroll()) ? "" : "<br><br><b>" + (String) getResources().getText(R.string.salary_Or_Wages) + "</b><br> " + entity.getPayroll());
			htmlContent = htmlContent.replace("$access$", TextUtils.isEmpty(entity.getAccess()) ? "" : "<br><br><b>" + (String) getResources().getText(R.string.starting_Date) + "</b> " + entity.getAccess());
			htmlContent = htmlContent.replace("$applicationTitle$", "<br><br><b>" + (String) getResources().getText(R.string.application) + "</b> ");
			htmlContent = htmlContent.replace("$deadlineDate$", TextUtils.isEmpty(entity.getDeadlineDate()) ? "" : "<br>" + (String) getResources().getText(R.string.application_Deadline) + " " + entity.getDeadlineDate().substring(0, entity.getDeadlineDate().indexOf("T")));

			htmlContent = htmlContent.replace("$reference$", TextUtils.isEmpty(entity.getReference()) ? "" : "<br><b>" + (String) getResources().getText(R.string.indicate_Reference) + "</b> " + entity.getReference());

			htmlContent = htmlContent.replace("$applicationEmail$", TextUtils.isEmpty(entity.getEmail()) ? "<br>" + (String) getResources().getText(R.string.we_do_not_accept_application_via_email) : "<br>" + (String) getResources().getText(R.string.we_Welcome_Application_Via_Email) + " " + "<a href='mailto:" + entity.getEmail() + "'>" + entity.getEmail() + "</a> <br>");

			htmlContent = htmlContent.replace("$misc$", TextUtils.isEmpty(entity.getMisc()) ? "" : "<br>" + entity.getMisc()+"<br>");

			htmlContent = htmlContent.replace("$postAdressTitle$", "</br><b>" + (String) getResources().getText(R.string.postal_Address) + "</b><br>");
			htmlContent = htmlContent.replace("$employer$", TextUtils.isEmpty(entity.getEmployer()) ? "" : "" + entity.getEmployer());
			htmlContent = htmlContent.replace("$postAddress$", TextUtils.isEmpty(entity.getPostAddress()) ? "" : "<br>" + entity.getPostAddress());
			htmlContent = htmlContent.replace("$postCode$", TextUtils.isEmpty(entity.getPostCode()) ? "" : "<br>" + entity.getPostCode());
			htmlContent = htmlContent.replace("$postDistrict$", TextUtils.isEmpty(entity.getPostDistrict()) ? "" : "<br>" + entity.getPostDistrict());
			htmlContent = htmlContent.replace("$postCountry$", TextUtils.isEmpty(entity.getPostCountry()) ? "" : "<br>" + entity.getPostCountry());
			htmlContent = htmlContent.replace("$visitorAdressTitle$", "<br><br><b>" + (String) getResources().getText(R.string.visiting_Address) + "</b> ");
			htmlContent = htmlContent.replace("$visitorAddress$", TextUtils.isEmpty(entity.getVisitorAddress()) ? "" : "<br>" + entity.getVisitorAddress() + "<br>");
			htmlContent = htmlContent.replace("$visitorTown$", TextUtils.isEmpty(entity.getVisitorTown()) ? "" : "<br>" + entity.getVisitorTown() + "<br>");
			htmlContent = htmlContent.replace("$empPhone$", TextUtils.isEmpty(entity.getTelephoneNumber()) ? "" : "<br><b>" + (String) getResources().getText(R.string.phone) + "</b> " + "<a href='tel:" + entity.getTelephoneNumber() + "'>" + entity.getTelephoneNumber() + "</a><br>");

			htmlContent = htmlContent.replace("$empWebSite$", TextUtils.isEmpty(entity.getEmpWebSite()) ? "" : "<br><b>" + (String) getResources().getText(R.string.web) + "</b> " + "<a href='" + entity.getEmpWebSite() + "'>" + entity.getEmpWebSite() + "</a></br>");
			htmlContent = htmlContent.replace("$empEmail$", TextUtils.isEmpty(entity.getEmpEmail()) ? "" : "<br><b>" + (String) getResources().getText(R.string.email) + "</b> " + "<a href='mailto:" + entity.getEmpEmail() + "'>" + entity.getEmpEmail() + "</a></br>");

			htmlContent = htmlContent.replace("$orgNumber$", TextUtils.isEmpty(entity.getOrgNumber()) ? "" : "<br><b>" + (String) getResources().getText(R.string.corporate_Registration_Number) + "</b> " + "<a href='tel:" + entity.getOrgNumber() + "'>" + entity.getOrgNumber() + "</a><br>");

			htmlContent = htmlContent.replace("$jobId$", TextUtils.isEmpty(entity.getJobId()) ? "" : "<br><b>" + (String) getResources().getText(R.string.ad_Id) + "</b> " + entity.getJobId().trim());

			String contactPersons = "";
			if (entity.contactPersonsList != null && entity.contactPersonsList.size() > 0) {
				for (int i = 0; i < entity.contactPersonsList.size(); i++) {
					ContactPersonEntity contact = entity.contactPersonsList.get(i);
					contactPersons += "<span class='label'>"+(String) getResources().getText(R.string.name)+"</span>" + " " + contact.getName() + " <br>";
					contactPersons += "<span class='label'>"+(String) getResources().getText(R.string.title) +"</span>"+ " " + contact.getTitle() + " <br>";
					contactPersons += "<span class='label'>"+(String) getResources().getText(R.string.phone_Number) +"</span>"+ " " + "<a href='tel:" + contact.getPhoneNumber() + "'>" + contact.getPhoneNumber() + "</a><br>";
					contactPersons += "<span class='label'>"+(String) getResources().getText(R.string.mobile_Number)+"</span>" + " " + "<a href='tel:" + contact.getMobileNumber() + "'>" + contact.getMobileNumber() + "</a><br>";
					contactPersons += "<br>";
				}
			}
			htmlContent = htmlContent.replace("$contactPersons$", TextUtils.isEmpty(contactPersons) ? "" : "<br><br><b>" + (String) getResources().getText(R.string.contact_Persons) + "</b> </br> " + contactPersons);

			Log.d("htmlContent :", htmlContent.toString());
			webview.loadDataWithBaseURL("", htmlContent.toString(), "text/html", "utf-8", "");
		}
	}
}
