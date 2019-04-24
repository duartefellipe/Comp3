package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.derby.impl.sql.catalog.SYSPERMSRowFactory;

import dominio.InterfaceProductCatalog;
import dominio.Product;

public class ProductCatalog implements InterfaceProductCatalog{
	
	@Override
	public Product findProduct(String description) {
		ConnectionPool conPoll = ConnectionPool .GetInstance();
		String query = "SELECT productCost,profitMargin FROM product WHERE description like ? ";
		Connection con;
		try {
			con = conPoll.getAConnection();
			PreparedStatement s = con.prepareStatement(query);
			s.setString(1, description);
			ResultSet rs = s.executeQuery();
			
			if (rs.next()) {
				Double productCost = rs.getDouble(1);
				Double productMargin = rs.getDouble(2);
				con.close();
				return new Product(description,productCost,productMargin);
			}else {
				con.close();
				return null;
			}			
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	@Override
	public boolean storeNewProduct(Product newProduct) {
		ConnectionPool conPoll = ConnectionPool .GetInstance();
		Connection con;
		try {
			con = conPoll.getAConnection();
			PreparedStatement s = con.prepareStatement("INSERT description, cost,margin INTO product values (?,?,?); ");
			s.setString(1, newProduct.getDescription());
			s.setDouble(2, newProduct.getProductCost());
			s.setDouble(3, newProduct.getProductPrice());
			s.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			return false;
		}		
		
	}


}
