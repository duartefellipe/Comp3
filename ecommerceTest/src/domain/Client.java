package domain;

public class Client {
	private String address;
	private int number;
	private String complement;
	private String district;
	private CreditCard payment;

	public Client(String address, int number, String complement, String district, int[] ccNumber, int[] ccDV) {
		this.address = address;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.payment = new CreditCard(ccNumber, ccDV);
	}
	
}
