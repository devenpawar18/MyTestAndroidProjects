package com.customcomponents.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CustomControl extends View
{
	public CustomControl(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private Paint paint;

	/**
	 * 
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		paint = new Paint();
		paint.setColor(Color.CYAN);
		paint.setTextSize(25);
		paint.setAntiAlias(true);
		Rect r = new Rect();
		r.set(100, 100, 200, 200);
		canvas.drawColor(Color.RED);
		canvas.drawRect(r, paint);
		canvas.drawText("hello world...", 0, 30, paint);
	}
}
