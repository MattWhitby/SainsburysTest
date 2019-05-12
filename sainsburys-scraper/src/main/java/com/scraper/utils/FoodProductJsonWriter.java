package com.scraper.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.products.sainsburys.FoodProduct;

/**
 * This class is responsible for writing a list of {@link FoodProduct} to a JSON string and including
 * the gross total of all the items on the page as well as VAT on the gross.
 */
public class FoodProductJsonWriter {
	private List<FoodProduct> products = new ArrayList<FoodProduct>();
	private Double VAT_CONVERSION = 1.2;   //

	public FoodProductJsonWriter(List<FoodProduct> products) {
		if (products != null) {
			this.products.addAll(products);			
		}
	}

	public String outputJsonResult() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		jsonObject.put("results", jsonArray);

		Double gross = 0D;
		for (FoodProduct product : products) {
			JSONObject jsonContent = new JSONObject();
			jsonArray.put(jsonContent);

			jsonContent.put("title", product.getTitle());

			if (product.getKcalPer100g() > 0) {
				jsonContent.put("kcal_per_100g", product.getKcalPer100g());
			}
			
			jsonContent.put("unit_price", product.getUnitPrice());
			jsonContent.put("description", product.getDescription());

			gross = gross + product.getUnitPrice();
		}

		JSONObject totalObject = new JSONObject();
		jsonObject.put("total", totalObject);
		totalObject.put("gross", gross);
		totalObject.put("vat", calculateVAT(gross));
		System.out.println(jsonObject.toString(4));
		return jsonObject.toString();
	}

	private Double calculateVAT(Double gross) {
		Double result = gross / VAT_CONVERSION;
		result = gross - result;
		BigDecimal decimal = new BigDecimal(result).setScale(2, RoundingMode.FLOOR);
		result = decimal.doubleValue();
		return result;

	}
}
