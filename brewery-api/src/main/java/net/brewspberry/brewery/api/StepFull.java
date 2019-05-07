package net.brewspberry.brewery.api;

import java.util.List;

public class StepFull extends StepLight {

	protected List<QuantifiedIngredient> ingredients;

	public List<QuantifiedIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<QuantifiedIngredient> ingredients) {
		this.ingredients = ingredients;
	}


}
