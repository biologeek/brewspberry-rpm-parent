package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ManyToAny;
import org.springframework.stereotype.Component;




@Entity
@Component
public class Brassin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1291264930519466257L;
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private long bra_id;
	private String bra_nom;
    private Date bra_debut;
    private Date bra_fin;
    private Date bra_date_maj;
    private Double bra_quantiteEnLitres;
    @Enumerated(EnumType.STRING)
    private BrewStatus bra_statut;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="malt_brassin")
    private List<Malt> bra_malts;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="hbl_brassin")
    private List<Houblon> bra_houblons;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="lev_brassin")
    private List<Levure> bra_levures;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="etp_brassin")
    List<Etape> bra_etapes;
    
	@OneToOne(mappedBy="beer_brassin")
    private Biere bra_beer;
    
	/**
	 * What is it ????
	 */
    private String bra_type;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="tmes_brassin")
    private List<ConcreteTemperatureMeasurement> bra_temperature_measurement;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="act_brassin")
    private List<Actioner> bra_actioner;
    
    
	public Brassin() {
		super();
		
	}


	public Biere getBra_Beer() {
		return bra_beer;
	}


	public void setBra_Beer(Biere beer) {
		this.bra_beer = beer;
	}


	public Long getBra_id() {
		return bra_id;
	}


	public void setBra_id(Long bra_id) {
		this.bra_id = bra_id;
	}


	public Date getBra_debut() {
		return bra_debut;
	}


	public void setBra_debut(Date bra_debut) {
		this.bra_debut = bra_debut;
	}


	public Date getBra_fin() {
		return bra_fin;
	}


	public void setBra_fin(Date bra_fin) {
		this.bra_fin = bra_fin;
	}


	public Double getBra_quantiteEnLitres() {
		return bra_quantiteEnLitres;
	}


	public void setBra_quantiteEnLitres(Double bra_quantiteEnLitres) {
		this.bra_quantiteEnLitres = bra_quantiteEnLitres;
	}


	public Date getBra_date_maj() {
		return bra_date_maj;
	}


	public void setBra_date_maj(Date bra_date_maj) {
		this.bra_date_maj = bra_date_maj;
	}


	public BrewStatus getBra_statut() {
		return bra_statut;
	}


	public void setBra_statut(BrewStatus bra_statut) {
		this.bra_statut = bra_statut;
	}


	public List<Malt> getBra_malts() {
		return bra_malts;
	}


	public void setBra_malts(List<Malt> bra_malts) {
		this.bra_malts = bra_malts;
	}


	public List<Houblon> getBra_houblons() {
		return bra_houblons;
	}


	public void setBra_houblons(List<Houblon> bra_houblons) {
		this.bra_houblons = bra_houblons;
	}


	public List<Levure> getBra_levures() {
		return bra_levures;
	}


	public void setBra_levures(List<Levure> bra_levures) {
		this.bra_levures = bra_levures;
	}


	public Biere getBra_beer() {
		return bra_beer;
	}


	public void setBra_beer(Biere bra_beer) {
		this.bra_beer = bra_beer;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getBra_nom() {
		return bra_nom;
	}


	public void setBra_nom(String bra_nom) {
		this.bra_nom = bra_nom;
	}


	public String getBra_type() {
		return bra_type;
	}


	public void setBra_type(String bra_type) {
		this.bra_type = bra_type;
	}


	public void setBra_id(long bra_id) {
		this.bra_id = bra_id;
	}


	public List<Etape> getBra_etapes() {
		return bra_etapes;
	}


	public void setBra_etapes(List<Etape> bra_etapes) {
		this.bra_etapes = bra_etapes;
	}


	public List<ConcreteTemperatureMeasurement> getBra_temperature_measurement() {
		return bra_temperature_measurement;
	}


	public void setBra_temperature_measurement(
			List<ConcreteTemperatureMeasurement> bra_temperature_measurement) {
		this.bra_temperature_measurement = bra_temperature_measurement;
	}


	@Override
	public String toString() {
		return "Brassin [bra_id=" + bra_id + ", bra_nom=" + bra_nom
				+ ", bra_debut=" + bra_debut + ", bra_fin=" + bra_fin
				+ ", bra_date_maj=" + bra_date_maj + ", bra_quantiteEnLitres="
				+ bra_quantiteEnLitres + ", bra_statut=" + bra_statut
				+ ", bra_malts=" + bra_malts + ", bra_houblons=" + bra_houblons
				+ ", bra_levures=" + bra_levures + ", bra_etapes=" + bra_etapes
				+ ", bra_beer=" + bra_beer + ", bra_type=" + bra_type
				+ ", bra_temperature_measurement="
				+ bra_temperature_measurement + ", bra_actioner="
				+ bra_actioner + "]";
	}


	

}