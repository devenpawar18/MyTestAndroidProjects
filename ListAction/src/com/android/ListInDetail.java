package com.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListInDetail extends Activity {

	private TextView desc;
	private Button b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_action);
		Intent intent = new Intent();
		desc = (TextView) findViewById(R.id.desc);

		b = (Button) findViewById(R.id.button);
		desc.setText(getIntent().getExtras().getString("info"));

		b = (Button) this.findViewById(R.id.button);

		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ListInDetail.this, ListAction.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);

			}
		});

	}

}
