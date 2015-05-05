package com.sogeti.appitecture;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;

public class ApplicationEx extends android.app.Application
{
	private static final String TAG = "ApplicationEx";
	private static final int CORE_POOL_SIZE = 3;
	private static final int MAXIMUM_POOL_SIZE = 3;
	
	public static ThreadPoolExecutor operationsQueue;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		Log.d(TAG, "onCreate");
		
		operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 100000L, 
									TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
		Data.getInstance().setContext(this);
	}
}