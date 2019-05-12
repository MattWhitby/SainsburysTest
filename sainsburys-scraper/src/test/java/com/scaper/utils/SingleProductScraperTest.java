package com.scaper.utils;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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

public class SingleProductScraperTest {

	private Connection mockConnection;
	private Document mockDocument;
	
	private static final String TEST_DESCRIPTION = "test description";
	private static final int TEST_KCAL_VALUE = 13;

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
	public void testScrapingOneProductWithPrimaryDescriptionSource() {
		//Setup
		mockElements();
		mockPrimaryDescriptionSource();
		SingleProductScraper testObj = new SingleProductScraper(mockConnection, new SainsburysBerriesHtmlQueries());
		
		//Test
		FoodProduct testProduct = testObj.getProductInfo();
		
		//Verify
		assertEquals(TEST_KCAL_VALUE, testProduct.getKcalPer100g());
		assertEquals(TEST_DESCRIPTION, testProduct.getDescription());
		
	}
	
	@Test
	public void testScrapingOneProductWithAlternateDescriptionSource() {
		//Setup
		mockElements();
		mockAlternateDescriptionSource();
		SingleProductScraper testObj = new SingleProductScraper(mockConnection, new SainsburysBerriesHtmlQueries());
		
		//Test
		FoodProduct testProduct = testObj.getProductInfo();
		
		//Verify
		assertEquals(TEST_KCAL_VALUE, testProduct.getKcalPer100g());
		assertEquals(TEST_DESCRIPTION, testProduct.getDescription());
		
	}

	private void mockElements() {
		SainsburysBerriesHtmlQueries mockQueries = new SainsburysBerriesHtmlQueries();

		Element mockElement = Mockito.mock(Element.class);

		Elements mockElements = Mockito.mock(Elements.class);
		Mockito.when(mockElement.select(mockQueries.tableBodyQuery())).thenReturn(mockElements);
		Mockito.when(mockElement.getElementsByIndexEquals(0)).thenReturn(mockElements);
		Mockito.when(mockElements.size()).thenReturn(1);
		Mockito.when(mockElements.get(0)).thenReturn(mockElement);
		Mockito.when(mockElements.text()).thenReturn(TEST_KCAL_VALUE + "kcal");
		Mockito.when(mockElements.select(mockQueries.tableElementsQuery())).thenReturn(mockElements);
		Mockito.when(mockDocument.select(mockQueries.nutritionTableQuery())).thenReturn(mockElements);
		
		Elements mockElements2 = Mockito.mock(Elements.class);
		Mockito.when(mockElements2.size()).thenReturn(1);
		Mockito.when(mockElements2.get(0)).thenReturn(mockElement);
	}
	
	private void mockPrimaryDescriptionSource() {
		Element mockElement = Mockito.mock(Element.class);
		Mockito.when(mockElement.text()).thenReturn(TEST_DESCRIPTION);
		Elements mockElements2 = Mockito.mock(Elements.class);
		Mockito.when(mockElements2.size()).thenReturn(1);
		Mockito.when(mockElements2.get(0)).thenReturn(mockElement);
		Mockito.when(mockDocument.select(new SainsburysBerriesHtmlQueries().descriptionQuery())).thenReturn(mockElements2);
	}

	private void mockAlternateDescriptionSource() {
		Element mockElement = Mockito.mock(Element.class);
		Mockito.when(mockElement.text()).thenReturn(TEST_DESCRIPTION);
		Elements mockElements2 = Mockito.mock(Elements.class);
		Mockito.when(mockElements2.size()).thenReturn(0);
		Mockito.when(mockElements2.get(0)).thenReturn(mockElement);
		Mockito.when(mockDocument.select(new SainsburysBerriesHtmlQueries().descriptionQuery())).thenReturn(mockElements2);
		Mockito.when(mockDocument.select(new SainsburysBerriesHtmlQueries().alternateDescriptionQuery())).thenReturn(mockElements2);
	}
	private void mockJsoupConnection() throws IOException {
		mockDocument = Mockito.mock(Document.class);
		mockConnection = Mockito.mock(Connection.class);
		Mockito.when(mockConnection.get()).thenReturn(mockDocument);
	}
}
