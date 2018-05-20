package net.brewspberry.monitoring.api;

import java.util.List;

public class DeviceDto {

	private long genericId;
	private long id;
	private String name;
	private String uuid;
	private ActionerType type;
	private long stepId;
	private long brewId;

	public enum ActionerType {
		DS18B20, ENGINE_RELAY, VALVE;
	}

	public enum ActionerStatus {
		STOPPED, STARTED, PAUSED, IDLE, UP, DOWN;
	}

	private String picture;
	private List<ChartDto> chart;
	private String pin;
	private boolean isActive;
	private ActionerStatus state;
	private boolean used;
	private long begin;
	private long end;

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

	public void setType(ActionerType string) {
		this.type = string;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<ChartDto> getChart() {
		return chart;
	}

	public void setChart(List<ChartDto> chart) {
		this.chart = chart;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public ActionerStatus getState() {
		return state;
	}

	public void setState(ActionerStatus state) {
		this.state = state;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getGenericId() {
		return genericId;
	}

	public void setGenericId(long genericId) {
		this.genericId = genericId;
	}

	public long getStepId() {
		return stepId;
	}

	public void setStepId(long stepId) {
		this.stepId = stepId;
	}

	public long getBrewId() {
		return brewId;
	}

	public void setBrewId(long brewId) {
		this.brewId = brewId;
	}

	public DeviceDto uuid(String uuid2) {
		this.uuid = uuid2;
		return this;
	}

	public DeviceDto id(Long id2) {
		this.id= id2;
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
}
