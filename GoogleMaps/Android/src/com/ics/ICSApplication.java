package com.ics;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.ics.entity.Faq;
import android.util.Log;



public class ICSApplication extends android.app.Application{
	
	private static final String TAG = "ICSApplication";
	private static final int CORE_POOL_SIZE = 3;
	private static final int MAXIMUM_POOL_SIZE = 3;
	private static ICSApplication icsApp;
	public static ArrayList<Faq> faqs;
	
	public static ThreadPoolExecutor operationsQueue;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		Log.d(TAG, "onCreate");
		
		operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 100000L, 
									TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		icsApp = this;
		faqs = new ArrayList<Faq>();
//		Data.getInstance().setContext(this);
	}
	
	public static ICSApplication getInstance() {
		return icsApp;
	}	

}
