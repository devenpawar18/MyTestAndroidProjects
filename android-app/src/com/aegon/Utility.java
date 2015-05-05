package com.aegon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

public class Utility {

	public static boolean isExternalStorageAvailable() {
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			return true;
		}
		return false;
	}

	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getSDCardPublicDirPath() {
		return Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM).getAbsolutePath();
	}

	public static boolean isFrontFacingCameraAvailable(Context context) {
		PackageManager packageManager = context.getPackageManager();
		String FRONT_FACING_CAMERA_API = "android.hardware.camera.front";
		boolean frontCamera;
		frontCamera = packageManager.hasSystemFeature(FRONT_FACING_CAMERA_API);
		return frontCamera;
	}

	public static boolean isNetworkNotAvailable(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = con.getActiveNetworkInfo();
		return (info == null);
	}
}
