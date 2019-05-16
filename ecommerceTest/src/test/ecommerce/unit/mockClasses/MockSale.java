package test.ecommerce.unit.mockClasses;

import domain.AbstractSale;
import domain.Client;
import domain.ItemInterface;

public class MockSale extends AbstractSale {
	
	public MockSale(Client saleClient) {
		super(saleClient);
	}

	@Override
	protected ItemInterface itemFactory(String description, int amount) {
		ItemInterface newItem = new MockItem(description,amount);
		return newItem;
	}
}
