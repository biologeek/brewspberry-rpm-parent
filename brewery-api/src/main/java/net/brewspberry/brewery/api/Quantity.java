package net.brewspberry.brewery.api;

public class Quantity {
	private Float quantity;
	private String unit;

	public Quantity(Float quantity2, String string) {
		quantity = quantity2;
		unit = string;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
