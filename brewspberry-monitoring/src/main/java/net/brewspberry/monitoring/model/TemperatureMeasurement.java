package net.brewspberry.monitoring.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TemperatureMeasurement {

	@Id
	@GeneratedValue
	private Long id;
	private Date date;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sensor_id")
	private TemperatureSensor sensor;
	private Float temperature;
	private String externalId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public TemperatureSensor getSensor() {
		return sensor;
	}

	public void setSensor(TemperatureSensor sensor) {
		this.sensor = sensor;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public TemperatureMeasurement date(Date date2) {
		setDate(date2);
		return this;
	}

	public TemperatureMeasurement sensor(TemperatureSensor sensor) {
		setSensor(sensor);
		return this;
	}

	public TemperatureMeasurement temperature(Float temp) {
		setTemperature(temp);
		return this;
	}

	public TemperatureMeasurement externalId(String extId) {
		setExternalId(extId);
		return this;
	}

}
