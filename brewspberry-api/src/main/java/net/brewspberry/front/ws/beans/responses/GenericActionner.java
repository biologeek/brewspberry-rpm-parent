package net.brewspberry.front.ws.beans.responses;


import net.brewspberry.front.ws.beans.responses.ActionnerResponse.ActionerType;

public class GenericActionner {

	
	private long id;
	private String name;
	private String uuid;
	private ActionerType type;
	private String picture;
	private String pin;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
}
