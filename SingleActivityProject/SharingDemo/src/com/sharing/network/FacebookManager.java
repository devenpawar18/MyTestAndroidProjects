package com.sharing.network;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.content.Context;
import android.os.Bundle;

import com.sharing.externalutils.facebook.android.AsyncFacebookRunner;
import com.sharing.externalutils.facebook.android.AsyncFacebookRunner.RequestListener;
import com.sharing.externalutils.facebook.android.DialogError;
import com.sharing.externalutils.facebook.android.Facebook;
import com.sharing.externalutils.facebook.android.Facebook.DialogListener;
import com.sharing.externalutils.facebook.android.Facebook.FBResponseListener;
import com.sharing.externalutils.facebook.android.FacebookError;
import com.sharing.externalutils.facebook.android.SessionEvents;
import com.sharing.externalutils.facebook.android.SessionEvents.AuthListener;
import com.sharing.externalutils.facebook.android.SessionStore;

public class FacebookManager {

	public static final int PERMISSIONREQUESTCODE = 1;
	public static final int MESSAGEPUBLISHED = 2;

	public static final int REQUEST_LOGIN = 0;// FBRequestDelegateImpl
	public static final int REQUEST_LOGOUT = 1;
	public static final int REQUEST_STATUS_UPDATE = 2;
	public static final int REQUEST_GET_TIMELINE = 3;
	public static final int REQUEST_USER_REQUEST = 4;// FBUserRequest
	public static final int REQUEST_USER_LIKE = 5;
	public static final int REQUEST_USER_COMMENT = 6;

	private static boolean isPermissionRequired = true;
	private static int REQUEST_GET_PERMISSION = 50;
	public static final int TIMEOUT = 30000;

	private static FacebookManager instance;
	// private static final String kApiKey =
	// "f1f58f359c1712352b930c82907477cc";//yaRSS
	// private static final String kApiKey =
	// "c5f732428e3c689623168b7b9e8fbf86";//xpress it for iphone
	// private static final String kApiKey =
	// "8dd6d2c272557103d07c1d613d19cd9d";//xpressit for android
	// private static final String kApiKey = "8dd6d2c272557103d07c1d613d19cd9d";
	// Enter either your API secret or a callback URL (as described in
	// documentation):
	// private static final String kApiSecret =
	// "57f55ad6a6ce45f6fa9a8bed951fc3e3";//"5782aae6148ab675874a9c6aeba5dbf8"
	// private static final String kApiSecret =
	// "e70fc66e0b5b8d1acb9b98dcc33543ba";//xpress it for iphone
	// private static final String kApiSecret =
	// "7121870dcfa4e76d23cd8c25c3be68ac";//xpress it for android
//	private static final String kApiSecret = "7121870dcfa4e76d23cd8c25c3be68ac";// xpress
//																				// it
//																				// for
//																				// android
	
	private static final String kApiSecret = "276625162406952";

	private static final String kGetSessionProxy = null; // "<YOUR SESSION CALLBACK)>";
	// public final static String APP_ID = "188060077896404";
	public final static String APP_ID = "276625162406952";
//	public final static String APP_ID = "175729095772478";
	public static final String[] PERMISSIONS = new String[] { "publish_stream",
			"read_stream" };

	private Context context;
	private int requestcode;

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

	public void doLogout() {
		SessionStore.clear(context);
		new AsyncFacebookRunner(mfb).logout(context, new RequestListener() {
			public void onComplete(String response) {
				((FBResponseListener) context).logout();
			}

			public void onFileNotFoundException(FileNotFoundException error) {
				((FBResponseListener) context).logout();
			}

			public void onIOException(IOException error) {
				((FBResponseListener) context).logout();
			}

			public void onMalformedURLException(MalformedURLException error) {
				((FBResponseListener) context).logout();
			}

			public void onFacebookError(FacebookError error) {
				((FBResponseListener) context).logout();
			}
		});
	}

	private final class LoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			try {
				SessionEvents.onLoginSuccess();
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

	public void updateStatus(final String message) {
		SessionStore.restore(mfb, context);
		Thread timeLineThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Bundle params = new Bundle();
				params.putString("message", message);
				try {
					requestcode = FacebookManager.REQUEST_STATUS_UPDATE;
					mfb.request("me/feed", params, "POST");
					((FBResponseListener) context).responseSuccess(requestcode,
							null);
				} catch (FileNotFoundException e) {
					((FBResponseListener) context).responseFailure(requestcode);

				} catch (MalformedURLException e) {
					((FBResponseListener) context).responseFailure(requestcode);

				} catch (IOException e) {
					((FBResponseListener) context).responseFailure(requestcode);
				}
			}
		});
		timeLineThread.start();
	}

	public void postComment(final String post_id, final String message) {
		requestcode = REQUEST_USER_COMMENT;
		SessionStore.restore(mfb, context);
		Thread commentThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Bundle params = new Bundle();
				params.putString("message", message);

				try {
					mfb.request(post_id + "/comments", params, "POST");
					((FBResponseListener) context).responseSuccess(requestcode,
							null);
				} catch (FileNotFoundException e) {
					((FBResponseListener) context).responseFailure(requestcode);

				} catch (MalformedURLException e) {
					((FBResponseListener) context).responseFailure(requestcode);

				} catch (IOException e) {
					((FBResponseListener) context).responseFailure(requestcode);
				}

			}

		});
		commentThread.start();

	}

}
