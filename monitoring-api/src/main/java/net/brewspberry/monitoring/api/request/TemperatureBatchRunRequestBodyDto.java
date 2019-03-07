package net.brewspberry.monitoring.api.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO used to launch a new batch of
 * 
 * @author caron-x
 *
 */
@JsonAutoDetect
public class TemperatureBatchRunRequestBodyDto {

	private String device;
	/**
	 * The external identifier of step/brew/other element related to this batch,
	 * optional
	 */
	private String externalId;
	private Integer frequency;
	private Float duration;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

}
