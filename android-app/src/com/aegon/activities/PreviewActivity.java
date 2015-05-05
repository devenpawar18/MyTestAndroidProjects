package com.aegon.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.aegon.AegonConstants;
import com.aegon.AppAnalytics;
import com.aegon.BitmapManager;
import com.aegon.ScrollImageView;
import com.aegon.ZoomImage;
import com.aegon.ZoomImageView;

public class PreviewActivity extends Activity implements ZoomImage {

	private ScrollImageView mScrollImageView = null;
	private ZoomImageView mZoomImageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture);
		AppAnalytics.getInstance().trackPage(false,
				AegonConstants.CAPTURE_PAGE_LINK_URL);

		mScrollImageView = (ScrollImageView) findViewById(R.id.picture_preview);
		mScrollImageView.setListener(this);
		mZoomImageView = (ZoomImageView) findViewById(R.id.zoom);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mScrollImageView
				.setImageBitmap(BitmapManager.getInstance().getBitmap());
	}

	@Override
	protected void onPause() {
		mScrollImageView.setImageBitmap(null);
		// BitmapManager.getInstance().recycle();
		super.onPause();
	}

	public void onPhotoClick(View view) {
		if (view.getId() == R.id.open_camera) {
			BitmapManager.getInstance().recycle();
			openCamera();
		} else if (view.getId() == R.id.create_aging_photo) {
			Bundle bundle = new Bundle();
			bundle.putFloat("leftEyeX", mScrollImageView.getLeftEyePoint().x);
			bundle.putFloat("leftEyeY", mScrollImageView.getLeftEyePoint().y);
			bundle.putFloat("rightEyeX", mScrollImageView.getRightEyePoint().x);
			bundle.putFloat("rightEyeY", mScrollImageView.getRightEyePoint().y);
			bundle.putFloat("leftMouthX",
					mScrollImageView.getLeftMouthPoint().x);
			bundle.putFloat("leftMouthY",
					mScrollImageView.getLeftMouthPoint().y);
			bundle.putFloat("rightMouthX",
					mScrollImageView.getRigthMouthPoint().x);
			bundle.putFloat("rightMouthY",
					mScrollImageView.getRigthMouthPoint().y);
			Intent intent = new Intent(PreviewActivity.this, WaitActivity.class);
			intent.putExtras(bundle);
			finish();
			startActivity(intent);

		}
	}

	private void openCamera() {
		finish();
		this.startActivity(new Intent(PreviewActivity.this, CameraPreview.class));
	}

	@Override
	public void onBackPressed() {
		openCamera();
	}

	@Override
	public void setImage(Bitmap bitmap, boolean value) {
		mZoomImageView.setBitmap(bitmap);
		mZoomImageView.isTooom(value);
		mZoomImageView.invalidate();

	}

}
