package net.brewspberry.monitoring.model;

import java.time.LocalDateTime;
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
	private LocalDateTime date;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sensor_id")
	private TemperatureSensor sensor;
	private Float temperature;
	/**
	 * The external ID of the operation (step, brew, whatever...) that this
	 * measurement is linked to. <br>
	 * <br>
	 * The caller knows what data references this external ID.
	 */
	private String externalId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
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

	public TemperatureMeasurement date(LocalDateTime date2) {
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
