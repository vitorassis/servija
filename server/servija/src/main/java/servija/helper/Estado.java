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
			JSONArray estJson = new JSONArray(
					ExternalJSONReader.access("https://servicodados.ibge.gov.br/api/v1/localidades/estados")
			);
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
