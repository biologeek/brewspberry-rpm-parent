package net.brewspberry.brewery.service;

import java.util.List;

import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.Step;

public interface StepService {

	List<Step> createSteps(List<Step> steps) throws ValidationException;
	List<Step> updateSteps(List<Step> steps);
	Step updateStep(Step step);

}
