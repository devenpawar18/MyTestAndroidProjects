package com.sogeti.appitecture.services;

import com.sogeti.appitecture.HTTPRequest;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class GetImageService implements Runnable
{
	public interface GetImageServiceListener
	{
		void onGetImageFinished(GetImageService getImageService, Drawable image);
		void onGetImageFailed(GetImageService getImageService, String error);
	}
	
	private GetImageServiceListener listener;
	private Drawable image;
	private String imageURL;
	private String errorMessage;
	
	public void run()
	{		
		HTTPRequest request = new HTTPRequest(imageURL);

		try
		{
			request.execute(HTTPRequest.RequestMethod.GET);
			image = request.getResponseDrawable();
		}
		catch(Exception e)
		{
			image = null;
			errorMessage = e.getLocalizedMessage();
		}
		
		handler.sendEmptyMessage(0);
	}
	
	private Handler handler = new Handler()
	{
        @Override
        public void handleMessage(Message msg)
        {
        	if(image != null)
        		listener.onGetImageFinished(GetImageService.this, image);
			else
				listener.onGetImageFailed(GetImageService.this, errorMessage);
        }
	};
	
	public GetImageServiceListener getListener()
	{
		return listener;
	}

	public void setListener(GetImageServiceListener listener)
	{
		this.listener = listener;
	}

	public String getImageURL()
	{
		return imageURL;
	}

	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}
}