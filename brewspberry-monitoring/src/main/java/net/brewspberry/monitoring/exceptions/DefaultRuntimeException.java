package net.brewspberry.monitoring.exceptions;

public class DefaultRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665272100944843205L;
	private String code;

	public DefaultRuntimeException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
