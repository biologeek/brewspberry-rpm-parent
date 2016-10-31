package net.brewspberry.business.exceptions;

import java.util.List;

import net.brewspberry.business.validation.BusinessError;

public class ValidationException extends Exception {
	
	
	private List<BusinessError> errors;
	
	
	
	public ValidationException(List<BusinessError> errs) {
		super(getMessage(errs));

		this.setErrors(errs);	
	}

	public static String getMessage(List<BusinessError> errors) {
		
		StringBuilder message= new StringBuilder();

		for (BusinessError err : errors){
			
			if (!err.equals(errors.get(0)))
				message.append(", ");
			
			
			message.append(err.getErrLabel());
			
		}
		
		return message.toString();
	}
	@Override
	public boolean equals(Object obj) {
		
		return super.equals(obj);
	}

	public List<BusinessError> getErrors() {
		return errors;
	}

	public void setErrors(List<BusinessError> errors) {
		this.errors = errors;
	}

}
