package net.brewspberry.main.business.beans.brewing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Embedded;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.util.DateManipulator;

@Entity
@Component
public class Etape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1849499413089750093L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long etp_id;
	private Integer etp_numero; // step number in the brew
	private String etp_nom;
	private Date etp_debut;
	private Date etp_fin;
	private Date etp_creation_date;
	private Date etp_update_date;
	private Date etp_debut_reel;
	private Date etp_fin_reel;

	@Embedded
	private DurationBO etp_duree;

	/**
	 * etape type object contains tops to allow rules to apply or not for
	 * various algorithms Fetching it eagerly, and cascading modfications to
	 * linked EtapeType object
	 */
	@Enumerated(EnumType.STRING)
	private EtapeType etp_etape_type;

	private Double etp_temperature_theorique;
	private String etp_remarque;

	@ManyToOne(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	@JoinColumn(name = "etp_bra_id")
	@Cascade(CascadeType.ALL)
	@JsonIgnore
	private Brassin etp_brassin;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tmes_etape")
	@JsonIgnore
	private List<ConcreteTemperatureMeasurement> etp_temperature_measurement;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "act_etape")
	private List<Actioner> etp_actioner;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "malt_etape")
	@Cascade(CascadeType.ALL)
	private Set<Malt> etp_malts;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "hbl_etape")
	@Cascade(CascadeType.ALL)
	private Set<Houblon> etp_houblons;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "lev_etape")
	@Cascade(CascadeType.ALL)
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

	public List<Actioner> getEtp_actioner() {
		return etp_actioner;
	}

	public void setEtp_actioners(List<Actioner> etp_actioner) {
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
	 * 
	 * @return
	 */
	public DurationBO getEtp_duree() {

		return etp_duree;
	}

	/**
	 * Returns duration in seconds
	 * 
	 * @return
	 */
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

	public Etape id(long id) {
		this.setEtp_id(id);
		return this;
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

	public void setEtp_actioner(List<Actioner> etp_actioner) {
		this.etp_actioner = etp_actioner;
	}

	public void setEtp_ingredients(List<AbstractIngredient> etp_ingredients) {
		this.etp_ingredients = etp_ingredients;
	}

	public boolean isEtp_active() {
		return etp_active;
	}

	public void setEtp_active(boolean etp_active) {
		this.etp_active = etp_active;
	}

	public boolean hasActioners() {
		if (etp_actioner != null) {
			if (etp_actioner.size() > 0) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static class Builder {
		private Long etp_id;
		private Integer etp_numero;
		private String etp_nom;
		private Date etp_debut;
		private Date etp_fin;
		private Date etp_creation_date;
		private Date etp_update_date;
		private Date etp_debut_reel;
		private Date etp_fin_reel;
		private DurationBO etp_duree;
		private EtapeType etp_etape_type;
		private Double etp_temperature_theorique;
		private String etp_remarque;
		private Brassin etp_brassin;
		private List<ConcreteTemperatureMeasurement> etp_temperature_measurement;
		private List<Actioner> etp_actioner;
		private Set<Malt> etp_malts;
		private Set<Houblon> etp_houblons;
		private Set<Levure> etp_levures;
		private PalierType etp_palier_type;
		private List<AbstractIngredient> etp_ingredients;
		private boolean etp_active;

		public Builder etp_id(Long etp_id) {
			this.etp_id = etp_id;
			return this;
		}

		public Builder etp_numero(Integer etp_numero) {
			this.etp_numero = etp_numero;
			return this;
		}

		public Builder etp_nom(String etp_nom) {
			this.etp_nom = etp_nom;
			return this;
		}

		public Builder etp_debut(Date etp_debut) {
			this.etp_debut = etp_debut;
			return this;
		}

		public Builder etp_fin(Date etp_fin) {
			this.etp_fin = etp_fin;
			return this;
		}

		public Builder etp_creation_date(Date etp_creation_date) {
			this.etp_creation_date = etp_creation_date;
			return this;
		}

		public Builder etp_update_date(Date etp_update_date) {
			this.etp_update_date = etp_update_date;
			return this;
		}

		public Builder etp_debut_reel(Date etp_debut_reel) {
			this.etp_debut_reel = etp_debut_reel;
			return this;
		}

		public Builder etp_fin_reel(Date etp_fin_reel) {
			this.etp_fin_reel = etp_fin_reel;
			return this;
		}

		public Builder etp_duree(DurationBO etp_duree) {
			this.etp_duree = etp_duree;
			return this;
		}

		public Builder etp_etape_type(EtapeType etp_etape_type) {
			this.etp_etape_type = etp_etape_type;
			return this;
		}

		public Builder etp_temperature_theorique(Double etp_temperature_theorique) {
			this.etp_temperature_theorique = etp_temperature_theorique;
			return this;
		}

		public Builder etp_remarque(String etp_remarque) {
			this.etp_remarque = etp_remarque;
			return this;
		}

		public Builder etp_brassin(Brassin etp_brassin) {
			this.etp_brassin = etp_brassin;
			return this;
		}

		public Builder etp_temperature_measurement(List<ConcreteTemperatureMeasurement> etp_temperature_measurement) {
			this.etp_temperature_measurement = etp_temperature_measurement;
			return this;
		}

		public Builder etp_actioner(List<Actioner> etp_actioner) {
			this.etp_actioner = etp_actioner;
			return this;
		}

		public Builder etp_malts(Set<Malt> etp_malts) {
			this.etp_malts = etp_malts;
			return this;
		}

		public Builder etp_houblons(Set<Houblon> etp_houblons) {
			this.etp_houblons = etp_houblons;
			return this;
		}

		public Builder etp_levures(Set<Levure> etp_levures) {
			this.etp_levures = etp_levures;
			return this;
		}

		public Builder etp_palier_type(PalierType etp_palier_type) {
			this.etp_palier_type = etp_palier_type;
			return this;
		}

		public Builder etp_ingredients(List<AbstractIngredient> etp_ingredients) {
			this.etp_ingredients = etp_ingredients;
			return this;
		}

		public Builder etp_active(boolean etp_active) {
			this.etp_active = etp_active;
			return this;
		}

		public Etape build() {
			return new Etape(this);
		}
	}

	private Etape(Builder builder) {
		this.etp_id = builder.etp_id;
		this.etp_numero = builder.etp_numero;
		this.etp_nom = builder.etp_nom;
		this.etp_debut = builder.etp_debut;
		this.etp_fin = builder.etp_fin;
		this.etp_creation_date = builder.etp_creation_date;
		this.etp_update_date = builder.etp_update_date;
		this.etp_debut_reel = builder.etp_debut_reel;
		this.etp_fin_reel = builder.etp_fin_reel;
		this.etp_duree = builder.etp_duree;
		this.etp_etape_type = builder.etp_etape_type;
		this.etp_temperature_theorique = builder.etp_temperature_theorique;
		this.etp_remarque = builder.etp_remarque;
		this.etp_brassin = builder.etp_brassin;
		this.etp_temperature_measurement = builder.etp_temperature_measurement;
		this.etp_actioner = builder.etp_actioner;
		this.etp_malts = builder.etp_malts;
		this.etp_houblons = builder.etp_houblons;
		this.etp_levures = builder.etp_levures;
		this.etp_palier_type = builder.etp_palier_type;
		this.etp_ingredients = builder.etp_ingredients;
		this.etp_active = builder.etp_active;
	}
}