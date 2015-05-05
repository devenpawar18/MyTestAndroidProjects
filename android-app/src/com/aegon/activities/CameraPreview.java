package com.aegon.activities;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.aegon.AegonConstants;
import com.aegon.AppAnalytics;
import com.aegon.BitmapManager;
import com.aegon.Utility;

public class CameraPreview extends Activity {

	private Preview mPreview;
	private Camera mCamera;
	private int numberOfCameras;
	private int cameraCurrentlyLocked;
	// The first rear facing camera
	int defaultCameraId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide the window title.
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Create a RelativeLayout container that will hold a SurfaceView,
		// and set it as the content of our activity.
		setContentView(R.layout.camera);
		mPreview = (Preview) findViewById(R.id.camera);

		AppAnalytics.getInstance().trackPage(false,
				AegonConstants.CAMERA_PAGE_LINK_URL);

		// show switch camera button
		if (Utility.isFrontFacingCameraAvailable(this)) {
			((Button) findViewById(R.id.switch_camera))
					.setVisibility(View.VISIBLE);

			// Find the total number of cameras available
			numberOfCameras = Camera.getNumberOfCameras();

			// Find the ID of the default camera
			CameraInfo cameraInfo = new CameraInfo();
			for (int i = 0; i < numberOfCameras; i++) {
				Camera.getCameraInfo(i, cameraInfo);
				if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
					defaultCameraId = i;
				} else if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
					defaultCameraId = i;
				}
			}
		}
	}

	private void switchCamera() {
		// OK, we have multiple cameras.
		// Release this camera -> cameraCurrentlyLocked
		if (mCamera != null) {
			mCamera.stopPreview();
			mPreview.setCamera(null);
			mCamera.release();
			mCamera = null;
		}

		// Acquire the next camera and request Preview to reconfigure
		// parameters.
		cameraCurrentlyLocked = (cameraCurrentlyLocked + 1) % numberOfCameras;
		mCamera = Camera.open(cameraCurrentlyLocked);
		mPreview.switchCamera(mCamera);

		setButtonClickable(true);
	}

	public void onPhotoClick(View view) {
		if (view.getId() == R.id.take_photo) {
			setButtonClickable(false);
			mCamera.takePicture(null, null, mPictureCallback);
		} else if (view.getId() == R.id.switch_camera) {
			setButtonClickable(false);
			switchCamera();
		}
	}

	private void setButtonClickable(boolean value) {
		((Button) findViewById(R.id.take_photo)).setClickable(value);
		((Button) findViewById(R.id.switch_camera)).setClickable(value);
	}

	PictureCallback mPictureCallback = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {
			if (data == null) {
				return;
			}
			pictureTaken(data);

		}
	};

	private void pictureTaken(final byte[] data) {

		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(cameraCurrentlyLocked, info);
		int degrees = 90;
		boolean isFrontFacing = false;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			degrees = 270;
			isFrontFacing = true;
		} else { // back-facing
			degrees = 90;
			isFrontFacing = false;
		}

		BitmapManager.getInstance().storeAsBitmap(data, degrees, isFrontFacing);
		finish();
		this.startActivity(new Intent(CameraPreview.this, PreviewActivity.class));

	}

	@Override
	protected void onResume() {
		super.onResume();

		// Free the bitmap manager to make room for new images
		BitmapManager.getInstance().recycle();

		// Open the default i.e. the first rear facing camera.
		mCamera = Camera.open(defaultCameraId);
		cameraCurrentlyLocked = defaultCameraId;
		mPreview.setCamera(mCamera);

	}

	@Override
	protected void onPause() {

		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera != null) {
			mPreview.setCamera(null);
			mCamera.release();
			mCamera = null;
		}
		System.gc();
		super.onPause();
	}

}
