package ch.hevs.dsl.webservices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestClient {
	
//	private static final String URL = "http://88.99.66.215:8080/demo3-1/contacts";
	
	private static final String URL = "https://www.openfood.ch/api/v3/products";
	private static final String SERVERURL = "http://localhost:8080/products";
	
	private static JSONArray productsArray = null;
	
	public static void main(String[] args) {
		
		getProductsFromOpenFood();
//		put();
//		delete();
		get();
	
	}
	public static void get(){
		String string = "";
			
		try {
			URL url = new URL(SERVERURL);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.addRequestProperty("Content-Type", "application/json");
			//connection.addRequestProperty("Authorization", "Token token=071923fbfa66c60ec25a84b6db8d4ace");
			
			if(connection.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : "
						+ connection.getResponseCode() + " " + connection.getResponseMessage());
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String response = null;
			
			while ((response = in.readLine()) != null ) {
				System.out.println("\n REST GET Service Invoked Successfully..");
				System.out.println("\n " + response);
			}
			
			in.close();
			connection.disconnect();
		} catch (Exception e) {
			System.out.println("\nError while calling Crunchify REST Service");
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public static void getProductsFromOpenFood(){
		String string = "";
		try {
 
			// Get products from OpenFood API
			// Step1: Let's 1st read file from fileSystem
			// Change CrunchifyJSON.txt path here
			InputStream crunchifyInputStream = new FileInputStream("Files/ProductsFilter.txt");
			InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
			BufferedReader br = new BufferedReader(crunchifyReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
			
			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(URL + "/_search");
				HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.addRequestProperty("Authorization", "Token token=071923fbfa66c60ec25a84b6db8d4ace");
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				String responseStr = null;
				while ((responseStr = in.readLine()) != null) {
					JSONObject response = new JSONObject(responseStr);
					productsArray = response.getJSONObject("hits").getJSONArray("hits");
					System.out.println("\n REST Service Invoked Successfully..");
					System.out.println("\n " + productsArray);
				}
				
				System.out.println("\nCrunchify REST Service Invoked Successfully..");
				in.close();
				connection.disconnect();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
 
			br.close();
			
			
			//Remove all languages other than french
			
			ArrayList<String> langToRemove = new ArrayList<String>();
			langToRemove.add("de");
			langToRemove.add("it");
			langToRemove.add("en");
			
			for(int i = 0; i < productsArray.length(); i++){
				JSONObject sourceObj = productsArray.getJSONObject(i).getJSONObject("_source");
				removeLanguages(sourceObj, "name_translations", langToRemove);
				removeLanguages(sourceObj, "ingredients_translations", langToRemove);
				System.out.println("\nProduct n° " + i);
				
				Iterator iter = sourceObj.getJSONObject("nutrients").keys();
				while(iter.hasNext()){
					String key = (String)iter.next();
					removeLanguages(sourceObj.getJSONObject("nutrients").getJSONObject(key), "name_translations", langToRemove);
				}	
				sourceObj.put("name",sourceObj.get("name_translations"));
				sourceObj.remove("name_translations");
				
				sourceObj.put("ingredients",sourceObj.get("ingredients_translations"));
				sourceObj.remove("ingredients_translations");
				
				postProductsIntoWS(sourceObj);
				System.out.println(sourceObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject removeLanguages(JSONObject objectJson, String jsonElement, ArrayList<String> langToRemove){
		
		
		for (String language : langToRemove) {
			try {
				objectJson.getJSONObject(jsonElement).remove(language);
			} catch (Exception e) {
				continue;
			}
		}
		
		try {
			String french = objectJson.getJSONObject(jsonElement).getString("fr");
			objectJson.remove(jsonElement);
			objectJson.put(jsonElement, french);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objectJson;
		
	}
	
	public static void postProductsIntoWS(JSONObject object){
		String string = "";
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(SERVERURL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(object.toString());
				out.close();
 
				/*BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				String response = null;
				while ((response = in.readLine()) != null) {
					System.out.println("\n REST Service Invoked Successfully..");
					System.out.println("\n " + response);
				}*/
				System.out.println("\nCrunchify REST POST Service Invoked Successfully..");
				//in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service during POST");
				e.printStackTrace();
			}
	}
	
	public static void put(){
		String string = "";
		try {
 
			// Step1: Let's 1st read file from fileSystem
			// Change CrunchifyJSON.txt path here
			InputStream crunchifyInputStream = new FileInputStream("Files/Contact.txt");
			InputStreamReader crunchifyReader = new InputStreamReader(crunchifyInputStream);
			BufferedReader br = new BufferedReader(crunchifyReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
 
			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(URL.concat("/58dcec64488dcb28fc56915c"));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setRequestMethod("PUT");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				String response = null;
				while ((response = in.readLine()) != null) {
					System.out.println("\n REST Service Invoked Successfully..");
					System.out.println("\n " + response);
				}
				System.out.println("\nCrunchify REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
 
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void delete(){
		String string = "";
		try {
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(URL.concat("/58dcec64488dcb28fc56915c"));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("DELETE");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
 
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
 
				String response = null;
				while ((response = in.readLine()) != null) {
					System.out.println("\n REST Service Invoked Successfully..");
					System.out.println("\n " + response);
				}
				System.out.println("\nCrunchify REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
