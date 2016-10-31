package net.brewspberry.business.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.stereotype.Component;

@Entity
@Component
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
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
	private long act_id;
	/**
	 * 1 : thermometer measurement 2 : Engine (relay) 3 : Pump (relay)
	 */
	@Enumerated(EnumType.STRING)
	private ActionerType act_type;
	
	
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
	
	private String act_nom;
	private String act_uuid;
	@Enumerated(EnumType.STRING)
	private ActionerStatus act_status;
	private String act_raspi_pin;
	private boolean act_activated;
	private boolean act_used;
	private String act_picture;


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

	public ActionerType getAct_type() {
		return act_type;
	}

	public void setAct_type(ActionerType act_type) {
		this.act_type = act_type;
	}


	public String getAct_raspi_pin() {
		return act_raspi_pin;
	}

	public void setAct_raspi_pin(String act_raspi_pin) {
		this.act_raspi_pin = act_raspi_pin;
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

	public ActionerStatus getAct_status() {
		return act_status;
	}

	public void setAct_status(ActionerStatus act_status) {
		this.act_status = act_status;
	}


	public String getAct_picture() {
		return act_picture;
	}

	public void setAct_picture(String act_picture) {
		this.act_picture = act_picture;
	}


	public String getOffPicture(net.brewspberry.business.beans.Actioner.ActionerType act_type2) {

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
		result = prime * result + (act_activated ? 1231 : 1237);
		result = prime * result + (int) (act_id ^ (act_id >>> 32));
		result = prime * result + ((act_nom == null) ? 0 : act_nom.hashCode());
		result = prime * result + ((act_picture == null) ? 0 : act_picture.hashCode());
		result = prime * result + ((act_raspi_pin == null) ? 0 : act_raspi_pin.hashCode());
		result = prime * result + ((act_status == null) ? 0 : act_status.hashCode());
		result = prime * result + ((act_type == null) ? 0 : act_type.hashCode());
		result = prime * result + (act_used ? 1231 : 1237);
		result = prime * result + ((act_uuid == null) ? 0 : act_uuid.hashCode());
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
		if (act_activated != other.act_activated)
			return false;
		if (act_id != other.act_id)
			return false;
		if (act_nom == null) {
			if (other.act_nom != null)
				return false;
		} else if (!act_nom.equals(other.act_nom))
			return false;
		if (act_picture == null) {
			if (other.act_picture != null)
				return false;
		} else if (!act_picture.equals(other.act_picture))
			return false;
		if (act_raspi_pin == null) {
			if (other.act_raspi_pin != null)
				return false;
		} else if (!act_raspi_pin.equals(other.act_raspi_pin))
			return false;
		if (act_status != other.act_status)
			return false;
		if (act_type != other.act_type)
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



}
