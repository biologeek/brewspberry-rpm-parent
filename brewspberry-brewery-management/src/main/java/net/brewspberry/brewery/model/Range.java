package net.brewspberry.brewery.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import net.brewspberry.brewery.api.Unit;

/**
 * A range of whatever quantifiable
 *
 */
@Embeddable
public class Range {

	private Double low;
	private Double high;
	@Enumerated(EnumType.STRING)
	private Unit unit;

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
