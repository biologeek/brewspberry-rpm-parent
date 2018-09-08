package net.brewsbpberry.brewery.model;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents the association of an ingredient, its addition time, method,
 * quantity, ...
 *
 */
@Entity
public class StepIngredient {

	@ManyToOne
	@JoinColumn(name = "ing_id")
	private AbstractIngredient ingredient;

	private Date additionTime;

	@Embedded
	private Quantity quantity;

	public AbstractIngredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(AbstractIngredient ingredient) {
		this.ingredient = ingredient;
	}

	public Date getAdditionTime() {
		return additionTime;
	}

	public void setAdditionTime(Date additionTime) {
		this.additionTime = additionTime;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

}
