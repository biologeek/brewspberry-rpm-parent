package net.brewspberry.business.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


public enum EtapeType {
	
	CONCASSAGE(10, "Concassage", true, false),
	PALIER(20, "Brassage", true, false),
	FILTRATION(30, "Filtration", false, false),
	EBULLITION(40, "ebullition", true, false),
	WHIRLPOOL(50, "whirlpool", false, false),
	REFROIDISSEMENT(60, "refroidissement", false, false),
	FERMENTATION(70, "fermentation", false, false),
	GARDE(80, "garde", true, true),
	EMBOUTEILLAGE(90, "embouteillage", true, true),
	REFERMENTATION(100, "embouteillage", false, true);
	
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int ety_id;
	private String ety_libelle;
	private boolean ety_canIngredientsBeAdded;
	private boolean ety_isProductManipulated;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="etp_etape_type")
	private List<Etape> ety_etapes;

	private EtapeType(int ety_id, String ety_libelle, boolean ety_canIngredientsBeAdded,
			boolean ety_isProductManipulated) {
		this.ety_id = ety_id;
		this.ety_libelle = ety_libelle;
		this.ety_canIngredientsBeAdded = ety_canIngredientsBeAdded;
		this.ety_isProductManipulated = ety_isProductManipulated;
	}
	
	public int getEty_id() {
		return ety_id;
	}
	public void setEty_id(int ety_id) {
		this.ety_id = ety_id;
	}
	public String getEty_libelle() {
		return ety_libelle;
	}
	public void setEty_libelle(String ety_libelle) {
		this.ety_libelle = ety_libelle;
	}
	public boolean isEty_canIngredientsBeAdded() {
		return ety_canIngredientsBeAdded;
	}
	public void setEty_canIngredientsBeAdded(boolean ety_canIngredientsBeAdded) {
		this.ety_canIngredientsBeAdded = ety_canIngredientsBeAdded;
	}
	public boolean isEty_isProductManipulated() {
		return ety_isProductManipulated;
	}
	public void setEty_isProductManipulated(boolean ety_isProductManipulated) {
		this.ety_isProductManipulated = ety_isProductManipulated;
	}
	

}
