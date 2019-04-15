package test.ecommerce.unitario;

import org.junit.Test;

import dominio.AbstractSale;
import dominio.Product;
import test.ecommerce.unitario.mockClasses.MockSale;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Fellipe
 * 
 * testes unitarios considerando que Sale e SaleItem ainda não estavam prontas (Usando um mock object no lugar delas).
 *
 */
public class JunitMockDemo {
	
	@Test
	public void testSaleDate() {
		AbstractSale sale = new MockSale();
		Calendar saleDate = Calendar.getInstance();
		Calendar currentDate = Calendar.getInstance();
		saleDate.setTime(sale.getDate());
		currentDate.setTime(new Date());

		assertEquals(currentDate.get(Calendar.YEAR), saleDate.get(Calendar.YEAR));
		assertEquals(currentDate.get(Calendar.MONTH), saleDate.get(Calendar.MONTH));
		assertEquals(currentDate.get(Calendar.DAY_OF_MONTH), saleDate.get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void testAdd() throws Exception{
		AbstractSale currentSale = new MockSale();
		currentSale.add("biscoito da vaquinha", 10);
		assertEquals(1, currentSale.getItemsCount(), 0);
		
		currentSale.add("goiabada", 2);
		assertEquals(2, currentSale.getItemsCount(), 0);		
	}
	
	@Test
	public void testAddSameItem() throws Exception{
		AbstractSale currentSale = new MockSale();
		currentSale.add("biscoito da vaquinha", 10);
		assertEquals(1, currentSale.getItemsCount(), 0);
		
		currentSale.add("goiabada", 2);
		assertEquals(2, currentSale.getItemsCount(), 0);		

		currentSale.add("biscoito da vaquinha", 3);
		assertEquals(2, currentSale.getItemsCount(), 0);

		currentSale.add("bala", 50);
		assertEquals(3, currentSale.getItemsCount(), 0);	
	}
	
	@Test
	public void testTotal() throws Exception{
		AbstractSale currentSale = new MockSale();
		currentSale.add("biscoito da vaquinha", 10);
		// supor que biscoito da vaquina custa 5
		assertEquals(50, currentSale.saleCost(), 0);

		// supor que biscoito da vaquina custa 10 (coloquei um erro no mock object de proposito. O ideal era que o produto fosse o mock e nao o item)
		currentSale.add("goiabada", 2);
		assertEquals(70, currentSale.saleCost(), 0);		
	}
	
	@Test
	public void testProductPrice() throws Exception{
		Product product = new Product("new product", 5, 1.2);
		
		assertEquals(5*1.2, product.getProductPrice(),0);

	}
	
}