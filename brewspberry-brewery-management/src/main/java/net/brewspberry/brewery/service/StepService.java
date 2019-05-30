package net.brewspberry.brewery.service;

import java.util.List;

import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.QuantifiedIngredient;
import net.brewspberry.brewery.model.Step;
import net.brewspberry.brewery.model.TemperatureStageOperation;

public interface StepService {

	List<Step> createSteps(List<Step> steps) throws ValidationException;
	List<Step> updateSteps(List<Step> steps);
	Step updateStep(Step step);
	Step addNewIngredient(Long stepId, QuantifiedIngredient model) throws ElementNotFoundException;
	Step getStepById(Long stepId) throws ElementNotFoundException;
	Step addNewStage(Long stepId, TemperatureStageOperation model) throws ElementNotFoundException;
}
