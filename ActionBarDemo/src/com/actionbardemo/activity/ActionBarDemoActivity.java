package com.actionbardemo.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionBarDemoActivity extends Activity
{
	ActionBar actionBar;
	Drawable drawable;
	TextView tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "9930486071")));

		// Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		// emailIntent.setType("text/html");
		// emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "testing email send.");
		// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
		// Html.fromHtml("<b>this is html text in email body.</b>"));
		// startActivity(Intent.createChooser(emailIntent, "Email to Friend"));

		String dateString = "03/26/2012 11:49:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date convertedDate = new Date();
		Long l = null;

		try
		{
			convertedDate = dateFormat.parse(dateString);
			l = convertedDate.getTime();
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(convertedDate);
		tv = (TextView) findViewById(R.id.textview);
		// long longDate;
		// String str = "40625";
		// longDate = Long.parseLong(str);
		// Date date = new Date();
		// longDate = date.getTime();
		// Date date = new Date(1359624199000l);
		// DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// String strDate = formatter.format(date);
		// Date date = Calendar.getInstance().getTime();
		// DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// String today = formatter.format(date);
		// String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new
		// java.util.Date(1351683485208 * 1000));
		tv.setText("" + l);
		// tv.setText("" + longDate);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.HORIZONTAL);

		drawable = getResources().getDrawable(R.drawable.highlightitembgcolor);

		Button btn1 = new Button(this);
		Button btn2 = new Button(this);
		TextView tv = new TextView(this);
		LayoutParams lp = new LayoutParams(Gravity.LEFT);
		LayoutParams lp1 = new LayoutParams(Gravity.RIGHT);
		LayoutParams lp2 = new LayoutParams(Gravity.CENTER);
		// iv1.setLayoutParams(lp1);
		// iv.setLayoutParams(lp);
		// iv.setImageResource(R.drawable.map_marker);
		// iv1.setImageResource(R.drawable.ic_launcher);
		btn1.setText("Left");
		btn1.setLayoutParams(lp);
		btn2.setText("Right");
		btn2.setLayoutParams(lp1);
		tv.setText("Center");
		tv.setLayoutParams(lp2);
		actionBar = getActionBar();
		// actionBar.setTitle("ActionBar Demo");
		// actionBar.setSubtitle("SubTitle");
		// actionBar.setHomeButtonEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		layout.addView(btn1);
		layout.addView(btn2);
		layout.addView(tv);
		actionBar.setCustomView(layout);
		// actionBar.setCustomView(iv1, lp1);
		// actionBar.setCustomView(iv, lp);
		actionBar.setBackgroundDrawable(drawable);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.show();

	}

	// View view = LayoutInflater.from(this).inflate(R.layout.sherlock_header, null);
	// textview = (TextView) view.findViewById(R.id.mytext);
	// textview.setOnClickListener(new OnClickListener()
	// {
	//
	// @Override
	// public void onClick(View v)
	// {
	// Toast.makeText(IncidentsActivity.this, "Header clicked...", Toast.LENGTH_SHORT).show();
	//
	// }
	// });
	//
	// getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	// getSupportActionBar().setCustomView(R.layout.sherlock_header);

	// // Send Email
	// public static void sendEmail(Context context)
	// {
	// Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	// emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// emailIntent.setType("text/html");
	// emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
	// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(""));
	// context.startActivity(Intent.createChooser(emailIntent, "Email to Incident Contact"));
	// }
}