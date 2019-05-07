package net.brewspberry.brewery.api;

public class Hop extends AbstractIngredient {

	private Float alphaPercentage;
	private String hopType;

	public Hop() {
		this.type = "H";
	}

	public String getHopType() {
		return hopType;
	}

	public void setHopType(String hopType) {
		this.hopType = hopType;
	}

	public Float getAlphaPercentage() {
		return alphaPercentage;
	}

	public void setAlphaPercentage(Float lphaPercentage) {
		this.alphaPercentage = lphaPercentage;
	}

}
