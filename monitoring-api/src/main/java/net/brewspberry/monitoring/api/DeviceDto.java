package net.brewspberry.monitoring.api;

import java.util.Date;
import java.util.Map;

public class DeviceDto {

	private long genericId;
	private long id;
	private String name;
	private String uuid;
	private ActionerType type;
	Map<String, String> externalIds;
	private Date creation, update, lastChange;

	private String pin;
	private int pinAddress;
	private boolean isActive;
	private ActionerStatus state;
	private boolean plugged;

	public enum ActionerType {
		DS18B20, ENGINE_RELAY, VALVE;
	}

	public enum ActionerStatus {
		STOPPED, STARTED, PAUSED, IDLE, UP, DOWN;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public int getPinAddress() {
		return pinAddress;
	}

	public void setPinAddress(int pinAddress) {
		this.pinAddress = pinAddress;
	}

	public Map<String, String> getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(Map<String, String> externalIds) {
		this.externalIds = externalIds;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getLastChange() {
		return lastChange;
	}

	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isPlugged() {
		return plugged;
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

	public void setType(ActionerType string) {
		this.type = string;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public DeviceDto isPlugged(boolean b) {
		this.plugged = b;
		return this;
	}

	public void setPlugged(boolean plugged) {
		this.plugged = plugged;
	}

	public ActionerStatus getState() {
		return state;
	}

	public void setState(ActionerStatus state) {
		this.state = state;
	}

	public long getGenericId() {
		return genericId;
	}

	public void setGenericId(long genericId) {
		this.genericId = genericId;
	}

	public DeviceDto uuid(String uuid2) {
		this.uuid = uuid2;
		return this;
	}

	public DeviceDto id(Long id2) {
		this.id = id2;
		return this;
	}

	public DeviceDto pin(String pin2) {
		this.pin = pin2;
		return this;
	}

	public DeviceDto state(ActionerStatus pin2) {
		this.state = pin2;
		return this;
	}

	public DeviceDto name(String name2) {
		this.name = name2;
		return this;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
