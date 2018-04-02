package net.brewspberry.main.business.validation;

import java.util.List;

import net.brewspberry.main.business.beans.monitoring.GenericActionner;

public class GenericActionnerValidator implements Validator<GenericActionner> {
	private List<GenericActionner> actionnersList;
	@Override
	public List<BusinessError> validate(GenericActionner toVal) {

		return null;
	}

	@Override
	public String computeErrors(List<BusinessError> errs, String delimiter) {

		String finalStr = new String();

		for (BusinessError err : errs) {

			finalStr += err.getErrLabel() + "<br>";

		}
		return finalStr;
	}


	@Override
	public void setOTherParameters(Object... otherParams) {
		
		if (otherParams != null && otherParams[0] instanceof List<?>)
			this.actionnersList = (List<GenericActionner>) otherParams[0];
	}

}
