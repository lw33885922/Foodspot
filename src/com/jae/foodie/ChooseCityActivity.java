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
	private static final String[] dataList = {"�Ϻ�","����","����","����","�Ͼ�","����","����","�ɶ�","����","���","����","����","����","����","����","�人","�ع�","�麣","��ͷ","��ɽ","����","տ��","ï��","����","����","÷��","��β","��Դ","����","��Զ","��ݸ","��ɽ","����","����","�Ƹ�","����","����","����","����","����","���Ǹ�"};
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
     * ��ʼ��listview�����ð�ť�ĵ���¼�
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
