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
		boolean find = true;
		while (find) {
			System.out
					.println("Select one methode : "
							+ "\n\t1: get all products, "
							+ "\n\t2: get all products by quantity"
							+ "\n\t3: getting products by name"
							+ "\n\t4: getting products by name"
							+ "\n\t5: create product"
							+ "\n\t6: update product"
							+ "\n\t7: delete product"
							+ "\n\t other key to exit");
			int request = scan.nextInt();
			scan.nextLine();
			switch (request) {
			case 1:
				getAllProducts(apiClient);
				break;
			case 2:
				getAllProductsByQuantity(apiClient);
				break;
			case 3:
				getProductsByName(apiClient);
				break;
			case 4:
				getProductsByNameAndQuantity(apiClient);
				break;
			case 5:
				CreateProduct(apiClient);
				break;
			case 6:
				UpdateProduct(apiClient);
				break;
			case 7:
				DeleteProduct(apiClient);
				break;
			default:
				find = false;
				break;
			}
		}
		scan.close();
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
	}

	private static void getAllProductsByQuantity(ApiClient client) {
		System.out.println("\rProducts by quantity");
		System.out.println(client.getByQuantity());
	}
	private static void getProductsByNameAndQuantity(ApiClient client){
		System.out.print("What's the name of the product ? ");
		String name = scan.nextLine();
		System.out.print("What's the quantity ? ");
		double quantity = scan.nextDouble();
		scan.nextLine();
		System.out.println("The products with the name : "+name+" and the quantity "+quantity+" are : ");
		System.out.println(client.getByNameAndQuantity(name, quantity));	
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
