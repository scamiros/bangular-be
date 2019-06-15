package it.lucius.customers.enumeration;

public enum MoneyCategoryType {

	E("E"),
	U("U");
	
	private String type;
	
	private MoneyCategoryType(String e) {
		this.type = e;
	}
	
	public String getMoneyCategoryType() {
		return this.type;
	}
}
