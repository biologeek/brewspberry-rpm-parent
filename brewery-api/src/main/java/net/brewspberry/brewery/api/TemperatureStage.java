package net.brewspberry.brewery.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import net.brewspberry.brewery.api.converters.LocalDateTimeToLongConverter;
import net.brewspberry.brewery.api.converters.LongToLocalDateTimeConverter;

public class TemperatureStage {

	private Long id;
	private StageType type;
	@JsonDeserialize(converter = LongToLocalDateTimeConverter.class)
	@JsonSerialize(converter = LocalDateTimeToLongConverter.class)
	private LocalDateTime beginning;
	private Long beginningToStep;
	private Long duration;
	private Float startTemperature;
	private Float endTemperature;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBeginningToStep() {
		return beginningToStep;
	}

	public void setBeginningToStep(Long beginningToStep) {
		this.beginningToStep = beginningToStep;
	}

	public StageType getType() {
		return type;
	}

	public void setType(StageType type) {
		this.type = type;
	}

	public LocalDateTime getBeginning() {
		return beginning;
	}

	public void setBeginning(LocalDateTime beginning) {
		this.beginning = beginning;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Float getStartTemperature() {
		return startTemperature;
	}

	public void setStartTemperature(Float startTemperature) {
		this.startTemperature = startTemperature;
	}

	public Float getEndTemperature() {
		return endTemperature;
	}

	public void setEndTemperature(Float endTemperature) {
		this.endTemperature = endTemperature;
	}
}
