package net.brewspberry.monitoring.exceptions;

/**
 * Exception thrown when a functional error occurs on device
 *
 */
public class MonitoringException extends Exception {

	private String deviceUuid;
	
	public MonitoringException(String uuid, Exception e) {
		super(e);
		deviceUuid = uuid;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public void setDeviceUuid(String deviceUuid) {
		this.deviceUuid = deviceUuid;
	}

}
