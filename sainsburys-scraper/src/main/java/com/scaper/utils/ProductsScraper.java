package com.scaper.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.products.sainsburys.FoodProduct;
import com.scraper.constants.HTMLScraperQueries;

/**
 * This scrapes a page containing multiple items and returns a list of
 * {@link FoodProduct}.
 */
public class ProductsScraper {

	private Document urlDocument;
	private HTMLScraperQueries htmlQueries;

	public ProductsScraper(Connection connection, HTMLScraperQueries htmlQueries) {
		try {
			this.urlDocument = connection.get();
		} catch (IOException e) {
			System.out.println("[ProductScraper] Failed to get Document from connection to url");
			e.printStackTrace();
		}
		this.htmlQueries = htmlQueries;
	}

	public List<FoodProduct> getProducts() {
		Elements products = urlDocument.select(htmlQueries.allProductsQuery());
		List<FoodProduct> foodProducts = new ArrayList<>();

		for (Element product : products) {
			FoodProduct foodProduct = new FoodProduct();
			Elements productInfo = product.select(htmlQueries.productInfoQuery());

			Elements productDetails = productInfo.select(htmlQueries.productLinkQuery());
			String pricePerUnit = modifyPricePerUnit(product.select(htmlQueries.pricePerUnitQuery()).get(0).text());
			foodProduct.setTitle(productDetails.text());
			foodProduct.setUnitPrice(Double.valueOf(pricePerUnit));

			//Get the url for the specific product and connect to it to retrieve the extra details necessary
			String url = productDetails.get(0).absUrl("href");
			Connection productDetailsConnection = Jsoup.connect(url);
			FoodProduct tempProduct = new SingleProductScraper(productDetailsConnection, htmlQueries).getProductInfo();
			foodProduct.setKcalPer100g(tempProduct.getKcalPer100g());
			foodProduct.setDescription(tempProduct.getDescription());

			foodProducts.add(foodProduct);
		}

		return foodProducts;
	}

	private String modifyPricePerUnit(String pricePerUnit) {
		int index = pricePerUnit.indexOf("/unit");
		pricePerUnit = pricePerUnit.substring(1, index);
		return pricePerUnit;
	}
}
