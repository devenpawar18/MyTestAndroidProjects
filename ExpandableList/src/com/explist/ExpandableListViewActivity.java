/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.explist;

import android.R.color;
import android.app.ExpandableListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class ExpandableListViewActivity extends ExpandableListActivity {

	ExpandableListAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set up our adapter
		mAdapter = new MyExpandableListAdapter();
		setListAdapter(mAdapter);
		registerForContextMenu(getExpandableListView());
	}

//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		menu.setHeaderTitle("Sample menu");
//		menu.add(0, 0, 0, R.string.expandable_list_sample_action);
//	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();
		String title = ((TextView) info.targetView).getText().toString();

		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView
					.getPackedPositionChild(info.packedPosition);
			Toast.makeText(
					this,
					title + ": Child " + childPos + " clicked in group "
							+ groupPos, Toast.LENGTH_SHORT).show();
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			Toast.makeText(this, title + ": Group " + groupPos + " clicked",
					Toast.LENGTH_SHORT).show();
			return true;
		}

		return false;
	}

	/**
	 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
	 * photo is displayed as an image. This adapter supports clearing the list
	 * of photos and adding a new photo.
	 * 
	 */
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		// Sample data set. children[i] contains the children (String[]) for
		// groups[i].
		private String[] groups = { "Planning & Control", "Infrastructure",
				"Preparation", "Specification", "Execution", "Completion" };
		private String[][] children = { { "Aim", "Activities", "Products" },
				{ "Aim", "Activities", "Products" },
				{ "Aim", "Activities", "Products" },
				{ "Aim", "Activities", "Products" },
				{ "Aim", "Activities", "Products" },
				{ "Aim", "Activities", "Products" } };

		public Object getChild(int groupPosition, int childPosition) {
			return children[groupPosition][childPosition];
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			return children[groupPosition].length;
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);

			TextView textView = new TextView(ExpandableListViewActivity.this);
			textView.setLayoutParams(lp);
			// Center the text vertically
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			textView.setPadding(5, 0, 0, 0);
			return textView;
		}

		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setTextColor(Color.BLACK);
			textView.setBackgroundColor(Color.WHITE);
			textView.setText("- "
					+ getChild(groupPosition, childPosition).toString());
			return textView;
		}

		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}

		public int getGroupCount() {
			return groups.length;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setTextColor(Color.BLACK);
			switch (groupPosition) {
			case 0:
				textView.setBackgroundResource(R.drawable.planningControl);
				break;
			case 1:
				textView.setBackgroundResource(R.drawable.infrastructure);
				break;
			case 2:
				textView.setBackgroundResource(R.drawable.preparation);
				break;
			case 3:
				textView.setBackgroundResource(R.drawable.specification);
				break;
			case 4:
				textView.setBackgroundResource(R.drawable.execution);
				break;
			case 5:
				textView.setBackgroundResource(R.drawable.completion);
				break;
			}
			// textView.setBackgroundColor(Color.CYAN);
			textView.setPadding(45, 0, 0, 0);
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

	}
}
