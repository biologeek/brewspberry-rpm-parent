package net.brewspberry.brewery.model;

import javax.persistence.Embeddable;

import net.brewspberry.brewery.api.Unit;

@Embeddable
public class CustomMass {

	private Float value;
	private Unit unit;

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
