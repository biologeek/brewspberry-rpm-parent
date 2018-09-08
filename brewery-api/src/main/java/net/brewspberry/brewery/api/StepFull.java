package net.brewspberry.brewery.api;

import java.util.List;

public class StepFull extends StepLight {

	protected List<StepIngredient> stepIngredients;

	public List<StepIngredient> getStepIngredients() {
		return stepIngredients;
	}

	public void setStepIngredients(List<StepIngredient> stepIngredients) {
		this.stepIngredients = stepIngredients;
	}

}
