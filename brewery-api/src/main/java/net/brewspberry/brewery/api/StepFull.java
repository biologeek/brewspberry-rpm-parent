package net.brewspberry.brewery.api;

import java.util.List;

public class StepFull extends StepLight {

	protected List<QuantifiedIngredient> ingredients;
	protected List<TemperatureStage> stages;

	public List<QuantifiedIngredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<QuantifiedIngredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<TemperatureStage> getStages() {
		return stages;
	}

	public void setStages(List<TemperatureStage> stages) {
		this.stages = stages;
	}

}
