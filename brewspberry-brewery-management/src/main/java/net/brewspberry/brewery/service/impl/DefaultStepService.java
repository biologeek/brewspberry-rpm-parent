package net.brewspberry.brewery.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.QuantifiedIngredient;
import net.brewspberry.brewery.model.Step;
import net.brewspberry.brewery.model.TemperatureStageOperation;
import net.brewspberry.brewery.repositories.QuantifiedIngredientRepository;
import net.brewspberry.brewery.repositories.StepRepository;
import net.brewspberry.brewery.repositories.TemperatureStageOperationRepository;
import net.brewspberry.brewery.service.StepService;

@Service
public class DefaultStepService implements StepService {

	@Autowired
	private StepRepository stepRepo;
	@Autowired
	private TemperatureStageOperationRepository temperatureStageOperationRepository;
	@Autowired
	private QuantifiedIngredientRepository quantifiedIngredientRepository;

	@Override
	public List<Step> updateSteps(List<Step> steps) {
		if (steps == null || steps.isEmpty())
			return new ArrayList<>();
		return steps.stream()//
				.map(this::updateStep)//
				.collect(Collectors.toList());
	}

	@Override
	public Step updateStep(Step step) {
		Step saved = this.stepRepo.getOne(step.getId());
		saved = this.mergeStep(step, saved);

		return doSaveStep(saved);
	}

	/**
	 * Method performing persistence operations for step and its subcomponents
	 * 
	 * @param saved
	 *            step to save
	 * @return persisted step
	 */
	private Step doSaveStep(Step saved) {
		this.updateStepIngredients(saved.getStepIngredients());
		this.updateStepTemperatureStages(saved.getTemperatureStages());
		return this.stepRepo.save(saved);
	}

	/**
	 * Service method for updating temperature stages. Should eventually be placed
	 * in a separate service class...
	 * 
	 * @param temperatureStages
	 * @return
	 */
	private List<TemperatureStageOperation> updateStepTemperatureStages(
			List<TemperatureStageOperation> temperatureStages) {
		return this.temperatureStageOperationRepository.saveAll(temperatureStages);

	}

	private TemperatureStageOperation updateStepTemperatureStage(TemperatureStageOperation temperatureStage) {
		return this.temperatureStageOperationRepository.save(temperatureStage);

	}

	private List<QuantifiedIngredient> updateStepIngredients(List<QuantifiedIngredient> stepIngredients) {
		return this.quantifiedIngredientRepository.saveAll(stepIngredients);
	}

	private Step mergeStep(Step step, Step saved) {
		saved.setBeginning(step.getBeginning());
		saved.setEndDate(step.getEndDate());
		saved.setLabel(step.getLabel());
		saved.setStepIngredients(step.getStepIngredients());
		saved.setStepType(step.getStepType());
		saved.setTemperatureStages(step.getTemperatureStages());
		saved.setUpdateDate(step.getUpdateDate());

		return saved;
	}

	@Override
	public List<Step> createSteps(List<Step> steps) throws ValidationException {
		List<Step> result = new ArrayList<>();
		for (Step elt : steps) {
			result.add(createStep(elt));
		}
		return result;
	}

	private Step createStep(Step step) throws ValidationException {
		if (step == null)
			throw new ValidationException("Null step");

		this.validateStep(step);
		return doSaveStep(step);
	}

	private void validateStep(Step step) {
		// TODO Auto-generated method stub

	}

	@Override
	public Step addNewIngredient(Long stepId, QuantifiedIngredient model) throws ElementNotFoundException {
		Step step = this.getStepById(stepId);
		model.setStep(step);
		model = this.quantifiedIngredientRepository.save(model);
		step.getStepIngredients().add(model);
		return step;
	}

	public Step getStepById(Long stepId) throws ElementNotFoundException {
		Step result = this.stepRepo.getOne(stepId);
		if (result != null)
			return result;
		throw new ElementNotFoundException();
	}

	@Override
	public Step addNewStage(Long stepId, TemperatureStageOperation model) throws ElementNotFoundException {
		Step step = this.getStepById(stepId);
		model.setStep(step);
		model = this.temperatureStageOperationRepository.save(model);
		step.getTemperatureStages().add(model);
		return step;
	}

}
