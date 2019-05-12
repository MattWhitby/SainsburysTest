package com.scaper.utils;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.products.sainsburys.FoodProduct;
import com.scraper.constants.HTMLScraperQueries;

/**
 * This scraper is used to scrape information from the URL for a specific
 * {@link FoodProduct} product.
 */
public class SingleProductScraper {
	private Document urlDocument;
	private HTMLScraperQueries htmlQueries;

	public SingleProductScraper(Connection connection, HTMLScraperQueries htmlQueries) {
		try {
			this.urlDocument = connection.get();
		} catch (IOException e) {
			System.out.println("[SingleProductScraper] Failed to get Document from connection to url");
			e.printStackTrace();
		}
		this.htmlQueries = htmlQueries;
	}

	public FoodProduct getProductInfo() {
		FoodProduct product = new FoodProduct();
		Elements tableElements = urlDocument.select(htmlQueries.nutritionTableQuery());
		if (tableElements.size() > 0) {
			Element table = tableElements.get(0);
			Elements tableInfoElements = table.select(htmlQueries.tableBodyQuery())
					.select(htmlQueries.tableElementsQuery());
			String kcalString = tableInfoElements.get(0).getElementsByIndexEquals(0).text();
			product.setKcalPer100g(getKcalPer100gAsInt(kcalString));
		}

		product.setDescription(getDescription(urlDocument));

		return product;
	}

	private int getKcalPer100gAsInt(String kcalString) {
		int index = kcalString.indexOf("kcal");
		kcalString = kcalString.substring(0, index);

		return Integer.parseInt(kcalString);
	}

	private String getDescription(Document document) {
		if (document.select(htmlQueries.descriptionQuery()).size() > 0) {
			return document.select(htmlQueries.descriptionQuery()).get(0).text();
		} else {
			return document.select(htmlQueries.alternateDescriptionQuery()).get(0).text();
		}
	}
}
