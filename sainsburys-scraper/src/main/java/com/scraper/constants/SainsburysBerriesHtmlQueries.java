package com.scraper.constants;

public class SainsburysBerriesHtmlQueries implements HTMLScraperQueries {

	@Override
	public String allProductsQuery() {
		return "div[class=product]";
	}

	@Override
	public String productInfoQuery() {
		return "div[class=productInfo]";
	}

	@Override
	public String productLinkQuery() {
		return "a[href]";
	}

	@Override
	public String pricePerUnitQuery() {
		return "p[class=pricePerUnit]";
	}

	@Override
	public String nutritionTableQuery() {
		return "table[class=nutritionTable]";
	}

	@Override
	public String tableBodyQuery() {
		return "tbody";
	}

	@Override
	public String tableElementsQuery() {
		return "tr[class=tableRow0]";
	}

	@Override
	public String descriptionQuery() {
		return "div[class=productText]";
	}

	@Override
	public String alternateDescriptionQuery() {
		return "div[class=memo]";
	}

}
