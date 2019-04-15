package dominio;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractSale{

	protected Date date;
	private Set<ItemInterface> saleItens;
	
	/**  the Sale is the Item factory
	 * @throws Exception 
	 */
	protected abstract ItemInterface itemFactory(String description, int amount) throws Exception;
	
	public void add(String description, int amount) throws Exception{
		if (saleItens == null) {
			saleItens = new HashSet<ItemInterface>();
		};
		
		ItemInterface newItem = null;
		for (ItemInterface ii : saleItens) {
			if (ii.getDescription() == description) {
				ii.increaseAmount(amount);
				newItem = ii;
			};
		}
		
		if (newItem == null) {
			newItem = itemFactory(description, amount);
			saleItens.add(newItem); 
		}
		
	}	
	
	public Date getDate() {
		return date;
	}
	
	public int getItemsCount() {
		return saleItens.size();
	}

	public double saleCost() {
		
		double currentAmount = 0;
		for (ItemInterface i :saleItens) {
			currentAmount += i.itemCost();
		}
		
		return currentAmount;
	}
	
	public double salePrice() {
		
		double currentAmount = 0;
		for (ItemInterface i :saleItens) {
			currentAmount += i.itemPrice();
		}
		
		return currentAmount;
	}

}
