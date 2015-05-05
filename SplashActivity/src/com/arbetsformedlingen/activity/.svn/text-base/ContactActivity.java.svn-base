package com.arbetsformedlingen.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.arbetsformedlingen.adapter.ContactScreenAdapter;

public class ContactActivity extends GlobalBaseActivity{

	private ContactScreenAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        
		setTitleWithResource(R.string.contact);
        
    	String TECHNICAL_SUPPORT_NUMBER =(String)getResources().getText(R.string.techsupportNumberText);
    	String VISIT_US = (String)getResources().getText(R.string.visit_Us);
    	String FOLLOW_ON = (String)getResources().getText(R.string.follow_Us_On);
    	String SEND_BY_MAIL = (String)getResources().getText(R.string.send_Email);
    	String CUSTOMER_SERVICE_NUMBER = (String)getResources().getText(R.string.cxSupportNumberText);
    	String FACEBOOK_TITLE = (String)getResources().getText(R.string.facebook);
    	
    	String TECH_SUPPORT = (String)getResources().getText(R.string.technical_Support);
    	String CUSTOMER_SERVICE = (String)getResources().getText(R.string.customer_Service);


    	ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> titleImageMap = new HashMap<String, String>();
		titleImageMap.put("title", CUSTOMER_SERVICE_NUMBER);
		titleImageMap.put("image", Integer.toString(R.drawable.contact_phone_icon));
		list.add(titleImageMap);

		titleImageMap = new HashMap<String, String>();
		titleImageMap.put("title", SEND_BY_MAIL);
		titleImageMap.put("image", Integer.toString(R.drawable.contact_mail_icon));
		list.add(titleImageMap);
		
		titleImageMap = new HashMap<String, String>();
		titleImageMap.put("title", FOLLOW_ON + " " + FACEBOOK_TITLE);
		titleImageMap.put("image",Integer.toString(R.drawable.contact_facebook_icon));
		list.add(titleImageMap);
		
		titleImageMap = new HashMap<String, String>();
		titleImageMap.put("title", VISIT_US);
		titleImageMap.put("image", Integer.toString(R.drawable.contact_list_icon));
		list.add(titleImageMap);

		ArrayList<Map<String, String>> supportlistMap = new ArrayList<Map<String, String>>();
		titleImageMap = new HashMap<String, String>();
		titleImageMap.put("title", TECHNICAL_SUPPORT_NUMBER);
		titleImageMap.put("image", Integer.toString(R.drawable.contact_phone_icon));
		supportlistMap.add(titleImageMap);

		titleImageMap = new HashMap<String, String>();
		titleImageMap.put("title", SEND_BY_MAIL);
		titleImageMap.put("image", Integer.toString(R.drawable.contact_mail_icon));
		supportlistMap.add(titleImageMap);
		
		adapter = new ContactScreenAdapter(this);
		adapter.addSection("", new ContactsImageAdapter(this, list));
		adapter.addSection(TECH_SUPPORT, new ContactsImageAdapter(this, supportlistMap));
		
		
		ListView contactList = (ListView) findViewById(R.id.contactList);
		contactList.setAdapter(adapter);
		contactList.setDivider(null);
		contactList.setOnItemClickListener(contactItemClickListener);
		
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
	}

	private OnItemClickListener contactItemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			Intent newIntent;
			switch(position)
			{
			case 1:
				GlobalBaseActivity.tracker.trackEvent("EmpContact", "Phone",  "Android", 6);      
				
				TextView phView = (TextView)view.findViewById(R.id.valueTextView);
				String number =(String)phView.getText();
				number = number.replace(" ", "");
				number = number.replace("-", "");
				newIntent = new Intent(android.content.Intent.ACTION_CALL);
				newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				newIntent.setData(Uri.parse("tel:" + number));
				startActivity(newIntent);
				break;
			case 2:
				GlobalBaseActivity.tracker.trackEvent("EmpContact", "Mail",  "Android", 7);      
				newIntent = new Intent(Intent.ACTION_SEND);
				String to="kundtjanst@arbetsformedlingen.se", subject=(String)getResources().getText(R.string.customer_Service),body="";
				newIntent.setType("text/html");
				newIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { to });
				newIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
				if (body != null)
					newIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
				else
					newIntent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(newIntent, "Select email application."));
				break;
			case 3:
				GlobalBaseActivity.tracker.trackEvent("EmpContact", "Facebook",  "Android", 8);      
				newIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/Arbetsformedlingen"));
				startActivity(newIntent);
				break;
			case 4:
				currentActivityState = ActivityState.ContactCountry;
				newIntent = new Intent(ContactActivity.this, WorkLocationActivity.class);
				newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(newIntent);
				break;
			case 6:
				GlobalBaseActivity.tracker.trackEvent("EmpContact", "Phone",  "Android", 6);      
				
				TextView phNumberView = (TextView)view.findViewById(R.id.valueTextView);
				String technumber =(String)phNumberView .getText();
				technumber = technumber.replace(" ", "");
				technumber = technumber.replace("-", "");
				newIntent = new Intent(android.content.Intent.ACTION_CALL);
				newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				newIntent.setData(Uri.parse("tel:" + technumber));
				startActivity(newIntent);
				break;
			case 7:
				GlobalBaseActivity.tracker.trackEvent("EmpContact", "Mail",  "Android", 7);      
				
				newIntent = new Intent(Intent.ACTION_SEND);
				String tech_to="support@arbetsformedlingen.se", tech_subject=(String)getResources().getText(R.string.technical_Support),tech_body="";
				newIntent.setType("text/html");
				newIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { tech_to });
				newIntent.putExtra(Intent.EXTRA_SUBJECT, tech_subject);
				if (tech_body != null)
					newIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(tech_body));
				else
					newIntent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(newIntent, "Select email application."));
				break;
			default:
				Toast.makeText(ContactActivity.this, "Wrong Selection", 500).show();
				break;
			}
		
		}
		
	};

	@Override
	public void refreshCompleted(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		
	}
	
	public class ContactsImageAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<Map<String, String>> valuesList = new ArrayList<Map<String, String>>();

		/**
		 * @param context
		 * @param c
		 */
		public ContactsImageAdapter(Context m_context, ArrayList<Map<String, String>> list) {
			this.context = m_context;
			this.valuesList = list;
		}

		@Override
		public int getCount() {
			return valuesList != null ? valuesList.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return valuesList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (v == null) {
				v = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.imageitem_row, parent, false);
			}
			
			Map<String, String> valueMap = valuesList.get(position);
			
			TextView valueTextView = (TextView) v.findViewById(R.id.valueTextView);
			ImageView itemImageView = (ImageView) v.findViewById(R.id.itemImageView);
				
			valueTextView.setText(valueMap.get("title"));
			itemImageView.setImageResource(Integer.parseInt(valueMap.get("image")));
			
			v.setTag(valueMap);
			return v;

		}
	}
	
}
