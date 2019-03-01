package net.brewsbpberry.brewery.model;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Stores common data for an ingredient such as brand, variety, ... Data related
 * to conditioning, ... are stored in separate module
 *
 */
public abstract class AbstractIngredient {

	@Id
	@GeneratedValue
	private Long id;

	private String brand;
	
	private String model;

	@OneToMany(fetch = FetchType.LAZY)
	private List<QuantifiedIngredient> stepIngredients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
