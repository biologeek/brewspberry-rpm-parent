package net.brewspberry.monitoring.api;



import net.brewspberry.monitoring.api.DeviceDto.ActionerType;

public class GenericActionnerDto {

	
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
}
