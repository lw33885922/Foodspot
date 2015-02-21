package com.jae.foodie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jae.foodie.R;

import db.FoodieDB;
import model.Restaurant;
import util.HttpCallbackListener;
import util.HttpUtil;
import util.Utility;
import view.SlidingMenu;
import adapter.RestaurantAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import api.FoodieApi;

 public class MainActivity extends Activity implements OnClickListener { 
	
	private ProgressDialog progressDialog;
    private FoodieDB foodieDB;
    private RestaurantAdapter adapter;
    private ListView listView;
    private Map<String, String> paramMap = new HashMap<String, String>();
    private List<Restaurant> datasList = new ArrayList<Restaurant>();
    private List<Restaurant> restaurantList;
    private SlidingMenu mLeftMenu ;
    private Button mTitleSearchButton;
    
    private RelativeLayout mMenu1;
    private RelativeLayout mMenu2;
    private RelativeLayout mMenu3;
    
    private String titleTextData;
    private TextView mtitleTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        foodieDB = FoodieDB.getInstance(this);
        
        //获取标题
        mtitleTextView = (TextView) findViewById(R.id.et_title_text);
        Intent intent = getIntent();
        titleTextData =intent.getStringExtra("extra_data");
        mtitleTextView.setText(titleTextData);
        
        initParamMap(); 
        initView();
        queryRestaurants();
     }
    
    /**
     * 初始化控件
     */
	private void initView() {
		 mLeftMenu = (SlidingMenu) findViewById(R.id.id_menu);
		 adapter = new RestaurantAdapter(MainActivity.this, R.layout.listview_item, datasList);
	     listView = (ListView) findViewById(R.id.lv_main);
	     listView.setAdapter(adapter);
	     listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				titleTextData = "餐厅信息";
				Intent intent = new Intent(MainActivity.this, MenuActivity.class);
				intent.putExtra("titleText_data", titleTextData);
				startActivity(intent);
			}
		});
	     
	     mTitleSearchButton = (Button) findViewById(R.id.btn_title_search);
	     mTitleSearchButton.setOnClickListener(this);
	     
	     mMenu1 = (RelativeLayout) findViewById(R.id.menu1);
	     mMenu2 = (RelativeLayout) findViewById(R.id.menu2);
	     mMenu3 = (RelativeLayout) findViewById(R.id.menu3);
	     mMenu1.setOnClickListener(this);
	     mMenu2.setOnClickListener(this);
	     mMenu3.setOnClickListener(this);     
	}
	
	/**
     * 点击事件，界面切换
     */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_search:
			titleTextData = "搜索";
			Intent intent1 = new Intent(MainActivity.this, MenuActivity.class);
			intent1.putExtra("titleText_data", titleTextData);
			startActivity(intent1);
			break;
		case R.id.menu1:
			mLeftMenu.toggle();
			break;
		case R.id.menu2:
			titleTextData = "我的";
			Intent intent2 = new Intent(MainActivity.this, MenuActivity.class);
			intent2.putExtra("titleText_data", titleTextData);
			startActivity(intent2);
			break;
		case R.id.menu3:
			titleTextData = "更多";
			Intent intent3 = new Intent(MainActivity.this, MenuActivity.class);
			intent3.putExtra("titleText_data", titleTextData);
			startActivity(intent3);
			break;

		default:
			break;
		}
	}
	
	/**
     * 切换菜单
     */
	public void toggleMenu(View view)
	{
		mLeftMenu.toggle();
	}
	
	/**
     * 从数据库加载餐厅数据
     */
    private void queryRestaurants() {
    	restaurantList = foodieDB.loadRestaurants();
    	if(restaurantList.size() > 0) {
    		datasList.clear();
    		for(Restaurant restaurant : restaurantList) {
    			datasList.add(restaurant);
    		}	
    		adapter.notifyDataSetChanged();
    		listView.setSelection(0);	
    	} else {
    		queryFromServer();
    	}	
    }
    
    /**
     * 从服务器加载餐厅数据
     */
    private void queryFromServer() {	
    	
    	showProgressDialog();
    	HttpUtil.requestHttpApi(FoodieApi.apiUrl, FoodieApi.appKey, FoodieApi.secret, paramMap, new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {	
				boolean result = false;
				result = Utility.handleRestaurantsResponse(foodieDB, response);
				if(result) {
					runOnUiThread(new Runnable() {			
						@Override
						public void run() {
							closeProgressDialog();
							queryRestaurants();
						}
					});
				} else {
					runOnUiThread(new Runnable() {		
						@Override
						public void run() {
							closeProgressDialog();
							Toast.makeText(MainActivity.this, "加载数据失败,请检查网络连通性", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {		
				runOnUiThread(new Runnable() {			
					@Override
					public void run() {
						closeProgressDialog();
						Toast.makeText(MainActivity.this, "加载失败,请检查网络连通性", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});	  	
    }
    
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
    	if(progressDialog == null) {
    		progressDialog = new ProgressDialog(this);
    		progressDialog.setMessage("正在加载...");
    		progressDialog.setCanceledOnTouchOutside(false);
    	}
    	progressDialog.show();
    }
    
    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
    	if(progressDialog != null) {
    		progressDialog.dismiss();
    	}
    }

	/**
     * 初始化paramMap数据
     */
	private void initParamMap() {
		paramMap.put("category", "美食");
		paramMap.put("city", titleTextData);
        //paramMap.put("latitude", "31.18268013000488");
        //paramMap.put("longitude", "121.42769622802734");
        paramMap.put("sort", "1");
        paramMap.put("limit", "20");
        paramMap.put("out_offset_type", "1");      
        paramMap.put("platform", "2");
	}  
	
	/**
     * 释放数据，清除餐厅数据
     */
	@Override
	protected void onDestroy() {
		paramMap.clear();
		adapter.clear();
		datasList.clear();
		//？？delay餐厅数据从数据库中  仿照FoodieDB.java中的saveRestaurant()写个delayRestaurant() 
		super.onDestroy();
	}
}
