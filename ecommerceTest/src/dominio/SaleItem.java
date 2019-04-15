package dominio;

import persistencia.ProductCatalog;

public class SaleItem implements ItemInterface {

	private int amount;
	private Product product;

	protected SaleItem(String description, int amount) throws Exception {
		
		this.amount = amount;
		InterfaceProductCatalog catalog = new ProductCatalog();
		product = catalog.findProduct(description);
		
		if (product == null)
			throw new Exception("Product description not found!");
	}
	
	@Override
	public String getDescription() {
		return product.getDescription();
	}

	@Override
	public double itemCost() {
		return amount*product.getProductCost();
	}
	
	@Override
	public double itemPrice() {
		return itemCost()*product.getProductPrice();
	}

	@Override
	public void increaseAmount(int newAmount) {
		amount += newAmount;
	}

}
