package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobEntity;

public class FilterActivity extends GlobalBaseActivity {
	
	private AlertDialog listDialog;
	private TextView professionValue;
	private TextView placeValue;
	public static ArrayList<JobEntity> countyList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter);
		
		//setTitleText(FILTER_TITLE);
		setTitleWithResource(R.string.filter);
		
		TableRow categoryLayout = (TableRow) findViewById(R.id.placeLayout);
		categoryLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if (countyList == null) {
					AppController controller = new AppController(FilterActivity.this);
					controller.getCountries(COUNTRIES_URL);
				} else {
					showDialog((String)getResources().getText(R.string.County), countyList, countryItmeClickListner);
				}
			}
			
		});
		
		TableRow professionLayout = (TableRow) findViewById(R.id.professionLayout);
		professionLayout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//currentActivityState = ActivityState.FilterProfession;
				Intent newIntent = new Intent(FilterActivity.this,SearchProfessionActivity.class);
				startActivity(newIntent);
			}
		});
		
		
		if(currentActivityState==ActivityState.SubProfession) {
			professionLayout.setVisibility(View.GONE);
			TableRow divider = (TableRow) findViewById(R.id.bottom_divider);
			divider.setVisibility(View.GONE);
			
		}
		else {
			categoryLayout.setVisibility(View.GONE);
			TableRow divider = (TableRow) findViewById(R.id.top_divider);
			divider.setVisibility(View.GONE);
		}
		
		String allText = (String)getResources().getText(R.string.all);
		
		professionValue = (TextView) findViewById(R.id.professionValue);
		
		professionValue.setText(TextUtils.isEmpty(FilterEntity.getFilterInstance().professionName)?allText:FilterEntity.getFilterInstance().professionName);
		placeValue = (TextView) findViewById(R.id.placeValue);
		
		placeValue.setText(TextUtils.isEmpty(FilterEntity.getFilterInstance().cityName)?allText:FilterEntity.getFilterInstance().cityName);
		

	}
	
	public OnItemClickListener countryItmeClickListner = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			JobEntity entity = (JobEntity)view.getTag();
			FilterEntity.getFilterInstance().cityId = entity.getId();
			if (entity.getId().length() != 0) {
				FilterEntity.getFilterInstance().cityName = entity.getName();
				placeValue.setText(FilterEntity.getFilterInstance().cityName);
			} else {
				FilterEntity.getFilterInstance().cityName = "";
				placeValue.setText((String)getResources().getText(R.string.all));
			}
			
			if(listDialog!=null)
				listDialog.dismiss();
		}
		
	};
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
//		getMenuInflater().inflate(menuRes, menu)
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	@Override
	protected void onStart() {
		String allText = (String)getResources().getText(R.string.all);
		professionValue = (TextView) findViewById(R.id.professionValue);
		
		professionValue.setText(TextUtils.isEmpty(FilterEntity.getFilterInstance().professionName)?allText:FilterEntity.getFilterInstance().professionName);
		placeValue = (TextView) findViewById(R.id.placeValue);
		
		placeValue.setText(TextUtils.isEmpty(FilterEntity.getFilterInstance().cityName)?allText:FilterEntity.getFilterInstance().cityName);

		super.onStart();
	}
	/**
	 * Displays a dialog with a list selection for the user to choose Brand,
	 * Working Area or the ChipBreaker item
	 * 
	 * @param title
	 *            - Title for the dialog
	 * @param query
	 *            - Query to be executed on the Database to populate the list
	 * @param colomnValue
	 *            - Colomn value selected to be displayed from the Database
	 * @param onItemClickListener
	 *            - Listener responsible to handle the ItemClicks
	 */
	private void showDialog(String title, ArrayList<JobEntity> listArr, OnItemClickListener onItemClickListener) {
		final AlertDialog.Builder dialogBuilder;
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		View dialogLayout = inflater.inflate(R.layout.list_dialog, null);
		ListView itemList = (ListView) dialogLayout.findViewById(R.id.dialog_list);

		itemList.setAdapter(new DialogAdapter(getApplicationContext(), listArr));
		itemList.setOnItemClickListener(onItemClickListener);
		dialogBuilder = new AlertDialog.Builder(this);
		dialogBuilder.setView(dialogLayout);
		dialogBuilder.setTitle(null);
		listDialog = dialogBuilder.create();
		listDialog.setTitle(title);
		listDialog.setInverseBackgroundForced(true);
		listDialog.show();
	}
	
	private class DialogAdapter extends BaseAdapter {

		private ArrayList<JobEntity> listArr;
		private Context context;

		public DialogAdapter(Context context, ArrayList<JobEntity> listArr) {
			this.context = context;
			this.listArr = listArr;
		}

		@Override
		public int getCount() {
			return listArr.size();
		}

		@Override
		public Object getItem(int position) {
			return listArr.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			JobEntity entity = listArr.get(position);
			View view = null;
			try {
				TextView textView = null;
				if (convertView == null) {
					view = (View) LayoutInflater.from(context).inflate(R.layout.dialog_item_row, parent, false);
				} else {
					view = convertView;
				}
				textView = (TextView) view.findViewById(R.id.dialog_text);
				view.setTag(entity);
				if (GlobalBaseActivity.currentLocale != null && GlobalBaseActivity.currentLocale.equals("ar")) {
					textView.setTypeface(Typeface.createFromAsset(FilterActivity.this.getAssets(), "fonts/DejaVuSans.ttf"));
				}
				textView.setText(entity.getName());
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("DialogAdapter", "getView :: " + "Exception :: " + e.toString());
			}
			return view;
		}
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCompleted(Object data) {
		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_counties), Toast.LENGTH_SHORT).show();
			return;
		}
		countyList = (ArrayList<JobEntity>)data;
		JobEntity entity = new JobEntity();
		entity.setId("");
		entity.setName((String)getResources().getText(R.string.all));
		entity.setJobCount("");
		countyList.add(0, entity);
		showDialog((String)getResources().getText(R.string.County), countyList, countryItmeClickListner);
		
	}
	

}
