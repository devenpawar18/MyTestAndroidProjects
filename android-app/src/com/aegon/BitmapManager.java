/*
 * Copyright
 * ============================================================================
 * Project: Aegon
 *
 * $RCSfile: BitmapManager.java$
 * ============================================================================
 */
package com.aegon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.aegon.activities.R;

/**
 * Singleton class to hold and handle the image bitmaps used in the aging
 * process in the app.
 * 
 * Downsizes input data and ensures a minimum of allocated bitmaps.
 * 
 * @version $Id$
 * @author grootren (c) Feb 14, 2012, AGEON B.V.
 */
public class BitmapManager {
	private final static String TAG = BitmapManager.class.getSimpleName();
	private SaveImageCallback mSaveImageCallback;
	private static final int MORPH_WIDTH_PIXELS = 600;

	private static BitmapManager sBitmapManager = null;

	private AegonApplication mApplication;
	private Bitmap mBitmap;

	private BitmapManager(AegonApplication aegonApplication) {
		mApplication = aegonApplication;
	}

	public static void createInstance(AegonApplication aegonApplication) {
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG,
					"createInstance(AegonApplication aegonApplication)"
							+ sBitmapManager);
		}

		sBitmapManager = new BitmapManager(aegonApplication);

		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG,
					"createInstance(AegonApplication aegonApplication)"
							+ sBitmapManager);
		}

	}

	public static BitmapManager getInstance() {
		return sBitmapManager;
	}

	public static void destroyInstance(AegonApplication aegonApplication) {

		if (aegonApplication == sBitmapManager.mApplication) {
			sBitmapManager = null;
		}

	}

	public void storeAsBitmap(byte[] bytes, int rotation, boolean mirror) {
		setBitmap(null);

		// Determine goal of image size
		int screenPixels = mApplication.getResources().getDisplayMetrics().widthPixels;
		int apiPixels = MORPH_WIDTH_PIXELS;
		int targetWidth = apiPixels; // Math.max(screenPixels, apiPixels);
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG, "Goal - width: " + targetWidth
					+ ", height: " + (targetWidth * 4) / 3);
		}

		// Scale the raw input to something close to target
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
		int scale = 1;
		if (rotation == 0 || rotation == 180) {
			scale = opts.outWidth / targetWidth;
		} else if (rotation == 90 || rotation == 270) {
			scale = opts.outHeight / targetWidth;
		} else {
			throw new IllegalArgumentException("Invalid rotation=" + rotation);
		}
		opts = new BitmapFactory.Options();
		opts.inSampleSize = scale;
		setBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts));
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG, "Coarse scale " + scale + " - width: "
					+ opts.outWidth + ", height: " + opts.outHeight);
		}

		// rotate and mirror image
		if (rotation != 0) {
			Bitmap replacement = Bitmap.createBitmap(mBitmap.getHeight(),
					mBitmap.getWidth(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(replacement);
			Matrix matrix = new Matrix();
			matrix.setRotate(rotation);
			if (mirror) {
				matrix.preScale(1, -1);
			}
			matrix.preTranslate(mBitmap.getWidth() / -2, mBitmap.getHeight()
					/ -2);
			matrix.postTranslate(replacement.getWidth() / 2,
					replacement.getHeight() / 2);
			canvas.drawBitmap(mBitmap, matrix, new Paint());
			setBitmap(replacement);
		}
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG, "Rotate " + rotation + " and mirror "
					+ mirror + " - width: " + mBitmap.getWidth() + ", height: "
					+ mBitmap.getHeight());
		}

		// scale image to exact size
		int x = targetWidth;
		int y = (mBitmap.getHeight() * targetWidth) / mBitmap.getWidth();
		setBitmap(Bitmap.createScaledBitmap(mBitmap, (int) x, (int) y, true));
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG, "Precise scale - width: " + x
					+ ", height: " + y);
		}

		// Crop to height images more then 3:4 as with 600x800px
		int targetHeight = (targetWidth * 4) / 3;
		if (y > targetHeight) {
			setBitmap(Bitmap.createBitmap(mBitmap, 0, 0, targetWidth,
					targetHeight));
		}
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d(TAG, "Cropping - width: " + targetWidth
					+ ", height: " + targetHeight);
		}
	}

	public void setBitmap(Bitmap replacement) {
		if (mBitmap != null && mBitmap != replacement) {
			mBitmap.recycle();
			mBitmap = null;
			System.gc();
		}
		mBitmap = replacement;
		if (LogConstants.DEBUG_ENABLED) {
			if (mBitmap != null)
				android.util.Log.d(TAG, "Update bitmap " + mBitmap
						+ " - width: " + mBitmap.getWidth() + ", height: "
						+ mBitmap.getHeight());
		}
	}

	public Bitmap getBitmap() {
		// if (LogConstants.DEBUG_ENABLED) {
		// if (mBitmap != null)
		// android.util.Log.d(TAG, "Retrieve bitmap " + mBitmap);
		// }
		return mBitmap;
	}

	public byte[] getPNGByteData() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
		return out.toByteArray();
	}

	public byte[] getJpegByteData() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		return out.toByteArray();
	}

	public InputStream getPNGImageBytes() {
		byte[] imgbytes = getPNGByteData();
		InputStream is = new ByteArrayInputStream(imgbytes);
		return is;
	}

	public InputStream getJpegImageBytes() {
		byte[] imgbytes = getJpegByteData();
		InputStream is = new ByteArrayInputStream(imgbytes);
		return is;
	}

	public void recycle() {
		if (mBitmap != null) {
			mBitmap.recycle();
		}
		mBitmap = null;
		System.gc();
	}

	public Bitmap getBitmap(int w, int h) {
		if (mBitmap.getWidth() != w) {
			setBitmap(Bitmap.createScaledBitmap(mBitmap, w, h, false));
		}
		return mBitmap;
	}

	public interface SaveImageCallback {
		public void saveImage(String msg);
	}

	public void setSaveImageCallback(SaveImageCallback saveImageCallback) {
		this.mSaveImageCallback = saveImageCallback;
	}

	public void saveAgedBitmap(final Context context) {
		if (!Utility.isExternalStorageAvailable()) {
			Toast.makeText(context,
					context.getResources().getString(R.string.no_sdcard),
					Toast.LENGTH_SHORT).show();
			return;
		}

		File path = new File(Utility.getSDCardPublicDirPath() + File.separator);
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("IMAGE_")
				.append(new SimpleDateFormat("yyyyMMdd_HHmmss")
						.format(new Date())).append(".jpg");
		if (!path.exists()) {
			path.mkdir();
		}
		path = new File(path, strBuf.toString());

		FileOutputStream foutStream = null;
		try {
			foutStream = new FileOutputStream(path);
			foutStream.write(getPNGByteData());
			foutStream.flush();
			// immediately available to the user.
			MediaScannerConnection.scanFile(context,
					new String[] { path.toString() }, null,
					new MediaScannerConnection.OnScanCompletedListener() {
						public void onScanCompleted(String path, Uri uri) {
							if (LogConstants.DEBUG_ENABLED) {
								Log.i("ExternalStorage", "Scanned " + path
										+ ":");
								Log.i("ExternalStorage", "-> uri=" + uri);
							}
						}
					});

			mSaveImageCallback.saveImage(context.getResources().getString(
					R.string.image_saved));
		} catch (Exception e) {
			mSaveImageCallback.saveImage(context.getResources().getString(
					R.string.image_not_saved));
			if (LogConstants.DEBUG_ENABLED) {
				android.util.Log
						.e("", "Unable to write the image to sdcard", e);
			}
		} finally {
			if (foutStream != null) {
				try {
					foutStream.close();
				} catch (IOException e) {
					if (LogConstants.DEBUG_ENABLED) {
						android.util.Log.e("",
								"Unable to write the image to sdcard", e);
					}
				}
			}
		}

	}
}
