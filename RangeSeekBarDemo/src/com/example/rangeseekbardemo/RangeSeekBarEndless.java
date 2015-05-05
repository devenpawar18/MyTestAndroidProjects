package com.example.rangeseekbardemo;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Widget that lets users select a minimum and maximum value on a given numerical range.
 */
public class RangeSeekBarEndless extends ImageView
{

	private static final int TEXT_SCREEN_PADDING = 2;
	private static final int TEXT_STROKE_WIDTH = 20;
	private static final int TEXT_THUMB_PADDING = 10;
	private int TEXT_SIZE = 11;
	private int TEXT_HEADER_SIZE = 14;
	private static final int TEXT_COLOR = Color.rgb(0, 0, 0);
	private static final int TEXT_BALOON_COLOR = Color.rgb(0, 0, 0);
	private static final int FILL_PROGRESS_COLOR = Color.rgb(34, 18, 17);
	private final Paint paint = new Paint();
	private final Bitmap thumbImage = BitmapFactory.decodeResource(getResources(), R.drawable.seek_thumb_normal);
	private final Bitmap thumbPressedImage = BitmapFactory.decodeResource(getResources(), R.drawable.seek_thumb_pressed);
	private final Bitmap textBaloon = BitmapFactory.decodeResource(getResources(), R.drawable.price_flag);
	private final float thumbWidth = thumbImage.getWidth();
	private final float thumbHalfWidth = 0.5f * thumbWidth;
	private final float thumbHalfHeight = 0.5f * thumbImage.getHeight();
	private final float lineHeight = 0.2f * thumbHalfHeight;
	private float padding = thumbHalfWidth;
	private final double absoluteMinValue, absoluteMaxValue;
	private final NumberType numberType;
	private final double absoluteMinValuePrim, absoluteMaxValuePrim;
	private double normalizedMinValue = 0d;
	private double normalizedMaxValue = 1d;
	private Thumb pressedThumb = null;
	private boolean notifyWhileDragging = false;
	private OnRangeSeekBarChangeListener listener;
	private final float ALIGNMENT_FACTOR = 0.5f;
	private String minText = "";
	private String maxText = "";
	private int screenPadding = 25;
	private String headerText = "price";

	private final OnRangeSeekBarChangeListener paintListener = new OnRangeSeekBarChangeListener()
	{
		@Override
		public void rangeSeekBarValuesChanged(double minValue, double maxValue)
		{
			String min = getFormattedAmount(minValue);
			String max = getFormattedAmount(maxValue);
			minText = "$" + min;
			maxText = "$" + max;
		}

	};

	private Paint textPaint;
	private Paint textHeaderPaint;
	private Paint textBaloonPaint;

	/**
	 * Creates a new RangeSeekBar.
	 * 
	 * @param absoluteMinValue
	 *            The minimum value of the selectable range.
	 * @param absoluteMaxValue
	 *            The maximum value of the selectable range.
	 * @param context
	 *            context of application
	 * @throws IllegalArgumentException
	 *             Will be thrown if min/max value type is not one of Long, Double, Integer, Float,
	 *             Short, Byte or BigDecimal.
	 */
	public RangeSeekBarEndless(double absoluteMinValue, double absoluteMaxValue, Context context) throws IllegalArgumentException
	{
		super(context);
		this.absoluteMinValue = absoluteMinValue;
		this.absoluteMaxValue = absoluteMaxValue;
		absoluteMinValuePrim = absoluteMinValue;
		absoluteMaxValuePrim = absoluteMaxValue;
		numberType = NumberType.fromNumber(absoluteMinValue);
		paintListener.rangeSeekBarValuesChanged((int) absoluteMinValue, (int) absoluteMaxValue);
		initTextPaint();
		// if (EndlessUtils.isKindleFire(getContext()))
		// screenPadding = 35;
		// else
		screenPadding = (int) (screenPadding * getContext().getResources().getDisplayMetrics().density);
		padding = screenPadding;
	}

