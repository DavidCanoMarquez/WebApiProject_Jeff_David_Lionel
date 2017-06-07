package ch.hevs.dsl.webservices.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Nutrient {
	
	@Id
	private String id;
	private Double per_Hundred;
	private String unit;
	private Double per_Portion;
	private String name;
	private Double per_Day;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getPerHundred() {
		return per_Hundred;
	}
	public void setPerHundred(Double perHundred) {
		this.per_Hundred = perHundred;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Double getPerPortion() {
		return per_Portion;
	}
	public void setPerPortion(Double perPortion) {
		this.per_Portion = perPortion;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPerDay() {
		return per_Day;
	}
	public void setPerDay(Double perDay) {
		this.per_Day = perDay;
	}
}
