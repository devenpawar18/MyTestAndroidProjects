package com.arbetsformedlingen.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.arbetsformedlingen.adapter.SingleItemAdapter;
import com.arbetsformedlingen.controller.AppController;
import com.arbetsformedlingen.entity.JobEntity;

public class ProfessionActivity extends GlobalBaseActivity {
	

	ListView itemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grouplayout);
		
		//setTitleText(OCCUPATION_TITLE);
		setTitleWithResource(R.string.occupational_Field);
		
		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		itemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				JobEntity entity = (JobEntity)arg1.getTag();
				if(!TextUtils.isEmpty(entity.getId())){
				Intent newIntent = new Intent(ProfessionActivity.this,SubProfessionActivity.class);
				newIntent.putExtra("ProfessionID", entity.getId());
				newIntent.putExtra("ProfessionName", entity.getName());
				startActivity(newIntent);
				}
			}
			
		});
		
		AppController controller = new AppController(ProfessionActivity.this);
        controller.getProfessions(OCCUPATION_URL);
		
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshCompleted(Object data) {
		if (data == null) {
			Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_occupational_fields), Toast.LENGTH_SHORT).show();
		}
		else
			itemList.setAdapter(new SingleItemAdapter(this,"Professions",(ArrayList<JobEntity>)data));
//		if(itemList ==null || itemList.getAdapter()== null || itemList.getAdapter().getCount()<=0)
//		{
//			TextView emptyText = (TextView)findViewById(R.id.emptyText);
//			emptyText.setText(R.string.emptyValueText);
//			emptyText.setVisibility(View.VISIBLE);
//		}
		
	}

}
