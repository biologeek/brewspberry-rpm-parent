package net.brewspberry.business.validation;

import java.util.List;

public interface Validator<T> {
	
	
	public void setOTherParameters(Object... otherParams);
	public List<BusinessError> validate(T toVal);
	
	public String computeErrors(List<BusinessError> errs, String delimiter);

}
