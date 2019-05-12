package com.scaper.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import com.products.sainsburys.FoodProduct;
import com.scraper.constants.SainsburysBerriesHtmlQueries;

public class ProductScraperTest {
	private Connection mockConnection;
	private Document mockDocument;
	
	private static final String URL = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/shop/gb/groceries/berries-cherries-currants/sainsburys-british-strawberries-400g.html";
	private static final Double PRICE_TEST_VALUE = 2.75;
	private static final String TEST_TITLE = "test title";

	@Before
	public void setUp() throws IOException {
		mockJsoupConnection();
	}

	@After
	public void tearDown() {
		mockConnection = null;
		mockDocument = null;
	}

	@Test
	public void testConnectionOnConstruction() throws IOException {
		//Test
		new ProductsScraper(mockConnection, new SainsburysBerriesHtmlQueries());
		
		//Verify
		Mockito.verify(mockConnection).get();
	}
	
	@Test
	public void testGetProducts() {
		//Setup
		mockElements();
		SainsburysBerriesHtmlQueries mockQueries = new SainsburysBerriesHtmlQueries();
		ProductsScraper testObj = new ProductsScraper(mockConnection, new SainsburysBerriesHtmlQueries());
		
		//Test
		List<FoodProduct> testList = testObj.getProducts();
		
		//Verify
		Mockito.verify(mockDocument).select(mockQueries.allProductsQuery());
		assertEquals(1, testList.size());
		assertEquals(PRICE_TEST_VALUE, testList.get(0).getUnitPrice());
		assertEquals(TEST_TITLE, testList.get(0).getTitle());
	}

	private void mockJsoupConnection() throws IOException {
		mockDocument = Mockito.mock(Document.class);
		mockConnection = Mockito.mock(Connection.class);
		Mockito.when(mockConnection.get()).thenReturn(mockDocument);
	}
	
	private void mockElements() {
		SainsburysBerriesHtmlQueries mockQueries = new SainsburysBerriesHtmlQueries();
		
		Element mockElement = Mockito.mock(Element.class);
		Elements elements = new Elements(mockElement);
		Elements mockElements = Mockito.mock(Elements.class);
		Mockito.when(mockElements.select(mockQueries.productLinkQuery())).thenReturn(mockElements);
		Mockito.when(mockElements.get(Mockito.anyInt())).thenReturn(mockElement);
		Mockito.when(mockElements.text()).thenReturn(TEST_TITLE);
		
		Mockito.when(mockElement.select(mockQueries.productInfoQuery())).thenReturn(mockElements);
		Mockito.when(mockElement.select(mockQueries.productLinkQuery())).thenReturn(elements);
		Mockito.when(mockElement.absUrl(Mockito.anyString())).thenReturn(URL);   //Would have mocked the creation of SingleProductScraper but had problems with PowerMock giving IllegalAccessError
		Mockito.when(mockElement.select(mockQueries.pricePerUnitQuery())).thenReturn(elements);
		Mockito.when(mockElement.text()).thenReturn("£" + PRICE_TEST_VALUE + "/unit");
		
		Mockito.when(mockDocument.select(mockQueries.allProductsQuery())).thenReturn(elements);
	}
}
