package net.brewspberry.brewery.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.StepFull;
import net.brewspberry.brewery.api.StepLight;
import net.brewspberry.brewery.model.Step;
import net.brewspberry.brewery.model.Step.StepType;

@Component
public class StepMapper {
	
	@Autowired
	private StepIngredientMapper stepIngredientMapper;
	@Autowired
	private TemperatureStageMapper stagesMapper;

	public List<Step> toModel(List<StepFull> steps) {
		if (steps == null || steps.isEmpty())
			return new ArrayList<>();
		return steps.stream().map(this::toModel).collect(Collectors.toList());
	}

	public Step toModel(StepFull step) {
		Step res = new Step();
		res.setId(step.getId());
		res.setBeginning(step.getBeginning());
		res.setEndDate(step.getEnd());
		res.setLabel(step.getName());
		res.setStepType(StepType.valueOf(step.getType()));
		return res;
	}

	public List<StepFull> toFullDto(List<Step> steps) {
		if (steps == null || steps.isEmpty())
			return new ArrayList<>();
		return steps.stream().map(this::toFullDto).collect(Collectors.toList());
	}

	public StepFull toFullDto(Step step) {
		StepFull res = new StepFull();
		res = toDtoCommon(res, step);
		res.setStages(this.stagesMapper.toDto(step.getTemperatureStages()));
		res.setIngredients(stepIngredientMapper.toDto(step.getStepIngredients()));
		return res;
	}

	public List<StepLight> toLightDto(List<Step> steps) {
		if (steps == null || steps.isEmpty())
			return new ArrayList<>();
		return steps.stream().map(this::toFullDto).collect(Collectors.toList());
	}

	public StepLight toLightDto(Step step) {
		StepLight res = new StepLight();
		res = toDtoCommon(res, step);
		return res;
	}

	private <T extends StepLight> T toDtoCommon(T res, Step step) {
		res.setId(step.getId());
		res.setBeginning(step.getBeginning());
		res.setEnd(step.getEndDate());
		res.setName(step.getLabel());
		res.setType(step.getStepType().name());
		return res;
	}

}
