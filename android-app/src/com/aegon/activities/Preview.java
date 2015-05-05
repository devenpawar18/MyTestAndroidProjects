package com.aegon.activities;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.aegon.LogConstants;

public class Preview extends FrameLayout implements SurfaceHolder.Callback {
	private final String TAG = "Preview";

	private SurfaceView mSurfaceView;
	private SurfaceHolder mHolder;
	private Camera mCamera;

	public Preview(Context context) {
		super(context);
		init(context);
	}

	/**
	 * @param context
	 */
	public Preview(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		init(context);
	}

	private void init(Context context) {
		mSurfaceView = new SurfaceView(context);
		addView(mSurfaceView);

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		mHolder = mSurfaceView.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void setCamera(Camera camera) {
		mCamera = camera;
		if (mCamera != null) {
			requestLayout();
		}
	}

	public void switchCamera(Camera camera) {
		setCamera(camera);
		try {
			camera.setPreviewDisplay(mHolder);
			setupCameraParams();
		} catch (IOException exception) {
			android.util.Log.e(TAG,
					"IOException caused by setPreviewDisplay()", exception);
		}

		requestLayout();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.
		try {
			if (mCamera != null) {
				mCamera.setPreviewDisplay(holder);
			}
		} catch (IOException exception) {
			android.util.Log.e(TAG,
					"IOException caused by setPreviewDisplay()", exception);
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		if (mCamera != null) {
			mCamera.stopPreview();
		}
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;

		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		setupCameraParams();
	}

	private void setupCameraParams() {
		if (mCamera != null) {
			mCamera.setDisplayOrientation(90);

			List<Size> supportedPreviewSizes = mCamera.getParameters()
					.getSupportedPreviewSizes();
			List<Size> supportedPictureSizes = mCamera.getParameters()
					.getSupportedPictureSizes();

			int targetWidth = Math.max(getWidth(), 600);
			int targetHeight = targetWidth * 4 / 3;

			Size pictureSize = getOptimalPreviewSize(supportedPictureSizes,
					targetWidth, targetHeight);
			Size previewSize = getOptimalPreviewSize(supportedPreviewSizes,
					getWidth(), targetHeight);

			Parameters params = mCamera.getParameters();
			params.setPictureSize(pictureSize.width, pictureSize.height);
			params.setPreviewSize(previewSize.width, previewSize.height);

			if (LogConstants.DEBUG_ENABLED) {
				android.util.Log.w(TAG,
						"Camera options preview dimenions - width: "
								+ previewSize.width + ", height: "
								+ previewSize.height);
			}
			if (LogConstants.DEBUG_ENABLED) {
				android.util.Log.w(TAG,
						"Camera options picture dimenions - width: "
								+ pictureSize.width + ", height: "
								+ pictureSize.height);
			}
			mCamera.setParameters(params);

			mCamera.startPreview();
		}
	}
}