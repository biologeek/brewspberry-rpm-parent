package net.brewspberry.monitoring.exceptions;

public class ServiceException extends Exception {

	private String code;

	public ServiceException(String string, String code) {
		super(string);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
