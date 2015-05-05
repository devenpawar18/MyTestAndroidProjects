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
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.aegon.activities.R;

public class ZoomImageView extends ImageView {

	private Bitmap mZoomedBitmap;
	private boolean isToZoom = false;
	private Bitmap mZoomBitmap;

	/**
	 * @param context
	 * @param attrs
	 */
	public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mZoomBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.zoom);
	}

	public void setBitmap(Bitmap bitmap) {
		this.mZoomedBitmap = bitmap;
	}

	public void isTooom(boolean value) {
		this.isToZoom = value;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (mZoomedBitmap != null && isToZoom) {
			canvas.drawBitmap(mZoomBitmap, 0, 0, null);
			drawZoomedBitmap(canvas);
		} else {
			canvas.drawBitmap(mZoomBitmap, 0, 0, null);
		}

	}

	private void drawZoomedBitmap(Canvas canvas) {
		Bitmap zoomBitmap = mZoomedBitmap;

		// Create a temporary bitmap
		Bitmap tempBitmap = Bitmap.createBitmap(zoomBitmap.getWidth(),
				zoomBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas tempCanvas = new Canvas(tempBitmap);
		tempCanvas.drawBitmap(zoomBitmap, 10, 10, null);
		// transparent circle
		Paint mZoomPaint = new Paint();
		mZoomPaint.setColor(0xFF000000);
		mZoomPaint.setStyle(Style.STROKE);
		mZoomPaint.setStrokeWidth(20);
		mZoomPaint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.DST_OUT));
		tempCanvas.drawCircle((tempCanvas.getWidth() / 2) + 9,
				(tempCanvas.getHeight() / 2) + 9,
				(tempCanvas.getHeight() / 2) + 10, mZoomPaint);
		// draw bitmap
		canvas.drawBitmap(tempBitmap, 10, 10, null);
		// draw black border circle
		Paint maskPaint = new Paint();
		maskPaint.setColor(android.graphics.Color.BLACK);
		maskPaint.setStyle(Style.STROKE);
		maskPaint.setStrokeWidth(11);

		canvas.drawCircle((tempCanvas.getWidth() / 2) + 16,
				(tempCanvas.getHeight() / 2) + 16,
				(tempCanvas.getHeight() / 2), maskPaint);

	}
}
