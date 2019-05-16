package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;


public class ConnectionPool {
	
	private String jdbcConString;
	private String jdbcUsername;
	private String jdbcPassword;
	private static ConnectionPool instance;

	public static ConnectionPool GetInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}
	
	public Connection getAConnection() throws SQLException {
		return DriverManager.getConnection(jdbcConString, jdbcUsername, jdbcPassword);
	}
	
	
	protected ConnectionPool() {
//	    jdbcConString = "jdbc:derby:..\\ecommmerce_test_db;create=true";
	    jdbcConString = "jdbc:derby:../ecommmerce_test_db;create=true";
	    jdbcUsername = "comp3";
	    jdbcPassword = "comp3";
	}

	public void createDataset() throws SQLException {
		String[] dbScript = {
	    		"CREATE TABLE Product(\r\n" + 
	    		"   id   INTEGER              NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\r\n" + 
	    		"   description VARCHAR (100)     NOT NULL,\r\n" + 
	    		"   productCost   DECIMAL (18, 2),       \r\n" +
	    		"   profitMargin   DECIMAL (18, 2)       \r\n" +
	    		")", 
	    		"CREATE TABLE Sale(\r\n" + 
	    		"   id   INTEGER              NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\r\n" + 
	    		"   date DATE       		  NOT NULL \r\n" +
	    		")",	    		
	    		"CREATE TABLE SaleItem(\r\n" + 
	    		"   amount   INTEGER 			NOT NULL,\r\n" +
	    		"   saleId   INTEGER 			NOT NULL,\r\n" +
	    		"   productId   INTEGER 			NOT NULL,\r\n" +
	    		"CONSTRAINT pk_saleitem PRIMARY KEY (saleId,productId),\r\n"+
	    		"CONSTRAINT fk_sale FOREIGN KEY (saleId) REFERENCES Sale(id),\r\n"+
	    		"CONSTRAINT fk_product FOREIGN KEY (productId) REFERENCES Product(id)\r\n"+
	    		")",
	    };
	    

    	Connection con = null;
	    for (String sql : dbScript) {
	    	con = getAConnection();
	    	System.out.println(sql);
	    	PreparedStatement s = con.prepareStatement(sql);
			s.executeUpdate();
	    }
	    if (con != null)
	    	con.close();
	}
	
	public void dropDataset() {
		String[] dbScript = {
				"ALTER TABLE saleitem DROP FOREIGN KEY fk_sale",
				"ALTER TABLE saleitem DROP FOREIGN KEY fk_product",
	    		"DROP TABLE SaleItem",
	    		"DROP TABLE Sale",
	    		"DROP TABLE Product",
	    };
	    
    	Connection con = null;
		for (String sql : dbScript) {
			try {
				con = getAConnection();
				System.out.println(sql);
				long time = Calendar.getInstance().getTimeInMillis();
		    	PreparedStatement s = con.prepareStatement(sql);
				s.executeUpdate();
				time = Calendar.getInstance().getTimeInMillis() - time;
				System.out.println("in "+ time+ " ms");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
		
	    if (con != null)
			try {
				con.close();
			} catch (SQLException e) {}
	}
	
	public static void main(String[] args) {
		ConnectionPool pool = ConnectionPool.GetInstance();
		
		pool.dropDataset();
		try {
			pool.createDataset();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
