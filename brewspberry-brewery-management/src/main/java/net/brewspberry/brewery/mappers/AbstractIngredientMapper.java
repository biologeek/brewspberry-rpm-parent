package net.brewspberry.brewery.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import net.brewspberry.brewery.api.Quantity;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.CustomQuantity;

public abstract class AbstractIngredientMapper<T extends AbstractIngredient, U extends net.brewspberry.brewery.api.AbstractIngredient> {

	protected U toDtoAbstract(T model) {
		Assert.notNull(model, "null ingredient");
		U api = initDto();
		api.setId(model.getId());
		api.setBrand(model.getBrand());
		api.setModel(model.getModel());
		if (model.getPackaging() != null)
			api.setQuantity(new Quantity(model.getPackaging().getQuantity(), model.getPackaging().getUnit().name()));
		return api;
	}

	public List<T> toModel(List<U> additives) {
		if (additives == null || additives.isEmpty())
			return new ArrayList<>();
		return additives.stream().map(this::toModel).collect(Collectors.toList());
	}

	public List<U> toDto(List<T> additives) {
		if (additives == null || additives.isEmpty())
			return new ArrayList<>();
		return additives.stream().map(this::toDto).collect(Collectors.toList());
	}

	protected T toModelAbstract(U model) {
		Assert.notNull(model, "null ingredient");
		T api = initModel();
		api.setId(model.getId());
		api.setBrand(model.getBrand());
		api.setModel(model.getModel());
		if (model.getQuantity() != null)
			api.setPackaging(new CustomQuantity(model.getQuantity().getQuantity(), model.getQuantity().getUnit()));
		return api;
	}

	protected abstract T initModel();

	protected abstract U initDto();

	public abstract T toModel(U dto);

	public abstract U toDto(T model);

}
