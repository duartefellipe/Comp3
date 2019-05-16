package test.ecommerce.integration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import org.dbunit.DatabaseTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.AbstractSale;
import domain.Client;
import domain.Product;
import domain.Sale;
import persistence.ConnectionPool;
import persistence.ProductCatalog;

/***
 *  Before this test case run the main method from persistencia.ConnectionPool
 * @author Fellipe
 *
 */
public class SaleTestCase extends DatabaseTestCase{

	private AbstractSale currentSale;
	
	protected IDatabaseConnection getConnection() throws DatabaseUnitException, SQLException{
		ConnectionPool pool = ConnectionPool.GetInstance();
		Connection conn = pool.getAConnection();
		return new DatabaseConnection(conn);
	}
	
	protected IDataSet getDataSet() throws DataSetException, FileNotFoundException {
		return new XmlDataSet(new FileInputStream("./src/test/ecommerce/integracao/data/dataset2.xml"));
}
	
	protected DatabaseOperation getSetUpOperation() throws DatabaseUnitException, SQLException {
		IDatabaseConnection aCon = getConnection();
		Connection dbConnection = aCon.getConnection();
		dbConnection.createStatement().execute("ALTER TABLE saleitem DROP FOREIGN KEY fk_sale");
		dbConnection.createStatement().execute("ALTER TABLE saleitem DROP FOREIGN KEY fk_product");
		dbConnection.close();
		aCon.close();
		return DatabaseOperation.CLEAN_INSERT;
	}
	
	protected DatabaseOperation getTearDownOperation() throws SQLException, DatabaseUnitException{
		IDatabaseConnection aCon = getConnection();
		IDataSet ds = aCon.createDataSet();
		Connection dbConnection = aCon.getConnection();

		ITable tbp = ds.getTable("product");
		
		for (int i = 0; i < tbp.getRowCount(); i++) {
			System.out.println("PROD: row["+i+"]=<"+tbp.getValue(i, "id")+","+tbp.getValue(i, "description")+">");
		}

		ITable tbs = ds.getTable("sale");
		
		for (int i = 0; i < tbs.getRowCount(); i++) {
			System.out.println("SALE: row["+i+"]=<"+tbs.getValue(i, "id")+","+tbs.getValue(i, "date")+">");
		}

		ITable tbis = ds.getTable("saleitem");
		
		for (int i = 0; i < tbis.getRowCount(); i++) {
			System.out.println("SI  : row["+i+"]=<"+tbis.getValue(i, "productid")+","+tbis.getValue(i, "saleid")+","+tbis.getValue(i, "amount")+">");
		}
		
		Statement statement = dbConnection.createStatement();
		statement.execute("DELETE from product");
		statement.closeOnCompletion();
		statement = dbConnection.createStatement();
		statement.execute("DELETE from sale");
		statement.closeOnCompletion();
		statement = dbConnection.createStatement();
		statement.execute("DELETE from saleitem");
		statement.closeOnCompletion();
		
		try {
			dbConnection.createStatement().execute("ALTER TABLE product ALTER COLUMN id RESTART WITH 1");
			dbConnection.createStatement().execute("ALTER TABLE sale ALTER COLUMN id RESTART WITH 1");
		} catch (Exception e) {
			e.printStackTrace();
		}

		dbConnection.createStatement().execute("ALTER TABLE saleitem ADD CONSTRAINT fk_sale FOREIGN KEY (saleId) REFERENCES Sale(id)");
		dbConnection.createStatement().execute("ALTER TABLE saleitem ADD CONSTRAINT fk_product FOREIGN KEY (productId) REFERENCES Product(id)");
		dbConnection.close();
		aCon.close();
		
		return DatabaseOperation.NONE;
	}
	
	@Before
	public void setUp() {
		this.currentSale = new Sale(new Client("first street", 1, "second house", "any", new int[5], new int[3]));
		System.out.println("before");
	}
	
	@After
	public void tearDown() {
		this.currentSale = null;
		System.out.println("after");
	}
	
	@Test
	public void testFindProduct() throws Exception{
		
		ProductCatalog catalog = new ProductCatalog();
		String descriptionToSearch = "garrafa de agua";
		Product product = catalog.findProduct(descriptionToSearch);
		
		assertTrue(descriptionToSearch == product.getDescription());
		assertEquals(4.5, product.getProductCost(), 0);
		assertEquals(4.5*1.9, product.getProductPrice(), 0);
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

		currentSale.add("bala", 50);
		assertEquals(3, currentSale.getItemsCount(), 0);	
	}
	
	@Test
	public void testTotal() throws Exception{
		currentSale.add("biscoito da vaquinha", 10);
		// Suppose that "biscoito da vaquina" cost 5
		assertEquals(50, currentSale.saleCost(), 0);

		// Suppose that "biscoito da vaquina" cost 10
		// this test fails to show what junit do when a unit test fails  
		currentSale.add("goiabada", 2);
		assertEquals(70, currentSale.saleCost(), 0);		
	}
}