	public RangeSeekBarEndless(Context context) throws IllegalArgumentException
	{
		super(context);
		this.absoluteMinValue = new Double(0);
		this.absoluteMaxValue = new Double(100);
		absoluteMinValuePrim = absoluteMinValue;
		absoluteMaxValuePrim = absoluteMaxValue;
		numberType = NumberType.fromNumber(absoluteMinValue);
		paintListener.rangeSeekBarValuesChanged((int) absoluteMinValue, (int) absoluteMaxValue);
		initTextPaint();
		// if (EndlessUtils.isKindleFire(getContext()))
		// screenPadding = 35;
		// else
		screenPadding = (int) (screenPadding * getContext().getResources().getDisplayMetrics().density);
		padding = screenPadding;
	}

	public RangeSeekBarEndless(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);

		this.absoluteMaxValue = new Double(a.getInt(R.styleable.RangeSeekBar_maxValue, 100));
		this.absoluteMinValue = new Double(a.getInt(R.styleable.RangeSeekBar_minValue, 0));
		this.headerText = a.getString(R.styleable.RangeSeekBar_headerText);
		if (this.headerText == null)
			this.headerText = "";
		absoluteMinValuePrim = absoluteMinValue;
		absoluteMaxValuePrim = absoluteMaxValue;
		numberType = NumberType.fromNumber(absoluteMinValue);

		a.recycle();

