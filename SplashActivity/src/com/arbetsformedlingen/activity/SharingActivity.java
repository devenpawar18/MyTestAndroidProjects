package com.arbetsformedlingen.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.controller.FacebookManager;
import com.arbetsformedlingen.controller.LinkedInManager;
import com.arbetsformedlingen.controller.LinkedInManager.LinkedInResponseListener;
import com.arbetsformedlingen.controller.TwitterManager;
import com.arbetsformedlingen.controller.TwitterManager.TwitterResponseListener;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.externalutils.facebook.android.Facebook.FBResponseListener;

public class SharingActivity extends GlobalBaseActivity implements
		FBResponseListener, LinkedInResponseListener, TwitterResponseListener {

	private com.arbetsformedlingen.activity.TwitterDialog twitterDialog;
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

	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharing);
		TextView accountText = (TextView) findViewById(R.id.account);
		if (GlobalBaseActivity.currentLocale != null
				&& GlobalBaseActivity.currentLocale.equals("ar")) {
			accountText.setTypeface(Typeface.createFromAsset(this.getAssets(),
					"fonts/DejaVuSans.ttf"));
		}
		accountText.setText(R.string.account);
		TextView messageText = (TextView) findViewById(R.id.message);
		if (GlobalBaseActivity.currentLocale != null
				&& GlobalBaseActivity.currentLocale.equals("ar")) {
			messageText.setTypeface(Typeface.createFromAsset(this.getAssets(),
					"fonts/DejaVuSans.ttf"));
		}
		messageText.setText(R.string.message);
		TextView linkText = (TextView) findViewById(R.id.link);
		if (GlobalBaseActivity.currentLocale != null
				&& GlobalBaseActivity.currentLocale.equals("ar")) {
			linkText.setTypeface(Typeface.createFromAsset(this.getAssets(),
					"fonts/DejaVuSans.ttf"));
		}
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

		String message = getIntent().getStringExtra("Message");
		EditText text = (EditText) findViewById(R.id.statusMessage);
		text.setText(message);

		url = getIntent().getStringExtra("URL");

		type = getIntent().getStringExtra("Type");
		setTitleText(type);

		// socialNetTextView = (TextView)findViewById(R.id.socialNetText);
		// socialNetTextView.setText((String)getResources().getText(R.string.sign_In)
		// + " " + type);

		socialNetTextView = (TextView) findViewById(R.id.socialNetText);

		if (GlobalBaseActivity.currentLocale != null
				&& GlobalBaseActivity.currentLocale.equals("ar")) {
			socialNetTextView.setTypeface(Typeface.createFromAsset(
					this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
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
		lkdInmanager.setPreferences(getSharedPreferences(
				FilterEntity.APP_FILTER_DATA, 0));

		twitterManager = TwitterManager.getInstance();
		twitterManager.setPreferences(getSharedPreferences(
				FilterEntity.APP_FILTER_DATA, 0));

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
		if (GlobalBaseActivity.currentLocale != null
				&& GlobalBaseActivity.currentLocale.equals("ar")) {
			textButtonImage.setTypeface(Typeface.createFromAsset(
					this.getAssets(), "fonts/DejaVuSans.ttf"));
		}
		textButtonImage.setText((String) getResources().getText(R.string.send));
		if (currentLocale != null
				&& (currentLocale.equals("ru") || currentLocale.equals("ar"))) {
			textButtonImage
					.setBackgroundResource(R.layout.large_image_selector);
		}
		EditText postEditText = (EditText) findViewById(R.id.statusMessage);
		postEditText.requestFocus();

		if (type.equals(FACEBOOK_TITLE)) {
			if (fbManager.isValidSession()) {
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_out_to_facebook);
				} else {
					socialNetTextView.setText(LOG_OUT + " " + type);
				}
				textButtonImage.setEnabled(true);

			} else {
				textButtonImage.setEnabled(false);
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_in_to_facebook);
				} else {
					socialNetTextView.setText(LOG_IN + " " + type);
				}

			}
		} else if (type.equals(TWITTER_TITLE)) {
			if (twitterManager.checkIfAlreadyAuthenticated()) {
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_out_to_twitter);
				} else {
					socialNetTextView.setText(LOG_OUT + " " + type);
				}
				textButtonImage.setEnabled(true);

			} else {
				textButtonImage.setEnabled(false);
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_in_to_twitter);
				} else {
					socialNetTextView.setText(LOG_IN + " " + type);
				}
			}
		} else if (type.equals(LINKED_IN_TITLE)) {
			if (lkdInmanager.checkIfAlreadyAuthenticated()) {
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_out_to_linkedin);
				} else {
					socialNetTextView.setText(LOG_OUT + " " + type);
				}
				textButtonImage.setEnabled(true);
			} else {
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_in_to_linkedin);
				} else {
					socialNetTextView.setText(LOG_IN + " " + type);
				}
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
					if (type.equals(FACEBOOK_TITLE)) {
						if (fbManager.isValidSession()) {
							tracker.trackEvent("JobShared", FACEBOOK_TITLE,
									"Android", 1);
						}
					} else if (type.equals(TWITTER_TITLE)) {
						if (twitterManager.checkIfAlreadyAuthenticated()) {
							tracker.trackEvent("JobShared", TWITTER_TITLE,
									"Android", 2);
						}
					} else if (type.equals(LINKED_IN_TITLE)) {
						if (lkdInmanager.checkIfAlreadyAuthenticated()) {
							tracker.trackEvent("JobShared", LINKED_IN_TITLE,
									"Android", 3);
						}
					}

					AppController appController = new AppController(
							SharingActivity.this);
					String tempUrl = url;
					if (url.indexOf("http://") == -1) {
						tempUrl = "http://" + url;
					}
					String newurl = SHORTEN_URL + tempUrl + "&format=text";
					appController.getShortenURL(newurl);
					SharingActivity.this.finish();
				}
			}
		}
	};

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
			if (currentLocale != null && currentLocale.equals("ar")) {
				socialNetTextView.setText(R.string.log_in_to_linkedin);
			} else {
				socialNetTextView.setText(LOG_IN + " " + type);
			}
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
			if (currentLocale != null && currentLocale.equals("ar")) {
				socialNetTextView.setText(R.string.log_in_to_twitter);
			} else {
				socialNetTextView.setText(LOG_IN + " " + type);
			}
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
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_out_to_linkedin);
				} else {
					socialNetTextView.setText(LOG_OUT + " " + type);
				}
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
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_out_to_twitter);
				} else {
					socialNetTextView.setText(LOG_OUT + " " + type);
				}
				textButtonImage.setEnabled(true);
			}

		}
	}

	@Override
	public void refreshCompleted(Object data) {

		if (data == null || TextUtils.isEmpty((String) data)) {
			Toast.makeText(
					this,
					(String) getResources().getText(
							R.string.could_Not_Shorten_URL), 500).show();
			return;
		}

		EditText text = (EditText) findViewById(R.id.statusMessage);
		final String status = text.getText() + " " + (String) data;
		if (data instanceof String) {
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
						twitterManager.updateStatus(SharingActivity.this,
								status);
					}
				}).start();
			} else if (type.equals(FACEBOOK_TITLE)) {
				fbManager.updateStatus(status);
			}
		}
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loginSuccess() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_out_to_facebook);
				} else {
					socialNetTextView.setText(LOG_OUT + " " + type);
				}
				textButtonImage.setEnabled(true);
			}
		});
	}

	@Override
	public void logout() {
		runOnUiThread(new Runnable() {
			public void run() {
				if (currentLocale != null && currentLocale.equals("ar")) {
					socialNetTextView.setText(R.string.log_in_to_facebook);
				} else {
					socialNetTextView.setText(LOG_IN + " " + type);
				}
				textButtonImage.setEnabled(false);
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
	public void responseSuccess(int requestCode, Object result) {

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
	public void linkedInResponseSuccess() {

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
	public void twitterResponseSuccess() {

	}

}
