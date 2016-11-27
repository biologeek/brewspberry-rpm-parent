package net.brewspberry.main.business.beans.stock;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


public enum CounterTypeConstants implements Serializable {


	
	STOCK_DISPO_FAB(0, "Stock disponible a la fabrication"),
	STOCK_DLC_DEPASSEE(1, "Stock DLC depassee"),
	STOCK_RESERVE_FAB(2, "Stock reserve fabrication"),
	STOCK_DISPO_VENTE(3, "Stock dispo vente"),
	STOCK_RESERVE_CC(4, "Stock reserve CC"), 
	STOCK_DEM_CASSE(5, "Demarque casse"),
	STOCK_DEM_QUALITE(6, "Demarque qualite"),
	STOCK_EN_FAB(7, "Stock en cours de fabrication"),
	STOCK_BLOQUE_VENTE(8, "Stock bloque à la vente"),
	NONE(99, "Autre")
	;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4512540652466628210L;
	/**
	 * Represents a stock counter type. Example : fabrication available stock
	 */
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int cty_id;
	private String cty_libelle;
	private Date cty_date_cre;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cpt_counter_type")
	private List<StockCounter> cty_counters;
	

	CounterTypeConstants() {
	}

	CounterTypeConstants(int cty_id, String cty_libelle){
		this.cty_id = cty_id;
		this.cty_libelle = cty_libelle;
		this.setCty_date_cre(new Date());
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

	
	public CounterType toDBCouter(List<CounterType> list){
		
		CounterType resIfNone = new CounterType();
		resIfNone.setCty_date_cre(new Date());
		resIfNone.setCty_id(99);
		resIfNone.setCty_libelle("Autre");
		
		for (CounterType elt : list){
			
			if (elt.getCty_libelle().equals(this.getCty_libelle()) || elt.getCty_id() == this.getCty_id()){
				return elt;
			}
			
		}
		return resIfNone;
		
	}
}