		paintListener.rangeSeekBarValuesChanged((int) absoluteMinValue, (int) absoluteMaxValue);
		initTextPaint();
		// if (EndlessUtils.isKindleFire(getContext()))
		// screenPadding = 35;
		// else
		screenPadding = (int) (screenPadding * getContext().getResources().getDisplayMetrics().density);
		padding = screenPadding;
	}

	public RangeSeekBarEndless(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RangeSeekBar);

		this.absoluteMaxValue = new Double(a.getInt(R.styleable.RangeSeekBar_maxValue, 100));
		this.absoluteMinValue = new Double(a.getInt(R.styleable.RangeSeekBar_minValue, 0));
		this.headerText = a.getString(R.styleable.RangeSeekBar_headerText);
		if (this.headerText == null)
			this.headerText = "";

		absoluteMinValuePrim = absoluteMinValue;
		absoluteMaxValuePrim = absoluteMaxValue;
		numberType = NumberType.fromNumber(absoluteMinValue);

		a.recycle();
		paintListener.rangeSeekBarValuesChanged((int) absoluteMinValue, (int) absoluteMaxValue);

		initTextPaint();
		screenPadding = (int) (30 * getContext().getResources().getDisplayMetrics().density);
		padding = screenPadding;

	}

	private void initTextPaint()
	{

		TextView textView = new TextView(getContext());

		textPaint = new Paint(textView.getPaint());
		textPaint.setStrokeWidth(TEXT_STROKE_WIDTH);

		TEXT_SIZE = (int) (TEXT_SIZE * getContext().getResources().getDisplayMetrics().density);
		// if (EndlessUtils.isKindleFire(getContext()))
		// {
		// TEXT_SIZE = 15;
		// TEXT_HEADER_SIZE = 20;
		// }
		// else
		TEXT_HEADER_SIZE = (int) (TEXT_HEADER_SIZE * getContext().getResources().getDisplayMetrics().density);

		textPaint.setTextSize(TEXT_SIZE);
		textPaint.setColor(TEXT_COLOR);
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);

		textBaloonPaint = new Paint(textPaint);
		textBaloonPaint.setColor(TEXT_BALOON_COLOR);

		textHeaderPaint = new Paint(textPaint);
		textHeaderPaint.setTextSize(TEXT_HEADER_SIZE);

	}

	public boolean isNotifyWhileDragging()
	{
		return notifyWhileDragging;
	}

	/**
	 * to notify while the user is still dragging a thumb
	 * 
	 * @param flag
	 */
	public void setNotifyWhileDragging(boolean flag)
	{
		this.notifyWhileDragging = flag;
	}

	/**
	 * Returns the absolute minimum value of the range that has been set at construction time.
	 * 
	 * @return The absolute minimum value of the range.
	 */
	public Double getAbsoluteMinValue()
	{
		return absoluteMinValue;
	}

	/**
	 * Returns the absolute maximum value of the range that has been set at construction time.
	 * 
	 * @return The absolute maximum value of the range.
	 */
	public double getAbsoluteMaxValue()
	{
		return absoluteMaxValue;
	}

	/**
	 * Returns the currently selected min value.
	 * 
	 * @return The currently selected min value.
	 */
	public double getSelectedMinValue()
	{
		return normalizedToValue(normalizedMinValue);
	}

	/**
	 * Sets the currently selected minimum value.
	 * 
	 * @param value
	 *            minimum value
	 */
	public void setSelectedMinValue(Double value)
	{
		// in case absoluteMinValue == absoluteMaxValue, avoid division by zero
		// when normalizing.
		if (0 == (absoluteMaxValuePrim - absoluteMinValuePrim))
		{
			setNormalizedMinValue(0d);
		}
		else
		{
			setNormalizedMinValue(valueToNormalized(value));
		}
	}

	/**
	 * Returns the currently selected max value.
	 * 
	 * @return The currently selected max value.
	 */
	public double getSelectedMaxValue()
	{
		return normalizedToValue(normalizedMaxValue);
	}

	/**
	 * Sets the currently selected maximum value
	 * 
	 * @param value
	 *            maximum value
	 */
	public void setSelectedMaxValue(Double value)
	{
		// in case absoluteMinValue == absoluteMaxValue, avoid division by zero
		// when normalizing.
		if (0 == (absoluteMaxValuePrim - absoluteMinValuePrim))
		{
			setNormalizedMaxValue(1d);
		}
		else
		{
			setNormalizedMaxValue(valueToNormalized(value));
		}
	}

	/**
	 * Registers given listener callback to notify about changed selected values.
	 * 
	 * @param listener
	 *            The listener to notify about changed selected values.
	 */
	public void setOnRangeSeekBarChangeListener(OnRangeSeekBarChangeListener listener)
	{
		this.listener = listener;
	}

	private float leftThumbX = 0.0f;
	private float rightThumbX = 0.0f;

	/**
	 * Handles thumb selection and movement. Notifies listener callback on certain events.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{

			case MotionEvent.ACTION_DOWN:
				pressedThumb = evalPressedThumb(event.getX());
				if (Thumb.MIN.equals(pressedThumb))
				{
					leftThumbX = event.getX();
				}
				else
				{
					rightThumbX = event.getX();
				}
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				if (pressedThumb != null)
				{
					if (Thumb.MIN.equals(pressedThumb))
					{

						setNormalizedMinValue(screenToNormalized(event.getX() - screenPadding));
						leftThumbX = event.getX();

					}
					else if (Thumb.MAX.equals(pressedThumb))
					{

						setNormalizedMaxValue(screenToNormalized(event.getX() - screenPadding));
						rightThumbX = event.getX();

					}
					if (notifyWhileDragging && listener != null)
					{
						listener.rangeSeekBarValuesChanged((int) getSelectedMinValue(), (int) getSelectedMaxValue());
					}
					paintListener.rangeSeekBarValuesChanged((int) getSelectedMinValue(), (int) getSelectedMaxValue());
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				pressedThumb = null;
				invalidate();
				if (listener != null)
				{
					listener.rangeSeekBarValuesChanged((int) getSelectedMinValue(), (int) getSelectedMaxValue());
					paintListener.rangeSeekBarValuesChanged((int) getSelectedMinValue(), (int) getSelectedMaxValue());
				}
				break;
		}
		return true;
	}

	/**
	 * Ensures correct size of the widget.
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int width = 200;
		if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec))
		{
			width = MeasureSpec.getSize(widthMeasureSpec);
		}
		int height = thumbImage.getHeight() * 2;
		if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec))
		{
			height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
		}
		setMeasuredDimension(width, height + TEXT_SIZE + textBaloon.getHeight());
	}

	/**
	 * Draws the widget on the given canvas.
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// draw seek bar background line
		RectF rect = new RectF(screenPadding, ALIGNMENT_FACTOR * (getHeight() - lineHeight), getWidth() - screenPadding,
				ALIGNMENT_FACTOR * (getHeight() + lineHeight));
		paint.setStyle(Style.FILL);
		paint.setColor(getResources().getColor(R.drawable.seekbar_unfilled_divider_color));
		/**
		 * removed header.
		 */
		// canvas.drawText(headerText, screenPadding, TEXT_HEADER_SIZE, textHeaderPaint);
		canvas.drawRect(rect, paint);

		// draw seek bar active range line

		float normalizedToScreenMin = (float) normalizedToScreen(normalizedMinValue);
		float normalizedToScreenMax = (float) normalizedToScreen(normalizedMaxValue);

		rect.left = normalizedToScreenMin;
		rect.right = normalizedToScreenMax;

		final float minTextWidth = textPaint.measureText(minText);
		final float maxTextWidth = textPaint.measureText(maxText);

		float minTextX = normalizedToScreenMin - thumbHalfWidth;
		float maxTextX = normalizedToScreenMax - thumbHalfWidth;

		float textXMin = Math.max(minTextX, TEXT_SCREEN_PADDING);
		float textXMax = Math.min(maxTextX, getWidth() - maxTextWidth - TEXT_SCREEN_PADDING);

		if (textXMin + minTextWidth > textXMax)
		{
			textXMin -= (((textXMin + minTextWidth) - textXMax));
			textXMin -= 2;
		}

		/**
		 * Removed bottom selected values
		 */
		// float textY = ((ALIGNMENT_FACTOR * getHeight()) + thumbImage.getHeight()) +
		// TEXT_THUMB_PADDING;
		// canvas.drawText(minText, screenPadding, textY, textPaint);
		// canvas.drawText(maxText, getWidth() - maxTextWidth - TEXT_SCREEN_PADDING - screenPadding,
		// textY, textPaint);

		if (pressedThumb != null)
		{
			if (Thumb.MIN.equals(pressedThumb))
			{

				float baloonY = ((ALIGNMENT_FACTOR * getHeight()) - thumbHalfHeight) - textBaloon.getHeight();

				canvas.drawBitmap(textBaloon, minTextX - (thumbImage.getWidth() / 8), baloonY, paint);

				canvas.drawText(minText, (minTextX - (thumbImage.getWidth() / 8) + (textBaloon.getWidth() / 2)) - (minTextWidth / 2), baloonY + (textBaloon
						.getHeight() / 2), textBaloonPaint);

			}
			else if (Thumb.MAX.equals(pressedThumb))
			{

				float baloonY = ((ALIGNMENT_FACTOR * getHeight()) - thumbHalfHeight) - textBaloon.getHeight();
				canvas.drawBitmap(textBaloon, (maxTextX - thumbImage.getWidth() / 8), baloonY, paint);

				canvas.drawText(maxText, (maxTextX - (thumbImage.getWidth() / 8) + (textBaloon.getWidth() / 2)) - (maxTextWidth / 2), baloonY + (textBaloon
						.getHeight() / 2), textBaloonPaint);
			}
		}
		paint.setColor(FILL_PROGRESS_COLOR);
		canvas.drawRect(rect, paint);
		// draw minimum thumb
		leftThumbX = normalizedToScreenMin;
		drawThumb(normalizedToScreenMin, Thumb.MIN.equals(pressedThumb), canvas);
		// draw maximum thumb
		rightThumbX = normalizedToScreenMax;
		drawThumb(normalizedToScreenMax, Thumb.MAX.equals(pressedThumb), canvas);
	}

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable("SUPER", super.onSaveInstanceState());
		bundle.putDouble("MIN", normalizedMinValue);
		bundle.putDouble("MAX", normalizedMaxValue);
		bundle.putString("RANGE_TEXT_MIN", minText);
		bundle.putString("RANGE_TEXT_MAX", maxText);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable parcel)
	{
		Bundle bundle = (Bundle) parcel;
		super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
		normalizedMinValue = bundle.getDouble("MIN");
		normalizedMaxValue = bundle.getDouble("MAX");
		minText = bundle.getString("RANGE_TEXT_MIN");
		maxText = bundle.getString("RANGE_TEXT_MAX");

	}

	/**
	 * Draws the "normal" resp. "pressed" thumb image on specified x-coordinate.
	 * 
	 * @param screenCoord
	 *            x-coordinate
	 * @param pressed
	 *            Is the thumb currently in "pressed" state
	 * @param canvas
	 *            The canvas to draw upon.
	 */
	private void drawThumb(float screenCoord, boolean pressed, Canvas canvas)
	{
		canvas.drawBitmap(pressed ? thumbPressedImage : thumbImage, screenCoord - thumbHalfWidth, ((ALIGNMENT_FACTOR * getHeight()) - thumbHalfHeight), paint);
	}

	/**
	 * Decides which (if any) thumb is touched by the given x-coordinate.
	 * 
	 * @param touchX
	 *            The x-coordinate of a touch event in screen space.
	 * @return The pressed thumb or null if none has been touched.
	 */
	private Thumb evalPressedThumb(float touchX)
	{
		Thumb result = null;
		boolean minThumbPressed = isInThumbRange(touchX, normalizedMinValue);
		boolean maxThumbPressed = isInThumbRange(touchX, normalizedMaxValue);
		if (minThumbPressed && maxThumbPressed)
		{
			// if both thumbs are pressed (they lie on top of each other),
			// choose the one with more room to drag. this avoids "stalling" the
			// thumbs in a corner, not being able to drag them apart anymore.
			result = (touchX / getWidth() > 0.5f) ? Thumb.MIN : Thumb.MAX;
		}
		else if (minThumbPressed)
		{
			result = Thumb.MIN;
		}
		else if (maxThumbPressed)
		{
			result = Thumb.MAX;
		}
		return result;
	}

	/**
	 * Decides if given x-coordinate in screen space needs to be interpreted as "within" the
	 * normalized thumb x-coordinate.
	 * 
	 * @param touchX
	 *            The x-coordinate in screen space to check.
	 * @param normalizedThumbValue
	 *            The normalized x-coordinate of the thumb to check.
	 * @return true if x-coordinate is in thumb range, false otherwise.
	 */
	private boolean isInThumbRange(float touchX, double normalizedThumbValue)
	{
		return Math.abs(touchX - normalizedToScreen(normalizedThumbValue)) <= thumbHalfWidth;
	}

	/**
	 * Sets normalized min value
	 * 
	 * @param value
	 *            The new normalized min value to set.
	 */
	private void setNormalizedMinValue(double value)
	{
		normalizedMinValue = Math.max(0d, Math.min(1d, Math.min(value, normalizedMaxValue)));
		invalidate();
	}

	private double getNormalizedMinValue(double value)
	{
		return Math.max(0d, Math.min(1d, Math.min(value, normalizedMaxValue)));
	}

	/**
	 * Sets normalized max value
	 * 
	 * @param value
	 *            The new normalized max value to set.
	 */
	private void setNormalizedMaxValue(double value)
	{
		normalizedMaxValue = Math.max(0d, Math.min(1d, Math.max(value, normalizedMinValue)));
		invalidate();
	}

	private double getNormalizedMaxValue(double value)
	{
		return Math.max(0d, Math.min(1d, Math.max(value, normalizedMinValue)));

	}

	/**
	 * Converts a normalized value to a Number object in the value space between absolute minimum
	 * and maximum.
	 * 
	 * @param normalized
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private double normalizedToValue(double normalized)
	{
		return (Double) numberType.toNumber(absoluteMinValuePrim + normalized * (absoluteMaxValuePrim - absoluteMinValuePrim));
	}

	/**
	 * Converts the given Number value to a normalized double.
	 * 
	 * @param value
	 *            The Number value to normalize.
	 * @return The normalized double.
	 */
	private double valueToNormalized(Double value)
	{
		if (0 == absoluteMaxValuePrim - absoluteMinValuePrim)
		{
			// prevent division by zero, simply return 0.
			return 0d;
		}
		return (value.doubleValue() - absoluteMinValuePrim) / (absoluteMaxValuePrim - absoluteMinValuePrim);
	}

	/**
	 * Converts a normalized value into screen space.
	 * 
	 * @param normalizedCoord
	 *            The normalized value to convert.
	 * @return The converted value in screen space.
	 */
	private double normalizedToScreen(double normalizedCoord)
	{
		return (padding + normalizedCoord * (getWidth() - 2 * padding));
	}

	/**
	 * Converts screen space x-coordinates into normalized values.
	 * 
	 * @param screenCoord
	 *            The x-coordinate in screen space to convert.
	 * @return The normalized value.
	 */
	private double screenToNormalized(float screenCoord)
	{
		int width = getWidth() - 2 * (screenPadding);
		if (width <= 2 * padding)
		{
			// prevent division by zero, simply return 0.
			return 0d;
		}
		else
		{
			double result = (screenCoord - padding) / (width - 2 * padding);
			return Math.min(1d, Math.max(0d, result));
		}
	}

	/**
	 * to notify about changed range values.
	 */
	public interface OnRangeSeekBarChangeListener
	{
		void rangeSeekBarValuesChanged(double minValue, double maxValue);
	}

	/**
	 * Thumb constants (min and max).
	 */
	private static enum Thumb
	{
		MIN, MAX
	};

	/**
	 * Utility enumaration used to convert between Numbers and doubles.
	 */
	private static enum NumberType
	{
		LONG, DOUBLE, INTEGER, FLOAT, SHORT, BYTE, BIG_DECIMAL;

		public static <E extends Number> NumberType fromNumber(E value) throws IllegalArgumentException
		{
			if (value instanceof Long)
			{
				return LONG;
			}
			if (value instanceof Double)
			{
				return DOUBLE;
			}
			if (value instanceof Integer)
			{
				return INTEGER;
			}
			if (value instanceof Float)
			{
				return FLOAT;
			}
			if (value instanceof Short)
			{
				return SHORT;
			}
			if (value instanceof Byte)
			{
				return BYTE;
			}
			if (value instanceof BigDecimal)
			{
				return BIG_DECIMAL;
			}
			throw new IllegalArgumentException("Number class '" + value.getClass().getName() + "' is not supported");
		}

		public Number toNumber(double value)
		{
			switch (this)
			{
				case LONG:
					return new Long((long) value);
				case DOUBLE:
					return new Double(value);
				case INTEGER:
					return new Integer((int) value);
				case FLOAT:
					return new Float(value);
				case SHORT:
					return new Short((short) value);
				case BYTE:
					return new Byte((byte) value);
				case BIG_DECIMAL:
					return new BigDecimal(value);
			}
			throw new InstantiationError("can't convert " + this + " to a Number object");
		}
	}

	public String getMinText()
	{
		return minText;
	}

	public String getMaxText()
	{
		return maxText;
	}

	public void notifyValuesChanged()
	{
		minText = "$" + getFormattedAmount((int) getSelectedMinValue());
		maxText = "$" + getFormattedAmount((int) getSelectedMaxValue());
		// minText = "$" + "1155";
		// maxText = "$" + "1999";
		invalidate();
	}

	public void setHeaderText(String headerText)
	{
		this.headerText = headerText;
	}

	public String getHeaderText()
	{
		return headerText;
	}

	public static String getFormattedAmount(double minValue)
	{
		NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
		String formattedString = numberFormat.format(minValue);
		return formattedString;
	}
}
