package com.sogeti.appitecture.activities;

import com.sogeti.appitecture.AssetHelper;
import com.sogeti.appitecture.R;
import com.sogeti.appitecture.entities.App;

import android.app.Activity;
import android.os.Bundle;

import android.webkit.WebView;

public class AppActivity extends Activity
{
	private App app;

	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.app);
        
        if(savedInstanceState != null)
			app = savedInstanceState.getParcelable("app");
        else
        	app = getIntent().getExtras().getParcelable("app");
        
        String appTemplate = AssetHelper.getStringFromAssetFile(this, "templates/AppTemplate.html");
        
        appTemplate = appTemplate.replaceAll("_icon_", app.getIcon());
        appTemplate = appTemplate.replaceAll("_name_", app.getName());
        appTemplate = appTemplate.replaceAll("_developer_", app.getDeveloper());
        appTemplate = appTemplate.replaceAll("_description_", app.getDescription());
        
        WebView webview = (WebView) findViewById(R.id.webview);
        
        webview.loadDataWithBaseURL(null, appTemplate, "text/html", "utf-8", null);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		savedInstanceState.putParcelable("app", app);
		
	    super.onSaveInstanceState(savedInstanceState);
	}
}