package domain;

public interface InterfaceProductCatalog {
	
	public Product findProduct(String description);
	public boolean storeNewProduct(Product newProduct);
	
}
