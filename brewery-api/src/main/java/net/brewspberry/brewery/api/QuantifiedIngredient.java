package net.brewspberry.brewery.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.brewspberry.brewery.api.converters.LocalDateTimeToLongConverter;
import net.brewspberry.brewery.api.converters.LongToLocalDateTimeConverter;

public class QuantifiedIngredient {

	private Long ingredient;
	private Quantity quantityAdded;
	@JsonDeserialize(converter = LongToLocalDateTimeConverter.class)
	@JsonSerialize(converter = LocalDateTimeToLongConverter.class)
	private LocalDateTime additionTime;

	public Long getIngredient() {
		return ingredient;
	}

	public void setIngredient(Long ingredient) {
		this.ingredient = ingredient;
	}

	public Quantity getQuantityAdded() {
		return quantityAdded;
	}

	public void setQuantityAdded(Quantity quantityAdded) {
		this.quantityAdded = quantityAdded;
	}

	public LocalDateTime getAdditionTime() {
		return additionTime;
	}

	public void setAdditionTime(LocalDateTime additionTime) {
		this.additionTime = additionTime;
	}

}
