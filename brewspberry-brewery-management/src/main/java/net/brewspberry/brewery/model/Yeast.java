package net.brewspberry.brewery.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * A yeast is here an ingredient for brewing. It has all common ingredient
 * properties. A yeast has also a specie, and several properties such as
 * fermentation temperature range, final density level, ...
 * 
 * @author xavier
 *
 */
@Entity
public class Yeast extends AbstractIngredient {

	private String specie;
	private String densityLevel;
	@Embedded
	private Range ferementationTemperatureRange;

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getDensityLevel() {
		return densityLevel;
	}

	public void setDensityLevel(String densityLevel) {
		this.densityLevel = densityLevel;
	}

	public Range getFerementationTemperatureRange() {
		return ferementationTemperatureRange;
	}

	public void setFerementationTemperatureRange(Range ferementationTemperatureRange) {
		this.ferementationTemperatureRange = ferementationTemperatureRange;
	}

}
