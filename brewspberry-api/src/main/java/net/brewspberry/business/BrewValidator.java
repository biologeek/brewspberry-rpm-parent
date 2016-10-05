package net.brewspberry.business;

import java.util.ArrayList;
import java.util.List;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.validation.BusinessErrors;
import net.brewspberry.business.validation.Validator;

public class BrewValidator implements Validator<Brassin> {

	@Override
	/**
	 * Validates mandatory fields, non nullable fields, eventually validates dates, lengths, ...
	 */
	public List<BusinessErrors> validate(Brassin toVal) {
		// TODO Auto-generated method stub
		return new ArrayList<BusinessErrors>();
	}

}
