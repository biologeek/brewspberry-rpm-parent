package net.brewspberry.brewery.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import net.brewspberry.brewery.api.Unit;

@Embeddable
public class CustomQuantity {

	private Float quantity;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	public CustomQuantity() {
		super();
	}

	public CustomQuantity(Float qty, Unit unt) {
		quantity = qty;
		unit = unt;
	}

	public CustomQuantity(Float quantity2, String unit2) {
		this.quantity = quantity2;
		this.unit = Unit.valueOf(unit2);
	}

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

}
