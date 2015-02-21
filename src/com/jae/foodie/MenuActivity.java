package com.jae.foodie;

import fragment.InfoFragment;
import fragment.MoreFragment;
import fragment.MyFragment;
import fragment.SearchFragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	private TextView mTextView;
	private TextView mTitleName;
	
	private int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fragment);
		
		mTitleName = (TextView) findViewById(R.id.tv_title_name);	
		Intent intent = getIntent();
		String titleTextName = intent.getStringExtra("titleText_data");	
		mTitleName.setText(titleTextName);
		if(titleTextName.equals("搜索")) {
			flag = 0;
		} else if(titleTextName.equals("我的")) {
			flag = 1;
		} else if(titleTextName.equals("更多")){
			flag = 2;
		} else {
			flag = 3;
		}
		
		MyFragment myFragment = new MyFragment();
		MoreFragment moreFragment = new MoreFragment();
		SearchFragment searchFragment = new SearchFragment();
		InfoFragment infoFragment = new InfoFragment();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		switch (flag) {
		case 0:
			transaction.replace(R.id.frag_container, searchFragment);
			transaction.commit();
			break;

		case 1:
			transaction.replace(R.id.frag_container, myFragment);
			transaction.commit();
			break;
			
		case 2:
			transaction.replace(R.id.frag_container, moreFragment);
			transaction.commit();
			break;
			
		case 3:
			transaction.replace(R.id.frag_container, infoFragment);
			transaction.commit();
			break;
			
		default:
			break;
		}
	
		//String editTextData = intent.getStringExtra("extra_data");
		//mTextView = (TextView) findViewById(R.id.tv_extra_data);
		//mTextView.setText(editTextData);		
	}
}
