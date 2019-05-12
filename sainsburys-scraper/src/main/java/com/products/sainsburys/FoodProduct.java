package com.products.sainsburys;

/**
 * This object stores information for basic food products.
 */
public class FoodProduct {
	private String title = "";
	private int kcalPer100g = 0;
	private Double unitPrice;
	private String description = "";

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public int getKcalPer100g() {
		return kcalPer100g;
	}

	public void setKcalPer100g(int kcalPer100g) {
		this.kcalPer100g = kcalPer100g;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
