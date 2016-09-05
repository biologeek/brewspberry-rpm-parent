package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Etape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1849499413089750093L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long etp_id;
	private Integer etp_numero; // step number in the brew
	private String etp_nom;
	private Date etp_debut;
	private Date etp_fin;
	private Date etp_creation_date;
	private Date etp_update_date;
	private Date etp_debut_reel;
	private Date etp_fin_reel;
	/*@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "etp_ety_id")
	/**
	 * etape type object contains tops to allow rules to apply or not for
	 * various algorithms Fetching it eagerly, and cascading modfications to
	 * linked EtapeType object
	 */
	
	//private EtapeType etp_etape_type;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "dur_step")
	private DurationBO etp_duree;

	private Double etp_temperature_theorique;
	private String etp_remarque;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "etp_bra_id")
	private Brassin etp_brassin;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tmes_etape")
	private List<ConcreteTemperatureMeasurement> etp_temperature_measurement;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "act_etape")
	private List<Actioner> etp_actioner;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "malt_etape", cascade = CascadeType.ALL)
	private List<Malt> etp_malts;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hbl_etape", cascade = CascadeType.ALL)
	private List<Houblon> etp_houblons;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lev_etape", cascade = CascadeType.ALL)
	private List<Levure> etp_levures;

	@Enumerated(EnumType.ORDINAL)
	PalierType etp_palier_type;

	@Transient
	private List<AbstractIngredient> etp_ingredients;

	public Etape() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getEtp_id() {
		return etp_id;
	}

	public void setEtp_id(Long etp_id) {
		this.etp_id = etp_id;
	}

	public Integer getEtp_numero() {
		return etp_numero;
	}

	public void setEtp_numero(Integer etp_numero) {
		this.etp_numero = etp_numero;
	}

	public Brassin getEtp_brassin() {
		return etp_brassin;
	}

	public void setEtp_brassin(Brassin etp_brassin) {
		this.etp_brassin = etp_brassin;
	}

	public List<Actioner> getEtp_actioner() {
		return etp_actioner;
	}

	public void setEtp_actioner(List<Actioner> etp_actioner) {
		this.etp_actioner = etp_actioner;
	}

	public String getEtp_nom() {
		return etp_nom;
	}

	public void setEtp_nom(String etp_nom) {
		this.etp_nom = etp_nom;
	}

	public Date getEtp_debut() {
		return etp_debut;
	}

	public void setEtp_debut(Date etp_debut) {
		this.etp_debut = etp_debut;
	}

	public Date getEtp_fin() {
		return etp_fin;
	}

	public void setEtp_fin(Date etp_fin) {
		this.etp_fin = etp_fin;
	}

	public DurationBO getEtp_duree() {
		return etp_duree;
	}

	public void setEtp_duree(DurationBO etp_duree) {
		this.etp_duree = etp_duree;
	}

	public Double getEtp_temperature_theorique() {
		return etp_temperature_theorique;
	}

	public void setEtp_temperature_theorique(Double etp_temperature_theorique) {
		this.etp_temperature_theorique = etp_temperature_theorique;
	}

	public String getEtp_remarque() {
		return etp_remarque;
	}

	public void setEtp_remarque(String etp_remarque) {
		this.etp_remarque = etp_remarque;
	}

	public List<ConcreteTemperatureMeasurement> getEtp_temperature_measurement() {
		return etp_temperature_measurement;
	}

	public void setEtp_temperature_measurement(List<ConcreteTemperatureMeasurement> etp_temperature_measurement) {
		this.etp_temperature_measurement = etp_temperature_measurement;
	}

	public List<Malt> getEtp_malts() {
		return etp_malts;
	}

	public void setEtp_malts(List<Malt> etp_malts) {
		this.etp_malts = etp_malts;
	}

	public List<Houblon> getEtp_houblons() {
		return etp_houblons;
	}

	public void setEtp_houblons(List<Houblon> etp_houblons) {
		this.etp_houblons = etp_houblons;
	}

	public List<Levure> getEtp_levures() {
		return etp_levures;
	}

	public void setEtp_levures(List<Levure> etp_levures) {
		this.etp_levures = etp_levures;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public PalierType getEtp_palier_type() {
		return etp_palier_type;
	}

	public void setEtp_palier_type(PalierType etp_palier_type) {
		this.etp_palier_type = etp_palier_type;
	}

	@Override
	public String toString() {
		return "Etape [etp_id=" + etp_id + ", etp_numero=" + etp_numero + ", etp_nom=" + etp_nom + ", etp_debut="
				+ etp_debut + ", etp_fin=" + etp_fin + ", etp_duree=" + etp_duree + ", etp_temperature_theorique="
				+ etp_temperature_theorique + ", etp_remarque=" + etp_remarque + "]";
	}

	public boolean hasIngredients() {
		if (etp_malts == null && etp_houblons == null && etp_levures == null) {
			return false;
		} else if (etp_malts.size() == 0 && etp_houblons.size() == 0 && etp_levures.size() == 0) {
			return false;
		} else if (etp_malts.size() > 0 || etp_houblons.size() > 0 || etp_levures.size() > 0) {
			return true;
		}
		return false;

	}

	public List<AbstractIngredient> getEtp_ingredients() {

		ArrayList<AbstractIngredient> result = new ArrayList<AbstractIngredient>();

		result.addAll(getEtp_malts());
		result.addAll(getEtp_houblons());
		result.addAll(getEtp_levures());

		return result;
	}

	public Date getEtp_fin_reel() {
		return etp_fin_reel;
	}

	public void setEtp_fin_reel(Date etp_fin_reel) {
		this.etp_fin_reel = etp_fin_reel;
	}

	public Date getEtp_debut_reel() {
		return etp_debut_reel;
	}

	public void setEtp_debut_reel(Date etp_debut_reel) {
		this.etp_debut_reel = etp_debut_reel;
	}

	public Date getEtp_creation_date() {
		return etp_creation_date;
	}

	public void setEtp_creation_date(Date etp_creation_date) {
		this.etp_creation_date = etp_creation_date;
	}

	public Date getEtp_update_date() {
		return etp_update_date;
	}

	public void setEtp_update_date(Date etp_update_date) {
		this.etp_update_date = etp_update_date;
	}

	/**
	 * Determines if the step is a final step in the process. Serves for the
	 * swing from ingredients to products Possible final steps are : bottling if
	 * beer is not refermented in bottle or refermentation
	 * 
	 * @return true if is a final step. False if not
	 */
	public boolean isFinalStep() {
/*
		if (this.getEtp_etape_type().equals(EtapeType.EMBOUTEILLAGE)
				|| this.getEtp_etape_type().equals(EtapeType.REFERMENTATION))
			return true;*/
		return false;
	}

}