package com.broaddemo.network;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import android.app.Application;
import android.util.Log;

public class ApplicationEx extends Application
{
	private static final String TAG = "ApplicationEx";
	private static final int CORE_POOL_SIZE = 3;
	private static final int MAXIMUM_POOL_SIZE = 3;
	private static ApplicationEx appEX;

	public static ThreadPoolExecutor operationsQueue;

	@Override
	public void onCreate()
	{
		super.onCreate();

		Log.d(TAG, "onCreate");

		operationsQueue = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 100000L,
				TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		appEX = this;

	}

	public static ApplicationEx getInstance()
	{
		return appEX;
	}
}