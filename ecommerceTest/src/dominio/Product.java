package dominio;

public class Product {
	private String description;
	private double productCost;
	private double profitMargin;

	public Product(String description, double productCost, double profitMargin) {
		this.description = description;
		this.productCost = productCost;
		this.profitMargin = profitMargin;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getProductCost() {
		return productCost;
	}
	
	public double getProductPrice() {
		return productCost*profitMargin; 
	};
}
