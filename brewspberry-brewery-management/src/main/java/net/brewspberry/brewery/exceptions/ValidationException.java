package net.brewspberry.brewery.exceptions;

/**
 * Exception thrown when a functional error occurs on device
 *
 */
public class ValidationException extends Exception {

	private String deviceUuid;
	
	public ValidationException(String uuid, Exception e) {
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
