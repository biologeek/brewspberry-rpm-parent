package net.brewspberry.business.exceptions;

import java.util.List;

import net.brewspberry.business.validation.BusinessErrors;

public class ValidationException extends Exception {
	
	
	private List<BusinessErrors> errors;
	
	
	
	public ValidationException(List<BusinessErrors> errs) {
		
		this.errors = errs;	
	}

	@Override
	public String getMessage() {
		
		StringBuilder message= new StringBuilder();

		for (BusinessErrors err : errors){
			
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

}
