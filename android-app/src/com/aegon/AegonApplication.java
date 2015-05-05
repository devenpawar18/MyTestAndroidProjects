/**
 * 
 */
package com.aegon;

import android.app.Application;

public class AegonApplication extends Application {

	private static boolean isSplashToShow;
	private BitmapManager mManager;
	private AppAnalytics mAppAnalytics;

	@Override
	public void onCreate() {
		super.onCreate();
		isSplashToShow = true;
		BitmapManager.createInstance(this);
		mManager = BitmapManager.getInstance();
		AppAnalytics.createInstance(this);
		mAppAnalytics = AppAnalytics.getInstance();
	}

	@Override
	public void onTerminate() {
		mManager = null;
		BitmapManager.destroyInstance(this);
		AppAnalytics.destroyInstance();
		isSplashToShow = true;
	}

	/**
	 * @return the isSplashShown
	 */
	public boolean isSplashToShown() {
		return isSplashToShow;
	}

	/**
	 * @param isSplashShown
	 *            the isSplashShown to set
	 */
	public void setSplashToShow(boolean isSplashToShow) {
		AegonApplication.isSplashToShow = isSplashToShow;
	}
}
