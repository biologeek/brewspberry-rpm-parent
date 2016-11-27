package net.brewspberry.main.business.validation;

public class BusinessError {
	
	private String errLabel;

	public String getErrLabel() {
		return errLabel;
	}

	public void setErrLabel(String errLabel) {
		this.errLabel = errLabel;
	}

	public BusinessError error(String string) {
		setErrLabel(string);
		return this;
	}
	

}
