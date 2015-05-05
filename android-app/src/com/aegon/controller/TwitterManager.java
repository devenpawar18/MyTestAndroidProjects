package com.aegon.controller;

import java.io.IOException;
import java.io.InputStream;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.aegon.AegonConstants;
import com.aegon.LogConstants;

public class TwitterManager {
	private static TwitterManager instance = null;
	private Twitter twitter;
	private String userName;
	private SharedPreferences settings;
	private static boolean networkError = false;
	private static final String CONSUMER_KEY = "4J29MXX65KPHfn0A3NvBKw";
	private static final String CONSUMER_SECRET = "ubbKhmz0CZBD55WcAnOEyak6G1Rf9fNvpStQ7Gao";
	private OAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;

	public interface UploadCallBack {
		public void twitterUploadResponceSuccess();

		public void twitterUploadResponcefailed();
	}

	UploadCallBack twituploadcallback;

	public static TwitterManager getInstance() {
		if (instance == null) {
			instance = new TwitterManager();
		}
		Log.d("TwitterManager", "getInstance :: " + "instance :: " + instance);
		return instance;
	}

	public void createNew() {
		if (twitter != null)
			twitter = new TwitterFactory().getInstance();
	}

	/*
	 * Checks If user is already Authenticated by Twitter.
	 */
	public boolean checkIfAlreadyAuthenticated() {
		AccessToken a = getAccessToken();
		if (a == null)
			return false; // if there are no credentials stored then return to
							// usual activity
		else {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			twitter.setOAuthAccessToken(a);
			return true;
		}
	}

	public Twitter getTwitter() {
		if (twitter == null)
			twitter = new TwitterFactory().getInstance();
		return twitter;
	}

	/*
	 * Stores the Twitter Credentials of User
	 */
	private void storeUserCreds(AccessToken token) {
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d("TwitterManager", "storeUserCreds :: "
					+ "CALLED");
			android.util.Log.d("TwitterManager", "storeUserCreds :: "
					+ "token.getToken() :: " + token.getToken());
			android.util.Log.d("TwitterManager", "storeUserCreds :: "
					+ "token.getTokenSecret() :: " + token.getTokenSecret());
			android.util.Log.w("TwitterManager", " Enter storeUserCreds()");
		}

		SharedPreferences.Editor editor = settings.edit();
		editor.putString("Token", token.getToken());
		editor.putString("TokenSecret", token.getTokenSecret());
		editor.putString("UserName", userName);
		editor.commit();
		if (LogConstants.DEBUG_ENABLED) {
			android.util.Log.d("TwitterManager", "storeUserCreds :: "
					+ "After token is :: " + settings.getString("Token", ""));
		}
	}

	public void setPreferences(SharedPreferences settings) {
		if (this.settings == null)
			this.settings = settings;
	}

	public interface TwitterResponseListener {
		public void twitterResponseSuccess();

		public void twitterResponseFailure();
	}

	private AccessToken getAccessToken() {
		String token = settings.getString("Token", "");
		String tokenSecret = settings.getString("TokenSecret", "");
		if (token != null && tokenSecret != null && !"".equals(tokenSecret)
				&& !"".equals(token)) {
			return new AccessToken(token, tokenSecret);
		}
		return null;
	}

	/**
	 * Open the browser and asks the user to authorize the app. Afterwards, we
	 * redirect the user back here!
	 */
	public void askOAuth(Context context) {
		try {
			consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY,
					CONSUMER_SECRET);
			provider = new DefaultOAuthProvider(
					"https://api.twitter.com/oauth/request_token",
					"https://api.twitter.com/oauth/access_token",
					"https://api.twitter.com/oauth/authorize");

			String authUrl = provider.retrieveRequestToken(consumer,
					AegonConstants.CALLBACK_URL);
			context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(authUrl)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void twitterUpload(final InputStream img, final String msg)
			throws Exception {

		Thread twitteruploadthread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ImageUploadFactory factory = new ImageUploadFactory();

					ImageUpload upload = factory.getInstance(
							MediaProvider.TWITTER, twitter.getAuthorization());
					String URL = upload.upload("MY PIC", img, msg);

					if (!TextUtils.isEmpty(URL) && URL.contains("My Pic"))
						twituploadcallback.twitterUploadResponceSuccess();
					else
						twituploadcallback.twitterUploadResponcefailed();

				} catch (TwitterException e) {
					twituploadcallback.twitterUploadResponcefailed();
					e.printStackTrace();
				} finally {
					try {
						img.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
		twitteruploadthread.start();
	}

	/*
	 * Sets Up user after Authorization by Oauth of Twitter.
	 */
	public void connect(Context context, Uri uri) {
		String verifier = uri
				.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
		try {
			provider.retrieveAccessToken(consumer, verifier);
			AccessToken a = new AccessToken(consumer.getToken(),
					consumer.getTokenSecret());
			storeUserCreds(a);

			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			twitter.setOAuthAccessToken(a);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void setUploadCallBack(UploadCallBack call) {
		this.twituploadcallback = call;
	}

	public void clearAccessTocken() {
		twitter.shutdown();
	}

}
