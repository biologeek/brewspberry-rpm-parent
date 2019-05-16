package net.brewspberry.brewery.mappers;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.model.Spice;


@Component
public class SpiceMapper extends AbstractIngredientMapper<Spice, net.brewspberry.brewery.api.Spice> {

	public Spice toModel(net.brewspberry.brewery.api.Spice dto) {
		if (dto == null)
			return null;

		Spice model = toModelAbstract(dto);
		return model;
	}

	@Override
	protected Spice initModel() {
		return new Spice();
	}

	@Override
	protected net.brewspberry.brewery.api.Spice initDto() {
		return new net.brewspberry.brewery.api.Spice();
	}

	@Override
	public net.brewspberry.brewery.api.Spice toDto(Spice model) {
		if (model == null)
			return null;

		net.brewspberry.brewery.api.Spice dto = toDtoAbstract(model);
		return dto;
	}

}
