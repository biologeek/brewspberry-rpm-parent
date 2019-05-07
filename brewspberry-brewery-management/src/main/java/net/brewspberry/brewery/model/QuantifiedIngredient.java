package net.brewspberry.brewery.model;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Represents the association of an ingredient, its addition time, method,
 * quantity, ...
 *
 */
@Entity
public class QuantifiedIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

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
