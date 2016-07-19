package net.brewspberry.business.beans.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;



@Entity
@DiscriminatorValue("finished")
public class FinishedProductCounter extends StockCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6599625486711705779L;

	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cpt_stb_id")
	private AbstractFinishedProduct cpt_product;
	/**
	 * Counter used for finished product (bottled beer, fermenting beer, washed malt and eventually dry yeast) 
	 */
	
	public FinishedProductCounter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AbstractFinishedProduct getCpt_product() {
		return cpt_product;
	}
	public void setCpt_product(AbstractFinishedProduct cpt_product) {
		this.cpt_product = cpt_product;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
