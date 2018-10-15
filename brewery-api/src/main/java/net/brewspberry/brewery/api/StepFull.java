package net.brewspberry.brewery.api;

import java.util.List;

public class StepFull extends StepLight {

	protected List<QuantifiedIngredient> stepIngredients;

	public List<QuantifiedIngredient> getStepIngredients() {
		return stepIngredients;
	}

	public void setStepIngredients(List<QuantifiedIngredient> stepIngredients) {
		this.stepIngredients = stepIngredients;
	}

}
