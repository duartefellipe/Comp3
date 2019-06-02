package test.ecommerce.unit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.AbstractSale;
import domain.Product;
import test.ecommerce.unit.mockClasses.MockSale;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

/**
 * Suppose that Sale and SaleItem were not ready when this unit test was written.
 * @author Fellipe
 * 
 *
 */
public class JunitMockDemo {
	
	private AbstractSale currentSale;

	@Before
	public void setUp() {
		this.currentSale = new MockSale(null);
	}
	
	@After
	public void tearDown() {
		this.currentSale = null;
	}
	
	@Test
	public void testSaleDate() {
		Calendar saleDate = Calendar.getInstance();
		Calendar currentDate = Calendar.getInstance();
		saleDate.setTime(currentSale.getDate());
		currentDate.setTime(new Date());

		assertEquals(currentDate.get(Calendar.YEAR), saleDate.get(Calendar.YEAR));
		assertEquals(currentDate.get(Calendar.MONTH), saleDate.get(Calendar.MONTH));
		assertEquals(currentDate.get(Calendar.DAY_OF_MONTH), saleDate.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void testAdd() throws Exception{
		currentSale.add("biscoito da vaquinha", 10);
		assertEquals(1, currentSale.getItemsCount(), 0);
		
		currentSale.add("goiabada", 2);
		assertEquals(2, currentSale.getItemsCount(), 0);		
	}
	
	@Test
	public void testAddSameItem() throws Exception{
		currentSale.add("biscoito da vaquinha", 10);
		assertEquals(1, currentSale.getItemsCount(), 0);
		
		currentSale.add("goiabada", 2);
		assertEquals(2, currentSale.getItemsCount(), 0);		

		currentSale.add("biscoito da vaquinha", 3);
		assertEquals(2, currentSale.getItemsCount(), 0);

		currentSale.add("garrafa de agua", 50);
		assertEquals(3, currentSale.getItemsCount(), 0);	
	}
	
	@Test
	public void testTotal() throws Exception{
		currentSale.add("biscoito da vaquinha", 10);
		assertEquals(60, currentSale.saleCost(), 0);

		// this test fails to show what junit do when a unit test fails  
		currentSale.add("goiabada", 2);
		assertEquals(70, currentSale.saleCost(), 0);		
	}
	
	@Test
	public void testProductPrice() throws Exception{
		Product product = new Product("new product", 5, 1.2);
		
		assertEquals(5*1.2, product.getProductPrice(),0);

	}
	
}