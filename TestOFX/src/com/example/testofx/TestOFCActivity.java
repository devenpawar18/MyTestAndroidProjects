package com.example.testofx;

import com.fifththird.ofx.proxy.parser.OfxSaxParserTest;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TestOFCActivity extends Activity
{
	private OfxSaxParserTest saxParsertest;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_ofc);
		saxParsertest = new OfxSaxParserTest();
		try
		{
			saxParsertest.testParser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_test_ofc, menu);
		return true;
	}

}
