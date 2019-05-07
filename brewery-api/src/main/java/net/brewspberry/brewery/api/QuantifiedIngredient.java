package net.brewspberry.brewery.api;

import java.time.LocalDateTime;

public class QuantifiedIngredient {

	private Long ingredient;
	private Quantity quantityAdded;
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
