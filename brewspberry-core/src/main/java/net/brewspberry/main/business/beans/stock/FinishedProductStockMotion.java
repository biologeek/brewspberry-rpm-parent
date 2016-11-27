package net.brewspberry.main.business.beans.stock;

import net.brewspberry.main.business.beans.AbstractFinishedProduct;

public class FinishedProductStockMotion extends AbstractStockMotion {

	
	
	private AbstractFinishedProduct stf_product;

	public FinishedProductStockMotion() {
		super();
		
	}

	public AbstractFinishedProduct getStf_product() {
		return stf_product;
	}

	public void setStf_product(AbstractFinishedProduct stf_product) {
		this.stf_product = stf_product;
	}
	
}
