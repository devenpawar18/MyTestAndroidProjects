package com.example.rangeseekbardemo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.rangeseekbardemo.RangeSeekBar.OnRangeSeekBarChangeListener;

public class RangeSeekBarActivity extends Activity
{
	private TextView minValueTextView;
	private TextView maxValueTextView;
	public static int minValue = 0;
	public static int maxValue = 100;
	public static String minimumDate;
	public static String maximumDate;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_range_seek_bar);

		minValueTextView = (TextView) findViewById(R.id.min_value);
		maxValueTextView = (TextView) findViewById(R.id.max_value);

		// create RangeSeekBar as Date range between 1950-12-01 and now
		Date minDate;
		try
		{
			minDate = new SimpleDateFormat("yyyy/MM/dd").parse("2013/02/22");
			Date maxDate = new Date();
			RangeSeekBar<Long> seekBar = new RangeSeekBar<Long>(minDate.getTime(), maxDate.getTime(), this);
			seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Long>()
			{
				@Override
				public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Long minValue, Long maxValue)
				{
					minimumDate = convertDateToString(new Date(minValue));
					maximumDate = convertDateToString(new Date(maxValue));
					// handle changed range values
					Log.d("RangeSeekBarActivity.onCreate(...).new OnRangeSeekBarChangeListener() {...}", "onRangeSeekBarValuesChanged::" + "User selected new date range: MIN=" + new Date(
							minValue) + ", MAX=" + new Date(maxValue));
					Log.d("Minimum Date", minimumDate);
					Log.d("Maximum Date", maximumDate);
					onResume();
				}
			});

			// add RangeSeekBar to pre-defined layout
			ViewGroup layout = (ViewGroup) findViewById(R.id.view_date);
			layout.addView(seekBar);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		// create RangeSeekBar as Integer range between 20 and 75
		RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(0, 100, this);
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>()
		{
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer min_Value, Integer max_Value)
			{
				minValue = min_Value;
				maxValue = max_Value;

				onResume();
				// handle changed range values
				Log.d("RangeSeekBarActivity.onCreate(...).new OnRangeSeekBarChangeListener() {...}", "onRangeSeekBarValuesChanged::" + "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
			}
		});

		// add RangeSeekBar to pre-defined layout
		ViewGroup layout = (ViewGroup) findViewById(R.id.view_amount);
		layout.addView(seekBar);
	}

	@Override
	protected void onResume()
	{
		minValueTextView.setText("" + minimumDate);
		maxValueTextView.setText("" + maximumDate);
		// minValueTextView.setText("" + minValue);
		// maxValueTextView.setText("" + maxValue);
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
	 * Converts Date to String with the specified format.
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(date);
		return strDate;
	}

}
