package com.testimage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

public class TestImageActivity extends Activity {
	/** Called when the activity is first created. */
	private WebView webview;
	private ImageView image;
	private static String filePath = "";
	private String filename = "image";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		// webview = (WebView) findViewById(R.id.contentWebView);
		// filePath = "/sdcard/";
		// image = (ImageView) findViewById(R.id.image);
		// BitmapFactory.Options bmOptions;
		// bmOptions = new BitmapFactory.Options();
		// bmOptions.inSampleSize = 1;
		// Bitmap bm = LoadImage(
		// "http://ecx.images-amazon.com/images/I/41eXAxr%2BU3L._SL500__SS160_.jpg",
		// bmOptions);
		//
		// if (!isExternalStorageAvailable()) {
		// Toast.makeText(this, "no sd card", Toast.LENGTH_SHORT).show();
		// return;
		// }
		// storeFile(bm, filename);
		// image.setImageBitmap(retriveFile(filename));
		String str = "/sdcard/Endless/image.jpg";
		String str1 = "/sdcard/Endless/endlessIcon.jpg";
		String str2 = "/sdcard/Endless/logo.jpg";
		ArrayList<String> list = new ArrayList<String>();
		list.add(str);
		list.add(str1);
		list.add(str2);

		final Intent sendingIntent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);
//		Intent sendingIntent = new Intent(Intent.ACTION_SEND);

		sendingIntent.putExtra(Intent.EXTRA_SUBJECT,
				"Look what I found at endless.com");
		sendingIntent.setType("image/jpeg");
		sendingIntent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "deven.pawar@capgemini.com" });
		sendingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sendingIntent.setType("message/rfc822");
		ArrayList<Uri> uris = new ArrayList<Uri>();

		Iterator<String> itr = list.iterator();
		while (itr.hasNext()) {
			File fileIn = new File(itr.next());
			Uri u = Uri.fromFile(fileIn);
			uris.add(u);
		}

//		uris.add(Uri.parse(str));
//		uris.add(Uri.parse(str1));
//		uris.add(Uri.parse(str2));
		sendingIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		// sendingIntent.putExtra(Intent.EXTRA_STREAM, uris);
		// sendingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(str1));
		// sendingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(str2));
		// sendingIntent
		// .putExtra(
		// android.content.Intent.EXTRA_STREAM,
		// Html.fromHtml("<HTML><BODY><IMG SRC = file:///sdcard/test/image.jpg</BODY></HTML>"));
		startActivity(Intent.createChooser(sendingIntent, "Sending email..."));

	}

	public static Bitmap retriveFile(String filename) {
		Bitmap bMap = null;
		String root = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File dir = new File(root + File.separator + "test" + File.separator);

		String fName = filename + ".jpg";

		File file = new File(dir, fName);
		Log.d("temp exists : ", "file ecists  " + file.exists());
		if (file.exists() == true) {
			bMap = BitmapFactory.decodeFile(file.getAbsolutePath());

		}
		return bMap;

	}

	public static void storeFile(Bitmap bitmapImage, String filename) {
		OutputStream outStream = null;
		String root = Environment.getExternalStorageDirectory()
				.getAbsolutePath();

		File dir = new File(root + File.separator + "test" + File.separator);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		dir = new File(dir, filename + ".jpg");
		Log.d("temp exists : ", "file ecists  " + dir.exists());
		if (dir.exists() == true) {
			dir.delete();
		}
		try {
			outStream = new FileOutputStream(dir);
			bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
			// bitmapImage.recycle();
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// private void createFile(Bitmap bitmap) {
	// File imageFile = new File(imageFilePath + "image.jpg");
	// try {
	// // imageFile.createNewFile();
	// FileOutputStream fo = new FileOutputStream(imageFile);
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
	// fo.write(out.toByteArray());
	// fo.flush();
	// fo.close();
	// Toast.makeText(this, "Image saved to gallary", Toast.LENGTH_SHORT)
	// .show();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public static boolean isExternalStorageAvailable() {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	private Bitmap LoadImage(String URL, BitmapFactory.Options options) {
		Bitmap bitmap = null;
		InputStream in = null;
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			in.close();
		} catch (IOException e1) {
		}
		return bitmap;
	}

	private InputStream OpenHttpConnection(String strURL) throws IOException {
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpConn.getInputStream();
			}
		} catch (Exception ex) {
		}
		return inputStream;
	}

}