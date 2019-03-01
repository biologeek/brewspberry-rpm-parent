package net.brewsbpberry.brewery.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Hop;

@Component
public class HopMapper extends AbstractIngredientMapper<net.brewsbpberry.brewery.model.Hop, Hop> {

	public List<Hop> toDto(List<net.brewsbpberry.brewery.model.Hop> ingredients) {
		return null;
	}
	
	public Hop toDto(net.brewsbpberry.brewery.model.Hop ingredient) {
		Hop result = toDtoAbstract(ingredient);
		return result;
	}

	@Override
	protected Hop initDto() {
		return new Hop();
	}


}
