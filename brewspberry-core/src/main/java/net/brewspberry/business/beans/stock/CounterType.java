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
public enum CounterType implements Serializable {


	
	STOCK_DISPO_FAB(1, "Stock disponible à la fabrication"),
	STOCK_DLC_DEPASSEE(2, "Stock DLC depassee"),
	STOCK_RESERVE_FAB(3, "Stock reserve fabrication"),
	STOCK_DISPO_VENTE(4, "Stock dispo vente"),
	STOCK_RESERVE_CC(5, "Stock reserve CC"), 
	STOCK_DEM_CASSE(6, "Demarque casse"),
	STOCK_EN_FAB(8, "Stock en cours de fabrication"),
	STOCK_BLOQUE_VENTE(9, "Stock bloqué à la vente"),
	NONE(99, "none"),
	STOCK_DEM_QUALITE(7, "Demarque qualite");
	
	/**
	 * Represents a stock counter type. Example : fabrication available stock
	 */
	@Id@GeneratedValue(strategy=GenerationType.AUTO)	
	private int cty_id;
	private String cty_libelle;
	private Date cty_date_cre;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cpt_counter_type")
	private List<StockCounter> cty_counters;
	

	CounterType(int cty_id, String cty_libelle){
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

}
