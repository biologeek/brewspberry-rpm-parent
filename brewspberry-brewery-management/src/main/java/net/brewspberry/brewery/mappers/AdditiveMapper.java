package net.brewspberry.brewery.mappers;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Quantity;
import net.brewspberry.brewery.api.Unit;
import net.brewspberry.brewery.model.Additive;
import net.brewspberry.brewery.model.CustomQuantity;


@Component
public class AdditiveMapper extends AbstractIngredientMapper<Additive, net.brewspberry.brewery.api.Additive> {

	public Additive toModel(net.brewspberry.brewery.api.Additive dto) {
		if (dto == null)
			return null;

		Additive model = toModelAbstract(dto);
		model.setCode(dto.getCode());
		model.setFunction(dto.getFunction());
		model.setRecommendedQuantity(new CustomQuantity(dto.getRecommendedQuantity().getQuantity(),
				Unit.valueOf(dto.getRecommendedQuantity().getUnit())));

		return model;
	}

	@Override
	protected Additive initModel() {
		return new Additive();
	}

	@Override
	protected net.brewspberry.brewery.api.Additive initDto() {
		return new net.brewspberry.brewery.api.Additive();
	}

	@Override
	public net.brewspberry.brewery.api.Additive toDto(Additive model) {
		if (model == null)
			return null;

		net.brewspberry.brewery.api.Additive dto = toDtoAbstract(model);
		dto.setCode(model.getCode());
		dto.setFunction(model.getFunction());
		dto.setRecommendedQuantity(new Quantity(model.getRecommendedQuantity().getQuantity(),
				model.getRecommendedQuantity().getUnit().toString()));

		return dto;
	}

}
