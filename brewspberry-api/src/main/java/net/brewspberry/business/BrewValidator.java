package net.brewspberry.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.validation.BusinessErrors;
import net.brewspberry.business.validation.Validator;
import net.brewspberry.util.DateManipulator;

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

	public List<BusinessErrors> validate(Brassin toVal) {

		List<BusinessErrors> errs = new ArrayList<BusinessErrors>();

		errs.addAll(validateBrewCharacteristics(toVal));

		return errs;
	}

	private List<BusinessErrors> validateBrewCharacteristics(Brassin toVal) {

		List<BusinessErrors> errs = new ArrayList<BusinessErrors>();
		if (toVal.getBra_nom().equals("")) {
			errs.add(new BusinessErrors().error("Brew name is required"));
		}

		if (toVal.getBra_quantiteEnLitres() <= 0) {
			errs.add(new BusinessErrors().error("Quantity is 0"));
		}

		if (toVal.getBra_type() == null || toVal.getBra_type().equals("")) {
			errs.add(new BusinessErrors().error("Type is null"));
		}

		if (!DateManipulator.isDateInRange(toVal.getBra_debut(), dateDelay, dateDelayUnit)) {
			errs.add(new BusinessErrors().error("Beginning date should be within " + dateDelay + " " + dateDelayUnit));
		}

		if (!DateManipulator.isDateInRange(toVal.getBra_date_maj(), dateDelay, dateDelayUnit)) {
			errs.add(new BusinessErrors().error("Update date should be within " + dateDelay + " " + dateDelayUnit));
		}

		if (!DateManipulator.isDateInRange(toVal.getBra_fin(), dateDelay, dateDelayUnit)) {
			errs.add(new BusinessErrors().error("End date should be within " + dateDelay + " " + dateDelayUnit));
		}

		return errs;
	}

	@Override
	public String computeErrors(List<BusinessErrors> errs, String delimiter) {

		if (delimiter == null || delimiter.equals("")) {
			delimiter = ", ";
		}
		StringBuilder builder = new StringBuilder();
		for (BusinessErrors err : errs) {
			if (!errs.isEmpty() && !err.equals(errs.get(0))) {
				builder.append(delimiter);
			}
			builder.append(err.getErrLabel());

		}
		return builder.toString();
	}

}
