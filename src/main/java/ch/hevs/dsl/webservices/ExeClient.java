/*Author: Jeff Zufferey;
 *
 *
 *Project Name: demo2
 *Package: ch.hevs.dsl.webservices
 *File name: ExeClient.java
 *
 *
 *Date of creation: 6 juin 2017
 *
 */

package ch.hevs.dsl.webservices;

import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONException;


import ch.hevs.dsl.webservices.conversion.SerialiazerJsonOF;
import ch.hevs.dsl.webservices.database.Nutrient;
import ch.hevs.dsl.webservices.database.Product;


public class ExeClient {

	private static Scanner scan;
	public static void main(String[] args) {
		
		scan = new Scanner(System.in);
		ApiClient apiClient = new ApiClient();
		
				System.out.println("*********************************************************************************");
				getAllProducts(apiClient);
				System.out.println("*********************************************************************************");
				System.out.println("Methode to get all Products by name");
				System.out.println("*********************************************************************************");
				getProductsByName(apiClient);
				
				
				
				
				
				
			
			
		
		
	}

	private static void getAllProducts(ApiClient client) {
		System.out.println("All Products : ");
		System.out.println(client.getAll());
	}

	private static void getProductsByName(ApiClient client) {
		System.out.print("What product name? : ");
		String name = scan.nextLine();
		System.out.println("The products are : " + name);
		System.out.println(client.getByName(name));
		
		System.out.println("*********************************************************************************");
		System.out.println("Methode to get all Products by quantity");		
		System.out.println("*********************************************************************************");
		getAllProductsByQuantity(client);
		
	}

	private static void getAllProductsByQuantity(ApiClient client) {
		System.out.println("\rProducts by quantity");
		System.out.println(client.getByQuantity());
		
		System.out.println("*********************************************************************************");
		System.out.println("Methode to get all Products by name and quantity");
		System.out.println("*********************************************************************************");
		getProductsByNameAndQuantity(client);
		
	}
	private static void getProductsByNameAndQuantity(ApiClient client){
		System.out.print("What's the name of the product ? ");
		String name = scan.nextLine();
		System.out.print("What's the quantity ? ");
		double quantity = scan.nextDouble();
		scan.nextLine();
		System.out.println("The products with the name : "+name+" and the quantity "+quantity+" are : ");
		System.out.println(client.getByNameAndQuantity(name, quantity));	
		
		System.out.println("*********************************************************************************");
		System.out.println("Methode create a product");
		System.out.println("*********************************************************************************");
		CreateProduct(client);
		
		
	}

	// create product for client
	private static void CreateProduct(ApiClient client) {
		Product product = CreateProduct();
		String ask = null;
		try {
			SerialiazerJsonOF serializer = new SerialiazerJsonOF();
			ask = serializer.SerializeJSON(product);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int code = client.create(ask);
		if (code == 200) {
			System.out.println("Created");
		} else {
			System.out.println("Not possible to create, code " + code);
		}
		
		System.out.println("*********************************************************************************");
		System.out.println("Methode to update a product");
		System.out.println("*********************************************************************************");
		UpdateProduct(client);
	}

	// without client
	private static Product CreateProduct() {
		Product product = new Product();
		System.out.print("What product name ? : ");
		product.setName(scan.nextLine());
		System.out.print("What ingredients ? : ");
		product.setIngredients(scan.nextLine());
		System.out.print("What quantity ? : ");
		product.setQuantity(scan.nextDouble());
		scan.nextLine();
		System.out.print("What unit ? : ");
		product.setUnit(scan.nextLine());
		System.out.print("What quantity per portion : ");
		product.setPortion_quantity(scan.nextDouble());
		scan.nextLine();
		System.out.print("What unit per portion ? : ");
		product.setPortion_quantity(scan.nextDouble());
		HashMap<String, Nutrient> nutrientHashMap = new HashMap<String, Nutrient>();
		boolean next = true;
		while (next) {
			System.out.println("Add nutrients ? (y/n) ?");
			char answer = scan.next().charAt(0);
			if (answer == 'y') {
				scan.nextLine();
				Nutrient nutrientClass = new Nutrient();
				System.out.print("What name ? : ");
				nutrientClass.setName(scan.nextLine());
				System.out.print("What consumption par day ? : ");
				nutrientClass.setPerDay(scan.nextDouble());
				scan.nextLine();
				System.out.print("What consumption per Hundred ? : ");
				nutrientClass.setPerHundred(scan.nextDouble());
				scan.nextLine();
				System.out.print("What consumption per Portion ? : ");
				nutrientClass.setPerPortion(scan.nextDouble());
				scan.nextLine();
				System.out.print("What unit ? : ");
				nutrientClass.setUnit(scan.nextLine());
				nutrientHashMap.put(nutrientClass.getName(), nutrientClass);
			} else {
				next = false;
			}
		}
		product.setNutrients(nutrientHashMap);
		return product;
	}

	private static void UpdateProduct(ApiClient client) {
		System.out.println("Update product");
		Product product = UpdateProduct();
		String ask = null;
		try {
			SerialiazerJsonOF serializer = new SerialiazerJsonOF();
			ask = serializer.SerializeJSON(product);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int code = client.update(ask, product.getId());
		if (code == 200) {
			System.out.println("Updated");
		} else {
			System.out.println("Not possible to update, code " + code);
		}
		System.out.println("*********************************************************************************");
		System.out.println("Methode to delete a product");
		System.out.println("*********************************************************************************");
		DeleteProduct(client);
	}

	// without client
	private static Product UpdateProduct() {
		Product product = CreateProduct();
		System.out.print("Insert your product's id: ");
		scan.nextLine();
		product.setId(scan.nextLine());
		return product;
	}
	private static void DeleteProduct(ApiClient client){
		System.out.println("What id to delete ? ");
		String id = scan.nextLine();
		int code = client.delete(id);
		if(code==200){
			System.out.println("Product "+ id +" deleted.");
		}
		else{
			System.out.println("Not possible to delete, code " + code);
		}	
	}
}
