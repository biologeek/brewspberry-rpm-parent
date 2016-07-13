package net.brewspberry.business.beans;

import net.brewspberry.business.beans.stock.StockCounter;


/**
 * Object used to display stock counters. Adds stock value in money
 *
 */
public class TableDisplayStockCounter extends StockCounter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9124101770149947344L;

	
	private double std_total_stock_value;

	

	public TableDisplayStockCounter() {
		super();
		// TODO Auto-generated constructor stub
	}



	public TableDisplayStockCounter(double std_stock_value) {
		super();
		this.std_total_stock_value = std_stock_value;
	}


	public double getStd_stock_value() {
		return std_total_stock_value;
	}


	public void setStd_stock_value(double std_stock_value) {
		this.std_total_stock_value = std_stock_value;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
