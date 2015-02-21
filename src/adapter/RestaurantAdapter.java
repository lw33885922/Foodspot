package adapter;

import java.util.List;

import com.jae.foodie.R;

import model.Restaurant;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

	private int resourceId;
	public RestaurantAdapter(Context context, int textViewResourceId,
			List<Restaurant> objects) {
		super(context, textViewResourceId, objects);	
		resourceId = textViewResourceId;
	}
	
	/**
	 * @author jae
	 *通过ViewHolder优化listView获取控件实例
	 */
	class ViewHolder {
		ImageView  restaurantImage;
		TextView restaurantName;
		TextView restaurantDescription;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder viewHolder;
		Restaurant restaurant = getItem(position);
		
		if(convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.restaurantImage = (ImageView) view.findViewById(R.id.iv_restaurant);
			viewHolder.restaurantName = (TextView) view.findViewById(R.id.tv_restaurant_name);
			viewHolder.restaurantDescription = (TextView) view.findViewById(R.id.tv_restaurant_description);
			view.setTag(viewHolder);
		} else {
			view  = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.restaurantImage.setImageResource(restaurant.getId());
		viewHolder.restaurantName.setText(restaurant.getName());
		viewHolder.restaurantDescription.setText(restaurant.getAddress());
		return view;
	}
}
