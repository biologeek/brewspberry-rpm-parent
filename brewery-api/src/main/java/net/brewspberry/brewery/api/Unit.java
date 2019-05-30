package net.brewspberry.brewery.api;

public enum Unit {
	MILLILITER("ml"), CENTILITER("cl"), LITER("L"),

	/**/

	MILLIMETER("mm"), CENTIMETER("cm"), METER("m"), KILOMETER("km"),

	/**/

	MILLIGRAM("mg"), GRAM("g"), KILOGRAM("kg"),

	/**/
	SQUARE_METER("m2"),

	/**/

	CUBIC_METER("m3"),

	/**/

	PERCENT("");

	private String symbol;

	Unit(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
