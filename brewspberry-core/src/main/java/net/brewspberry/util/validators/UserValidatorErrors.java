package net.brewspberry.util.validators;

public enum UserValidatorErrors {
	
	EMPTY_USER (1, "user", "User field is empty"),
	EMPTY_PASSWORD (1, "password", "Password field is empty"),
	WRONG_PASSWORD (1, "password", "Password is incorrect"),
	SHORT_PASSWORD (2, "password", "Password is too short");
	
	int severity;
	String field;
	String error;
	
	
	UserValidatorErrors(int severity, String field, String error){
		
		this.severity = severity;
		this.field = field;
		this.error = error;
		
	}


	public int getSeverity() {
		return severity;
	}


	public void setSeverity(int severity) {
		this.severity = severity;
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}
	
	
	

}
