package net.brewspberry.business.beans.stock;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;



@Entity
@DiscriminatorValue("finished")
public class FinishedProductCounter extends StockCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6599625486711705779L;

	/**
	 * Counter used for finished product (bottled beer, fermenting beer, washed malt and eventually dry yeast) 
	 */
	
	public FinishedProductCounter() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
