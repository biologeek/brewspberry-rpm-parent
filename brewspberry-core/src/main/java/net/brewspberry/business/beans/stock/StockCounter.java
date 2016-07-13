package net.brewspberry.business.beans.stock;

import java.io.Serializable;
import java.util.Date;

public class StockCounter implements Serializable {

	/**
	 * A StockCounter is an element of stock. It is determined by the type of
	 * counter, the product concerned by the stock.
	 * 
	 * Value represents de quantity of product for CounterType.
	 * 
	 * Example : you have half the 30L production that is already bottled in 5L
	 * keg and half in 33cL bottles. Thus you'll have 2 counters.
	 * 
	 */
	private static final long serialVersionUID = -3853891928120391252L;
	private long cpt_id;
	private CompteurType cpt_counter_type;
	private Stockable cpt_product;
	private double cpt_value;
	private StockUnit cpt_unit;
	private Date cpt_date_cre;
	private Date cpt_date_maj;

	public StockCounter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getCpt_id() {
		return cpt_id;
	}

	public void setCpt_id(long cpt_id) {
		this.cpt_id = cpt_id;
	}

	public CompteurType getCpt_counter_type() {
		return cpt_counter_type;
	}

	public void setCpt_counter_type(CompteurType cpt_counter_type) {
		this.cpt_counter_type = cpt_counter_type;
	}

	public Stockable getCpt_product() {
		return cpt_product;
	}

	public void setCpt_product(Stockable cpt_product) {
		this.cpt_product = cpt_product;
	}

	public double getCpt_value() {
		return cpt_value;
	}

	public void setCpt_value(double cpt_value) {
		this.cpt_value = cpt_value;
	}

	public Date getCpt_date_cre() {
		return cpt_date_cre;
	}

	public void setCpt_date_cre(Date cpt_date_cre) {
		this.cpt_date_cre = cpt_date_cre;
	}

	public Date getCpt_date_maj() {
		return cpt_date_maj;
	}

	public void setCpt_date_maj(Date cpt_date_maj) {
		this.cpt_date_maj = cpt_date_maj;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public StockUnit getCpt_unit() {
		return cpt_unit;
	}

	public void setCpt_unit(StockUnit cpt_unit) {
		this.cpt_unit = cpt_unit;
	}

}