package com.sharing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Random;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class SharingUtils extends Application {

	public static final String GOOGLE_TRACKER_KEY = "UA-22524300-4";
	private static String API_KEY = "R_e9b43fbb5f1e8ec6102bc0ade8e4ecf6";
	private static String LOGIN = "idgnederland";
	public static String SHORTEN_URL = "http://api.bit.ly/shorten?version=2.0.1&format=xml&login="
			+ LOGIN + "&apiKey=" + API_KEY + "&longUrl=";
	public static String NEW_URL = "";
}
