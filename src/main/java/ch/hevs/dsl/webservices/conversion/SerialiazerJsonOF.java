/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservices.conversion
*File name: SerialiableJsonOF.java
*
*
*Date of creation: 6 juin 2017
*
*/



package ch.hevs.dsl.webservices.conversion;

import ch.hevs.dsl.webservices.database.*;

import org.json.JSONException;
import org.json.JSONObject;

public class SerialiazerJsonOF {

	//The tag for the json
	public static final String ID="id", NAME="name", 
			INGREDIENTS = "ingredients", UNIT = "unit", QUANTITY = "quantity", PORTION_QUANTITY = "portion_quantity", 
			PORTION_UNIT = "portion_unit", NUTRIENTS = "nutrients", 
			PER_HUNDRED = "per_Hundred", PER_PORTION = "per_Portion", PER_DAY = "per_Day";
	
	//We serialize a food into a json
	public String SerializeJSON(Product product) throws JSONException{
		JSONObject productObject = new JSONObject();
		//We add avery value to the json object
		productObject.put(ID, product.getId());
		productObject.put(NAME, product.getName());
		productObject.put(INGREDIENTS, product.getIngredients());
		productObject.put(UNIT, product.getUnit());
		productObject.put(QUANTITY, product.getQuantity());
		productObject.put(PORTION_QUANTITY, product.getPortion_quantity());
		productObject.put(PORTION_UNIT, product.getPortion_quantity());
		//We add the hashmap to the json object
		productObject.accumulate(NUTRIENTS, product.getNutrients());
		
		return(productObject.toString());
	}
	
}
