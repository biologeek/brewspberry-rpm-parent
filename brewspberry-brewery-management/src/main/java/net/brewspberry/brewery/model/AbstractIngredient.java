package net.brewspberry.brewery.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * Stores common data for an ingredient such as brand, variety, ... <br>
 * <br>
 * Data related to conditioning, stocks, ... are stored in separate module.
 * 
 * This class defines
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String brand;

	private String model;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "quantity", column = @Column(name = "packaging_qty")),
			@AttributeOverride(name = "unit", column = @Column(name = "packaging_unit")) })
	private CustomQuantity packaging;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ingredient")
	private List<QuantifiedIngredient> stepIngredients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomQuantity getPackaging() {
		return packaging;
	}

	public void setPackaging(CustomQuantity packaging) {
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
