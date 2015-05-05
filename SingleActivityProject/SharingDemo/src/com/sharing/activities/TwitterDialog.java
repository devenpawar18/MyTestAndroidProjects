package com.sharing.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sharing.network.TwitterManager;

public class TwitterDialog extends Dialog {

    static final int FB_BLUE = 0xFF6D84B4;
    static final float[] DIMENSIONS_LANDSCAPE = {460, 260};
    static final float[] DIMENSIONS_PORTRAIT = {280, 420};
    static final FrameLayout.LayoutParams FILL = 
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 
                         ViewGroup.LayoutParams.FILL_PARENT);
    static final int MARGIN = 4;
    static final int PADDING = 2;
    static final String DISPLAY_STRING = "touch";
    static final String FB_ICON = "icon.png";
    
    private String mUrl;
    private ProgressDialog mSpinner;
    private WebView mWebView;
    private LinearLayout mContent;
    private Context context;
    private int OP_CODE;
    public static final int TWITTER_DIALOG = 0;
    public static final int LINKEDIN_DIALOG = 1;
    
    public TwitterDialog(Context context, String url, int p_OP_CODE) {
        super(context);
        mUrl = url;
        this.context = context;
        OP_CODE = p_OP_CODE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage(context.getResources().getText(R.string.loading));
        mSpinner.setCancelable(false);
        
        mContent = new LinearLayout(getContext());
        mContent.setOrientation(LinearLayout.VERTICAL);
        //setUpTitle();
        setUpWebView();
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = display.getWidth() < display.getHeight() ?
        		DIMENSIONS_PORTRAIT : DIMENSIONS_LANDSCAPE;
        addContentView(mContent, new FrameLayout.LayoutParams(
        		(int) (dimensions[0] * scale + 0.5f),
        		(int) (dimensions[1] * scale + 0.5f)));
    }

    private void setUpWebView() {
        mWebView = new WebView(getContext());
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        Log.d("TwitterDialog", "setUpWebView :: " + "mUrl :: "+mUrl);
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new TwitterDialog.TwitterWebViewClient());
        mWebView.setLayoutParams(FILL);
        mContent.addView(mWebView);
    }
    
    private class TwitterWebViewClient extends WebViewClient {

    	 @Override
         public boolean shouldOverrideUrlLoading(WebView view, String url) {

    		 if(url.contains("https://"))
    			 return false;
    		 
    		 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
             getContext().startActivity(intent);
    		 return true;
    		 
    	 }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSpinner.dismiss();
        }   
        
        public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
    		 handler.proceed() ;
		}
        
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
    	// Done so that the twitter manager can be cleared of the past instance
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (OP_CODE == TWITTER_DIALOG && !TwitterManager.getInstance().checkIfAlreadyAuthenticated()) {
				TwitterManager.getInstance().logout();
			}
	    }
	    return super.onKeyDown(keyCode, event);
	}
    
    
}
