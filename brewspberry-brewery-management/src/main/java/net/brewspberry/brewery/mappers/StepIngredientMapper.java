package net.brewspberry.brewery.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import net.brewspberry.brewery.api.QuantifiedIngredient;
import net.brewspberry.brewery.api.Quantity;
import net.brewspberry.brewery.model.CustomQuantity;

@Component
public class StepIngredientMapper {

	public List<QuantifiedIngredient> toDto(List<net.brewspberry.brewery.model.QuantifiedIngredient> model) {
		return model.stream().map(this::toDto).collect(Collectors.toList());
	}

	public QuantifiedIngredient toDto(net.brewspberry.brewery.model.QuantifiedIngredient model) {
		QuantifiedIngredient res = new QuantifiedIngredient();

		res.setAdditionTime(model.getAdditionTime());
		res.setIngredient(model.getIngredient());
		res.setQuantityAdded(new Quantity(model.getQuantity().getQuantity(), model.getQuantity().getUnit().getSymbol()));

		return res;
	}

	public List<net.brewspberry.brewery.model.QuantifiedIngredient> toModel(List<QuantifiedIngredient> model) {
		return model.stream().map(this::toModel).collect(Collectors.toList());
	}

	public net.brewspberry.brewery.model.QuantifiedIngredient toModel(QuantifiedIngredient model) {
		net.brewspberry.brewery.model.QuantifiedIngredient res = new net.brewspberry.brewery.model.QuantifiedIngredient();

		res.setAdditionTime(model.getAdditionTime());
		res.setQuantity(
				new CustomQuantity(model.getQuantityAdded().getQuantity(), model.getQuantityAdded().getUnit()));
		
		res.setIngredient(model.getIngredient());
		return res;
	}
}
