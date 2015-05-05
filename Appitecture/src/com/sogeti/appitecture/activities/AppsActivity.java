package com.sogeti.appitecture.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sogeti.appitecture.ApplicationEx;
import com.sogeti.appitecture.R;
import com.sogeti.appitecture.Data;
import com.sogeti.appitecture.entities.App;
import com.sogeti.appitecture.services.GetAppsService;
import com.sogeti.appitecture.services.GetAppsService.GetAppsServiceListener;
import com.sogeti.appitecture.services.GetImageService;
import com.sogeti.appitecture.services.GetImageService.GetImageServiceListener;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AppsActivity extends ListActivity implements GetAppsServiceListener, GetImageServiceListener, OnScrollListener
{
	private static final String TAG = "MainActivity";
	private AppsListAdapter appsListAdapter;
	private Map<String, Drawable> images; // Drawables in HashMap should be saved as WeakReference or SoftReference
	private boolean scrolling;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps);
              
        images = new HashMap<String, Drawable>();
        
        appsListAdapter = new AppsListAdapter();
        setListAdapter(appsListAdapter);
        
        getListView().setOnScrollListener(this);
        
        if(Data.getInstance().getApps().size() > 0)
    		appsListAdapter.notifyDataSetChanged();

        getApps();
    }
    
	public void getApps()
	{
		GetAppsService service = new GetAppsService();
		service.setListener(this);
		ApplicationEx.operationsQueue.execute(service);
	}

	@Override
	public void onGetAppsFinished(ArrayList<App> apps)
	{
    	Log.d(TAG, "onGetAppsFinished: " + apps.size() + " apps loaded");

    	Data.getInstance().setApps(apps);
    	Data.getInstance().saveApps();
		appsListAdapter.notifyDataSetChanged();
	}

	@Override
	public void onGetAppsFailed(String error)
	{
		Log.d(TAG, "onGetAppsFailed: " + error);
	}
	
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
    	if(scrollState == OnScrollListener.SCROLL_STATE_IDLE)
    	{
            scrolling = false;
            for(int i = 0; i < view.getChildCount(); i++)
            {
            	 ImageView imageView = (ImageView)view.getChildAt(i).findViewById(R.id.image);
            	 if(imageView != null)
            	 {
            		 String imageURL = (String)imageView.getTag();
            		 if(imageURL != null && !images.containsKey(imageURL))
            			 getImage(imageURL);
            	 }
            }
        }
    	else
            scrolling = true;
    }
    
    @Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) { }
    
	private void getImage(String imageURL)
	{
		if(!images.containsKey(imageURL))
		{
			images.put(imageURL, null);
			GetImageService service = new GetImageService();
			service.setListener(this);
			service.setImageURL(imageURL);
        	ApplicationEx.operationsQueue.execute(service);
		}
	}

	@Override
	public void onGetImageFinished(GetImageService getImageService, Drawable image)
	{
		if(image != null)
		{
			images.put(getImageService.getImageURL(), image);
			
			ImageView imageView = (ImageView) getListView().findViewWithTag(getImageService.getImageURL());
			
			if(imageView != null)
				imageView.setImageDrawable(image);
		}
	}

	@Override
	public void onGetImageFailed(GetImageService getImageService, String error)
	{
		images.remove(getImageService.getImageURL());

		Log.e(TAG, "onGetImageFailed: " + error);
	}
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id)
	{
        super.onListItemClick(l, v, position, id);
        
        App app = Data.getInstance().getApps().get(position);
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("app", app);
	    startActivity(intent);
    }
	
	private class AppsListAdapter extends BaseAdapter
	{
        public int getCount()
        {
        	if(Data.getInstance().getApps() != null)
        		return Data.getInstance().getApps().size();
        	else
        		return 0;
        }
       
        public Object getItem(int position)
        {
            return position;
        }

        public long getItemId(int position)
        {
            return position;
        }
     
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View view = convertView;
            if(view == null)
            {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.apps_row, null);
            }
            
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView description = (TextView) view.findViewById(R.id.description);
            ImageView imageView = (ImageView) view.findViewById(R.id.image); 
            
            App app = Data.getInstance().getApps().get(position);
            name.setText(app.getName());
            description.setText(app.getDescription());
            String imageURL = app.getIcon();
    		imageView.setTag(imageURL);
    		
    		if(images.containsKey(imageURL) && images.get(imageURL) != null)
    		{
    			imageView.setImageDrawable(images.get(imageURL));
    		}
    		else
    		{
    			imageView.setImageResource(R.drawable.noimage);
    			if(!scrolling)
    				getImage(imageURL);
    		}

            return view;	
        }
	}
}