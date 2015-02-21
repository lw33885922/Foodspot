package com.jae.foodie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChooseCityActivity extends Activity {
	
	private ListView mListView;
	private ArrayAdapter<String> adapter;
	private static final String[] dataList = {"上海","北京","杭州","广州","南京","苏州","深圳","成都","重庆","天津","宁波","扬州","无锡","福州","厦门","武汉","韶关","珠海","汕头","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮","南宁","柳州","桂林","梧州","北海","防城港"};
	private EditText mEditText;
	private Button mButton;
	private String extraData;
	private String item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_city);
		initView();
	}
	
	  /**
     * 初始化listview和设置按钮的点击事件
     */
	private void initView() {
		mEditText = (EditText) findViewById(R.id.et_choose_city);
		mButton = (Button) findViewById(R.id.btn_choose_city);
		mButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				extraData = mEditText.getText().toString();
				Intent intent = new Intent(ChooseCityActivity.this, MainActivity.class);
				intent.putExtra("extra_data", extraData);
				startActivity(intent);
			}
		});
		
		adapter = new ArrayAdapter<String>(ChooseCityActivity.this, android.R.layout.simple_list_item_1, dataList);
		mListView = (ListView) findViewById(R.id.lv_choose_city);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				item = dataList[position];
				mEditText.setText(item);
			}		
		});
	}
}
