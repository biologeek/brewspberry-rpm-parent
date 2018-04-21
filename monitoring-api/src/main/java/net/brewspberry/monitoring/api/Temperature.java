package net.brewspberry.monitoring.api;

import java.util.Date;

public class Temperature {

	private DeviceDto sensor;
	private Date date;
	private Float temperature;

	public DeviceDto getSensor() {
		return sensor;
	}

	public void setSensor(DeviceDto sensor) {
		this.sensor = sensor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Temperature sensor(DeviceDto api) {
		this.sensor = api;
		return this;
	}

	public Temperature date(Date date) {
		this.date = date;
		return this;
	}

	public Temperature temperature(Float temperature) {
		this.temperature = temperature;
		return this;
	}

}
