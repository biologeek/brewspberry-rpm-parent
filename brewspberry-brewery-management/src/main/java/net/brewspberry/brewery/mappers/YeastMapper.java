package net.brewspberry.brewery.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Yeast;

@Component
public class YeastMapper extends AbstractIngredientMapper<net.brewspberry.brewery.model.Yeast, Yeast> {

	@Autowired
	private RangeMapper rangeMapper;

	public List<Yeast> toDto(List<net.brewspberry.brewery.model.Yeast> yeasts) {
		if (yeasts == null || yeasts.isEmpty())
			return new ArrayList<>();
		return yeasts.stream().map(t -> toDto(t)).collect(Collectors.toList());
	}

	public Yeast toDto(net.brewspberry.brewery.model.Yeast model) {
		Yeast dto = this.toDtoAbstract(model);
		return dto;
	}

	public net.brewspberry.brewery.model.Yeast toModel(Yeast dto) {
		net.brewspberry.brewery.model.Yeast model = this.toModelAbstract(dto);
		model.setDensityLevel(dto.getDensityLevel());
		model.setFerementationTemperatureRange(rangeMapper.toModel(dto.getTemperatureRange()));
		return model;
	}

	@Override
	protected Yeast initDto() {
		return new Yeast();
	}

	public List<net.brewspberry.brewery.model.Yeast> toModel(List<Yeast> yeasts) {
		if (yeasts == null || yeasts.isEmpty())
			return new ArrayList<>();
		return yeasts.stream().map(t -> toModel(t)).collect(Collectors.toList());
	}

	@Override
	protected net.brewspberry.brewery.model.Yeast initModel() {
		return new net.brewspberry.brewery.model.Yeast();
	}

}
