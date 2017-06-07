/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservices
*File name: ApiClient.java
*
*
*Date of creation: 6 juin 2017
*
*/



package ch.hevs.dsl.webservices;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class ApiClient {

	public String getAll(){
		
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = (client).target(getBaseURI());

		
		String response = target
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
				
		
		return response;
	}	
	
	public String getByQuantity(){
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = (client).target(getBaseURI());

		//We build the query and then send it
		String response = target.path("quantity")
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		
		return response;
	}
	
	public String getByName(String name){
		//We initiate the connection
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = (client).target(getBaseURI());

		//We build the query and then send it
		String response = target
				.queryParam("name", name)
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		//We return the response
		return response;
	}
	
	public String getByNameAndQuantity(String name, double quantity){
		//We initiate the connection
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = (client).target(getBaseURI());

		//We build the query and then send it
		String response = target
				.queryParam("name", name).queryParam("quantity", quantity)
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).get(String.class);
		//We return the response
		return response;
	}
	
	public int create(String food){

		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);
		WebTarget target = (client).target(getBaseURI());

		Response response = target
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).post(Entity.json(food));
		
		return response.getStatus();
	}
	
	public int update(String food, String id){
		
		ClientConfig config = new ClientConfig();
		System.out.println(food);
		System.out.println(id);
		Client client = ClientBuilder.newClient(config);

		WebTarget target = (client).target(getBaseURI());

		Response response = target
				.path("/"+ id)
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).put(Entity.json(food));
		
		return response.getStatus();
	}
	public int delete(String id){
		
		ClientConfig config = new ClientConfig();

		Client client = ClientBuilder.newClient(config);

		WebTarget target = (client).target(getBaseURI());

		
		Response response = target.
				path("/"+id)
				.request()
				.header("Accept", "application/json")
				.accept(MediaType.TEXT_PLAIN).delete();
	
		return response.getStatus();
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/food").build();
	}
	
}
