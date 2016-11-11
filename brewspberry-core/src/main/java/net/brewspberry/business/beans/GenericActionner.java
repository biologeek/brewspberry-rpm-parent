package net.brewspberry.business.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

@Entity
@Component
public class GenericActionner {
	

	/**
	 * 
	 * An Actioner is an object related to the devices plugged to Raspberry Pi.
	 * 
	 * It handles action related data : - Device
	 * 
	 **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long gact_id;
	/**
	 * 1 : thermometer measurement 2 : Engine (relay) 3 : Pump (relay)
	 */
	@Enumerated(EnumType.STRING)
	private ActionerType gact_type;
	
	
	public enum ActionerType {
		DS18B20, ENGINE_RELAY, PUMP_RELAY
	}
	
	public enum ActionerStatus {
		STOPPED, STARTED, PAUSED, IDLE, FAILED
	}

	public static final String DS18B20_OFF = "images/thermo-off.jpg";
	public static final String DS18B20_ON = "images/thermo-on.jpg";
	
	public static final String ENGINE_OFF = "images/engine-off.png";
	public static final String ENGINE_ON = "images/engine-on.png";
	
	public static final String PUMP_OFF = "images/pump-off.jpg";
	public static final String PUMP_ON = "images/pump-on.jpg";
	
	private String gact_nom;
	private String gact_uuid;
	@Enumerated(EnumType.STRING)
	private ActionerStatus gact_status;
	private String gact_raspi_pin;
	private boolean gact_activated;
	private String gact_picture;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="act_generic")
	private List<Actioner> gact_actionners;


	public long getGact_id() {
		return gact_id;
	}

	public void setGact_id(long act_id) {
		this.gact_id = act_id;
	}

	public boolean getGact_activated() {
		return gact_activated;
	}

	public void setGact_activated(boolean act_activated) {
		this.gact_activated = act_activated;
	}

	public ActionerType getGact_type() {
		return gact_type;
	}

	public void setGact_type(ActionerType act_type) {
		this.gact_type = act_type;
	}


	public String getGact_raspi_pin() {
		return gact_raspi_pin;
	}

	public void setGact_raspi_pin(String act_raspi_pin) {
		this.gact_raspi_pin = act_raspi_pin;
	}


	public String getGact_nom() {
		return gact_nom;
	}

	public void setGact_nom(String act_nom) {
		this.gact_nom = act_nom;
	}

	public String getGact_uuid() {
		return gact_uuid;
	}

	public void setGact_uuid(String act_uuid) {
		this.gact_uuid = act_uuid;
	}

	public ActionerStatus getGact_status() {
		return gact_status;
	}

	public void setGact_status(ActionerStatus act_status) {
		this.gact_status = act_status;
	}


	public String getGact_picture() {
		return gact_picture;
	}

	public void setGact_picture(String act_picture) {
		this.gact_picture = act_picture;
	}


	public String getOffPicture(ActionerType act_type2) {

		switch (act_type2) {
		case DS18B20:
			return DS18B20_OFF;
		case ENGINE_RELAY:
			return ENGINE_OFF;
		case PUMP_RELAY:
			return PUMP_OFF;
		default:
			return "";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (gact_activated ? 1231 : 1237);
		result = prime * result + (int) (gact_id ^ (gact_id >>> 32));
		result = prime * result + ((gact_nom == null) ? 0 : gact_nom.hashCode());
		result = prime * result + ((gact_picture == null) ? 0 : gact_picture.hashCode());
		result = prime * result + ((gact_raspi_pin == null) ? 0 : gact_raspi_pin.hashCode());
		result = prime * result + ((gact_status == null) ? 0 : gact_status.hashCode());
		result = prime * result + ((gact_type == null) ? 0 : gact_type.hashCode());
		result = prime * result + ((gact_uuid == null) ? 0 : gact_uuid.hashCode());
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
		GenericActionner other = (GenericActionner) obj;
		if (gact_activated != other.gact_activated)
			return false;
		if (gact_id != other.gact_id)
			return false;
		if (gact_nom == null) {
			if (other.gact_nom != null)
				return false;
		} else if (!gact_nom.equals(other.gact_nom))
			return false;
		if (gact_picture == null) {
			if (other.gact_picture != null)
				return false;
		} else if (!gact_picture.equals(other.gact_picture))
			return false;
		if (gact_raspi_pin == null) {
			if (other.gact_raspi_pin != null)
				return false;
		} else if (!gact_raspi_pin.equals(other.gact_raspi_pin))
			return false;
		if (gact_status != other.gact_status)
			return false;
		if (gact_type != other.gact_type)
			return false;
		if (gact_uuid == null) {
			if (other.gact_uuid != null)
				return false;
		} else if (!gact_uuid.equals(other.gact_uuid))
			return false;
		return true;
	}



}
