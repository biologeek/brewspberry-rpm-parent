package net.brewspberry.business.beans.stock;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="cpt_discriminator")
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
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private long cpt_id;
	
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cpt_cty_id")
	private CounterType cpt_counter_type;
	
	private double cpt_value;
//	@ManyToOne(fetch=FetchType.EAGER)
	//@JoinColumn(name="cpt_stu_id")
	@Enumerated
	@Column(name="cpt_stu_id")
	private StockUnit cpt_unit;
	private Date cpt_date_cre;
	private Date cpt_date_maj;
	

	public StockCounter() {
		super();
		
	}

	public long getCpt_id() {
		return cpt_id;
	}

	public void setCpt_id(long cpt_id) {
		this.cpt_id = cpt_id;
	}

	public CounterType getCpt_counter_type() {
		return cpt_counter_type;
	}

	public void setCpt_counter_type(CounterType cpt_counter_type) {
		this.cpt_counter_type = cpt_counter_type;
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