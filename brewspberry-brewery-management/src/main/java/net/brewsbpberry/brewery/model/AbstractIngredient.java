package net.brewsbpberry.brewery.model;

import java.util.List;

import javax.measure.quantity.Mass;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * Stores common data for an ingredient such as brand, variety, ... <br>
 * <br>
 * Data related to conditioning, stocks, ... are stored in separate module.
 *
 */
@MappedSuperclass
public abstract class AbstractIngredient {

	@Id
	@GeneratedValue
	private Long id;

	private String brand;

	private String model;

	private Mass packaging;

	@OneToMany(fetch = FetchType.LAZY)
	private List<QuantifiedIngredient> stepIngredients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Mass getPackaging() {
		return packaging;
	}

	public void setPackaging(Mass packaging) {
		this.packaging = packaging;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<QuantifiedIngredient> getStepIngredients() {
		return stepIngredients;
	}

	public void setStepIngredients(List<QuantifiedIngredient> stepIngredients) {
		this.stepIngredients = stepIngredients;
	}

}
