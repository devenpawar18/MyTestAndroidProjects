package com.aegon.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Context;
import android.os.Bundle;

import com.aegon.facebook.DialogError;
import com.aegon.facebook.Facebook;
import com.aegon.facebook.Facebook.DialogListener;
import com.aegon.facebook.Facebook.FBResponseListener;
import com.aegon.facebook.FacebookError;
import com.aegon.facebook.SessionEvents;
import com.aegon.facebook.SessionEvents.AuthListener;
import com.aegon.facebook.SessionStore;

public class FacebookManager {

	private static FacebookManager instance;

	private static final String APP_ID = "366369850054601";
	private static final String[] PERMISSIONS = { "user_photos",
			"photo_upload", "publish_checkins", "publish_actions",
			"publish_stream", "read_stream", "manage_pages" };

	private Context context;
	private facebookcallbacks myfbcallbacks;

	public interface facebookcallbacks {
		public void onLoginFailed(String error);

		public void onUpload(String response);
	}

	private FacebookManager() {
		instance = this;
	}

	public static FacebookManager getInstance() {
		if (instance == null)
			instance = new FacebookManager();
		return instance;
	}

	private AuthListener listener = new SampleAuthListener();

	public class SampleAuthListener implements AuthListener {

		public void onAuthSucceed() {

			SessionStore.save(mfb, context);
			SessionEvents.removeAuthListener(listener);
			((FBResponseListener) context).loginSuccess();
		}

		public void onAuthFail(String error) {
			myfbcallbacks.onLoginFailed(error);
		}

	}

	private Facebook mfb;

	public boolean isValidSession() {
		return mfb != null ? mfb.isSessionValid() : false;
	}

	public void doLogin(Context context) {
		mfb = new Facebook();
		this.context = context;
		SessionStore.restore(mfb, this.context);
		SessionEvents.addAuthListener(listener);
		mfb.authorize(context, APP_ID, PERMISSIONS, new LoginDialogListener());
	}

	private final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			try {
				SessionEvents.onLoginSuccess();
				mfb.setAccessToken(values.getString("access_token"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		public void onFacebookError(FacebookError error) {
			SessionEvents.onLoginError(error.getMessage());
		}

		public void onError(DialogError error) {
			SessionEvents.onLoginError(error.getMessage());
		}

		public void onCancel() {
			SessionEvents.onLoginError("Action Canceled");
		}
	}

	public void updateStatusWithImage(final byte[] imgaeBytes) {

		Thread timeLineThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Bundle params = new Bundle();
				params.putByteArray("picture", imgaeBytes);
				try {
					String response = mfb.request("me/photos", params, "POST");
					myfbcallbacks.onUpload(response);
				} catch (FileNotFoundException e) {
					myfbcallbacks.onUpload(e.toString());
				} catch (MalformedURLException e) {
					myfbcallbacks.onUpload(e.toString());
				} catch (IOException e) {
					myfbcallbacks.onUpload(e.toString());
				}
			}
		});
		timeLineThread.start();

	}

	public void setOnfbCallBacks(facebookcallbacks callobj) {
		myfbcallbacks = callobj;
	}

}
