package net.brewspberry.business.validation;

import java.util.List;

import net.brewspberry.business.beans.GenericActionner;

public class GenericActionnerValidator implements Validator<GenericActionner> {

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

}
