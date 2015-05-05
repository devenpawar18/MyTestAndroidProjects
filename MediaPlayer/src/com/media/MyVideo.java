package com.media;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

public class MyVideo extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		http://rescueddoggies.com/videos/MP4/Alegria+Moreno-20100302-1_x264.mp4
//		http://cdnbakmi.kaltura.com/p/623862/sp/62386200/serveFlavor/flavorId/1_syb2br5b/name/1_syb2br5b.mp3
		MyAudioView video = new MyAudioView(this);
		video.setMediaController(new MediaController(this));
		super.onCreate(savedInstanceState);
		video.setAudioURI(Uri.parse("http://cdnbakmi.kaltura.com/p/623862/sp/62386200/serveFlavor/flavorId/1_syb2br5b/name/1_syb2br5b.mp3"));
//		video.setImageResource(R.drawable.icon);
		setContentView(video);
	}
}
