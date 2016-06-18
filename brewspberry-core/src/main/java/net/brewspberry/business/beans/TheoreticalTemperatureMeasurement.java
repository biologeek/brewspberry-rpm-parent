package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Component;

@Component
public class TheoreticalTemperatureMeasurement extends TemperatureMeasurement implements Serializable,
		Comparable<TheoreticalTemperatureMeasurement> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -490697135984017295L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long thmes_id;
	private Date thmes_date;
	private Float thmes_value;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Etape.class)
	@JoinColumn(name = "thmes_etape_id")
	private Etape thmes_etape;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Brassin.class)
	@JoinColumn(name = "thmes_bra_id")
	private Brassin thmes_brassin;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Actioner.class)
	@JoinColumn(name = "thmes_act_id")
	private Actioner thmes_actioner;

	public TheoreticalTemperatureMeasurement() {
		super();
	}

	public Long getThmes_id() {
		return thmes_id;
	}

	public void setThmes_id(Long thmes_id) {
		this.thmes_id = thmes_id;
	}

	public Brassin getThmes_brassin() {
		return thmes_brassin;
	}

	public void setThmes_brassin(Brassin thmes_brassin) {
		this.thmes_brassin = thmes_brassin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getThmes_date() {
		return thmes_date;
	}

	public void setThmes_date(Date thmes_date) {
		this.thmes_date = thmes_date;
	}

	public Float getThmes_value() {
		return thmes_value;
	}

	public void setThmes_value(Float thmes_value) {
		this.thmes_value = thmes_value;
	}

	public Etape getThmes_etape() {
		return thmes_etape;
	}

	public void setThmes_etape(Etape thmes_etape) {
		this.thmes_etape = thmes_etape;
	}

	@Override
	public String toString() {
		return "TemperatureMeasurement [thmes_id=" + thmes_id + ", thmes_date="
				+ thmes_date + ", thmes_value="
				+ thmes_value + ", thmes_etape=" + thmes_etape + "]";
	}

	public Actioner getThmes_actioner() {
		return thmes_actioner;
	}

	public void setThmes_actioner(Actioner thmes_actioner) {
		this.thmes_actioner = thmes_actioner;
	}

	@Override
	public int compareTo(TheoreticalTemperatureMeasurement o) {

		if (o.getThmes_date().after(this.getThmes_date())) {
			return 1;
		} else if (o.getThmes_date().before(this.getThmes_date())) {

			return -1;
		} else {

			return 0;
		}
	}

}