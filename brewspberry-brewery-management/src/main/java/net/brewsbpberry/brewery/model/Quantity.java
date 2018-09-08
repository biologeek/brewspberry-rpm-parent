package net.brewsbpberry.brewery.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Quantity {

	private Float quantity;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public enum Unit {
		MILLILITER, CENTILITER, LITER, MILLIMETER, CENTIMETER, METER, KILOMETER, MILLIGRAM, GRAM, KILOGRAM;
	}

}
