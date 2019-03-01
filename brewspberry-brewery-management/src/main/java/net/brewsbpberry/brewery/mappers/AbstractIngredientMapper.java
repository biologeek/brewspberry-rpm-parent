package net.brewsbpberry.brewery.mappers;

import org.springframework.util.Assert;

import net.brewsbpberry.brewery.model.AbstractIngredient;

public abstract class AbstractIngredientMapper<T extends AbstractIngredient, U extends net.brewspberry.brewery.api.AbstractIngredient> {

	protected U toDtoAbstract(T model) {
		Assert.notNull(model, "null ingredient");
		U api = initDto();
		api.setId(model.getId());
		api.setBrand(model.getBrand());
		api.setModel(model.getModel());
		return api;
	}

	protected abstract U initDto();

}
