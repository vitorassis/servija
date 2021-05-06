package servija.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ExternalJSONReader {
	public static String access(String url) {
		String json = "";
		
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
	
			// Get the response
			BufferedReader rd = new BufferedReader
			    (new InputStreamReader(
			    response.getEntity().getContent()));
	
			String line = "";
			while ((line = rd.readLine()) != null) {
			    json+= line;
			}
		}catch(Exception ex) {
			json = "{}";
		}
		
		return json;
	}
}
