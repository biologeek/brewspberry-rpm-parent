package net.brewspberry.business.beans;

import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.StockCounter;

public class TableToDisplayFinishedProductCounter extends TableDisplayStockCounter {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6800023056808862194L;
	private AbstractFinishedProduct stf_product;

	public TableToDisplayFinishedProductCounter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TableToDisplayFinishedProductCounter(FinishedProductCounter arg0) {
		super(arg0);
		this.setStf_product(arg0.getCpt_product());
		
	}

	public AbstractFinishedProduct getStf_product() {
		return stf_product;
	}

	public void setStf_product(AbstractFinishedProduct stf_product) {
		this.stf_product = stf_product;
	}
	
	

}
