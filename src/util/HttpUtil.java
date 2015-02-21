package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

	public static void requestHttpApi(final String apiUrl, final String appKey, final String secret, final Map<String, String> paramMap, final HttpCallbackListener listener) {	
		new Thread(new Runnable() {		
			@Override
			public void run() {
				try {
					String response;
					response = DemoApiTool.requestApi(apiUrl, appKey, secret, paramMap);
					if(listener != null) 
						listener.onFinish(response);
				} catch (Exception e) {
					if(listener != null) 
						listener.onError(e);
				} 		
			}
		}).start();		
	}
		
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url =  new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null) {
						response.append(line);
					}
					if(listener != null) {
						listener.onFinish(response.toString());
					}	
				} catch (Exception e) {
					if(listener != null) {
						listener.onError(e);
					}
				} finally {
					if(connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
