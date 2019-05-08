package net.brewspberry.brewery.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * Additives are non-necesary products that are added to enhance certain
 * properties of beer such as iron powder for a more stable foam, egg white to
 * agregate yeasts, ...
 * 
 * @author xavier
 *
 */
@Entity
public class Additive extends AbstractIngredient {

	private String code;
	private String function;
	private CustomQuantity recommendedQuantity;

	@ManyToMany(mappedBy = "additives")
	private List<Brew> brew;

	public List<Brew> getBrew() {
		return brew;
	}

	public void setBrew(List<Brew> brew) {
		this.brew = brew;
	}

	public CustomQuantity getRecommendedQuantity() {
		return recommendedQuantity;
	}

	public void setRecommendedQuantity(CustomQuantity recommendedQuantity) {
		this.recommendedQuantity = recommendedQuantity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

}
