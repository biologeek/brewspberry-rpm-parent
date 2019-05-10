package net.brewspberry.brewery.service.impl;

import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.Additive;
import net.brewspberry.brewery.model.Malt;
import net.brewspberry.brewery.service.IngredientBusinessService;

@Service
public class DefaultAdditiveBusinessService implements IngredientBusinessService<Additive> {

	@Override
	public Additive preSave(AbstractIngredient bean) throws ValidationException, ServiceException {
		// TODO Auto-generated method stub
		return (Additive) bean;
	}

	@Override
	public Additive postSave(AbstractIngredient bean) throws ServiceException {
		// TODO Auto-generated method stub
		return (Additive) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Additive merge(AbstractIngredient elt, AbstractIngredient savedOne) {
		// TODO Auto-generated method stub
		return (Additive) savedOne;
	}

}
