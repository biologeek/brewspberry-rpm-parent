package net.brewspberry.brewery.rest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.brewery.api.QuantifiedIngredient;
import net.brewspberry.brewery.api.StepFull;
import net.brewspberry.brewery.api.TemperatureStage;
import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ElementNotFoundRuntimeException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.exceptions.ValidationRuntimeException;
import net.brewspberry.brewery.mappers.StepIngredientMapper;
import net.brewspberry.brewery.mappers.StepMapper;
import net.brewspberry.brewery.mappers.TemperatureStageMapper;
import net.brewspberry.brewery.model.Step.StepType;
import net.brewspberry.brewery.service.StepService;

@RestController
@RequestMapping("/step")
public class StepResource {

	@Autowired
	private StepService stepService;
	@Autowired
	private StepMapper stepMapper;
	@Autowired
	private StepIngredientMapper stepIngredientMapper;
	@Autowired
	private TemperatureStageMapper stepStageMapper;
	

	@GetMapping("/types")
	public ResponseEntity<List<String>> getStepTypes(){
		return new ResponseEntity<>(Arrays.asList(StepType.values()).stream().map(s -> s.name()).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StepFull> getStepById(@PathVariable("id") Long stepId){
		try {
			return new ResponseEntity<>(this.stepMapper.toFullDto(this.stepService.getStepById(stepId)), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundRuntimeException();
		}
	}

	@PostMapping("/{step}/ingredient")
	public ResponseEntity<StepFull> pushNewIngredient(@PathVariable("step") Long stepId, @RequestBody QuantifiedIngredient ingredient){
		try {
			return new ResponseEntity<>(this.stepMapper.toFullDto(this.stepService.addNewIngredient(stepId, this.stepIngredientMapper.toModel(ingredient))), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundRuntimeException();
		} catch (ValidationException e) {
			throw new ValidationRuntimeException(e.getMessage());
		}
	}
	@PostMapping("/{step}/stages")
	public ResponseEntity<StepFull> pushNewStage(@PathVariable("step") Long stepId, @RequestBody TemperatureStage stage){
		try {
			return new ResponseEntity<>(this.stepMapper.toFullDto(this.stepService.addNewStage(stepId, this.stepStageMapper.toModel(stage))), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundRuntimeException();
		} catch (ValidationException e) {
			throw new ValidationRuntimeException(e.getMessage());
		}
	}
}
