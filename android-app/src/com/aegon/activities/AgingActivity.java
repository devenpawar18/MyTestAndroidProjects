/**
 * 
 */
package com.aegon.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aegon.AegonConstants;
import com.aegon.AppAnalytics;
import com.aegon.BitmapManager;
import com.aegon.BitmapManager.SaveImageCallback;
import com.aegon.LogConstants;
import com.aegon.Utility;
import com.aegon.controller.FacebookManager;
import com.aegon.controller.FacebookManager.facebookcallbacks;
import com.aegon.controller.TwitterManager;
import com.aegon.controller.TwitterManager.UploadCallBack;
import com.aegon.facebook.Facebook.FBResponseListener;

public class AgingActivity extends Activity implements UploadCallBack,
		FBResponseListener, facebookcallbacks, SaveImageCallback {

	private final String TAG = AgingActivity.class.getSimpleName();
	private TwitterManager mTwitterManager;
	private FacebookManager mFbManager;
	private ProgressDialog mSessionprogress, mConnectingprogress,
			mUploadprogress, mSavingImageProgress;
	private ImageView mAgedImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aging);
		mAgedImageView = (ImageView) findViewById(R.id.aged_iamge);
		AppAnalytics.getInstance().trackPage(false,
				AegonConstants.AGING_PAGE_LINK_URL);

	}

	@Override
	public void onResume() {
		super.onResume();
		mAgedImageView.setImageBitmap(BitmapManager.getInstance().getBitmap());
		BitmapManager.getInstance().setSaveImageCallback(this);
		if (mSessionprogress != null)
			mSessionprogress.dismiss();
		setSharingImageClickable(true);
	}

	@Override
	protected void onPause() {
		mAgedImageView.setImageBitmap(null);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		BitmapManager.getInstance().recycle();
		super.onDestroy();
	}

	public void onAgingClick(View view) {
		switch (view.getId()) {
		case R.id.aging_take_photo:
			AppAnalytics.getInstance().trackEvents(
					AegonConstants.OPEN_CAMERA_EVENT_7, null,
					AegonConstants.OPEN_CAMERA);
			showAlertDialog(2);
			break;
		case R.id.aging_save_photo:
			mSavingImageProgress = ProgressDialog.show(AgingActivity.this, "",
					getResources().getString(R.string.saving_image), false);

			AppAnalytics.getInstance().trackEvents(
					AegonConstants.SAVE_IMAGE_EVENT_13, null,
					AegonConstants.SAVE_IMAGE);
			new Thread() {
				public void run() {
					BitmapManager.getInstance()
							.saveAgedBitmap(getBaseContext());
				}
			}.start();
			break;
		case R.id.facebook:
			setSharingImageClickable(false);
			if (Utility.isNetworkNotAvailable(this)) {
				showAlertDialog(1);
			} else {
				mFbManager = FacebookManager.getInstance();
				setFacebookSettings();
				mFbManager.setOnfbCallBacks(this);
			}
			setSharingImageClickable(true);
			break;
		case R.id.twitter:
			setSharingImageClickable(false);
			if (Utility.isNetworkNotAvailable(this)) {
				showAlertDialog(1);
			} else {
				mTwitterManager = TwitterManager.getInstance();
				mTwitterManager.setPreferences(getSharedPreferences("", 0));
				setTwitterSettings();
			}
			setSharingImageClickable(true);
			break;
		case R.id.company_website:
			AppAnalytics.getInstance().trackEvents(
					AegonConstants.WEB_URL_EVENT_19, AegonConstants.WEB_URL,
					AegonConstants.WEB_URL);
			Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri
					.parse(AegonConstants.WEB_URL));
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		showAlertDialog(2);

	}

	private void showAlertDialog(int id) {
		if (id == 1) {
			AlertDialog alertdiag = new AlertDialog.Builder(AgingActivity.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.no_connection)
					.setPositiveButton(R.string.ok, mNetworkCancelListener)
					.create();

			alertdiag.show();
		} else if (id == 2) {
			AlertDialog alertdiag = new AlertDialog.Builder(AgingActivity.this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.open_cam_title)
					.setMessage(R.string.open_cam_msg)
					.setNegativeButton(R.string.yes, mOpencamlistener)
					.setPositiveButton(R.string.no, mCancellistener).create();

			alertdiag.show();
		}
	}

	DialogInterface.OnClickListener mOpencamlistener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			finish();
			AgingActivity.this.startActivity(new Intent(AgingActivity.this,
					CameraPreview.class));
		}
	};

	DialogInterface.OnClickListener mCancellistener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	};

	DialogInterface.OnClickListener mNetworkCancelListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			setSharingImageClickable(true);
			dialog.dismiss();
			return;
		}
	};

	private void setFacebookSettings() {
		if (mFbManager.isValidSession()) {
			if (mUploadprogress != null && mUploadprogress.isShowing()) {
				mUploadprogress.dismiss();
			}
			mUploadprogress = ProgressDialog.show(AgingActivity.this, "",
					getResources().getString(R.string.uploading_image), false);
			mFbManager.updateStatusWithImage(BitmapManager.getInstance()
					.getPNGByteData());
		} else {
			mFbManager.doLogin(AgingActivity.this);
		}
	}

	private void setTwitterSettings() {
		if (mTwitterManager.checkIfAlreadyAuthenticated()) {
			uploadMyTwitpic();
		} else {
			if (LogConstants.DEBUG_ENABLED) {
				android.util.Log.w(TAG, "setTwitterSettings :: " + "CALLED");
			}
			AppAnalytics.getInstance().trackTweeterAuthEvents();
			mSessionprogress = ProgressDialog.show(AgingActivity.this, "",
					getResources().getString(R.string.checking_for_session),
					false);

			Thread loginthread = new Thread(new Runnable() {

				@Override
				public void run() {
					mTwitterManager.askOAuth(AgingActivity.this);
				}
			});
			loginthread.start();
		}
	}

	private void uploadMyTwitpic() {
		try {
			mUploadprogress = ProgressDialog.show(AgingActivity.this, "",
					getResources().getString(R.string.uploading_image), false);
			mTwitterManager.setUploadCallBack(this);
			mTwitterManager.twitterUpload(BitmapManager.getInstance()
					.getPNGImageBytes(), "My Pic");
		} catch (Exception e) {
			e.printStackTrace();
			if (mUploadprogress != null) {
				mUploadprogress.dismiss();
			}

		}
		setSharingImageClickable(true);
	}

	private void setSharingImageClickable(boolean value) {
		((ImageView) findViewById(R.id.twitter)).setClickable(value);
		((ImageView) findViewById(R.id.facebook)).setClickable(value);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (mSessionprogress != null)
			mSessionprogress.dismiss();
		setSharingImageClickable(true);
		if (intent != null && intent.getData() != null) {
			Uri uri = intent.getData();
			if (uri != null
					&& uri.toString().startsWith(AegonConstants.CALLBACK_URL)) {
				mConnectingprogress = ProgressDialog
						.show(AgingActivity.this,
								"",
								getResources().getString(
										R.string.connecting_to_server), false);
				mTwitterManager.connect(this, uri);

				if (mConnectingprogress != null)
					mConnectingprogress.dismiss();

				if (mTwitterManager.checkIfAlreadyAuthenticated()) {
					uploadMyTwitpic();
				}
			}
		}
	}

	@Override
	public void twitterUploadResponceSuccess() {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AppAnalytics.getInstance().trackTweeterPhotoUploadEvents();
				if (mUploadprogress != null)
					mUploadprogress.dismiss();
				setSharingImageClickable(true);
				// mTwitterManager.clearAccessTocken();
				Toast.makeText(
						AgingActivity.this,
						getResources().getString(
								R.string.twitter_upload_sucessfull),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void twitterUploadResponcefailed() {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mUploadprogress != null)
					mUploadprogress.dismiss();
				setSharingImageClickable(true);
				Toast.makeText(
						AgingActivity.this,
						getResources()
								.getString(R.string.twitter_upload_failed),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void responseSuccess(int requestCode, Object result) {
		setSharingImageClickable(true);
	}

	@Override
	public void responseFailure(int requestCode) {
		setSharingImageClickable(true);
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.w(TAG, "On login failed -- responce failure");
		}
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout() {
		setSharingImageClickable(true);

	}

	@Override
	public void loginSuccess() {
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.w(TAG, "On login success");
		}
		AppAnalytics.getInstance().trackFBAuthEvents();
		setSharingImageClickable(true);
		mUploadprogress = ProgressDialog.show(AgingActivity.this, "",
				getResources().getString(R.string.uploading_image), false);
		mFbManager.updateStatusWithImage(BitmapManager.getInstance()
				.getPNGByteData());

	}

	@Override
	public void onLoginFailed(String error) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mSessionprogress != null)
					mSessionprogress.dismiss();
				setSharingImageClickable(true);
				Toast.makeText(AgingActivity.this,
						getResources().getString(R.string.fb_login_failed),
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onUpload(final String response) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mUploadprogress != null)
					mUploadprogress.dismiss();
				setSharingImageClickable(true);
				if (response.contains("id")) {
					AppAnalytics.getInstance().trackFBPhotoUploadEvents();
					Toast.makeText(
							AgingActivity.this,
							getResources().getString(
									R.string.fb_upload_sucessfull),
							Toast.LENGTH_LONG).show();
				} else
					Toast.makeText(
							AgingActivity.this,
							getResources().getString(R.string.fb_upload_failed),
							Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void saveImage(final String msg) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (mSavingImageProgress != null)
					mSavingImageProgress.dismiss();
				Toast.makeText(AgingActivity.this, msg, Toast.LENGTH_LONG)
						.show();

			}
		});

	}
}
