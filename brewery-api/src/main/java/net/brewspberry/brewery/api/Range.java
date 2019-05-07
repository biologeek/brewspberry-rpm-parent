package net.brewspberry.brewery.api;

/**
 * A range of whatever quantifiable
 *
 */
public class Range {

	private Double low;
	private Double high;
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
