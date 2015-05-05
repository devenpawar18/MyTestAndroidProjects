package com.media;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaPlayerActivity extends Activity {
	/** Called when the activity is first created. */
	private static final String path = "http://www.ted.com/talks/download/video/8584/talk/761";
	private static final String path1 = "http://cdnbakmi.kaltura.com/p/623862/sp/62386200/serveFlavor/flavorId/1_syb2br5b/name/1_syb2br5b.mp3";
	// "http://commonsware.com/misc/test2.3gp";

	private VideoView video;
	private SurfaceView ctlr;
	MediaController mctrl;
	private MediaPlayer mMediaPlayer;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		mctrl = new MediaController(this);
		// mctrl = (MediaController) findViewById(R.id.surface_view);

		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(path1);
			mMediaPlayer.prepare();

			// mMediaPlayer.setDisplay(mctrl);
			mctrl.setAnchorView(mctrl);
			// mctrl.show();
			mMediaPlayer.start();

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// TODO Auto-generated method stub
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

}