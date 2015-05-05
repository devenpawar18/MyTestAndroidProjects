package com.videoplayer.activity;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoDemo extends Activity {
	/** Called when the activity is first created. */
	private static final String path = "http://www.ted.com/talks/download/video/8584/talk/761";
	private static final String path1 = "http://cdnbakmi.kaltura.com/p/623862/sp/62386200/serveFlavor/flavorId/1_syb2br5b/name/1_syb2br5b.mp3";
	// "http://commonsware.com/misc/test2.3gp";

	private VideoView video;
	private MediaController ctlr;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.main);
		video = (VideoView) findViewById(R.id.surface_view);
		ctlr = new MediaController(this);

		ctlr.setAnchorView(video);
		ctlr.setMediaPlayer(video);
//		ctlr.set
		Uri uri = Uri.parse(path1);
		video.setMediaController(ctlr);
		video.setVideoURI(uri);
		video.setBackgroundResource(R.drawable.nestleBlue);
		video.start();

	}

}