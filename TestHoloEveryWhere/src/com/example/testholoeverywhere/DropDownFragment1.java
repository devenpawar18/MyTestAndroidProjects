package com.example.testholoeverywhere;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.actionbarsherlock.app.SherlockFragment;

public class DropDownFragment1 extends SherlockFragment
{
	private RelativeLayout viewer = null;

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		viewer = (RelativeLayout) inflater.inflate(R.layout.dropdown_fragment_1, container, false);
		return viewer;
	}

}
