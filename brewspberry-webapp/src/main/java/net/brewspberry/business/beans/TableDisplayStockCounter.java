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


	public TableDisplayStockCounter(StockCounter arg0) {

		this.setCpt_counter_type(arg0.getCpt_counter_type());
		this.setCpt_date_cre(arg0.getCpt_date_cre());
		this.setCpt_date_maj(arg0.getCpt_date_maj());
		this.setCpt_id(arg0.getCpt_id());
		this.setCpt_unit(arg0.getCpt_unit());
		this.setCpt_value(arg0.getCpt_value());
		
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
