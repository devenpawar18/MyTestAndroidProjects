package com.example.testholoeverywhere;

import org.holoeverywhere.ArrayAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class TestHoloEveryWhere extends SherlockFragmentActivity
{

	private ActionBar actionBarSherlock;
	private SpinnerAdapter mSpinnerAdapter;
	private OnNavigationListener mOnNavigationListener;
	private android.support.v4.app.Fragment fragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	public static boolean isFirstFragment = false;

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_holo_every_where);

		actionBarSherlock = getSupportActionBar();
		actionBarSherlock.setTitle("Home Activity");
		actionBarSherlock.setHomeButtonEnabled(true);

		/**
		 * whether to show Standard Home Icon or not
		 */
		actionBarSherlock.setDisplayHomeAsUpEnabled(true);

		// actionBarSherlock.setDisplayShowCustomEnabled(true);

		/**
		 * whether to show custom Home Icon or not
		 */
		actionBarSherlock.setDisplayShowHomeEnabled(true);

		/**
		 * sets background to ActionBar Sherlock
		 */
		// actionBarSherlock.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_selector));
		// actionBarSherlock.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange));

		// actionBarSherlock.setLogo(R.drawable.abs__ic_voice_search_api_holo_light);
		// actionBarSherlock.setDisplayUseLogoEnabled(true);
		// actionBarSherlock.setIcon(R.drawable.abs__ic_ab_back_holo_light);
		actionBarSherlock.setIcon(R.drawable.abs__ic_voice_search_api_holo_light);

		getSupportActionBar().setNavigationMode(getSupportActionBar().NAVIGATION_MODE_LIST);
		mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.navigation_array, android.R.layout.simple_dropdown_item_1line);

		mOnNavigationListener = new OnNavigationListener()
		{
			public boolean onNavigationItemSelected(int itemPosition, long itemId)
			{

				fragmentManager = getSupportFragmentManager();
				fragmentTransaction = fragmentManager.beginTransaction();

				switch (itemPosition)
				{

					case 0:
						isFirstFragment = true;
						fragment = new DropDownFragment1();
						fragmentTransaction.replace(R.id.detailFragment, fragment);
						// fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
						Bundle bundle = new Bundle();
						bundle.putString("key", "wellcome");
						fragment.setArguments(bundle);
						// Toast.makeText(TestHoloEveryWhere.this, "Drop Down Item 1 Clicked",
						// Toast.LENGTH_SHORT).show();
						break;
					case 1:
						isFirstFragment = false;
						fragment = new DropDownFragment2();
						fragmentTransaction.replace(R.id.detailFragment, fragment);
						// fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
						// Toast.makeText(TestHoloEveryWhere.this, "Drop Down Item 2 Clicked",
						// Toast.LENGTH_SHORT).show();
						break;
					case 2:
						isFirstFragment = false;
						fragment = new DropDownFragment3();
						fragmentTransaction.replace(R.id.detailFragment, fragment);
						// fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
						// Toast.makeText(TestHoloEveryWhere.this, "Drop Down Item 3 Clicked",
						// Toast.LENGTH_SHORT).show();
						break;
					case 3:
						isFirstFragment = false;
						fragment = new DropDownFragment4();
						fragmentTransaction.replace(R.id.detailFragment, fragment);
						// fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
						// Toast.makeText(TestHoloEveryWhere.this, "Drop Down Item 4 Clicked",
						// Toast.LENGTH_SHORT).show();
						break;
				}
				return true;
			}
		};

		getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);

	}

	@Override
	public void onBackPressed()
	{
		if (isFirstFragment)
		{
			super.onBackPressed();
		}
		else
		{
			Intent intent = new Intent(TestHoloEveryWhere.this, TestHoloEveryWhere.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu)
	{
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_test_holo_every_where, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		// case R.id.menu_settings:
		// Toast.makeText(this, "Menu Clicked", Toast.LENGTH_SHORT).show();
		// break;

			case android.R.id.home:
				/**
				 * app icon in action bar clicked; go back
				 */
				finish();
				break;
			// case R.id.menu_1:
			// Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
			// break;
			case R.id.menu_2:
				Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sub_item_1:
				Toast.makeText(this, "Sub Menu 1 Clicked", Toast.LENGTH_SHORT).show();
				break;
			case R.id.sub_item_2:
				Toast.makeText(this, "Sub Menu 2 Clicked", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
