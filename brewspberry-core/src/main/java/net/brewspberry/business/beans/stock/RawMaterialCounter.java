package net.brewspberry.business.beans.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.brewspberry.business.beans.AbstractIngredient;


@Entity
@DiscriminatorValue("raw")
public class RawMaterialCounter extends StockCounter {

	/**
	 * Counter used for Raw material (malt, hop, yeast)
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cpt_stb_id")
	private AbstractIngredient cpt_product;
	
	
	public RawMaterialCounter() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AbstractIngredient getCpt_product() {
		return cpt_product;
	}


	public void setCpt_product(AbstractIngredient cpt_product) {
		this.cpt_product = cpt_product;
	}

	
}
