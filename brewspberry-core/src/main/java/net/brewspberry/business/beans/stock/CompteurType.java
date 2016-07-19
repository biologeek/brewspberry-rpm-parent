package net.brewspberry.business.beans.stock;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CompteurType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9182613664605339295L;
	/**
	 * Represents a stock counter type. Example : 75cl bottles, 33cl bottles, 25kg Malt bag 
	 */
	@Id@GeneratedValue(strategy=GenerationType.AUTO)	
	private int cty_id;
	private String cty_libelle;
	private Date cty_date_cre;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cpt_counter_type")
	private List<StockCounter> cty_counters;
	
	public CompteurType() {
		super();
	}

	public CompteurType(int cty_id, String cty_libelle){
		this.cty_id = cty_id;
		this.cty_libelle = cty_libelle;
	}

	public int getCty_id() {
		return cty_id;
	}

	public void setCty_id(int cty_id) {
		this.cty_id = cty_id;
	}

	public String getCty_libelle() {
		return cty_libelle;
	}

	public void setCty_libelle(String cty_libelle) {
		this.cty_libelle = cty_libelle;
	}

	public Date getCty_date_cre() {
		return cty_date_cre;
	}

	public void setCty_date_cre(Date cty_date_cre) {
		this.cty_date_cre = cty_date_cre;
	}

}
