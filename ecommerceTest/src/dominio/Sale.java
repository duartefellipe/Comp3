package dominio;

import dominio.ItemInterface;

import java.util.Date;

import dominio.AbstractSale;

public class Sale extends AbstractSale {

	public Sale() {
		date = new Date();
	}

	@Override
	protected ItemInterface itemFactory(String description, int amount) throws Exception {
		ItemInterface newItem = new SaleItem(description,amount);
		return newItem;
	}
}
