package com.scaper.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import com.products.sainsburys.FoodProduct;

public class FoodProductJsonWriterTest {
	
	private final static String TEST_JSON = "{\"total\":{\"gross\":4,\"vat\":0.66},\"results\":[{\"kcal_per_100g\":10,"
			+ "\"description\":\"Desc1\",\"title\":\"product1\",\"unit_price\":1.5},"
			+ "{\"kcal_per_100g\":15,\"description\":\"Desc2\",\"title\":\"product2\",\"unit_price\":2.5}]}";
	private final static String TEST_JSON_EMPTY = "{\"total\":{\"gross\":0,\"vat\":0},\"results\":[]}";
	
	private final static Double GROSS = 4D;
	private final static Double VAT = 0.66;
	private final static Double ZERO_VALUE = 0D;

	@Test
	public void testOutputJsonResultWithProducts() {
		//Setup
		FoodProductJsonWriter testObj = new FoodProductJsonWriter(createTestProducts());
		
		//Test
		String test = testObj.outputJsonResult();
		
		//Verify
		assertEquals(TEST_JSON, test);
		assertEquals(GROSS, getGross(test));
		assertEquals(VAT, getVAT(test));
	}
	
	@Test
	public void testOutputWhenProductsIsNull() {
		//Setup
		FoodProductJsonWriter testObj = new FoodProductJsonWriter(null);
		
		//Test
		String test = testObj.outputJsonResult();
		
		//Verify
		assertEquals(TEST_JSON_EMPTY, test);
		assertEquals(ZERO_VALUE, getGross(test));
		assertEquals(ZERO_VALUE, getVAT(test));
	}
	
	private Double getGross(String json) {
		JSONObject jsonObject = new JSONObject(json);
		JSONObject totalObject = jsonObject.getJSONObject("total");

		return totalObject.getDouble("gross");
	}
	
	private Double getVAT(String json) {
		JSONObject jsonObject = new JSONObject(json);
		JSONObject totalObject = (JSONObject) jsonObject.get("total");
		return totalObject.getDouble("vat");
	}
	
	private List<FoodProduct> createTestProducts() {
		List<FoodProduct> list = new ArrayList<>();
		FoodProduct product1 = new FoodProduct();
		product1.setDescription("Desc1");
		product1.setTitle("product1");
		product1.setKcalPer100g(10);
		product1.setUnitPrice(1.5);
		list.add(product1);
		
		FoodProduct product2 = new FoodProduct();
		product2.setDescription("Desc2");
		product2.setTitle("product2");
		product2.setKcalPer100g(15);
		product2.setUnitPrice(2.5);
		list.add(product2);
		return list;
	}
}
