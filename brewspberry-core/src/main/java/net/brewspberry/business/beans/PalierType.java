package net.brewspberry.business.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public enum PalierType {
	
	PROTEINIQUE (1, 45, 55, 50, "Palier proteinique"),
	SACCHARIFICATION(2, 55, 65, 64, "Saccharification"), 
	ALE(3, 68, 70, 69, "Monopalier Ale"), 
	DEXTRINES(4, 68, 72, 72, "Dextrines"), 
	EBULLITION(5, 96, 100, 98, "Ebullition"), 
	MASH_OUT(6, 78, 80, 78, "Mash out");

	
	private int plt_temperature_min;
	private int plt_temperature_max;
	private int plt_temperature;
	private String plt_libelle;
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private int plt_id;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="etp_palier_type")
	List<Etape> plt_etapes;

	PalierType(int plt_id, int plt_temperature_min, int plt_temperature_max, int temperature, String libelle){
		
		this.plt_temperature = temperature;
		this.plt_libelle = libelle;
		this.plt_id = plt_id;
		this.plt_temperature_min = plt_temperature_min;
		this.plt_temperature_max = plt_temperature_max;
		
	}

	public int getPlt_temperature() {
		return plt_temperature;
	}

	public void setPlt_temperature(int plt_temperature) {
		this.plt_temperature = plt_temperature;
	}

	public String getPlt_libelle() {
		return plt_libelle;
	}

	public void setPlt_libelle(String plt_libelle) {
		this.plt_libelle = plt_libelle;
	}

	public int getPlt_temperature_min() {
		return plt_temperature_min;
	}

	public void setPlt_temperature_min(int plt_temperature_min) {
		this.plt_temperature_min = plt_temperature_min;
	}

	public int getPlt_temperature_max() {
		return plt_temperature_max;
	}

	public void setPlt_temperature_max(int plt_temperature_max) {
		this.plt_temperature_max = plt_temperature_max;
	}

	public int getPlt_id() {
		return plt_id;
	}

	public void setPlt_id(int plt_id) {
		this.plt_id = plt_id;
	}

	public List<Etape> getPlt_etapes() {
		return plt_etapes;
	}

	public void setPlt_etapes(List<Etape> plt_etapes) {
		this.plt_etapes = plt_etapes;
	}
	

}
