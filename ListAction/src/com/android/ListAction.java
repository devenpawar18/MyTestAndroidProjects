package com.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListAction extends Activity {
	/** Called when the activity is first created. */

	Animation anim = null;
	private ListView lv;
	private ArrayList<ListDetails> la = new ArrayList<ListDetails>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		anim = AnimationUtils.loadAnimation(this, R.anim.magnify);
		lv = (ListView) findViewById(R.id.list_view);
		lv.setDivider(null);

		la.add(new ListDetails(R.drawable.ic_menu_barcode,"India"));
		la.add(new ListDetails(R.drawable.ic_menu_barcode,"Austrailia"));
		la.add(new ListDetails(R.drawable.ic_menu_barcode,"South Africa"));
		la.add(new ListDetails(R.drawable.ic_menu_barcode,"Sri Lanka"));
		la.add(new ListDetails(R.drawable.ic_menu_barcode,"Pakistan"));
		la.add(new ListDetails(R.drawable.icon,"England"));
		la.add(new ListDetails(R.drawable.icon,"New Zealand"));
		la.add(new ListDetails(R.drawable.icon,"West Indies"));
		la.add(new ListDetails(R.drawable.icon,"Bangladesh"));
		la.add(new ListDetails(R.drawable.icon,"Ireland"));
		la.add(new ListDetails(R.drawable.icon,"Zimbabwe"));

		lv.setAdapter(new ListAdapter(la, getApplicationContext()));
		lv.setOnItemClickListener(moreItemClickListener);
		
	}

	private OnItemClickListener moreItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Intent intent = null;
			arg1.startAnimation(anim);
//			intent = new Intent(ListAction.this, ListInDetail.class);
//			ListDetails b = la.get(position);
//			intent.putExtra("info", b.getText1());
//
//			ListAction.this.startActivity(intent);

		}

	};
	
	public void onItemSelected(AdapterView parent, View v, int position, long id) {
		v.startAnimation(anim);
	}

	class ListAdapter extends BaseAdapter {
		ArrayList<ListDetails> list_arr = new ArrayList<ListDetails>();
		Context context;

		public ListAdapter(ArrayList<ListDetails> l_arr, Context con) {

			list_arr = l_arr;
			context = con;
		}

		@Override
		public int getCount() {
			return list_arr.size();
		}

		@Override
		public Object getItem(int position) {
			return list_arr.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = null;

			if (convertView == null) {
				v = LayoutInflater.from(context).inflate(R.layout.list, parent,
						false);

			} else {
				v = (View) convertView;
			}
			ListDetails text = list_arr.get(position);

			ImageView iv = (ImageView) v.findViewById(R.id.img);
			TextView tv1 = (TextView) v.findViewById(R.id.desc);
			
			iv.setImageResource(text.getImage());
			tv1.setText(text.getText1());

			return v;
		}

	}
}