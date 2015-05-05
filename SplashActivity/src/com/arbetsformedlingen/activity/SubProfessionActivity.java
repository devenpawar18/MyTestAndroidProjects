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
import com.arbetsformedlingen.entity.FilterEntity;
import com.arbetsformedlingen.entity.JobEntity;

public class SubProfessionActivity extends GlobalBaseActivity {
	
	
	String professionId;
	String professionName;
	String subGroupId;
	String subGroupName;
	ListView itemList;
	private ActivityState ActivityState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grouplayout);
		
		professionId = getIntent().getStringExtra("ProfessionID");
		professionName = getIntent().getStringExtra("ProfessionName");
		subGroupId = getIntent().getStringExtra("GroupID");
		subGroupName = getIntent().getStringExtra("GroupName");
		
		itemList = (ListView) findViewById(R.id.itemList);
		itemList.setDivider(null);
		itemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				JobEntity entity = (JobEntity)arg1.getTag();
				Intent newIntent;
				if(TextUtils.isEmpty(entity.getId())){
					return;
				}
				
				switch(currentActivityState)
				{
				case Profession:
					newIntent = new Intent(SubProfessionActivity.this,SubProfessionActivity.class);
					newIntent.putExtra("GroupID", entity.getId());
					newIntent.putExtra("GroupName", entity.getName());
					startActivity(newIntent);
				break;
				case SubProfession:
					FilterEntity.newFilterInstance();
					newIntent = new Intent(SubProfessionActivity.this,ResultListActivity.class);
					FilterEntity.getFilterInstance().professionId = entity.getId();
					FilterEntity.getFilterInstance().professionName = entity.getName();
					FilterEntity.getFilterInstance().filterSearchType = FilterEntity.SERACH_BY_OCCUPATION;
					startActivity(newIntent);
					break;
				
				}
				
			}
			
		});
		AppController controller = new AppController(SubProfessionActivity.this);
		if(!TextUtils.isEmpty(professionId)&& TextUtils.isEmpty(subGroupId)){
			currentActivityState = ActivityState.Profession;
			//setTitleText(PROFESSION_TITLE);
			setTitleWithResource(R.string.occupational_Group);
			controller.getProfessions(PROFESSION_URL + professionId);
		}else
		//if(!TextUtils.isEmpty(subGroupId)){
		{
			currentActivityState = ActivityState.SubProfession;
			//setTitleText(SUBPROFESSION_TITLE);
			setTitleWithResource(R.string.occupation);
			controller.getProfessions(SUBPROFESSION_URL + subGroupId);
		}

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ActivityState = currentActivityState;
	}

	
	@Override
	protected void onRestart() {
		super.onRestart();
		currentActivityState = ActivityState;
	}

	@Override
	public void refreshList() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void refreshCompleted(Object data) {
		switch(currentActivityState)
		{
		case Profession:
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_occupational_groups_groups), Toast.LENGTH_SHORT).show();
				break;
			}
			itemList.setAdapter(new SingleItemAdapter(this,professionName,(ArrayList<JobEntity>)data));
		break;
		case SubProfession:
			if (data == null) {
				Toast.makeText(this, (String) getResources().getText(R.string.could_not_get_occupations), Toast.LENGTH_SHORT).show();
				break;
			}
			itemList.setAdapter(new SingleItemAdapter(this,subGroupName,(ArrayList<JobEntity>)data));
			break;
		
		}
		
//		if(itemList ==null || itemList.getAdapter()== null || itemList.getAdapter().getCount()<=0)
//		{
//			TextView emptyText = (TextView)findViewById(R.id.emptyText);
//			emptyText.setText(R.string.emptyValueText);
//		}
		
	}

}
