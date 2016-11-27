package net.brewspberry.main.front.ws.beans.responses;


import net.brewspberry.main.business.beans.GenericActionner.ActionerStatus;
import net.brewspberry.main.front.ws.beans.responses.ActionnerResponse.ActionerType;
import net.brewspberry.main.util.Constants;

public class GenericActionner {

	
	private long genericId;
	private String name;
	private String uuid;
	private ActionerType type;
	private String picture;
	private String pin;
	private String state;
	
	public long getGenericId() {
		return genericId;
	}
	public void setGenericId(long id) {
		this.genericId = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public ActionerType getType() {
		return type;
	}
	public void setType(ActionerType type) {
		this.type = type;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	

	public String getPictureWithStatus() {

		if (this.state.equals(ActionerStatus.STARTED)) {
			switch (this.type) {
			case DS18B20:
				return Constants.DS18B20_ON;
			case ENGINE_RELAY:
				return Constants.ENGINE_ON;
			case PUMP_RELAY:
				return Constants.PUMP_ON;
			default:
				return "";
			}
		} else {
			switch (this.type) {
			case DS18B20:
				return Constants.DS18B20_OFF;
			case ENGINE_RELAY:
				return Constants.ENGINE_OFF;
			case PUMP_RELAY:
				return Constants.PUMP_OFF;
			default:
				return "";
			}
		}
	}
}
