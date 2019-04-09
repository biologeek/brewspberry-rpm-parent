package net.brewspberry.monitoring.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.brewspberry.monitoring.api.converters.LocalDateTimeToLongConverter;
import net.brewspberry.monitoring.api.converters.LongToLocalDateTimeConverter;

public class Temperature {

	private Long sensor;
	@JsonDeserialize(converter=LocalDateTimeToLongConverter.class)
	@JsonSerialize(converter=LongToLocalDateTimeConverter.class)
	private LocalDateTime date;
	private Float temperature;

	public Long getSensor() {
		return sensor;
	}

	public void setSensor(Long sensor) {
		this.sensor = sensor;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Temperature sensor(Long api) {
		this.sensor = api;
		return this;
	}

	public Temperature date(LocalDateTime date) {
		this.date = date;
		return this;
	}

	public Temperature temperature(Float temperature) {
		this.temperature = temperature;
		return this;
	}

}
