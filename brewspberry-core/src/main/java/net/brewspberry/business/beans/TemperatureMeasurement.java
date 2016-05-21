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

@Entity
public class TemperatureMeasurement implements Serializable,
		Comparable<TemperatureMeasurement> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -490697135984017295L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tmes_id;
	private Date tmes_date;
	private String tmes_probeUI;
	private String tmes_probe_name;
	private Float tmes_value;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Etape.class)
	@JoinColumn(name = "tmes_etape_id")
	private Etape tmes_etape;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Brassin.class)
	@JoinColumn(name = "tmes_bra_id")
	private Brassin tmes_brassin;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Actioner.class)
	@JoinColumn(name = "tmes_act_id")
	private Actioner tmes_actioner;

	public TemperatureMeasurement() {
		super();
	}

	public Long getTmes_id() {
		return tmes_id;
	}

	public void setTmes_id(Long tmes_id) {
		this.tmes_id = tmes_id;
	}

	public Brassin getTmes_brassin() {
		return tmes_brassin;
	}

	public void setTmes_brassin(Brassin tmes_brassin) {
		this.tmes_brassin = tmes_brassin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getTmes_date() {
		return tmes_date;
	}

	public void setTmes_date(Date tmes_date) {
		this.tmes_date = tmes_date;
	}

	public String getTmes_probeUI() {
		return tmes_probeUI;
	}

	public void setTmes_probeUI(String tmes_probeUI) {
		this.tmes_probeUI = tmes_probeUI;
	}

	public String getTmes_probe_name() {
		return tmes_probe_name;
	}

	public void setTmes_probe_name(String tmes_probe_name) {
		this.tmes_probe_name = tmes_probe_name;
	}

	public Float getTmes_value() {
		return tmes_value;
	}

	public void setTmes_value(Float tmes_value) {
		this.tmes_value = tmes_value;
	}

	public Etape getTmes_etape() {
		return tmes_etape;
	}

	public void setTmes_etape(Etape tmes_etape) {
		this.tmes_etape = tmes_etape;
	}

	@Override
	public String toString() {
		return "TemperatureMeasurement [tmes_id=" + tmes_id + ", tmes_date="
				+ tmes_date + ", tmes_probeUI=" + tmes_probeUI
				+ ", tmes_probe_name=" + tmes_probe_name + ", tmes_value="
				+ tmes_value + ", tmes_etape=" + tmes_etape + "]";
	}

	public Actioner getTmes_actioner() {
		return tmes_actioner;
	}

	public void setTmes_actioner(Actioner tmes_actioner) {
		this.tmes_actioner = tmes_actioner;
	}

	@Override
	public int compareTo(TemperatureMeasurement o) {

		if (o.getTmes_date().after(this.getTmes_date())) {
			return 1;
		} else if (o.getTmes_date().before(this.getTmes_date())) {

			return -1;
		} else {

			return 0;
		}
	}

}