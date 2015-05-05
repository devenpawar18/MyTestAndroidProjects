package com.arbetsformedlingen.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class JobDetailEntity implements Cloneable {
	public static final String JobDetailEntitySingle = "jobdetail";
	public static final String JOBDETAIL_TABLENAME = "jobdetail";
	public static final String JobDetailTypeName = "vnd.android.cursor.item/vnd.com.arbetsformedlingen.controller.AppContentProvider";
	public static final Uri JOBDETAIL_CONTENT_URI = Uri.parse("content://com.arbetsformedlingen.controller.AppContentProvider/jobdetail");

	public static final String ID_COLUMN = "_id";
	public static final String TITLE_COLUMN = "title";
	public static final String DESC_COLUMN = "jobText";
	public static final String JOBNAME_COLUMN = "jobName";
	public static final String RELEVANCY_COLUMN = "relevancy";
	public static final String WORKID_COLUMN = "workId";
	public static final String DATE_COLUMN = "publicationDate";
	public static final String SPOTS_COLUMN = "spots";
	public static final String DISTRICT_COLUMN = "district";
	public static final String DURATION_COLUMN = "duration";
	public static final String WORKHOURS_COLUMN = "workHours";
	public static final String WORKDURATION_COLUMN = "workDuration";
	public static final String ACCESS_COLUMN = "access";
	public static final String PAYROLL_COLUMN = "payroll";
	public static final String REFERENCE_COLUMN = "reference";
	public static final String WEBADDRESS_COLUMN = "webbAddress";
	public static final String EMAIL_COLUMN = "email";
	public static final String DEADLINE_COLUMN = "deadlineDate";
	public static final String MISC_COLUMN = "misc";
	public static final String EMPLOYER_COLUMN = "employer";
	public static final String ORGNUMBER_COLUMN = "orgNumber";
	public static final String POSTCODE_COLUMN = "postCode";
	public static final String POSTDISTRICT_COLUMN = "postDistrict";
	public static final String POSTCOUNTRY_COLUMN = "postCountry";
	public static final String VISITORADDRESS_COLUMN = "visitorAddress";
	public static final String VISITORTOWN_COLUMN = "visitorTown";
	public static final String TELPHONE_COLUMN = "telephoneNumber";
	public static final String FAX_COLUMN = "faxNumber";
	public static final String EMPEMAIL_COLUMN = "empEmail";
	public static final String EMPWEBSITE_COLUMN = "empWebSite";
	public static final String EMPLOGO_COLUMN = "empLogo";
	public static final String CONTACTPERSON_COLUMN = "contactPersons";
	public static final String LICENSE_COLUMN = "driverLicenses";
	public static final String CAR_COLUMN = "car";
	private static final String POSTADDRESS_COLUMN = "postAddress";

	public static final String CREATE_TABLE_JOBDETAIL = "create table " + JOBDETAIL_TABLENAME + " ("
	+ ID_COLUMN + " text , " + TITLE_COLUMN  + " text null, " + DESC_COLUMN  + " text null, "
	+ JOBNAME_COLUMN  + " text null, " + RELEVANCY_COLUMN + " text null, " 
	+ WORKID_COLUMN  + " text null, " + DATE_COLUMN  + " text null, " 
	+ SPOTS_COLUMN  + " text null, " + DISTRICT_COLUMN  + " text null, "
	+ DURATION_COLUMN  + " text null, "
	+ WORKHOURS_COLUMN  + " text null, " + WORKDURATION_COLUMN  + " text null, "
	+ ACCESS_COLUMN  + " text null, " + PAYROLL_COLUMN  + " text null, "
	+ REFERENCE_COLUMN  + " text null, " + WEBADDRESS_COLUMN  + " text null, "
	+ EMAIL_COLUMN  + " text null, " + DEADLINE_COLUMN  + " text null, "
	+ MISC_COLUMN  + " text null, " + EMPLOYER_COLUMN  + " text null, "
	+ ORGNUMBER_COLUMN  + " text null, " + POSTCODE_COLUMN  + " text null, "
	+ POSTADDRESS_COLUMN  + " text null, "
	+ POSTDISTRICT_COLUMN  + " text null, " + POSTCOUNTRY_COLUMN  + " text null, "
	+ VISITORADDRESS_COLUMN  + " text null, " + VISITORTOWN_COLUMN  + " text null, "
	+ TELPHONE_COLUMN  + " text null, " + FAX_COLUMN  + " text null, "
	+ EMPEMAIL_COLUMN  + " text null, " + EMPWEBSITE_COLUMN  + " text null, "
	+ EMPLOGO_COLUMN  + " text null, " + CONTACTPERSON_COLUMN  + " text null, "
	+ LICENSE_COLUMN  + " text null, " + CAR_COLUMN  + " text null);";



	private String jobId, title, jobText, jobName, relevancy, workId,
	publicationDate, spots, district, duration, workHours, workDuration,
	access, payroll, reference, webbAddress, email, deadlineDate, misc, employer,
	orgNumber, postCode, postAddress, postDistrict, postCountry, visitorAddress,
	visitorTown, telephoneNumber, faxNumber, empEmail, empWebSite, empLogo,
	contactPersons, driverLicenses, car;

	public ArrayList<ContactPersonEntity> contactPersonsList;
	public ArrayList<String> driverLicensesList;

	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobText() {
		return jobText;
	}
	public void setJobText(String jobText) {
		this.jobText = jobText;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getRelevancy() {
		return relevancy;
	}
	public void setRelevancy(String relevancy) {
		this.relevancy = relevancy;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getSpots() {
		return spots;
	}
	public void setSpots(String spots) {
		this.spots = spots;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getWorkHours() {
		return workHours;
	}
	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}
	public String getWorkDuration() {
		return workDuration;
	}
	public void setWorkDuration(String workDuration) {
		this.workDuration = workDuration;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getPayroll() {
		return payroll;
	}
	public void setPayroll(String payroll) {
		this.payroll = payroll;
	}
	public String getWebbAddress() {
		return webbAddress;
	}
	public void setWebbAddress(String webbAddress) {
		this.webbAddress = webbAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDeadlineDate() {
		return deadlineDate;
	}
	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getOrgNumber() {
		return orgNumber;
	}
	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPostAddress() {
		return postAddress;
	}
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}
	public String getPostDistrict() {
		return postDistrict;
	}
	public void setPostDistrict(String postDistrict) {
		this.postDistrict = postDistrict;
	}
	public String getPostCountry() {
		return postCountry;
	}
	public void setPostCountry(String postCountry) {
		this.postCountry = postCountry;
	}
	public String getVisitorAddress() {
		return visitorAddress;
	}
	public void setVisitorAddress(String visitorAddress) {
		this.visitorAddress = visitorAddress;
	}
	public String getVisitorTown() {
		return visitorTown;
	}
	public void setVisitorTown(String visitorTown) {
		this.visitorTown = visitorTown;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmpEmail() {
		return empEmail;
	}
	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}
	public String getEmpWebSite() {
		return empWebSite;
	}
	public void setEmpWebSite(String empWebSite) {
		Log.d("JobDetailEntity", "setEmpWebSite :: " + "empWebSite :: "+empWebSite);
		if (empWebSite.length() != 0 && empWebSite.indexOf("http://") == -1) {
			empWebSite = "http://" + empWebSite;
		}
		this.empWebSite = empWebSite;
	}
	public String getEmpLogo() {
		return empLogo;
	}
	public void setEmpLogo(String empLogo) {
		this.empLogo = empLogo;
	}
	public String getContactPersons() {
		JSONArray contactArray = new JSONArray();
		for(ContactPersonEntity entity : contactPersonsList)
		{
			contactArray.put(ContactPersonEntity.toContactPersonJSON(entity));
		}
		return contactArray.toString();
	}
	public void setContactPersons(String contactPersons) {
		try {
			if(contactPersonsList==null) contactPersonsList = new ArrayList<ContactPersonEntity>();
			contactPersonsList.clear();
			JSONArray contactArray = new JSONArray(contactPersons);
			
			for(int i=0;i<contactArray.length();i++)
			{
				contactPersonsList.add(ContactPersonEntity.toContactPersonEntity(contactArray.getJSONObject(i)));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getDriverLicenses() {
		return driverLicensesList!=null?driverLicensesList.toString():"";
	}
	public void setDriverLicenses(String driverLicenses) {
		if(this.driverLicensesList==null)
			driverLicensesList = new ArrayList<String>();
		driverLicensesList.add(driverLicenses);
	}
	public void setAllDriverLicenses(String driverLicenses) {
		
		if(this.driverLicensesList==null)
			driverLicensesList = new ArrayList<String>();
		driverLicensesList.clear();
		driverLicenses = driverLicenses.replaceAll(driverLicenses, "");
		for(String licence: driverLicenses.split(","))
		{
			driverLicensesList.add(licence);
		}
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public JobDetailEntity getAddToJobDetailEntity(JSONObject jsonObject) {
		try {

			this.setJobId(!jsonObject.isNull("annonsid")?jsonObject.getString("annonsid"):"");
			this.setTitle(!jsonObject.isNull("annonsrubrik")?jsonObject.getString("annonsrubrik"):"");
			this.setJobName(!jsonObject.isNull("yrkesbenamning")?jsonObject.getString("yrkesbenamning"):"");
			this.setEmployer(!jsonObject.isNull("arbetsplatsnamn")?jsonObject.getString("arbetsplatsnamn"):"");
			this.setDistrict(!jsonObject.isNull("kommunnamn")?jsonObject.getString("kommunnamn"):"");
			this.setPublicationDate(!jsonObject.isNull("publiceraddatum")?jsonObject.getString("publiceraddatum"):"");
			this.setJobText(!jsonObject.isNull("annonstext")?jsonObject.getString("annonstext"):"");
			this.setJobText(this.getJobText().replaceAll("\r", "<br>"));
			this.setWorkId(!jsonObject.isNull("yrkesid")?jsonObject.getString("yrkesid"):"");
			this.setSpots(!jsonObject.isNull("antal_platser")?jsonObject.getString("antal_platser"):"");

		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return this;
	}

	public JobDetailEntity getCarToJobDetailEntity(JSONObject jsonObject) {
		try {
			JSONObject jListObject = jsonObject.getJSONObject("korkortslista");
			JSONArray list = !jListObject.isNull("korkortstyp")?jListObject.getJSONArray("korkortstyp"): new JSONArray();
			if(list!=null){
				for(int i =0;i<list.length();i++)
				{
					this.setDriverLicenses(list.getString(i) != null?list.getString(i):"");
				}
			}
			this.car = !jsonObject.isNull("car")?jsonObject.getString("car"):"";
		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return this;
	}
	//	
	//	public JobDetailEntity getJobToJobDetailEntity(JSONObject jsonObject) {
	//		try {
	//			this.setJobId(jsonObject.getString("annonsid"));
	//			this.setTitle(jsonObject.getString("annonsrubrik"));
	//			this.setJobName(jsonObject.getString("yrkesbenamning"));
	//			this.setEmployer(jsonObject.getString("arbetsplatsnamn"));
	//			this.setDistrict(jsonObject.getString("kommunnamn"));
	//			this.setPublicationDate(jsonObject.getString("publiceraddatum"));
	//
	//		} catch (Exception ex) {
	//			Log.d("JSON to Entity Error", ex.toString());
	//		}
	//		return this;
	//	}

	public JobDetailEntity getConditionToJobDetailEntity(JSONObject jsonObject) {

		try {
			this.setDuration(!jsonObject.isNull("varaktighet")?jsonObject.getString("varaktighet"):"");
			this.setWorkHours(!jsonObject.isNull("arbetstid")?jsonObject.getString("arbetstid"):"");
			this.setWorkDuration(!jsonObject.isNull("arbetstidvaraktighet")?jsonObject.getString("arbetstidvaraktighet"):"");
			this.setAccess(!jsonObject.isNull("Tilltrade")?jsonObject.getString("Tilltrade"):"");
			this.setPayroll(!jsonObject.isNull("loneform")?jsonObject.getString("loneform"):"");
		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return this;
	}

	public  JobDetailEntity getApplicationToJobDetailEntity(JSONObject jsonObject) {

		try {
			this.setReference(!jsonObject.isNull("referens")?jsonObject.getString("referens"):"");
			this.setWebbAddress(!jsonObject.isNull("webbplats")?jsonObject.getString("webbplats"):"");
			this.setEmail(!jsonObject.isNull("epostadress")?jsonObject.getString("epostadress"):"");
			this.setDeadlineDate(!jsonObject.isNull("sista_ansokningsdag")?jsonObject.getString("sista_ansokningsdag"):"");
			this.setMisc(!jsonObject.isNull("ovrigt_om_ansokan")?jsonObject.getString("ovrigt_om_ansokan"):"");

		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}

		return this;
	}

	public  JobDetailEntity getWorkplaceToJobDetailEntity(JSONObject jsonObject) {
		try {

			this.setOrgNumber(!jsonObject.isNull("organisationsnummer")?jsonObject.getString("organisationsnummer"):"");
			this.setPostCode(!jsonObject.isNull("postnummer")?jsonObject.getString("postnummer"):"");
			this.setPostAddress(!jsonObject.isNull("postadress")?jsonObject.getString("postadress"):"");
			this.setPostDistrict(!jsonObject.isNull("postort")?jsonObject.getString("postort"):"");
			this.setPostCountry(!jsonObject.isNull("postland")?jsonObject.getString("postland"):"");
			this.setVisitorAddress(!jsonObject.isNull("besoksadress")?jsonObject.getString("besoksadress"):"");
			this.setVisitorTown(!jsonObject.isNull("besoksort")?jsonObject.getString("besoksort"):"");
			this.setTelephoneNumber(!jsonObject.isNull("telefonnummer")?jsonObject.getString("telefonnummer"):"");
			this.setFaxNumber(!jsonObject.isNull("faxnummer")?jsonObject.getString("faxnummer"):"");
			this.setEmployer(!jsonObject.isNull("arbetsplatsnamn")?jsonObject.getString("arbetsplatsnamn"):"");
			this.setEmpEmail(!jsonObject.isNull("epostadress")?jsonObject.getString("epostadress"):"");
			this.setEmpWebSite(!jsonObject.isNull("hemsida")?jsonObject.getString("hemsida"):"");
			this.setEmpLogo(!jsonObject.isNull("logotypurl")?jsonObject.getString("logotypurl"):"");

		} catch (Exception ex) {
			Log.d("JSON to Entity Error", ex.toString());
		}
		return this;
	}

	public ContentValues getJobDetailValue() {
		ContentValues values = new ContentValues();
		values.put(ID_COLUMN, this.getJobId()!=null?this.getJobId():"");
		values.put(TITLE_COLUMN , this.getTitle()!=null?this.getTitle():"");
		Log.d("JobDetailEntity","getJobDetailValue::" +this.getTitle());
		values.put(DESC_COLUMN , this.getJobText()!=null?this.getJobText():"");
		Log.d("JobDetailEntity","getJobDetailValue::" +getJobText());
		values.put(EMPLOYER_COLUMN ,this.getEmployer()!=null?this.getEmployer():"" );
		values.put(DATE_COLUMN , this.getPublicationDate()!=null?this.getPublicationDate():"" );
		values.put(JOBNAME_COLUMN  , this.getJobName()!=null?this.getJobName():"" );
		values.put(RELEVANCY_COLUMN  , this.getRelevancy()!=null?this.getRelevancy():"" );
		values.put(WORKID_COLUMN  , this.getWorkId()!=null?this.getWorkId():"" );
		values.put(SPOTS_COLUMN  , this.getSpots()!=null?this.getSpots():"" );
		values.put(DISTRICT_COLUMN  , this.getDistrict()!=null?this.getDistrict():"" );
		values.put(DURATION_COLUMN  ,  this.getDuration()!=null? this.getDuration():"" );
		values.put(WORKHOURS_COLUMN  ,  this.getWorkHours()!=null? this.getWorkHours():"" );
		values.put(WORKDURATION_COLUMN   , this.getWorkDuration()!=null? this.getWorkDuration():"");
		values.put(ACCESS_COLUMN    , this.getAccess()!=null?  this.getAccess():"");
		values.put(PAYROLL_COLUMN     ,  this.getPayroll()!=null? this.getPayroll():"");
		values.put(REFERENCE_COLUMN      , this.getReference()!=null? this.getReference():"");
		values.put(WEBADDRESS_COLUMN       ,this.getWebbAddress()!=null?this.getWebbAddress():"" );
		values.put(EMAIL_COLUMN        , this.getEmail()!=null?this.getEmail():"" );
		values.put(DEADLINE_COLUMN         , this.getDeadlineDate()!=null?this.getDeadlineDate():"" );
		values.put(MISC_COLUMN          ,  this.getMisc()!=null?this.getMisc():"");
		values.put(ORGNUMBER_COLUMN           ,  this.getOrgNumber()!=null?this.getOrgNumber():"");
		values.put(POSTCODE_COLUMN            , this.getPostCode()!=null?this.getPostCode():"");
		values.put(POSTADDRESS_COLUMN, this.getPostAddress()!=null?this.getPostAddress():"");
		values.put(POSTDISTRICT_COLUMN             , this.getPostDistrict()!=null?this.getPostDistrict():"");
		values.put(POSTCOUNTRY_COLUMN              ,this.getPostCountry()!=null?this.getPostCountry():"");
		values.put(VISITORADDRESS_COLUMN               ,this.getVisitorAddress()!=null?this.getVisitorAddress():"" );
		values.put(VISITORTOWN_COLUMN                , this.getVisitorTown()!=null?this.getVisitorTown():"" );
		values.put(TELPHONE_COLUMN                 , this.getTelephoneNumber()!=null?this.getTelephoneNumber():"" );
		values.put(FAX_COLUMN                  ,  this.getFaxNumber()!=null?this.getFaxNumber():"");
		values.put(EMPEMAIL_COLUMN                   , this.getEmpEmail()!=null?this.getEmpEmail():"");
		values.put(EMPWEBSITE_COLUMN                    , this.getEmpWebSite()!=null? this.getEmpWebSite():"");
		values.put(EMPLOGO_COLUMN                     ,  this.getEmpLogo()!=null?  this.getEmpLogo():"");
		values.put(CONTACTPERSON_COLUMN                      , this.getContactPersons()!=null? this.getContactPersons():"");
		values.put(LICENSE_COLUMN                       , this.getDriverLicenses()!=null? this.getDriverLicenses():"");
		values.put(CAR_COLUMN, this.getCar()!=null?this.getCar():"");
		return values;
	}
	
	
	public JobDetailEntity getJobDetailEntity(Cursor c) {

		this.jobId = c.getString(c.getColumnIndex(ID_COLUMN));
		this.title = c.getString(c.getColumnIndex(TITLE_COLUMN));
		this.jobText = c.getString(c.getColumnIndex(DESC_COLUMN));
		this.employer = c.getString(c.getColumnIndex(EMPLOYER_COLUMN));
		this.employer = c.getString(c.getColumnIndex(DATE_COLUMN));
		this.jobName = c.getString(c.getColumnIndex(JOBNAME_COLUMN));
		this.relevancy = c.getString(c.getColumnIndex(RELEVANCY_COLUMN));
		this.workId = c.getString(c.getColumnIndex(WORKID_COLUMN));
		this.spots = c.getString(c.getColumnIndex(SPOTS_COLUMN));
		this.district = c.getString(c.getColumnIndex(DISTRICT_COLUMN));
		this.duration = c.getString(c.getColumnIndex(DURATION_COLUMN));
		this.workHours = c.getString(c.getColumnIndex(WORKHOURS_COLUMN));
		this.workDuration = c.getString(c.getColumnIndex(WORKDURATION_COLUMN));
		this.access = c.getString(c.getColumnIndex(ACCESS_COLUMN));
		this.payroll = c.getString(c.getColumnIndex(PAYROLL_COLUMN));
		this.reference = c.getString(c.getColumnIndex(REFERENCE_COLUMN));
		this.webbAddress = c.getString(c.getColumnIndex(WEBADDRESS_COLUMN));
		this.email = c.getString(c.getColumnIndex(EMAIL_COLUMN));
		this.deadlineDate = c.getString(c.getColumnIndex(DEADLINE_COLUMN));
		this.misc = c.getString(c.getColumnIndex(MISC_COLUMN));
		this.orgNumber = c.getString(c.getColumnIndex(ORGNUMBER_COLUMN));
		this.postCode = c.getString(c.getColumnIndex(POSTCODE_COLUMN));
		this.postAddress = c.getString(c.getColumnIndex(POSTADDRESS_COLUMN));
		this.postDistrict = c.getString(c.getColumnIndex(POSTDISTRICT_COLUMN));
		this.postCountry = c.getString(c.getColumnIndex(POSTCOUNTRY_COLUMN));
		this.visitorAddress = c.getString(c.getColumnIndex(VISITORADDRESS_COLUMN));
		this.visitorTown = c.getString(c.getColumnIndex(VISITORTOWN_COLUMN));
		this.telephoneNumber = c.getString(c.getColumnIndex(TELPHONE_COLUMN));
		this.faxNumber = c.getString(c.getColumnIndex(FAX_COLUMN));
		this.empEmail = c.getString(c.getColumnIndex(EMPEMAIL_COLUMN));
		this.empWebSite = c.getString(c.getColumnIndex(EMPWEBSITE_COLUMN));
		this.empLogo = c.getString(c.getColumnIndex(EMPLOGO_COLUMN));
		setContactPersons(c.getString(c.getColumnIndex(CONTACTPERSON_COLUMN)));
		this.car = c.getString(c.getColumnIndex(CAR_COLUMN));
		setAllDriverLicenses( c.getString(c.getColumnIndex(LICENSE_COLUMN)));
		
		return this;
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
