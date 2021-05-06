package servija.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class Cidade {
	public static ArrayList<servija.model.Cidade> fetch(servija.model.Estado estado){
		ArrayList<servija.model.Cidade> cidades = new ArrayList<servija.model.Cidade>();

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet("https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+estado.getId()+"/municipios");
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

	            cidades.add(
	            		new servija.model.Cidade(
	            				estado,
	            				obj.getString("nome")
	            		)
	            	);
	        }
			
		}catch(Exception ex) {
		}
		
		return cidades;
	}
}
