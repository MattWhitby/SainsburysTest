package com.scaper.app;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.scaper.utils.FoodProductJsonWriter;
import com.scaper.utils.ProductsScraper;
import com.scraper.constants.SainsburysBerriesHtmlQueries;

/**
 * Entry point for scraper application.
 */
public class ScraperApplication 
{
	private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
	
    public static void main( String[] args )
    {
    	Connection connection = Jsoup.connect(URL);
    	ProductsScraper scraper = new ProductsScraper(connection, new SainsburysBerriesHtmlQueries());
    	
    	FoodProductJsonWriter writer = new FoodProductJsonWriter(scraper.getProducts());
    	writer.outputJsonResult();
    }
}
