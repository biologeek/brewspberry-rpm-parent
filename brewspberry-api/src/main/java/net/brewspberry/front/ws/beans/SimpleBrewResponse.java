package net.brewspberry.front.ws.beans;

import java.util.Date;

import net.brewspberry.business.beans.EtapeType;

public class SimpleBrewResponse {
	
	
	
	private long id;
	private String description;
	private Date debut;
	private Date fin;
	private Date maj;
	private float quantite;
	private EtapeType statut;
	private String type;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDebut() {
		return debut;
	}
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public Date getMaj() {
		return maj;
	}
	public void setMaj(Date maj) {
		this.maj = maj;
	}
	public float getQuantite() {
		return quantite;
	}
	public void setQuantite(float quantite) {
		this.quantite = quantite;
	}
	public EtapeType getStatut() {
		return statut;
	}
	public void setStatut(EtapeType statut) {
		this.statut = statut;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
