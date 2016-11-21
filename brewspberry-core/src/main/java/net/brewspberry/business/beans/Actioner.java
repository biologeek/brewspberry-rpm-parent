package net.brewspberry.business.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

@Entity
@Component
public class Actioner {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long act_id;
	
	
	@ManyToOne
	@JoinColumn(name="act_generic_id")
	private GenericActionner act_generic;

	private Date act_date_debut;
	private Date act_date_fin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "act_bra_id")
	private Brassin act_brassin;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "act_etp_id")
	private Etape act_etape;
	private boolean act_used;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tmes_actioner")
	List<ConcreteTemperatureMeasurement> act_temperature_measurements;

	public Actioner() {
		super();
	}

	
	public Long getAct_id() {
		return act_id;
	}


	public void setAct_id(Long act_id) {
		this.act_id = act_id;
	}


	public Date getAct_date_debut() {
		return act_date_debut;
	}

	public void setAct_date_debut(Date act_date_debut) {
		this.act_date_debut = act_date_debut;
	}

	public Date getAct_date_fin() {
		return act_date_fin;
	}

	public void setAct_date_fin(Date act_date_fin) {
		this.act_date_fin = act_date_fin;
	}

	public List<ConcreteTemperatureMeasurement> getAct_temperature_measurements() {
		return act_temperature_measurements;
	}

	public void setAct_temperature_measurements(List<ConcreteTemperatureMeasurement> act_temperature_measurements) {
		this.act_temperature_measurements = act_temperature_measurements;
	}

	public Brassin getAct_brassin() {
		return act_brassin;
	}

	public void setAct_brassin(Brassin act_brassin) {
		this.act_brassin = act_brassin;
	}

	public Etape getAct_etape() {
		return act_etape;
	}

	public void setAct_etape(Etape act_etape) {
		this.act_etape = act_etape;
	}


	public GenericActionner getAct_generic() {
		return act_generic;
	}


	public void setAct_generic(GenericActionner act_generic) {
		this.act_generic = act_generic;
	}

	public boolean getAct_used() {
		return act_used;
	}

	public void setAct_used(boolean act_used) {
		this.act_used = act_used;
	}


	public Actioner id(Long id) {
		this.act_id = id;
		return this;
	}
}