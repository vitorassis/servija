package servija.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Estado {
	
	public static ArrayList<servija.model.Estado> fetchAll(){
		ArrayList<servija.model.Estado> estados = new ArrayList<servija.model.Estado>();

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados");
			HttpResponse response = client.execute(request);
	
			// Get the response
			BufferedReader rd = new BufferedReader
			    (new InputStreamReader(
			    response.getEntity().getContent()));
	
			String line = "", json = line;
			while ((line = rd.readLine()) != null) {
			    json+= line;
			}
			
			JSONArray estJson = new JSONArray(json);
			JSONObject obj;
	        for(int i = 0; i < estJson.length(); i++){
	        	obj = estJson.getJSONObject(i);

	            estados.add(
	            		new servija.model.Estado(
	            				obj.getString("nome"),
	            				obj.getString("sigla"), 
	            				obj.getInt("id")
	            		)
	            	);
	        }
			
		}catch(Exception ex) {
		}
		
		return estados;
	}
}
