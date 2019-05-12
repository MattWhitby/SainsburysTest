package com.scraper.app;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.scraper.constants.SainsburysBerriesHtmlQueries;
import com.scraper.utils.FoodProductJsonWriter;
import com.scraper.utils.ProductsScraper;

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
