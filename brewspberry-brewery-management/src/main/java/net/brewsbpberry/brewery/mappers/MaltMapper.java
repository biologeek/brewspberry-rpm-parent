package net.brewsbpberry.brewery.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Malt;

@Component
public class MaltMapper extends AbstractIngredientMapper<net.brewsbpberry.brewery.model.Malt, Malt>{

	public List<Malt> toDto(List<net.brewsbpberry.brewery.model.Malt> malts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Malt initDto() {
		return new Malt();
	}

}
