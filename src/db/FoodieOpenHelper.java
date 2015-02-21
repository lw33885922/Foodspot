package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodieOpenHelper extends SQLiteOpenHelper {

	private static final String CREATE_RESTAURANT = "create table Restaurant ("+" id integer primary key autoincrement,"+"restaurant_name text,"+"restaurant_address text)";
	private static final String CREATE_CITY = "create table City ("+" id integer primary key autoincrement,"+"city_name text)";
	
	public FoodieOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_RESTAURANT);
		db.execSQL(CREATE_CITY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
