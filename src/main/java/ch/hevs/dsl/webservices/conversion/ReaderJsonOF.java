/*Author: Jeff Zufferey;
*
*
*Project Name: demo2
*Package: ch.hevs.dsl.webservices.conversion
*File name: ReaderJsonOF.java
*
*
*Date of creation: 6 juin 2017
*
*/



package ch.hevs.dsl.webservices.conversion;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import ch.hevs.dsl.webservices.database.*;

//note à moi meme JsonReaderOpenFood
public class ReaderJsonOF {

	private static ArrayList<Product> products; //if error maybe make FINAL
	
	/*
	 * Compatility with json data
	 */
	public static final String DATA = "data", ID="id", NAME_TRANSLATIONS="name_translations", 
			INGREDIENT_TRANSLATION = "ingredients_translations", DE="de", FR="fr", IT="it", 
			EN = "en", UNIT = "unit", QUANTITY = "quantity", PORTION_QUANTITY = "portion_quantity", 
			PORTION_UNIT = "portion_unit", NUTRIENTS = "nutrients", PROTEIN = "protein", 
			PER_HUNDRED = "per_hundred", SUGAR = "sugar", SALT = "salt", FIBER = "fiber",
			SATUREDFAT = "saturedFat", PER_PORTION = "per_portion", PER_DAY = "per_day", CARBOHYDRATES = "carbohydrates", 
			FAT = "fat", ENERGY_KCAL = "energy_kcal", ENERGY = "energy";
	
	public void read(String dataFromOpenFood) throws JSONException{
		products = new ArrayList<Product>();
		
		JSONObject object = new JSONObject(dataFromOpenFood);
		JSONArray arrayObjectJson = object.getJSONArray(DATA);
		
		for(int i = 0; i< arrayObjectJson.length(); i++){
			Product product = new Product();
			JSONObject productObject = arrayObjectJson.getJSONObject(i);
			product.setId(productObject.getInt(ID)+"");
			
			JSONObject nameTranslationObject = productObject.getJSONObject(NAME_TRANSLATIONS);
			JSONObject ingredientTranslationObject = productObject.getJSONObject(INGREDIENT_TRANSLATION);
			
			/*
			 * Here we want only the fr language but if there hasnt the fr, we check for an other langage by default
			 * With helping from external collegue
			 */
			try {
				product.setName(nameTranslationObject.getString(FR));
				product.setIngredients(ingredientTranslationObject.getString(FR));
			} catch (JSONException e) {
				try{
					product.setName(nameTranslationObject.getString(EN));
					product.setIngredients(ingredientTranslationObject.getString(EN));
				} catch (JSONException e2){
					try{
						product.setName(nameTranslationObject.getString(DE));
						product.setIngredients(ingredientTranslationObject.getString(DE));
					} catch (JSONException e3){
						try{
							product.setName(nameTranslationObject.getString(IT));
							product.setIngredients(nameTranslationObject.getString(IT));

						} catch (JSONException e4){
							product.setName("NONAME");
							product.setIngredients("NONAME");
							System.out.println("The food you entered has no ingredient in it");
						}
					}
				}
		}
		
		try{
			product.setUnit(productObject.getString(UNIT));
		} catch (JSONException e){
			product.setUnit("no_unit");
		}
		
		try{
			product.setQuantity(productObject.getDouble(UNIT));
		} catch (JSONException e){
			product.setQuantity(0.0);
		}
		
		try{
			product.setPortion_quantity(productObject.getDouble(PORTION_QUANTITY));
		} catch (JSONException e){
			product.setPortion_quantity(0.0);
		}
		
		try {
			product.setPortionUnit(productObject.getString(PORTION_UNIT));
		} catch (JSONException e) {
			product.setPortionUnit("");
		}		
		
		/*
		 * we add the nutrients into hashmap 
		 */
		
		JSONObject nutrients = productObject.getJSONObject(NUTRIENTS);
		
		HashMap<String, Nutrient> productHashmap = new HashMap<String, Nutrient>();
		
		productHashmap.put(SALT, addIngredient(product, nutrients, SALT));
		productHashmap.put(PROTEIN, addIngredient(product, nutrients, PROTEIN));
		productHashmap.put(FIBER, addIngredient(product, nutrients, FIBER));
		productHashmap.put(SUGAR, addIngredient(product, nutrients, SUGAR));
		productHashmap.put(CARBOHYDRATES, addIngredient(product, nutrients, CARBOHYDRATES));
		productHashmap.put(SATUREDFAT, addIngredient(product, nutrients, SATUREDFAT));
		productHashmap.put(ENERGY_KCAL, addIngredient(product, nutrients, ENERGY_KCAL));
		productHashmap.put(ENERGY, addIngredient(product, nutrients, ENERGY));
	
		
		//We add the hashmap to the food
		product.setNutrients(productHashmap);
		
		//We add the food object to the food list
		products.add(product);
	}
}
	
	//this method add a given nutrients to a given food
	private static Nutrient addIngredient(Product product, JSONObject nutrients, String ingredientName) {
		JSONObject ingredientObject = null;
	
		try {
			ingredientObject = nutrients.getJSONObject(ingredientName);
		} catch (JSONException e1) {
	
			System.out.println("NO "+ingredientName+" ADDED !");
			return null;
		}
		
		Nutrient ingredient = new Nutrient();
		JSONObject nameTranslationObject = null;
		try {
			nameTranslationObject = ingredientObject.getJSONObject(NAME_TRANSLATIONS);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	//	We set up the fat translations and their values and handle where there is none
		try {
			ingredient.setName(nameTranslationObject.getString(FR));
		} catch (JSONException e) {
			try{
				ingredient.setName(nameTranslationObject.getString(EN));
			} catch (JSONException e2){
				try{
					ingredient.setName(nameTranslationObject.getString(DE));
				} catch (JSONException e3){
					try{
						ingredient.setName(nameTranslationObject.getString(IT));
					} catch (JSONException e4){
						ingredient.setName("NONAME");
						System.out.println("The "+ingredientName+" you entered has no ingredient in it");
					}
				}
			}
		}
		//We add the value to the nutrient
		try {
			ingredient.setUnit(ingredientObject.getString(UNIT));
		} catch (JSONException e) {
			ingredient.setUnit("");
		}	
		try {
			ingredient.setPerHundred(ingredientObject.getDouble(PER_HUNDRED));
		} catch (JSONException e) {
			ingredient.setPerHundred(0.0);
		}
		try {
			ingredient.setPerPortion(ingredientObject.getDouble(PER_PORTION));
		} catch (JSONException e) {
			ingredient.setPerPortion(0.0);
		}
		try {
			ingredient.setPerDay(ingredientObject.getDouble(PER_DAY));
		} catch (JSONException e) {
			ingredient.setPerDay(0.0);
		}
		//We return the value
		return ingredient;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

}
