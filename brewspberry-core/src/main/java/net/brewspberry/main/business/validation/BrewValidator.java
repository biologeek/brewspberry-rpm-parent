package net.brewspberry.main.business.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.GenericActionner;
import net.brewspberry.main.util.DateManipulator;

public class BrewValidator implements Validator<Brassin> {

	@Value("${param.validation.brew.maxDelay}")
	Integer dateDelay;
	@Value("${param.validation.brew.maxDelayUnit}")
	Integer dateDelayUnit;
	

	public BrewValidator() {
		if (dateDelay == null) {
			dateDelay = 5;
		}
		if (dateDelayUnit == null) {
			dateDelayUnit = 1;
		}
	}

	@Override
	/**
	 * Validates mandatory fields, non nullable fields, eventually validates
	 * dates, lengths, ...
	 */

	public List<BusinessError> validate(Brassin toVal) {

		List<BusinessError> errs = new ArrayList<BusinessError>();

		errs.addAll(validateBrewCharacteristics(toVal));

		return errs;
	}

	private List<BusinessError> validateBrewCharacteristics(Brassin toVal) {

		List<BusinessError> errs = new ArrayList<BusinessError>();
		if (toVal.getBra_nom().equals("")) {
			errs.add(new BusinessError().error("Brew name is required"));
		}

		if (toVal.getBra_quantiteEnLitres() <= 0) {
			errs.add(new BusinessError().error("Quantity is 0"));
		}

		if (toVal.getBra_type() == null || toVal.getBra_type().equals("")) {
			errs.add(new BusinessError().error("Type is null"));
		}

		if (!DateManipulator.isDateInRange(toVal.getBra_debut(), dateDelay, dateDelayUnit)) {
			errs.add(new BusinessError().error("Beginning date should be within " + dateDelay + " " + dateDelayUnit));
		}


		if (!DateManipulator.isDateInRange(toVal.getBra_fin(), dateDelay, dateDelayUnit)) {
			errs.add(new BusinessError().error("End date should be within " + dateDelay + " " + dateDelayUnit));
		}

		return errs;
	}

	@Override
	public String computeErrors(List<BusinessError> errs, String delimiter) {

		if (delimiter == null || delimiter.equals("")) {
			delimiter = ", ";
		}
		StringBuilder builder = new StringBuilder();
		for (BusinessError err : errs) {
			if (!errs.isEmpty() && !err.equals(errs.get(0))) {
				builder.append(delimiter);
			}
			builder.append(err.getErrLabel());

		}
		return builder.toString();
	}

	@Override
	public void setOTherParameters(Object... otherParams) {
		// TODO Auto-generated method stub
		
	}

}
