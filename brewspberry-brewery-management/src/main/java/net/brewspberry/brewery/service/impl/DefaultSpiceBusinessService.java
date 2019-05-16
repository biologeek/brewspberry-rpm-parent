package net.brewspberry.brewery.service.impl;

import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.Spice;
import net.brewspberry.brewery.service.IngredientBusinessService;

@Service
public class DefaultSpiceBusinessService implements IngredientBusinessService<Spice> {

	@Override
	public Spice preSave(AbstractIngredient bean) throws ValidationException, ServiceException {
		// TODO Auto-generated method stub
		return (Spice) bean;
	}

	@Override
	public Spice postSave(AbstractIngredient bean) throws ServiceException {
		// TODO Auto-generated method stub
		return (Spice) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Spice merge(AbstractIngredient elt, AbstractIngredient savedOne) {

		Spice eltH = (Spice) elt;
		Spice savedOneH = (Spice) savedOne;
		
		savedOneH.setShape(eltH.getShape());
		return (Spice) savedOne;
	}

}
