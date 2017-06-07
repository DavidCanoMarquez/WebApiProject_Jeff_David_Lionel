/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservices.conversion
*File name: Connection.java
*
*
*Date of creation: 6 juin 2017
*
*/

package ch.hevs.dsl.webservices.conversion;


import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class Connection {

public String startConnection(){
	
	ClientConfig configuration = new ClientConfig();
	
	Client client = ClientBuilder.newClient(configuration);
	
	WebTarget target = (client).target(getBaseURI());
	
	/*
	 * Connexion to Open food api.
	 */
	String response = target.path("api").path("v3").path("products")
			.queryParam("excludes", "barcode%2Cdisplay_name_translations%2Corigin_translations%2Cstatus%2Calcohol_by_volume%2Cimages%2Ccreated_at%2Cupdated_at")
			.request()
			.header("Accept", "application/json")
			.header("Authorization", "Token token=e372958fd7a3ed3c065d61a27caca6b6")
			.accept(MediaType.TEXT_PLAIN).get(String.class);		
	return response;
	}

	private static URI getBaseURI(){
		return UriBuilder.fromUri("https://www.openfood.ch").build();
	}
	
	
	
}
