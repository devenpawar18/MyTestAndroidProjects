package com.sharing.network;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.sharing.activities.SharingActivity;
import com.sharing.externalutils.twitter4j.Twitter;
import com.sharing.externalutils.twitter4j.TwitterException;
import com.sharing.externalutils.twitter4j.http.AccessToken;
import com.sharing.externalutils.twitter4j.http.RequestToken;

public class TwitterManager {
	private static final String TAG = "TwitterManager";
	private static TwitterManager instance = null;
	private Twitter twitter;
	private String userName;
	private SharedPreferences settings;
	private String authUrl;
	private RequestToken token;
	public static boolean networkError = false;

//	public static String CONSUMER_KEY = "R4DV2OkbcA6ElVkbbSi53A";
//	public static String CONSUMER_SECRET = "wGDXrU3qvYaZdApsY5Z11Ir55L45dQcTLECXnrhZ0";

	public static String CONSUMER_KEY = "Um6oSsBDWKdwoeU2WqwQOQ";
	public static String CONSUMER_SECRET = "bUZjAQio6jkk0phLcSsWqMFcFShXSZtHUpBqaaYxCDY";

	public static TwitterManager getInstance() {
		if (instance == null) {
			instance = new TwitterManager();
		}
		Log.d("TwitterManager", "getInstance :: " + "instance :: " + instance);
		return instance;
	}

	public void createNew() {
		if (twitter != null)
			twitter = new Twitter();
	}

	/*
	 * Checks If user is already Authenticated by Twitter.
	 */
	public boolean checkIfAlreadyAuthenticated() {
		Log.d(TAG, "checkIfAlreadyAuthenticated :: " + "settings :: "
				+ settings);
		String token = settings.getString("Token", "");
		Log.d(TAG, "checkIfAlreadyAuthenticated :: " + "token :: " + token);
		return (!token.equals(""));

	}

	public Twitter getTwitter() {
		if (twitter == null)
			twitter = new Twitter();
		return twitter;
	}

	/*
	 * Gets the Twitter User Name of the Use
	 */
	public String getUserName() {
		return userName;
	}

	/*
	 * Gets the Twitter Authorization Url of the Use
	 */
	public String getAuthorizationURL() {
		return authUrl;
	}

	/*
	 * Creates the twitter Object from stored User Creds
	 */
	public Boolean setup(String verifier) {
		try {
			AccessToken a = twitter.getOAuthAccessToken(token.getToken(),
					token.getTokenSecret(), verifier);
			twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			twitter.setOAuthAccessToken(a);
			storeUserCreds(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Creates the twitter Object from stored User Creds
	 */
	public Boolean loadUser() {
		try {
			String token = settings.getString("Token", "");
			String tokenSecret = settings.getString("TokenSecret", "");
			userName = settings.getString("UserName", "");
			getTwitter().setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
			AccessToken a = new AccessToken(token, tokenSecret);
			twitter.setOAuthAccessToken(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Stores the Twitter Credentials of User
	 */
	private void storeUserCreds(AccessToken token) {
		Log.d(TAG, "storeUserCreds :: " + "CALLED");
		Log.d(TAG,
				"storeUserCreds :: " + "token.getToken() :: "
						+ token.getToken());
		Log.d(TAG,
				"storeUserCreds :: " + "token.getTokenSecret() :: "
						+ token.getTokenSecret());
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("Token", token.getToken());
		editor.putString("TokenSecret", token.getTokenSecret());
		editor.putString("UserName", userName);
		editor.commit();
		Log.d("TwitterManager", "storeUserCreds :: " + "After token is :: "
				+ settings.getString("Token", ""));

	}

	/*
	 * Sets Up user after Authorization by Oauth of Twitter.
	 */
	public Boolean connect() {
		try {
			getTwitter().setOAuthConsumer(TwitterManager.CONSUMER_KEY,
					TwitterManager.CONSUMER_SECRET);
			token = getTwitter().getOAuthRequestToken(
					SharingActivity.CALLBACK_URL);
			if (token != null) {
				authUrl = token.getAuthorizationURL();
			}
			return !TextUtils.isEmpty(authUrl);
		} catch (Exception e) {
			twitter = null;
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * Logs Out Session for Current User
	 */
	public Boolean logout() {
		try {

			SharedPreferences.Editor editor = settings.edit();
			editor.putString("Token", "");
			editor.putString("TokenSecret", "");
			editor.putString("UserName", "");
			editor.commit();
			twitter = null;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Creates the twitter Object from stored User Creds
	 */
	public Boolean updateStatus(TwitterResponseListener listener, String twit) {
		try {
			String twitText;
			if (twit.length() > 140)
				twitText = twit.substring(0, 139);
			else
				twitText = twit;
			getTwitter().updateStatus(twitText);
			listener.twitterResponseSuccess();
			return true;
		} catch (TwitterException e) {
			e.printStackTrace();
			networkError = true;
			listener.twitterResponseFailure();
			return false;
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
}
