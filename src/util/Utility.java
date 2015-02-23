package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import db.FoodieDB;
import model.City;
import model.Restaurant;

public class Utility {

	public synchronized static boolean handleRestaurantsResponse(FoodieDB foodieDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				int mcount = jsonObject.getInt("count");
				JSONArray businesses = jsonObject.getJSONArray("businesses");
				for(int i=0; i<mcount; i++) {
					JSONObject restaurantObj = businesses.getJSONObject(i);
					saveRestaurantInfo(foodieDB, restaurantObj.getString("name"),  restaurantObj.getString("address"), restaurantObj.getString("business_url"));
				}			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public synchronized static String handleCitiesResponse(FoodieDB foodieDB, String response) {
		String status = "ERROR";
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONObject jsonObject = new JSONObject(response);
				status = jsonObject.getString("status");
				JSONArray cities = jsonObject.getJSONArray("cities");		 
				for (int i = 0; i < cities.length(); i++) {
					City city = new City();
					city.setName(cities.getString(i));
					foodieDB.saveCity(city);
				}
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return status;
		}
		return status;
	}

	public static void saveRestaurantInfo(FoodieDB foodieDB, String name, String address, String url) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(name);
		restaurant.setAddress(address);
		restaurant.setUrl(url); 
		foodieDB.saveRestaurant(restaurant);
	}
}
