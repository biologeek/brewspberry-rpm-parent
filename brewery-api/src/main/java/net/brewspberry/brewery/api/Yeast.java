package net.brewspberry.brewery.api;

public class Yeast extends AbstractIngredient{

	
	private Range temperatureRange;
	private String densityLevel;
	public Yeast() {
		this.type = "Y";
	}
	public Range getTemperatureRange() {
		return temperatureRange;
	}
	public void setTemperatureRange(Range temperatureRange) {
		this.temperatureRange = temperatureRange;
	}
	public String getDensityLevel() {
		return densityLevel;
	}
	public void setDensityLevel(String densityLevel) {
		this.densityLevel = densityLevel;
	}
	
}
