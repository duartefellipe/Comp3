package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

	protected void createDataset() throws SQLException {
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
	    		"CREATE INDEX description_index ON Product(description)"
	    };
	    
		
	    for (String sql : dbScript) {
	    	Connection con = getAConnection();
	    	System.out.println(sql);
	    	PreparedStatement s = con.prepareStatement(sql);
			s.executeUpdate();
	    }
	}
	
	protected void dropDataset(){
		String[] dbScript = {
	    		"DROP TABLE SaleItem",
	    		"DROP TABLE Product", 
	    		"DROP TABLE Sale"
	    };
	    
	    for (String sql : dbScript) {
	    	Connection con;
			try {
				con = getAConnection();
				System.out.println(sql);
		    	PreparedStatement s = con.prepareStatement(sql);
				s.executeUpdate();
			} catch (SQLException e) {}
	    }
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
