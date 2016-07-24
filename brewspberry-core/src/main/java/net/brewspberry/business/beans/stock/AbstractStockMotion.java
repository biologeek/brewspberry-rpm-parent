package net.brewspberry.business.beans.stock;

import java.util.Date;

public class AbstractStockMotion {

	
	private double stm_value;
	private StockUnit stm_unit;
	private CounterType stm_counter_from;
	private CounterType stm_counter_to;
	private Date stm_motion_date;
	
	
	
	public AbstractStockMotion() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getStm_value() {
		return stm_value;
	}
	public void setStm_value(double stm_value) {
		this.stm_value = stm_value;
	}
	public StockUnit getStm_unit() {
		return stm_unit;
	}
	public void setStm_unit(StockUnit stm_unit) {
		this.stm_unit = stm_unit;
	}
	public CounterType getStm_counter_from() {
		return stm_counter_from;
	}
	public void setStm_counter_from(CounterType stm_counter_from) {
		this.stm_counter_from = stm_counter_from;
	}
	public CounterType getStm_counter_to() {
		return stm_counter_to;
	}
	public void setStm_counter_to(CounterType stm_counter_to) {
		this.stm_counter_to = stm_counter_to;
	}
	public Date getStm_motion_date() {
		return stm_motion_date;
	}
	public void setStm_motion_date(Date stm_motion_date) {
		this.stm_motion_date = stm_motion_date;
	}


	
	
}
