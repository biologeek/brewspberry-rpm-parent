package net.brewspberry.business.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Actioner {

	/**
	 * 
	 * An Actioner is an object related to the devices plugged to Raspberry Pi.
	 * 
	 * It handles action related data : - Device
	 * 
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long act_id;
	/**
	 * 1 : thermometer measurement
	 * 2 : Engine (relay)
	 * 3 : Pump (relay)
	 */
	private String act_type;
	private String act_nom;
	private String act_uuid;
	private int act_status;
	private Date act_date_debut;
	private Date act_date_fin;
	private String act_raspi_pin;
	private boolean act_activated;
	private boolean act_used;
	private String act_picture;

	@ManyToOne
	@JoinColumn(name = "act_bra_id")
	private Brassin act_brassin;

	@ManyToOne
	@JoinColumn(name = "act_etp_id")
	private Etape act_etape;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="tmes_actioner")
	List<ConcreteTemperatureMeasurement> act_temperature_measurements;

	public Actioner() {
		super();
	}

	public long getAct_id() {
		return act_id;
	}

	public void setAct_id(long act_id) {
		this.act_id = act_id;
	}

	public boolean getAct_activated() {
		return act_activated;
	}

	public void setAct_activated(boolean act_activated) {
		this.act_activated = act_activated;
	}

	public String getAct_type() {
		return act_type;
	}

	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}

	public Brassin getAct_brassin() {
		return act_brassin;
	}

	public void setAct_brassin(Brassin act_brassin) {
		this.act_brassin = act_brassin;
	}

	public Etape getAct_etape() {
		return act_etape;
	}

	public String getAct_raspi_pin() {
		return act_raspi_pin;
	}

	public void setAct_raspi_pin(String act_raspi_pin) {
		this.act_raspi_pin = act_raspi_pin;
	}

	public void setAct_etape(Etape act_etape) {
		this.act_etape = act_etape;
	}

	public boolean getAct_used() {
		return act_used;
	}

	public void setAct_used(boolean act_used) {
		this.act_used = act_used;
	}

	public String getAct_nom() {
		return act_nom;
	}

	public void setAct_nom(String act_nom) {
		this.act_nom = act_nom;
	}

	public String getAct_uuid() {
		return act_uuid;
	}

	public void setAct_uuid(String act_uuid) {
		this.act_uuid = act_uuid;
	}

	public int getAct_status() {
		return act_status;
	}

	public void setAct_status(int act_status) {
		this.act_status = act_status;
	}

	public Date getAct_date_debut() {
		return act_date_debut;
	}

	public void setAct_date_debut(Date act_date_debut) {
		this.act_date_debut = act_date_debut;
	}

	public Date getAct_date_fin() {
		return act_date_fin;
	}

	public void setAct_date_fin(Date act_date_fin) {
		this.act_date_fin = act_date_fin;
	}

	public List<ConcreteTemperatureMeasurement> getAct_temperature_measurements() {
		return act_temperature_measurements;
	}

	public void setAct_temperature_measurements(
			List<ConcreteTemperatureMeasurement> act_temperature_measurements) {
		this.act_temperature_measurements = act_temperature_measurements;
	}

	@Override
	public String toString() {
		return "Actioner [act_id=" + act_id + ", act_type=" + act_type
				+ ", act_nom=" + act_nom + ", act_uuid=" + act_uuid
				+ ", act_status=" + act_status + ", act_date_debut="
				+ act_date_debut + ", act_date_fin=" + act_date_fin
				+ ", act_raspi_pin=" + act_raspi_pin + ", act_activated="
				+ act_activated + ", act_used=" + act_used + ", act_brassin="
				+ act_brassin + ", act_etape=" + act_etape+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (act_activated ? 1231 : 1237);
		result = prime * result
				+ ((act_brassin == null) ? 0 : act_brassin.hashCode());
		result = prime * result
				+ ((act_date_debut == null) ? 0 : act_date_debut.hashCode());
		result = prime * result
				+ ((act_date_fin == null) ? 0 : act_date_fin.hashCode());
		result = prime * result
				+ ((act_etape == null) ? 0 : act_etape.hashCode());
		result = prime * result + (int) (act_id ^ (act_id >>> 32));
		result = prime * result + ((act_nom == null) ? 0 : act_nom.hashCode());
		result = prime * result
				+ ((act_raspi_pin == null) ? 0 : act_raspi_pin.hashCode());
		result = prime * result + act_status;
		result = prime
				* result
				+ ((act_temperature_measurements == null) ? 0
						: act_temperature_measurements.hashCode());
		result = prime * result
				+ ((act_type == null) ? 0 : act_type.hashCode());
		result = prime * result + (act_used ? 1231 : 1237);
		result = prime * result
				+ ((act_uuid == null) ? 0 : act_uuid.hashCode());
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
		Actioner other = (Actioner) obj;
		if (act_activated != other.act_activated)
			return false;
		if (act_brassin == null) {
			if (other.act_brassin != null)
				return false;
		} else if (!act_brassin.equals(other.act_brassin))
			return false;
		if (act_date_debut == null) {
			if (other.act_date_debut != null)
				return false;
		} else if (!act_date_debut.equals(other.act_date_debut))
			return false;
		if (act_date_fin == null) {
			if (other.act_date_fin != null)
				return false;
		} else if (!act_date_fin.equals(other.act_date_fin))
			return false;
		if (act_etape == null) {
			if (other.act_etape != null)
				return false;
		} else if (!act_etape.equals(other.act_etape))
			return false;
		if (act_id != other.act_id)
			return false;
		if (act_nom == null) {
			if (other.act_nom != null)
				return false;
		} else if (!act_nom.equals(other.act_nom))
			return false;
		if (act_raspi_pin == null) {
			if (other.act_raspi_pin != null)
				return false;
		} else if (!act_raspi_pin.equals(other.act_raspi_pin))
			return false;
		if (act_status != other.act_status)
			return false;
		if (act_temperature_measurements == null) {
			if (other.act_temperature_measurements != null)
				return false;
		} else if (!act_temperature_measurements
				.equals(other.act_temperature_measurements))
			return false;
		if (act_type == null) {
			if (other.act_type != null)
				return false;
		} else if (!act_type.equals(other.act_type))
			return false;
		if (act_used != other.act_used)
			return false;
		if (act_uuid == null) {
			if (other.act_uuid != null)
				return false;
		} else if (!act_uuid.equals(other.act_uuid))
			return false;
		return true;
	}

	public String getAct_picture() {
		return act_picture;
	}

	public void setAct_picture(String act_picture) {
		this.act_picture = act_picture;
	}

	
	
}