/**
 * 
 */
package com.aegon.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aegon.AegonApplication;
import com.aegon.AegonConstants;
import com.aegon.AppAnalytics;
import com.aegon.LogConstants;

public class AegonActivity extends Activity {

	private static final String TAG = AegonActivity.class.getSimpleName();
	private static final int SPLASH_MSG = 1;
	private static final int SPLASHTIME = 5000;
	private AegonApplication mAegonApplication;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aegon);
		mAegonApplication = (AegonApplication) getApplicationContext();

		if (mAegonApplication.isSplashToShown()) {
			AppAnalytics.getInstance().trackPage(true,
					AegonConstants.SPLASH_PAGE_LINK_URL);
			showSplashImage();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.w(TAG, " Enter onResume() Splash == "
					+ mAegonApplication.isSplashToShown());
		}

		if (mAegonApplication.isSplashToShown()) {
			showSplashImage();
			Message msg = new Message();
			msg.what = SPLASH_MSG;
			mHanlder.sendMessageDelayed(msg, SPLASHTIME);
		} else {
			((Button) findViewById(R.id.open_camera)).setClickable(true);
			showHelpImage();
		}

	}

	private void showSplashImage() {

		splashEyeAnimation();
		((ImageView) findViewById(R.id.info_image)).setClickable(true);
		((RelativeLayout) findViewById(R.id.splash_layout))
				.setVisibility(View.VISIBLE);
		((RelativeLayout) findViewById(R.id.help_layout))
				.setVisibility(View.GONE);

	}

	private void splashEyeAnimation() {

		AppAnalytics.getInstance().trackPage(false,
				AegonConstants.HOME_PAGE_LINK_URL);

		final ImageView splashimg = ((ImageView) findViewById(R.id.splash_image));
		splashimg.setVisibility(View.VISIBLE);

		splashimg.setBackgroundResource(R.anim.splash_anim_list);

		splashimg.post(new Runnable() {
			public void run() {
				AnimationDrawable eyeanim = (AnimationDrawable) splashimg
						.getBackground();

				eyeanim.start();
			}
		});
	}

	private void showHelpImage() {
		((RelativeLayout) findViewById(R.id.splash_layout))
				.setVisibility(View.GONE);
		((RelativeLayout) findViewById(R.id.help_layout))
				.setVisibility(View.VISIBLE);

	}

	private final Handler mHanlder = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			if (msg.what == SPLASH_MSG) {
				showHelpImage();
				mAegonApplication.setSplashToShow(false);
			} else {
				super.dispatchMessage(msg);
			}

		}
	};

	DialogInterface.OnClickListener infolistener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	public void onViewClick(View view) {
		if (view.getId() == R.id.open_camera) {
			((Button) findViewById(R.id.open_camera)).setClickable(false);
			this.startActivity(new Intent(AegonActivity.this,
					CameraPreview.class));
		} else if (view.getId() == R.id.info_image) {

			final AlertDialog infodiag = new AlertDialog.Builder(
					AegonActivity.this)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle(R.string.infotitle).setMessage(R.string.infomsg)
					.setNeutralButton(R.string.ok, infolistener).create();

			infodiag.show();
			((ImageView) findViewById(R.id.info_image)).setClickable(false);
		}
	}

	@Override
	protected void onDestroy() {
		mAegonApplication.setSplashToShow(true);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		mAegonApplication.setSplashToShow(true);
		System.exit(1);
		super.onBackPressed();
	}

}
