package net.brewspberry.business.validation;

public class BusinessErrors {
	
	private String errLabel;

	public String getErrLabel() {
		return errLabel;
	}

	public void setErrLabel(String errLabel) {
		this.errLabel = errLabel;
	}

	public BusinessErrors error(String string) {
		setErrLabel(string);
		return this;
	}
	

}