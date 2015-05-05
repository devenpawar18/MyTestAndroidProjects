package com.aegon.activities;

import java.util.Calendar;
import java.util.TimerTask;

import nl.sogeti.android.aegon.morphlibrary.model.ImageWarp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aegon.AegonConstants;
import com.aegon.AppAnalytics;
import com.aegon.BitmapManager;

public class WaitActivity extends Activity {

	private final String TAG = WaitActivity.class.getSimpleName();
	private static final int MAX_YEAR = 35;
	private int mCurrentYear = 0;
	private int mMaxyear = 0;
	private TextView mYearTextView;
	private ImageView arrowView;
	private Context mContext = this;
	private float mLeftEyeX;
	private float mLeftEyeY;
	private float mRightEyeX;
	private float mRightEyeY;
	private float mLeftMouthX;
	private float mLeftMouthY;
	private float mRightMouthX;
	private float mRightMouthY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wait);
		AppAnalytics.getInstance().trackPage(false,
				AegonConstants.WAITING_PAGE_LINK_URL);

		Bitmap bitmap = BitmapManager.getInstance().getBitmap(600, 800);
		if (bitmap != null) {
			Bundle bundle = getIntent().getExtras();
			mLeftEyeX = bundle.getFloat("leftEyeX");
			mLeftEyeY = bundle.getFloat("leftEyeY");
			mRightEyeX = bundle.getFloat("rightEyeX");
			mRightEyeY = bundle.getFloat("rightEyeY");
			mLeftMouthX = bundle.getFloat("leftMouthX");
			mLeftMouthY = bundle.getFloat("leftMouthY");
			mRightMouthX = bundle.getFloat("rightMouthX");
			mRightMouthY = bundle.getFloat("rightMouthY");

			new MorphAsynTask().execute(bitmap);
		}

		Calendar calender = Calendar.getInstance();
		mCurrentYear = calender.get(Calendar.YEAR);
		mYearTextView = (TextView) findViewById(R.id.anim_text_id);

		mMaxyear = mCurrentYear + MAX_YEAR;

		animateUpperText();
		animateYearText();
		animateArrow();
	}

	private void animateYearText() {

		mYearTextView.setText("" + mCurrentYear);
		// start year animation
		mHanlder.removeCallbacks(mAnimrun);
		mHanlder.postDelayed(mAnimrun, 0);
	}

	private void animateUpperText() {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				ImageView upper = (ImageView) findViewById(R.id.upper_part);
				Animation anim = (Animation) AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.push_up_in);
				upper.startAnimation(anim);
			}
		});

	}

	private void animateLowerText() {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				ImageView lower = (ImageView) findViewById(R.id.lower_part);
				Animation anim = (Animation) AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.push_up_out);
				lower.setBackgroundResource(R.drawable.calculation_bottom);
				lower.startAnimation(anim);

				Animation anim2 = (Animation) AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.push_left_out_cont2);
				arrowView.startAnimation(anim2);
				anim2.setFillAfter(true);

			}
		});
	}

	private void animateArrow() {
		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				arrowView = (ImageView) findViewById(R.id.arrow);
				Animation anim = (Animation) AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.push_left_out_cont);
				anim.setFillAfter(true);
				arrowView.startAnimation(anim);

				mHanlder.postDelayed(loweranimtask, anim.getDuration() + 1000);

			}
		});
	}

	TimerTask loweranimtask = new TimerTask() {

		@Override
		public void run() {
			animateLowerText();
		}
	};

	private final Handler mHanlder = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {

			} else {
				super.handleMessage(msg);
			}
		};
	};

	private class MorphAsynTask extends AsyncTask<Bitmap, Void, Void> {

		@Override
		protected Void doInBackground(Bitmap... params) {

			ImageWarp mImageWrap = new ImageWarp(mContext);
			mImageWrap.setGridPoints(new PointF(mLeftEyeX, mLeftEyeY),
					new PointF(mRightEyeX, mRightEyeY), new PointF(mLeftMouthX,
							mLeftMouthY),
					new PointF(mRightMouthX, mRightMouthY));
			params[0] = mImageWrap.ageBitmap(params[0]);

			BitmapManager.getInstance().setBitmap(params[0]);

			return null;
		}

		protected void onPostExecute(Void result) {
			finish();
			startActivity(new Intent(WaitActivity.this, AgingActivity.class));
		};

	}

	private Runnable mAnimrun = new Runnable() {

		@Override
		public void run() {

			int value = Integer.parseInt(mYearTextView.getText().toString());
			if (value < mMaxyear) {
				mYearTextView.setText("" + mCurrentYear);
				mCurrentYear++;
			}
			mHanlder.postDelayed(this, 130);
		}
	};

	public void onBackPressed() {
	};

	@Override
	protected void onDestroy() {
		mHanlder.removeCallbacks(mAnimrun);
		mHanlder.removeCallbacks(loweranimtask);
		finish();
		super.onDestroy();
	}

}