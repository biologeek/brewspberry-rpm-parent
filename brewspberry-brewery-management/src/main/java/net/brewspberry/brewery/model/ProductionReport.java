package net.brewspberry.brewery.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * A production report is the qualitative and quantitative result of a brewing
 * session. <br>
 * <br>
 * It is composed of a single product as a result of one session (only 1 beer
 * can be brewed at a time).
 * 
 * @author xavier
 *
 */
@Entity
public class ProductionReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Product product;
	@Embedded
	private CustomQuantity quantity;
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public CustomQuantity getQuantity() {
		return quantity;
	}

	public void setQuantity(CustomQuantity quantity) {
		this.quantity = quantity;
	}

}
