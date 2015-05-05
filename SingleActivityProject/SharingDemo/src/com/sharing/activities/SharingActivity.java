package com.sharing.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.sharing.externalutils.facebook.android.Facebook.FBResponseListener;
import com.sharing.network.FacebookManager;
import com.sharing.network.LinkedInManager;
import com.sharing.network.LinkedInManager.LinkedInResponseListener;
import com.sharing.network.TwitterManager;
import com.sharing.network.TwitterManager.TwitterResponseListener;
import com.sharing.services.ShortenUrlService;
import com.sharing.services.ShortenUrlService.GetShortenURLServiceListener;
import com.sharing.utils.SharingUtils;

public class SharingActivity extends Activity implements FBResponseListener,
		LinkedInResponseListener, TwitterResponseListener,
		GetShortenURLServiceListener {
	private Button share;
	private ProgressDialog pd;
	private TextView share_text;
	private Dialog dialog;
	private com.sharing.activities.TwitterDialog twitterDialog;
	private LinkedInManager lkdInmanager;
	private String type;
	private FacebookManager fbManager;
	private TextView socialNetTextView;
	private ImageView buttonImage;
	private TextView textButtonImage;
	private String url;
	private TwitterManager twitterManager;
	private String TWITTER_TITLE;
	private String FACEBOOK_TITLE;
	private String LINKED_IN_TITLE;
	private String LOG_IN;
	private String LOG_OUT;
	private String SUCCESS;
	private String FAILED;
	private TextView titleTextView;
	public static String CALLBACK_URL = "setting://callbackurl";
	public static GoogleAnalyticsTracker tracker;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharing);

		tracker = GoogleAnalyticsTracker.getInstance();
		// Start the tracker in manual dispatch mode...
		tracker.start(SharingUtils.GOOGLE_TRACKER_KEY, this);

		TextView accountText = (TextView) findViewById(R.id.account);
		accountText.setText(R.string.account);
		TextView messageText = (TextView) findViewById(R.id.message);
		messageText.setText(R.string.message);
		TextView linkText = (TextView) findViewById(R.id.link);
		linkText.setText(R.string.article_Link);
		TWITTER_TITLE = (String) getResources().getText(R.string.twitter);
		FACEBOOK_TITLE = (String) getResources().getText(R.string.facebook);
		LINKED_IN_TITLE = (String) getResources().getText(R.string.linkedIn);
		LOG_IN = (String) getResources().getText(R.string.sign_In);
		LOG_IN = (String) getResources().getText(R.string.sign_In);
		LOG_OUT = (String) getResources().getText(R.string.sign_Out_Of);
		SUCCESS = (String) getResources().getText(
				R.string.message_Successfully_Posted);
		FAILED = (String) getResources().getText(R.string.message_Not_Posted);
		titleTextView = (TextView) findViewById(R.id.heading);

		String message = getIntent().getStringExtra("Message");
		EditText text = (EditText) findViewById(R.id.statusMessage);
		text.setText(message);

		url = getIntent().getStringExtra("URL");

		type = getIntent().getStringExtra("Type");
		titleTextView.setText(type);

		// socialNetTextView = (TextView)findViewById(R.id.socialNetText);
		// socialNetTextView.setText((String)getResources().getText(R.string.sign_In)
		// + " " + type);

		socialNetTextView = (TextView) findViewById(R.id.socialNetText);

		socialNetTextView.setText((String) getResources().getText(
				R.string.sign_In)
				+ " " + type);

		ImageView socialNetImageView = (ImageView) findViewById(R.id.socialNetImage);

		if (type.equals(TWITTER_TITLE)) {
			socialNetImageView.setImageResource(R.drawable.twittericon);
		} else if (type.equals(FACEBOOK_TITLE)) {
			socialNetImageView.setImageResource(R.drawable.facebookicon);
		} else if (type.equals(LINKED_IN_TITLE)) {
			socialNetImageView.setImageResource(R.drawable.linkedinicon);
		}

		lkdInmanager = LinkedInManager.getInstance();
		lkdInmanager.setPreferences(getSharedPreferences("token1", 0));

		twitterManager = TwitterManager.getInstance();
		twitterManager.setPreferences(getSharedPreferences("token", 0));

		fbManager = FacebookManager.getInstance();

		RelativeLayout socialNetLayout = (RelativeLayout) findViewById(R.id.socialNetLayout);
		socialNetLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (type.equals(TWITTER_TITLE)) {
					setTwitterSettings();
				} else if (type.equals(FACEBOOK_TITLE)) {
					setFacebookSettings();
				} else if (type.equals(LINKED_IN_TITLE)) {
					setLinkedInSettings();
				}

			}

		});

		textButtonImage = (TextView) findViewById(R.id.button_image);
		textButtonImage.setOnFocusChangeListener(focusListener);
		textButtonImage.setVisibility(View.VISIBLE);
		textButtonImage.setText((String) getResources().getText(R.string.send));
		EditText postEditText = (EditText) findViewById(R.id.statusMessage);
		postEditText.requestFocus();

		if (type.equals(FACEBOOK_TITLE)) {
			if (fbManager.isValidSession()) {
				socialNetTextView.setText(LOG_OUT + " " + type);
				textButtonImage.setEnabled(true);

			} else {
				textButtonImage.setEnabled(false);
				socialNetTextView.setText(LOG_IN + " " + type);

			}
		} else if (type.equals(TWITTER_TITLE)) {
			if (twitterManager.checkIfAlreadyAuthenticated()) {
				socialNetTextView.setText(LOG_OUT + " " + type);
				textButtonImage.setEnabled(true);

			} else {
				textButtonImage.setEnabled(false);
				socialNetTextView.setText(LOG_IN + " " + type);
			}
		} else if (type.equals(LINKED_IN_TITLE)) {
			if (lkdInmanager.checkIfAlreadyAuthenticated()) {
				socialNetTextView.setText(LOG_OUT + " " + type);
				textButtonImage.setEnabled(true);
			} else {
				socialNetTextView.setText(LOG_IN + " " + type);
				textButtonImage.setEnabled(false);
			}
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private OnFocusChangeListener focusListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			Log.d("SharingActivity.focusListener", "onFocusChange :: "
					+ "hasFocus :: " + hasFocus);
			if (hasFocus) {
				if (v.isEnabled()) {
//					if (type.equals(FACEBOOK_TITLE)) {
//						if (fbManager.isValidSession()) {
//							tracker.trackEvent("JobShared", FACEBOOK_TITLE,
//									"Android", 1);
//						}
//					} else if (type.equals(TWITTER_TITLE)) {
//						if (twitterManager.checkIfAlreadyAuthenticated()) {
//							tracker.trackEvent("JobShared", TWITTER_TITLE,
//									"Android", 2);
//						}
//					} else if (type.equals(LINKED_IN_TITLE)) {
//						if (lkdInmanager.checkIfAlreadyAuthenticated()) {
//							tracker.trackEvent("JobShared", LINKED_IN_TITLE,
//									"Android", 3);
//						}
//					}

					String tempUrl = url;
					if (url.indexOf("http://") == -1) {
						tempUrl = "http://" + url;
					}
					SharingUtils.NEW_URL = SharingUtils.SHORTEN_URL + tempUrl
							+ "&format=text";
					getShortenURL();
					SharingActivity.this.finish();
				}
			}
		}
	};

	public void getShortenURL() {
		ShortenUrlService service = new ShortenUrlService();
		service.setListener(this);
		SharingApplication.operationsQueue.execute(service);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return true;
	}

	private void setLinkedInSettings() {
		if (lkdInmanager.checkIfAlreadyAuthenticated()) {
			lkdInmanager.logout();
			socialNetTextView.setText(LOG_IN + " " + type);
			textButtonImage.setEnabled(false);
		} else {
			pd = ProgressDialog.show(this, "",
					(String) getResources().getText(R.string.loading));
			new Thread(new Runnable() {
				public void run() {
					if (lkdInmanager.connect()) {
						linkedInOauthSuccess();
					} else {
						linkedInOauthFailed();
					}
				};

			}).start();
		}
	}

	private void setFacebookSettings() {
		if (fbManager.isValidSession()) {
			fbManager.doLogout();

		} else {
			fbManager.doLogin(this);
		}

	}

	private void setTwitterSettings() {
		if (twitterManager.checkIfAlreadyAuthenticated()) {
			twitterManager.logout();
			socialNetTextView.setText(LOG_IN + " " + type);
			textButtonImage.setEnabled(false);
		} else {
			Log.d("SharingActivity", "setTwitterSettings :: " + "CALLED");
			pd = ProgressDialog.show(this, "",
					(String) getResources().getText(R.string.loading));
			new Thread(new Runnable() {
				public void run() {
					if (twitterManager.connect()) {
						twitterOauthSuccess();
					} else {
						twitterOauthFailed();
					}
				};

			}).start();

		}
	}

	private void twitterOauthSuccess() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (pd != null) {
					pd.cancel();
					pd = null;
				}
				twitterDialog = new TwitterDialog(SharingActivity.this,
						Uri.parse(twitterManager.getAuthorizationURL())
								.toString(), TwitterDialog.TWITTER_DIALOG);
				twitterDialog.show();
			}
		});
	}

	private void linkedInOauthSuccess() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (pd != null) {
					pd.cancel();
					pd = null;
				}
				twitterDialog = new TwitterDialog(SharingActivity.this, Uri
						.parse(lkdInmanager.getAuthorizationUrl()).toString(),
						TwitterDialog.LINKEDIN_DIALOG);
				twitterDialog.show();

			}
		});
	}

	private void twitterOauthFailed() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (pd != null) {
					pd.cancel();
					pd = null;
				}
				Toast.makeText(
						SharingActivity.this,
						TWITTER_TITLE
								+ (String) getResources().getText(
										R.string.login_Is_Not_Successful), 500)
						.show();
			}
		});

	}

	private void linkedInOauthFailed() {

		runOnUiThread(new Runnable() {
			public void run() {
				if (pd != null) {
					pd.cancel();
					pd = null;
				}
				Toast.makeText(
						SharingActivity.this,
						LINKED_IN_TITLE
								+ (String) getResources().getText(
										R.string.login_Is_Not_Successful), 500)
						.show();
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {

		super.onNewIntent(intent);
		Log.d("SettingsActivity", "onNewIntent :: " + "CALLED");
		Uri uri = intent.getData();
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {

			String verifier = "";

			if (twitterDialog != null && twitterDialog.isShowing())
				twitterDialog.dismiss();

			if (type.equals(LINKED_IN_TITLE)) {
				verifier = uri.getQueryParameter("oauth_verifier");
				if (!lkdInmanager.setup(verifier)) {
					Toast.makeText(
							SharingActivity.this,
							LINKED_IN_TITLE
									+ (String) getResources().getText(
											R.string.login_Is_Not_Successful),
							500).show();
					return;
				}
				socialNetTextView.setText(LOG_OUT + " " + type);
				textButtonImage.setEnabled(true);
			} else if (type.equals(TWITTER_TITLE)) {
				verifier = uri.getQueryParameter("oauth_verifier");
				if (!twitterManager.setup(verifier)) {
					Toast.makeText(
							SharingActivity.this,
							TWITTER_TITLE
									+ (String) getResources().getText(
											R.string.login_Is_Not_Successful),
							500).show();
					return;
				}
				socialNetTextView.setText(LOG_OUT + " " + type);
				textButtonImage.setEnabled(true);
			}

		}
	}

	@Override
	public void twitterResponseSuccess() {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(SharingActivity.this, SUCCESS, 500).show();
			}
		});

	}

	@Override
	public void twitterResponseFailure() {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(SharingActivity.this, FAILED, 500).show();
			}
		});

	}

	@Override
	public void linkedInResponseSuccess() {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(SharingActivity.this, SUCCESS, 500).show();
			}
		});

	}

	@Override
	public void linkedInResponseFailure() {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(SharingActivity.this, FAILED, 500).show();
			}
		});
	}

	@Override
	public void responseSuccess(int requestCode, Object result) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(SharingActivity.this, SUCCESS, 500).show();
			}
		});

	}

	@Override
	public void responseFailure(int requestCode) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(SharingActivity.this, FAILED, 500).show();
			}
		});

	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout() {
		runOnUiThread(new Runnable() {
			public void run() {
				socialNetTextView.setText(LOG_IN + " " + type);
				textButtonImage.setEnabled(false);
			}
		});
	}

	@Override
	public void loginSuccess() {
		runOnUiThread(new Runnable() {
			public void run() {
				socialNetTextView.setText(LOG_OUT + " " + type);
				textButtonImage.setEnabled(true);
			}
		});
	}

	@Override
	public void onGetShortenURLFinished(String resp) {
		EditText text = (EditText) findViewById(R.id.statusMessage);
		final String status = text.getText() + " " + resp;
		if (type.equals(LINKED_IN_TITLE)) {
			// lkdInmanager.loadUser();
			new Thread(new Runnable() {
				public void run() {
					lkdInmanager.updateStatus(SharingActivity.this, status);
				}
			}).start();
		} else if (type.equals(TWITTER_TITLE)) {
			new Thread(new Runnable() {
				public void run() {
					twitterManager.loadUser();
					twitterManager.updateStatus(SharingActivity.this, status);
				}
			}).start();
		} else if (type.equals(FACEBOOK_TITLE)) {
			fbManager.updateStatus(status);
		}
	}

	@Override
	public void onGetShortenURLFailed(String error) {
		Toast.makeText(this,
				getResources().getString(R.string.could_Not_Shorten_URL),
				Toast.LENGTH_SHORT).show();
	}

}