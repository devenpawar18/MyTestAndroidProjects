/**
 * 
 */
package com.aegon;

import android.app.Application;

import com.omniture.AppMeasurement;

public class AppAnalytics {

	private static AppMeasurement mAppMeasurement = null;
	private static AppAnalytics mAppAnalytics = null;

	private AppAnalytics() {

	}

	public static AppAnalytics createInstance(Application application) {
		if (mAppAnalytics == null) {
			mAppAnalytics = new AppAnalytics();
			mAppMeasurement = new AppMeasurement(application);
			configuration();
		}
		return mAppAnalytics;
	}

	public static AppAnalytics getInstance() {
		return mAppAnalytics;
	}

	public static void destroyInstance() {
		mAppAnalytics = null;
		mAppMeasurement = null;
	}

	private static void configuration() {
		/* Specify the Report Suite ID(mAppMeasurement) to track here */
		mAppMeasurement.account = "advaegonappsprod";
		/* You may add or alter any code config here */
		mAppMeasurement.currencyCode = "EUR";
		/* Turn on and configure debugging here */
		mAppMeasurement.debugTracking = true;
		/*
		 * WARNING: Changing any of the below variables will cause drastic
		 * changes to how your visitor data is collected. Changes should only be
		 * made when instructed to do so by your account manager.
		 */
		mAppMeasurement.trackingServer = "aegon.122.2o7.net";
		mAppMeasurement.visitorNamespace = "aegon";

		mAppMeasurement.trackOffline = true;

		mAppMeasurement.offlineLimit = 10;

		mAppMeasurement.offlineThrottleDelay = 3000;
	}

	private void trackPageVar() {
		mAppMeasurement.prop8 = "Pensioen Fun";
		mAppMeasurement.eVar8 = "Pensioen Fun";
		mAppMeasurement.prop50 = "Android";
		mAppMeasurement.eVar50 = "Android";
	}

	public void trackPage(boolean event, String pageName) {
		mAppMeasurement.pageName = pageName;
		trackPageVar();
		if (event) {
			mAppMeasurement.events = "event8";
		}
		mAppMeasurement.track();
	}

	// events
	private void trackEventsVar() {
		mAppMeasurement.prop8 = "Pensioen Fun";
		mAppMeasurement.eVar8 = "Pensioen Fun";
		mAppMeasurement.prop50 = "Android";
		mAppMeasurement.eVar51 = "Android";
	}

	public void trackEvents(String events, String linkUrl, String linkname) {
		trackEventsVar();
		mAppMeasurement.events = events;
		mAppMeasurement.trackLink(linkUrl, "o", linkname);
	}

	public void trackFBAuthEvents() {
		trackPageVar();
		mAppMeasurement.prop4 = "Facebook";
		mAppMeasurement.eVar4 = "Facebook";
		mAppMeasurement.events = "event17";
		mAppMeasurement.trackLink(null, "o", "content plaatsen op Facebook");
	}

	public void trackTweeterAuthEvents() {
		trackPageVar();
		mAppMeasurement.prop4 = "Twitter";
		mAppMeasurement.eVar4 = "Twitter";
		mAppMeasurement.events = "event17";
		mAppMeasurement.trackLink(null, "o", "content plaatsen op Twitter");
	}

	public void trackFBPhotoUploadEvents() {
		trackPageVar();
		mAppMeasurement.prop4 = "Facebook";
		mAppMeasurement.eVar4 = "Facebook";
		mAppMeasurement.events = "event6";
		mAppMeasurement.trackLink(null, "o", "content geplaatst op Facebook");
	}

	public void trackTweeterPhotoUploadEvents() {
		trackPageVar();
		mAppMeasurement.prop4 = "Twitter";
		mAppMeasurement.eVar4 = "Twitter";
		mAppMeasurement.events = "event6";
		mAppMeasurement.trackLink(null, "o", "content geplaatst op Twitter");
	}

}
