package net.brewspberry.brewery.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.TemperatureStage;
import net.brewspberry.brewery.model.TemperatureStageOperation;

@Component
public class TemperatureStageMapper {

	public TemperatureStage toDto(TemperatureStageOperation model) {
		TemperatureStage dto = new TemperatureStage();
		dto.setId(model.getId());
		dto.setBeginning(model.getBeginning());
		dto.setDuration(model.getDuration());
		dto.setEndTemperature(model.getEndSetPoint());
		dto.setStartTemperature(model.getBeginningSetPoint());
		dto.setBeginningToStep(model.getBeginningToStep());
		dto.setType(model.getStageType());
		return dto;
	}

	public List<TemperatureStage> toDto(List<TemperatureStageOperation> model) {
		if (model == null || model.isEmpty())
			return new ArrayList<>();
		return model.stream()//
				.map(this::toDto)//
				.collect(Collectors.toList());
	}

	public TemperatureStageOperation toModel(TemperatureStage dto) {
		TemperatureStageOperation model = new TemperatureStageOperation();
		model.setId(dto.getId());
		model.setBeginning(dto.getBeginning());
		model.setDuration(dto.getDuration());
		model.setEndSetPoint(dto.getEndTemperature());
		model.setBeginningSetPoint(dto.getStartTemperature());
		model.setBeginningToStep(dto.getBeginningToStep());
		model.setStageType(dto.getType());
		return model;
	}

	public List<TemperatureStageOperation> toModel(List<TemperatureStage> model) {
		if (model == null || model.isEmpty())
			return new ArrayList<>();
		return model.stream()//
				.map(this::toModel)//
				.collect(Collectors.toList());
	}
}
