package net.brewspberry.brewery.service.impl;

import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.Yeast;
import net.brewspberry.brewery.service.IngredientBusinessService;

@Service
public class DefaultYeastBusinessService implements IngredientBusinessService<Yeast> {

	@Override
	public Yeast preSave(AbstractIngredient bean) throws ValidationException, ServiceException {
		// TODO Auto-generated method stub
		return (Yeast) bean;
	}

	@Override
	public Yeast postSave(AbstractIngredient bean) throws ServiceException {
		// TODO Auto-generated method stub
		return (Yeast) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Yeast merge(AbstractIngredient elt, AbstractIngredient savedOne) {

		Yeast eltH = (Yeast) elt;
		Yeast savedOneH = (Yeast) savedOne;
		savedOneH.setDensityLevel(eltH.getDensityLevel());
		savedOneH.setFerementationTemperatureRange(eltH.getFerementationTemperatureRange());
		savedOneH.setSpecie(eltH.getSpecie());
		return (Yeast) savedOne;
	}

}
