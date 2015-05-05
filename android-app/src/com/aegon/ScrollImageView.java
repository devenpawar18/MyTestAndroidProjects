/**
 * 
 */
package com.aegon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.aegon.activities.R;

public class ScrollImageView extends ImageView {

	private final String TAG = ScrollImageView.class.getSimpleName();
	private float mPosX;
	private float mPosY;
	private RectF mLeftEyeRect = null;
	private RectF mRightEyeRect = null;
	private RectF mLeftMouthRect = null;
	private RectF mRightMouthRect = null;
	private Paint mPaint;
	private ZoomImage mZoomImage;
	private Bitmap mLeftEyeBitmap;
	private Bitmap mRightEyeBitmap;
	private Bitmap mLeftMouthBitmap;
	private Bitmap mRightMouthBitmap;
	private boolean isfirstRect = false;
	private boolean isSecondRect = false;
	private boolean isThirdRect = false;
	private boolean isFourthRect = false;
	private boolean isToZoom = false;
	private boolean isMoveImage = false;
	private Bitmap zoomImage;

	public void setListener(ZoomImage zoomImage) {
		this.mZoomImage = zoomImage;
	}

	/**
	 * @param context
	 */
	public ScrollImageView(Context context, AttributeSet attrSet) {
		super(context, attrSet);
		init();
	}

