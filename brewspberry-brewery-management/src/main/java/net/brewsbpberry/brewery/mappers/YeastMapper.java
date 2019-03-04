package net.brewsbpberry.brewery.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.Yeast;

@Component
public class YeastMapper extends AbstractIngredientMapper<net.brewsbpberry.brewery.model.Yeast, Yeast>{

	public List<Yeast> toDto(List<net.brewsbpberry.brewery.model.Yeast> yeasts) {
		return null;
	}
	
	public Yeast toDto(net.brewsbpberry.brewery.model.Yeast model) {
		Yeast dto = this.toDtoAbstract(model);
		return dto;
	}

	@Override
	protected Yeast initDto() {
		return new Yeast();
	}

}
