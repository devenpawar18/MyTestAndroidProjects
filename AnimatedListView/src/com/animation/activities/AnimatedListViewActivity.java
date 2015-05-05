package com.animation.activities;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AnimatedListViewActivity extends ListActivity implements
		AdapterView.OnItemSelectedListener {
	Animation anim = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		anim = AnimationUtils.loadAnimation(this, R.anim.magnify);
		setContentView(R.layout.main);
		ListView v = getListView(); // set up item selection listener
		v.setOnItemSelectedListener(this); // see OnItemSelectedListener methods
											// below
		ArrayList<String> items = new ArrayList<String>();
		items.add("Red");
		items.add("Grey");
		items.add("Cyan");
		items.add("Blue");
		items.add("Green");
		items.add("Yellow");
		items.add("Black");
		items.add("White");
		ArrayAdapter itemsAdapter = new ArrayAdapter(this, R.layout.row, items);
		v.setAdapter(getListAdapter());
		setListAdapter(itemsAdapter);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		v.startAnimation(anim);
	}

	// --- AdapterView.OnItemSelectedListener methods ---
	public void onItemSelected(AdapterView parent, View v, int position, long id) {
		v.startAnimation(anim);
	}

	public void onNothingSelected(AdapterView parent) {
	}
}
