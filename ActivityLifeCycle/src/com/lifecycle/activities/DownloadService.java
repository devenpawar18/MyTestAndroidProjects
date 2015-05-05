package com.lifecycle.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

public class DownloadService extends IntentService {

	private int result = Activity.RESULT_CANCELED;
	public static final String URL = "urlpath";
	public static final String FILENAME = "filename";
	public static final String FILEPATH = "filepath";
	public static final String RESULT = "result";
	public static final String NOTIFICATION = "com.servicedemo";

	public DownloadService() {
		super("DownloadService");
	}

	// will be called asynchronously by Android
	@Override
	protected void onHandleIntent(Intent intent) {
		String extStorageDirectory = Environment.getExternalStorageDirectory()
				.toString();
		File folder = new File(extStorageDirectory, "pdf");
		folder.mkdir();
		File file = new File(folder, "ST-txt.txt");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			FileOutputStream f = new FileOutputStream(file);
			URL u = new URL("http://www.sjsu.edu/map/docs/SJSU_campus_map.pdf");
			// URL u = new URL(
			// "http://www.sjsu.edu/publicaffairs/pics/boccardo_gateway_web.jpg");
//			URL u = new URL(
//					"http://vireo.biology.sjsu.edu/bio5/Modules/Turtle%20Experiment%20text.txt");

//			URLConnection c = u.openConnection();
//            c.connect();
            
			HttpURLConnection c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setDoOutput(true);
			c.connect();

			InputStream in = c.getInputStream();

			byte[] buffer = new byte[1024];
			int len1 = 0;
			while ((len1 = in.read(buffer)) > 0) {
				f.write(buffer, 0, len1);
			}
			f.close();
		} catch (Exception e) {
 			e.printStackTrace();
		}
		result = Activity.RESULT_OK;
		publishResults("Result", result);
	}

	private void publishResults(String outputPath, int result) {
		Intent intent = new Intent(NOTIFICATION);
		intent.putExtra(FILEPATH, outputPath);
		intent.putExtra(RESULT, result);
		sendBroadcast(intent);
	}
}