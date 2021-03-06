package net.brewspberry.main.business.beans.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.brewspberry.main.business.beans.brewing.AbstractIngredient;


@Entity
@DiscriminatorValue("raw")
public class RawMaterialCounter extends StockCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5798990988485371240L;
	/**
	 * Counter used for Raw material (malt, hop, yeast)
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cpt_stb_id")
	private AbstractIngredient cpt_product;
	
	
	public RawMaterialCounter() {
		super();
		
	}


	public AbstractIngredient getCpt_product() {
		return cpt_product;
	}


	public void setCpt_product(AbstractIngredient cpt_product) {
		this.cpt_product = cpt_product;
	}

	
}
