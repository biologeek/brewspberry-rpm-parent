package net.brewspberry.monitoring.api.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO used to launch a new batch of 
 * @author caron-x
 *
 */
@JsonAutoDetect
public class TemperatureBatchRunRequestBodyDto {

	private List<String> devices;
	/**
	 * The external identifier of step/brew/other element related to this batch, optional
	 */
	private String externalId;
	private Long frequency;
	private Long duration;

	public List<String> getDevices() {
		return devices;
	}

	public void setDevices(List<String> devices) {
		this.devices = devices;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Long getFrequency() {
		return frequency;
	}

	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

}
