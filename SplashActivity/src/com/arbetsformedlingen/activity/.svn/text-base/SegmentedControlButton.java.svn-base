package com.arbetsformedlingen.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class SegmentedControlButton extends RadioButton{
	private float mX;
	private int layoutWidth;
	private int layoutHeight;

	public SegmentedControlButton(Context context) {
	super(context);
	}

	public SegmentedControlButton(Context context, AttributeSet attrs) {
	super(context, attrs);
	}

	public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	}

	@Override
	public void onDraw(Canvas canvas) {

	String text = this.getText().toString();
	Paint textPaint = new Paint();
	textPaint.setAntiAlias(true);
	float currentWidth = textPaint.measureText(text);
	float currentHeight = textPaint.measureText("x");

	textPaint.setTextSize(this.getTextSize());
	textPaint.setTextAlign(Paint.Align.CENTER);

	if (isChecked()) {
	GradientDrawable grad = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] { 0xffd55954, 0xffc10800 });
	grad.setBounds(0, 0, layoutWidth, layoutHeight);
	grad.draw(canvas);
	textPaint.setColor(Color.WHITE);
	} else {
	GradientDrawable grad = new GradientDrawable(Orientation.TOP_BOTTOM, new int[] { 0xffef5f41, 0xffeb320c });
	grad.setBounds(0, 0, layoutWidth,layoutHeight);
	grad.draw(canvas);
	textPaint.setColor(Color.WHITE);
	}

	float w = (layoutWidth / 2) - currentWidth;
	float h = (layoutHeight / 2) + currentHeight;
	canvas.drawText(text, mX, h, textPaint);

	Paint paint = new Paint();
	paint.setColor(Color.BLACK);
	paint.setStyle(Style.STROKE);
	Rect rect = new Rect(0, 0, layoutWidth, layoutHeight);
	canvas.drawRect(rect, paint);

	}

	@Override
	protected void onSizeChanged(int w, int h, int ow, int oh) {
	super.onSizeChanged(w, h, ow, oh);
	mX = w * 0.5f; // remember the center of the screen
	layoutWidth = w;
	layoutHeight = h;
	}



	
}
