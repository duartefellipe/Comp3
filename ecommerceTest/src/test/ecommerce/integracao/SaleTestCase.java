package test.ecommerce.integracao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.dbunit.DatabaseTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import dominio.Product;
import persistencia.ConnectionPool;
import persistencia.ProductCatalog;

public class SaleTestCase extends DatabaseTestCase{
	
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
	
	@Test
	public void testFindProduct() throws Exception{
		ConnectionPool pool = ConnectionPool.GetInstance();
		pool.createDataset();
		
		ProductCatalog catalog = new ProductCatalog();
		String descriptionToSearch = "garrafa de agua";
		Product product = catalog.findProduct(descriptionToSearch);
		
		assertTrue(descriptionToSearch == product.getDescription());
		assertEquals(4.5, product.getProductCost(), 0);
		assertEquals(4.5*1.9, product.getProductPrice(), 0);
		
		pool.dropDataset();
	}
}
