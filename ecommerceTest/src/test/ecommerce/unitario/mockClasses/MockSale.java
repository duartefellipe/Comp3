package test.ecommerce.unitario.mockClasses;

import dominio.ItemInterface;

import java.util.Date;

import dominio.AbstractSale;

public class MockSale extends AbstractSale {
	
	public MockSale() {
		date = new Date();
	}

	@Override
	protected ItemInterface itemFactory(String description, int amount) {
		ItemInterface newItem = new MockItem(description,amount);
		return newItem;
	}
}
