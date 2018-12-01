package windows.client.utils;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestUtils {
	private final static String BASE_URL = "http://localhost:8080/windows";
	
	public static HttpResponse doPost(JSONObject object, String url) throws UnsupportedEncodingException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		StringEntity entity = new StringEntity(object.toString());
		entity.setContentType("application/json");

		try {
		    HttpPost request = new HttpPost(getAbsoluteUrl(url));
		    
		    
		    request.addHeader("content-type", "application/json");
		    request.setEntity(entity);
		    HttpResponse response = httpClient.execute(request);
		    
		    return response;
		    

		}catch (Exception ex) {
			ex.printStackTrace();
			return null;

		} finally {
		    //Deprecated
		    //httpClient.getConnectionManager().shutdown(); 
		}
	}
	private static String getAbsoluteUrl(String url) {
		return BASE_URL + url;
	}
}
