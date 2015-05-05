package com.arbetsformedlingen.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arbetsformedlingen.activity.GlobalBaseActivity;
import com.arbetsformedlingen.activity.R;

public class ResultListSectionAdapter extends BaseAdapter {
	public final Map<String, Adapter> sections = new LinkedHashMap<String, Adapter>();
	public final HeaderAdapter headers;
	public final static int TYPE_SECTION_HEADER = 0;
	public final static int TYPE_EMPTY_HEADER = -1;
	public Context context;

	public ResultListSectionAdapter(Context context) {
		this.context = context;
		headers = new HeaderAdapter();
	}

	public void addSection(String section, Adapter adapter) {
		this.headers.add(section);
		this.sections.put(section, adapter);
	}

	public Object getItem(int position) {
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0)
				return section;
			if (position < size)
				return adapter.getItem(position - 1);

			// otherwise jump into next section
			position -= size; // (size + (isSectionEmpty?1:0));
		}
		return null;
	}

	public int getCount() {
		// total together all sections, plus one for each section header and one
		// for empty sections.
		int total = 0;
		for (Adapter adapter : this.sections.values()) {
			total += adapter.getCount() + 1;
		}
		return total;
	}

	public int getViewTypeCount() {
		// assume that headers count as one, then total all sections
		int total = 1;// +1;
		for (Adapter adapter : this.sections.values())
			total += adapter.getViewTypeCount();
		return total;
	}

	public int getItemViewType(int position) {
		int type = 1;
		boolean isSectionEmpty = false;

		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			isSectionEmpty = adapter.getCount() <= 0 ? true : false;

			// check if position inside this section
			if (position == 0)
				return TYPE_SECTION_HEADER;
			if (position < size)
				return type + adapter.getItemViewType(position - 1);
			// otherwise jump into next section
			position -= size; 
			type += adapter.getViewTypeCount();
		}
		return -1;
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isEnabled(int position) {
		return (getItemViewType(position) != TYPE_SECTION_HEADER);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int sectionnum = 0;
		boolean isSectionEmpty = false;
		for (Object section : this.sections.keySet()) {
			Adapter adapter = sections.get(section);
			int size = adapter.getCount() + 1;

			// check if position inside this section
			if (position == 0) {
				return headers.getView(sectionnum, convertView, parent);
			}

			if (position < size)
				return adapter.getView(position - 1, convertView, parent);

			// otherwise jump into next section
			position -= size; // (size+(isSectionEmpty?1:0));
			// position -= (size);
			sectionnum++;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class HeaderAdapter extends BaseAdapter {

		private ArrayList<String> headerList = new ArrayList<String>();

		public void add(String object) {
			headerList.add(object);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			try {
				View view = null;
				TextView text = null;
				String title = headerList.get(position);
				if (title.length() == 0) {
					view = LayoutInflater.from(context).inflate(R.layout.info_header_without_margin, parent, false);
					return view;
				} else {
					if(sections.get(title).getCount()>0)
						view = LayoutInflater.from(context).inflate(R.layout.freetext_header, parent, false);
					else
					{
						view = LayoutInflater.from(context).inflate(R.layout.info_header_without_margin, parent, false);
						return view;
					}
					
				}
				text = (TextView) view.findViewById(R.id.jobCount);
				if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
					text.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/DejaVuSans.ttf"));
				}
				text.setText(title);
				text.setTag(title);
				return view;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public int getCount() {
			return headerList != null ? headerList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return headerList != null ? headerList.get(position) : "";
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}
}
