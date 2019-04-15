package test.ecommerce.unitario.mockClasses;

import dominio.ItemInterface;

public class MockItem implements ItemInterface {

	private String description;
	private int amount;

	public MockItem(String description, int amount) {
		this.description = description;
		this.amount = amount;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public double itemCost() {
		return amount*5;
	}
	
	@Override
	public double itemPrice() {
		return itemCost()*1.1;
	}

	@Override
	public void increaseAmount(int newAmount) {
		amount += newAmount;
	}

}
