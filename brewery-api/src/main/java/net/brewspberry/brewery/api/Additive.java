package net.brewspberry.brewery.api;

public class Additive extends AbstractIngredient {

	
	private String code;
	private String function;
	private Quantity recommendedQuantity;

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

	public Quantity getRecommendedQuantity() {
		return recommendedQuantity;
	}

	public void setRecommendedQuantity(Quantity recommendedQuantity) {
		this.recommendedQuantity = recommendedQuantity;
	}
}
