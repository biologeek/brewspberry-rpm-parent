package net.brewspberry.business.beans.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("raw")
public class RawMaterialCounter extends StockCounter {

	/**
	 * Counter used for Raw material (malt, hop, yeast)
	 */
	
	public RawMaterialCounter() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
