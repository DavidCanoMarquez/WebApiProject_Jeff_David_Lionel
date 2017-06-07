/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservices.conversion
*File name: TakeData.java
*
*
*Date of creation: 6 juin 2017
*
*/



package ch.hevs.dsl.webservices.conversion;

import org.json.JSONException;

import ch.hevs.dsl.webservices.ApiClient;
import ch.hevs.dsl.webservices.database.*;


//note à moimeme filldata
public class TakeData {
	
	public static void main(String[] args){
		Connection connection = new Connection();
		ReaderJsonOF reader = new ReaderJsonOF();
		
		try {
			reader.read(connection.startConnection());
			System.out.println("entrez les données");
		} catch (JSONException e) {
			System.out.println("Erreur try/catch pour l'insertion des données");
		}
		ApiClient client = new ApiClient();//classe pas encore crée

		for (Product product : reader.getProducts()) {
			String request = null; 

			try {
				
				SerialiazerJsonOF serializer = new SerialiazerJsonOF();
				request = serializer.SerializeJSON(product);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			client.create(request);		
			};
	
	}

}
