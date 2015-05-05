package com.example.rangeseekbardemo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.rangeseekbardemo.RangeSeekBarEndless.OnRangeSeekBarChangeListener;

public class RangeSeekBarEndlessActivity extends Activity
{
	private TextView minValueTextView;
	private TextView maxValueTextView;
	// public static double minValue = 1100;
	// public static double maxValue = 1200;
	private RangeSeekBarEndless mSeekBar;
	private RelativeLayout priceSliderLayout;
	private double mProductLowestPrice = 1000;
	private double mProductHighestPrice = 2000;
	private long mProductLowestDate = 1264549807457l;
	private long mProductHighestDate = 1364549807457l;
	private double mSelectedMinValue;
	private double mSelectedMaxValue;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_range_seek_bar);

		minValueTextView = (TextView) findViewById(R.id.min_value);
		maxValueTextView = (TextView) findViewById(R.id.max_value);

		long l = new Date().getTime();
		Log.d("get current time", "" + new Date().getTime());

		priceSliderLayout = (RelativeLayout) findViewById(R.id.view_amount);
		priceSliderLayout.removeAllViews();
		mSeekBar = new RangeSeekBarEndless(mProductLowestPrice, mProductHighestPrice, this);
		mSeekBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 100));
		priceSliderLayout.addView(mSeekBar, 0);

		// mSeekBar = new RangeSeekBarEndless(mProductLowestDate, mProductHighestDate, this);
		// mSeekBar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 100));
		// priceSliderLayout.addView(mSeekBar, 0);

		// mSeekBar.setSelectedMinValue(minValue);
		// mSeekBar.setSelectedMaxValue(maxValue);

		// if (mSeekBar.getSelectedMinValue() == mSeekBar.getSelectedMaxValue())
		// mSeekBar.setSelectedMinValue(mProductLowestPrice);

		// mSeekBar.notifyValuesChanged();
		mSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener()
		{

			@Override
			public void rangeSeekBarValuesChanged(double minValue, double maxValue)
			{
				// mPriceSelectedCount = mPriceSelectedCount + 1;
				// if (mFilterTypeArrayList != null)
				// {
				// if (minValue != mSelectedMinValue || maxValue != mSelectedMaxValue)
				// {
				Log.d("Min Value", "" + minValue);
				Log.d("Min Value", "" + maxValue);
				mSelectedMinValue = minValue;
				mSelectedMaxValue = maxValue;
				mSeekBar.notifyValuesChanged();
				// if (mSelectedDepartmentId != null && !TextUtils.isEmpty(mSelectedDepartmentId))
				// createSearchString(mSelectedDepartmentId);
				// else
				// createSearchString(mNode);
				// if (mPriceSelectedCount != 0)
				// mSearchParam.append(getResources().getString(R.string.price_low) + (int)
				// (minValue * 100) + getResources()
				// .getString(R.string.price_high) + (int) (maxValue * 100));
				// getSearchResult(mSearchParam.toString());
				// }

				// }
			}
		});
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_range_seek_bar, menu);
		return true;
	}

	/**
	 * Converts long to date
	 * 
	 * @param timeStamp
	 *            date
	 * @return date converts the date in required format
	 */
	public static String convertToDate(long timeStamp)
	{
		if (timeStamp <= 0)
		{
			return "";
		}
		Date date = new Date(timeStamp);
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * Converts Date to String with the specified format.
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date)
	{
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		String strDate = formatter.format(date);
		return strDate;
	}

}
