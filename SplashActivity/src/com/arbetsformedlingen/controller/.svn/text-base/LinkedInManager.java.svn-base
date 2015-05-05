package com.arbetsformedlingen.controller;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.arbetsformedlingen.activity.GlobalBaseActivity;
import com.arbetsformedlingen.externalutils.linkedinapi.client.LinkedInApiClient;
import com.arbetsformedlingen.externalutils.linkedinapi.client.LinkedInApiClientFactory;
import com.arbetsformedlingen.externalutils.linkedinapi.client.oauth.LinkedInAccessToken;
import com.arbetsformedlingen.externalutils.linkedinapi.client.oauth.LinkedInOAuthService;
import com.arbetsformedlingen.externalutils.linkedinapi.client.oauth.LinkedInOAuthServiceFactory;
import com.arbetsformedlingen.externalutils.linkedinapi.client.oauth.LinkedInRequestToken;


public class LinkedInManager {
	private static LinkedInManager instance = null;
	private LinkedInRequestToken linkedInToken;
	LinkedInApiClientFactory factory;
	LinkedInOAuthService oAuthService;
	private LinkedInApiClient client;
	private String authUrl;
	private SharedPreferences settings;
	
	public String getAuthorizationUrl() {
		return authUrl;
	}


	public static boolean networkError = false;
	public static String CONSUMER_KEY = "UzDRXwlclaKFqUuXPnVVi4nU7sTdcQDuzWQku943_Ccxvle4_hT5ZDYaa1fYFJ0G";
	public static String CONSUMER_SECRET = "amy_FV5Bh_gQ-_O5xA5VbU706dwAUN6DyS2JsSoCOCXYodyJVEYzuzhUpOPgZJ7X";

	public static LinkedInManager getInstance() {
		if (instance == null) {
			instance = new LinkedInManager();
		}
		return instance;
	}

	/*
	 * Checks If user is already Authenticated by LinkedIn.
	 */
	public boolean checkIfAlreadyAuthenticated() {
		String token = settings.getString("LinkedInToken", "");
		Log.d("TwitterManager", "checkIfAlreadyAuthenticated :: " + "token :: "+token);
		return (!token.equals(""));

	}


	/*
	 * Sets Up user after Authorization by Oauth of LinkedIn.
	 */
	public Boolean connect() {
		try {
			oAuthService = LinkedInOAuthServiceFactory.getInstance().createLinkedInOAuthService(CONSUMER_KEY, CONSUMER_SECRET);
			factory = LinkedInApiClientFactory.newInstance(CONSUMER_KEY, CONSUMER_SECRET);         
			linkedInToken = oAuthService.getOAuthRequestToken(GlobalBaseActivity.CALLBACK_URL);
			storeToken(linkedInToken);
			authUrl = linkedInToken!=null?linkedInToken.getAuthorizationUrl():"";			         
			Log.d("TwitterManager", "setUp :: " + "authUrl :: "+authUrl);
			return !TextUtils.isEmpty(authUrl);
			
		} catch (Exception e) {
			Log.v("", e.toString());
			e.printStackTrace();
			return false;
		}

	}

	/*
	 * Creates the LinkedIn Object from stored User Creds
	 */
	public Boolean setup(String verifier) {
		try {
			Log.d("TwitterManager", "setup :: " + "CALLED");
			String token = settings.getString("LinkedInToken", "");
	        String tokenSecret = settings.getString("LinkedInTokenSecret", "");
	        authUrl = settings.getString("LinkedInAuthUrl", "");
	        linkedInToken = new LinkedInRequestToken(token,tokenSecret);
	        linkedInToken.setAuthorizationUrl(authUrl);
	        storeTokenVerifier(verifier);
	        LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(linkedInToken, verifier);                 
	        client = factory.createLinkedInApiDomClient(accessToken); 
	        return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
    }
	
	public Boolean loadUser() {
		try {
			Log.d("TwitterManager", "loadUser :: " + "CALLED");
			String token = settings.getString("LinkedInToken", "");
	        String tokenSecret = settings.getString("LinkedInTokenSecret", "");
	        String verifier = settings.getString("LinkedInTokenVerifier", "");
	        authUrl = settings.getString("LinkedInAuthUrl", "");
	        linkedInToken = new LinkedInRequestToken(token,tokenSecret);
	        linkedInToken.setAuthorizationUrl(authUrl);
	        LinkedInAccessToken accessToken = oAuthService.getOAuthAccessToken(linkedInToken, verifier);                 
	        client = factory.createLinkedInApiDomClient(accessToken);
	        return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
    }

	private void storeTokenVerifier(String verifier) {

		SharedPreferences.Editor editor = settings.edit();
		editor.putString("LinkedInTokenVerifier", verifier);
		editor.commit();
		
	}

			
	/*
	 * Stores the LinkedIn Credentials of User
	 */
	private void storeToken(LinkedInRequestToken token) {
		Log.d("TwitterManager", "storeUserCreds :: " + "CALLED");
		Log.d("TwitterManager", "storeUserCreds :: " + "token.getToken() :: "+token.getToken());
		Log.d("TwitterManager", "storeUserCreds :: " + "token.getTokenSecret() :: "+token.getTokenSecret());
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("LinkedInToken", token.getToken());
		editor.putString("LinkedInTokenSecret", token.getTokenSecret());
		editor.putString("LinkedInAuthUrl", token.getAuthorizationUrl());
		editor.commit();
		Log.d("TwitterManager", "storeUserCreds :: " + "After token is :: "+settings.getString("Token", ""));
		
	}

	/*
	 * Logs Out Session for Current User
	 */
	public Boolean logout() {
		try {

			SharedPreferences.Editor editor = settings.edit();
			editor.putString("LinkedInToken", "");
			editor.putString("LinkedInTokenSecret", "");
			editor.putString("LinkedInAuthUrl", "");
			editor.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * Creates the LinkedIn Object from stored User Creds
	 */
	public Boolean updateStatus(LinkedInResponseListener listener, String status) {
		try {
			String message;
			if (status.length() > 140)
				message = status.substring(0, 139);
			else
				message = status;
			Log.d("TwitterManager", "message :: " +message);
			if(client ==null ) {
				Log.d("TwitterManager", "client null");
				listener.linkedInResponseFailure();
				return false;
				}
			
			client.updateCurrentStatus(message);
			listener.linkedInResponseSuccess();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			networkError = true;
			listener.linkedInResponseFailure();
			return false;
		}

	}

	public void setPreferences(SharedPreferences settings) {
		if (this.settings == null)
			this.settings = settings;
	}
	
	public interface LinkedInResponseListener {
		public void linkedInResponseSuccess();
		public void linkedInResponseFailure();
	}
}