	public void init() {
		setLongClickable(true);
		setFocusable(true);
		setFocusableInTouchMode(true);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(android.graphics.Color.WHITE);

		mLeftEyeBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.left_eye);
		mRightEyeBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.right_eye);
		mLeftMouthBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.left_mouthcorner);
		mRightMouthBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.right_mouthcorner);

		mLeftEyeRect = new RectF(100, 160, 100 + mLeftEyeBitmap.getWidth(),
				160 + mLeftEyeBitmap.getHeight());
		mRightEyeRect = new RectF(300, 160, 300 + mRightEyeBitmap.getWidth(),
				160 + mRightEyeBitmap.getHeight());
		mLeftMouthRect = new RectF(100, 360, 100 + mLeftMouthBitmap.getWidth(),
				360 + mLeftMouthBitmap.getHeight());
		mRightMouthRect = new RectF(300, 360,
				300 + mRightMouthBitmap.getWidth(),
				360 + mRightMouthBitmap.getHeight());
		zoomImage = BitmapFactory.decodeResource(getResources(),
				R.drawable.zoom);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float startX = 0;
		float startY = 0;
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			startX = event.getX();
			startY = event.getY();
			touchStart(startX, startY);
			break;
		case MotionEvent.ACTION_MOVE:
			float currentX = event.getX();
			float currentY = event.getY();
			startX = currentX;
			startY = currentY;
			touchMove(startX, startY);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touchUp();
			invalidate();
			break;

		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawZoomBitmap(canvas, mPosX, mPosY);
		scrollImage(canvas);
	}

	private void touchStart(float x, float y) {
		mPosX = x;
		mPosY = y;

		if (mLeftEyeRect.contains(mPosX, mPosY)) {
			isfirstRect = true;
			isSecondRect = false;
			isThirdRect = false;
			isFourthRect = false;
			isToZoom = true;
			isMoveImage = true;
		} else if (mRightEyeRect.contains(mPosX, mPosY)) {
			isfirstRect = false;
			isSecondRect = true;
			isThirdRect = false;
			isFourthRect = false;
			isToZoom = true;
			isMoveImage = true;
		} else if (mLeftMouthRect.contains(mPosX, mPosY)) {
			isfirstRect = false;
			isSecondRect = false;
			isThirdRect = true;
			isFourthRect = false;
			isToZoom = true;
			isMoveImage = true;
		} else if (mRightMouthRect.contains(mPosX, mPosY)) {
			isfirstRect = false;
			isSecondRect = false;
			isThirdRect = false;
			isFourthRect = true;
			isToZoom = true;
			isMoveImage = true;
		}
	}

	private void touchMove(float x, float y) {
		if (isMoveImage) {
			float dx = Math.abs(x - mPosX);
			float dy = Math.abs(y - mPosY);
			if (dx >= 4 || dy >= 4) {
				mPosX = x;
				mPosY = y;
				if (isfirstRect) {
					mLeftEyeRect.set(mPosX - mLeftEyeBitmap.getWidth() / 2,
							mPosY - mLeftEyeBitmap.getHeight() / 2, mPosX
									+ mLeftEyeBitmap.getWidth() / 2, mPosY
									+ mLeftEyeBitmap.getHeight() / 2);
				} else if (isSecondRect) {
					mRightEyeRect.set(mPosX - mRightEyeBitmap.getWidth() / 2,
							mPosY - mRightEyeBitmap.getHeight() / 2, mPosX
									+ mRightEyeBitmap.getWidth() / 2, mPosY
									+ mRightEyeBitmap.getHeight() / 2);
				} else if (isThirdRect) {
					mLeftMouthRect.set(mPosX - mLeftMouthBitmap.getWidth() / 2,
							mPosY - mLeftMouthBitmap.getHeight() / 2, mPosX
									+ mLeftMouthBitmap.getWidth() / 2, mPosY
									+ mLeftMouthBitmap.getHeight() / 2);
				} else if (isFourthRect) {
					mRightMouthRect.set(mPosX - mRightMouthBitmap.getWidth()
							/ 2, mPosY - mRightMouthBitmap.getHeight() / 2,
							mPosX + mRightMouthBitmap.getWidth() / 2, mPosY
									+ mRightMouthBitmap.getHeight() / 2);
				}
			}
		}
	}

	private void touchUp() {
		isfirstRect = false;
		isSecondRect = false;
		isThirdRect = false;
		isFourthRect = false;
		isToZoom = false;
		isMoveImage = false;
	}

	private void scrollImage(Canvas canvas) {
		canvas.drawBitmap(mLeftEyeBitmap, null, mLeftEyeRect, null);
		canvas.drawBitmap(mRightEyeBitmap, null, mRightEyeRect, null);
		canvas.drawBitmap(mLeftMouthBitmap, null, mLeftMouthRect, null);
		canvas.drawBitmap(mRightMouthBitmap, null, mRightMouthRect, null);
	}

	private void drawZoomBitmap(Canvas canvas, float screenX, float screenY) {
		Bitmap original = BitmapManager.getInstance().getBitmap();

		if (isToZoom && (original != null)) {
			PointF onBitmap = copyOfNormalizedGrid(screenX, screenY,
					getWidth(), original.getWidth());
			if (onBitmap.y < 0) {
				onBitmap.y = 1;
			} else if (onBitmap.y > original.getHeight()) {
				onBitmap.y = original.getHeight();
			}

			if (onBitmap.x < 0) {
				onBitmap.x = 1;
			} else if (onBitmap.x > original.getWidth()) {
				onBitmap.x = original.getWidth();
			}

			if ((onBitmap.y + zoomImage.getHeight() / 2) <= original
					.getHeight()
					&& (onBitmap.x + zoomImage.getWidth() / 2) <= original
							.getWidth()) {
				int newX = (int) onBitmap.x - zoomImage.getWidth() / 4;
				int newY = (int) onBitmap.y - zoomImage.getHeight() / 4;
				if (newX > 0 && newY > 0) {
					Bitmap zoomedBitmap = Bitmap.createBitmap(original, newX,
							newY, zoomImage.getWidth() / 2,
							zoomImage.getHeight() / 2);
					zoomedBitmap = Bitmap.createScaledBitmap(zoomedBitmap, 100,
							100, false);
					mZoomImage.setImage(zoomedBitmap, isToZoom);
				} else {
					mZoomImage.setImage(null, false);
				}
			} else {
				mZoomImage.setImage(null, false);
			}
		} else {
			mZoomImage.setImage(null, false);
		}

	}

	/**
	 * scale the grid to normalized (600*800) size
	 * 
	 * @return
	 */
	public PointF copyOfNormalizedGrid(float x, float y, int originalWidth,
			int targetWidth) {
		Bitmap original = BitmapManager.getInstance().getBitmap();
		float px = x, py = y;

		px *= original.getWidth();
		px /= (float) getWidth();

		// On a 4:3 image the getHeight() of this View is larger then needed,
		// but because the getWidth() is maxed and the aspect ratio is
		// maintained
		// the width scaling can be applied to the height
		py *= original.getWidth();
		py /= (float) getWidth();

		return new PointF(px, py);
	}

	public PointF getLeftEyePoint() {
		PointF pointf = copyOfNormalizedGrid(mLeftEyeRect.centerX(),
				mLeftEyeRect.centerY(), getWidth(), 600);
		return pointf;
	}

	public PointF getRightEyePoint() {
		PointF pointf = copyOfNormalizedGrid(mRightEyeRect.centerX(),
				mRightEyeRect.centerY(), getWidth(), 600);
		return pointf;
	}

	public PointF getLeftMouthPoint() {
		PointF pointf = copyOfNormalizedGrid(mLeftMouthRect.centerX(),
				mLeftMouthRect.centerY(), getWidth(), 600);
		return pointf;
	}

	public PointF getRigthMouthPoint() {
		PointF pointf = copyOfNormalizedGrid(mRightMouthRect.centerX(),
				mRightMouthRect.centerY(), getWidth(), 600);
		return pointf;
	}
}
