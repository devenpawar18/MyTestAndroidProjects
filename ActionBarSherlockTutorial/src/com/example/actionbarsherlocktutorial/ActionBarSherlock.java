package com.example.actionbarsherlocktutorial;

import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public class ActionBarSherlock extends SherlockFragmentActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action_bar_sherlock);

	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu)
	{
		getSupportMenuInflater().inflate(R.menu.activity_action_bar_sherlock, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);

		switch (item.getItemId())
		{
			case R.id.phone:
				Toast.makeText(getBaseContext(), "You selected Phone", Toast.LENGTH_SHORT).show();
				break;

			case R.id.computer:
				Toast.makeText(getBaseContext(), "You selected Computer", Toast.LENGTH_SHORT).show();
				break;

		}
		return true;
	}
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu)
	// {
	// getMenuInflater().inflate(R.menu.activity_action_bar_sherlock, menu);
	// return true;
	// }
}
