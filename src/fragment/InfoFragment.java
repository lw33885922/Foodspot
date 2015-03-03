package fragment;

import com.jae.foodie.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InfoFragment extends Fragment {
	
	private String restaurantUrl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		restaurantUrl = getFragmentManager().findFragmentByTag("110").getArguments().getString("restaurant_url");
		View view = inflater.inflate(R.layout.fragment_info, container,false);
		WebView webView = (WebView)view.findViewById(R.id.wv_info);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		webView.loadUrl(restaurantUrl);
		return view;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
	}
}
