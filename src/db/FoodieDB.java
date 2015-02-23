package db;

import java.util.ArrayList;
import java.util.List;

import com.jae.foodie.R;

import model.City;
import model.Restaurant;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodieDB {
	
	public static final String DB_NAME = "foodie";
	public static final int VERSION = 1;
	private static FoodieDB foodieDB;
	private SQLiteDatabase db;
	
	private FoodieDB(Context context) {
		FoodieOpenHelper dbHelper = new FoodieOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	public synchronized static FoodieDB getInstance(Context context) {
		if(foodieDB == null) {
			foodieDB = new FoodieDB(context);
		}
		return foodieDB;
	}
	
	public void saveRestaurant(Restaurant restaurant) {
		if(restaurant != null) {
			ContentValues values = new ContentValues();
			values.put("restaurant_name", restaurant.getName());
			values.put("restaurant_address", restaurant.getAddress());
			values.put("restaurant_url", restaurant.getUrl());
			db.insert("Restaurant", null, values);
		} 
	}
	
	public void deleteRestaurant() {
		db.delete("Restaurant", "id >= ?",  new String[]{"0"});
	}
	
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DB_NAME);
	}
	
	public List<Restaurant> loadRestaurants() {
		List<Restaurant> list = new ArrayList<Restaurant>();
		Cursor cursor = db.query("Restaurant", null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				Restaurant restaurant = new Restaurant();
				restaurant.setName(cursor.getString(cursor.getColumnIndex("restaurant_name")));
				restaurant.setId(R.drawable.icon);
				restaurant.setAddress(cursor.getString(cursor.getColumnIndex("restaurant_address")));
				restaurant.setUrl(cursor.getString(cursor.getColumnIndex("restaurant_url")));
				list.add(restaurant);
			} while(cursor.moveToNext());
		}
		return list;
	}

	public void saveCity(City city) {
		if(city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getName());
			db.insert("City", null, values);
		} 
	}
	
	public List<City> loadCities() {
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, null, null, null, null, null);
		if(cursor.moveToFirst()) {
			do {
				City city = new City();
				city.setName(cursor.getString(cursor.getColumnIndex("city_name")));
				list.add(city);
			} while(cursor.moveToNext());
		}
		return list;
	}
	
}
