package domain;

public interface ItemInterface {
	public abstract double itemCost();
	public abstract double itemPrice();
	public abstract String getDescription();
	public abstract void increaseAmount(int newAmount);
}
