package ch.hevs.dsl.webservices.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {
 
	/*
	 * creation of variables
	 */
	@Id
	private String id;
	private String ingredients;
	private String unit;
	private String portion_unit;
	private String name;
	private Double quantity, portion_quantity;
	
	
	/*
	 * This hashmap have the nutrients
	 */
	private HashMap<String,Nutrient> nutrients;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getPortionUnit() {
		return portion_unit;
	}
	public void setPortionUnit(String portionUnit) {
		this.portion_unit = portionUnit;
	}
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Double getPortion_quantity() {
		return portion_quantity;
	}
	public void setPortion_quantity(Double portion_quantity) {
		this.portion_quantity = portion_quantity;
	}
	public HashMap<String,Nutrient> getNutrients() {
		return nutrients;
	}
	public void setNutrients(HashMap<String,Nutrient> nutrients) {
		this.nutrients = nutrients;
	}

	 
 }