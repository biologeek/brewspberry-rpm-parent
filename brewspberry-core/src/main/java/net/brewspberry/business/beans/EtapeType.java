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

@Entity
public class EtapeType {
	
	
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int ety_id;
	private String ety_libelle;
	private boolean ety_canIngredientsBeAdded;
	private boolean ety_isProductManipulated;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="etp_etape_type")
	private List<Etape> ety_etapes;
	
	public EtapeType() {
		super();
		// TODO Auto-generated constructor stub
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
