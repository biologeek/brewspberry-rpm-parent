package net.brewspberry.business.validation;

import java.util.List;

public interface Validator<T> {
	
	public List<BusinessErrors> validate(T toVal);

}
