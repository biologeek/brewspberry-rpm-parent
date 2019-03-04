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
public class QuantifiedIngredient {

	@ManyToOne
	@JoinColumn(name = "ing_id")
	private AbstractIngredient ingredient;

	/**
	 * The date ingredient was added
	 */
	private Date additionTime;

	@Embedded
	private CustomQuantity quantity;
	
	@ManyToOne
	private Step step;

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

	public CustomQuantity getQuantity() {
		return quantity;
	}

	public void setQuantity(CustomQuantity quantity) {
		this.quantity = quantity;
	}

}
