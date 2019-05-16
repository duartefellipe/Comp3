package domain;

import domain.AbstractSale;
import domain.ItemInterface;

public class Sale extends AbstractSale {

	public Sale(Client saleClient) {
		super(saleClient);
	}

	@Override
	protected ItemInterface itemFactory(String description, int amount) throws Exception {
		ItemInterface newItem = new SaleItem(description,amount);
		return newItem;
	}
}
