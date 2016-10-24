package net.brewspberry.business.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import net.brewspberry.util.DateManipulator;

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

	
	/**
	 * etape type object contains tops to allow rules to apply or not for
	 * various algorithms Fetching it eagerly, and cascading modfications to
	 * linked EtapeType object
	 */
	@Enumerated(EnumType.STRING)
	private EtapeType etp_etape_type;

	private Double etp_temperature_theorique;
	private String etp_remarque;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "etp_bra_id")
	private Brassin etp_brassin;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tmes_etape")
	private List<ConcreteTemperatureMeasurement> etp_temperature_measurement;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "act_etape")
	private Set<Actioner> etp_actioner;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "malt_etape", cascade = CascadeType.ALL)
	private Set<Malt> etp_malts;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hbl_etape", cascade = CascadeType.ALL)
	private Set<Houblon> etp_houblons;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "lev_etape", cascade = CascadeType.ALL)
	private Set<Levure> etp_levures;

	@Enumerated(EnumType.STRING)
	PalierType etp_palier_type;
 
	@Transient
	private List<AbstractIngredient> etp_ingredients;
	
	private boolean etp_active;

	public Etape() {
		super();
		
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

	public Set<Actioner> getEtp_actioner() {
		return etp_actioner;
	}

	public void setEtp_actioners(Set<Actioner> etp_actioner) {
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

	/**
	 * Returns duration in seconds
	 * @return
	 */
	public long getEtp_duree() {
		return (getEtp_fin_reel().getTime() - getEtp_debut_reel().getTime()) / 1000;
	}
	
	/**
	 * Returns duration in seconds
	 * @return
	 */
	public void setEtp_duree() {
		
		if (this.getEtp_debut_reel() != null && DateManipulator.isDateInRange(etp_debut_reel, 5, Calendar.YEAR)){
			
			if (this.getEtp_fin_reel() == null){
				
				this.setEtp_fin_reel(new Date(this.getEtp_debut_reel().getTime() - this.getEtp_duree()));
				
			}
		}
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

	public Set<Malt> getEtp_malts() {
		return etp_malts;
	}

	public void setEtp_malts(Set<Malt> etp_malts) {
		this.etp_malts = etp_malts;
	}

	public Set<Houblon> getEtp_houblons() {
		return etp_houblons;
	}

	public void setEtp_houblons(Set<Houblon> etp_houblons) {
		this.etp_houblons = etp_houblons;
	}

	public Set<Levure> getEtp_levures() {
		return etp_levures;
	}

	public void setEtp_levures(Set<Levure> etp_levures) {
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
				+ etp_debut + ", etp_fin=" + etp_fin + ", etp_duree=" + getEtp_duree() + ", etp_temperature_theorique="
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

		if (this.getEtp_etape_type().equals(EtapeType.EMBOUTEILLAGE)
				|| this.getEtp_etape_type().equals(EtapeType.REFERMENTATION))
			return true;
		return false;
	}

	public EtapeType getEtp_etape_type() {
		return etp_etape_type;
	}

	public void setEtp_etape_type(EtapeType etp_etape_type) {
		this.etp_etape_type = etp_etape_type;
	}

	public void setEtp_actioner(Set<Actioner> etp_actioner) {
		this.etp_actioner = etp_actioner;
	}

	public void setEtp_ingredients(List<AbstractIngredient> etp_ingredients) {
		this.etp_ingredients = etp_ingredients;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((etp_actioner == null) ? 0 : etp_actioner.hashCode());
		result = prime * result + ((etp_creation_date == null) ? 0 : etp_creation_date.hashCode());
		result = prime * result + ((etp_debut == null) ? 0 : etp_debut.hashCode());
		result = prime * result + ((etp_debut_reel == null) ? 0 : etp_debut_reel.hashCode());
		result = prime * result + ((etp_fin == null) ? 0 : etp_fin.hashCode());
		result = prime * result + ((etp_fin_reel == null) ? 0 : etp_fin_reel.hashCode());
		result = prime * result + ((etp_houblons == null) ? 0 : etp_houblons.hashCode());
		result = prime * result + ((etp_id == null) ? 0 : etp_id.hashCode());
		result = prime * result + ((etp_ingredients == null) ? 0 : etp_ingredients.hashCode());
		result = prime * result + ((etp_levures == null) ? 0 : etp_levures.hashCode());
		result = prime * result + ((etp_malts == null) ? 0 : etp_malts.hashCode());
		result = prime * result + ((etp_nom == null) ? 0 : etp_nom.hashCode());
		result = prime * result + ((etp_numero == null) ? 0 : etp_numero.hashCode());
		result = prime * result + ((etp_palier_type == null) ? 0 : etp_palier_type.hashCode());
		result = prime * result + ((etp_remarque == null) ? 0 : etp_remarque.hashCode());
		result = prime * result + ((etp_temperature_measurement == null) ? 0 : etp_temperature_measurement.hashCode());
		result = prime * result + ((etp_temperature_theorique == null) ? 0 : etp_temperature_theorique.hashCode());
		result = prime * result + ((etp_update_date == null) ? 0 : etp_update_date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Etape other = (Etape) obj;
		if (etp_actioner == null) {
			if (other.etp_actioner != null)
				return false;
		} else if (!etp_actioner.equals(other.etp_actioner))
			return false;
		if (etp_brassin == null) {
			if (other.etp_brassin != null)
				return false;
		} else if (!etp_brassin.equals(other.etp_brassin))
			return false;
		if (etp_creation_date == null) {
			if (other.etp_creation_date != null)
				return false;
		} else if (!etp_creation_date.equals(other.etp_creation_date))
			return false;
		if (etp_debut == null) {
			if (other.etp_debut != null)
				return false;
		} else if (!etp_debut.equals(other.etp_debut))
			return false;
		if (etp_debut_reel == null) {
			if (other.etp_debut_reel != null)
				return false;
		} else if (!etp_debut_reel.equals(other.etp_debut_reel))
			return false;
		if (etp_fin == null) {
			if (other.etp_fin != null)
				return false;
		} else if (!etp_fin.equals(other.etp_fin))
			return false;
		if (etp_fin_reel == null) {
			if (other.etp_fin_reel != null)
				return false;
		} else if (!etp_fin_reel.equals(other.etp_fin_reel))
			return false;
		if (etp_houblons == null) {
			if (other.etp_houblons != null)
				return false;
		} else if (!etp_houblons.equals(other.etp_houblons))
			return false;
		if (etp_id == null) {
			if (other.etp_id != null)
				return false;
		} else if (!etp_id.equals(other.etp_id))
			return false;
		if (etp_ingredients == null) {
			if (other.etp_ingredients != null)
				return false;
		} else if (!etp_ingredients.equals(other.etp_ingredients))
			return false;
		if (etp_levures == null) {
			if (other.etp_levures != null)
				return false;
		} else if (!etp_levures.equals(other.etp_levures))
			return false;
		if (etp_malts == null) {
			if (other.etp_malts != null)
				return false;
		} else if (!etp_malts.equals(other.etp_malts))
			return false;
		if (etp_nom == null) {
			if (other.etp_nom != null)
				return false;
		} else if (!etp_nom.equals(other.etp_nom))
			return false;
		if (etp_numero == null) {
			if (other.etp_numero != null)
				return false;
		} else if (!etp_numero.equals(other.etp_numero))
			return false;
		if (etp_palier_type != other.etp_palier_type)
			return false;
		if (etp_remarque == null) {
			if (other.etp_remarque != null)
				return false;
		} else if (!etp_remarque.equals(other.etp_remarque))
			return false;
		if (etp_temperature_measurement == null) {
			if (other.etp_temperature_measurement != null)
				return false;
		} else if (!etp_temperature_measurement.equals(other.etp_temperature_measurement))
			return false;
		if (etp_temperature_theorique == null) {
			if (other.etp_temperature_theorique != null)
				return false;
		} else if (!etp_temperature_theorique.equals(other.etp_temperature_theorique))
			return false;
		if (etp_update_date == null) {
			if (other.etp_update_date != null)
				return false;
		} else if (!etp_update_date.equals(other.etp_update_date))
			return false;
		return true;
	}

	public boolean isEtp_active() {
		return etp_active;
	}

	public void setEtp_active(boolean etp_active) {
		this.etp_active = etp_active;
	}

}