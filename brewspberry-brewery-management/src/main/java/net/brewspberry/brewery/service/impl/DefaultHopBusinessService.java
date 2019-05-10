package net.brewspberry.brewery.service.impl;

import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.Hop;
import net.brewspberry.brewery.service.IngredientBusinessService;

@Service
public class DefaultHopBusinessService implements IngredientBusinessService<Hop> {

	@Override
	public Hop preSave(AbstractIngredient bean) throws ValidationException, ServiceException {
		// TODO Auto-generated method stub
		return (Hop) bean;
	}

	@Override
	public Hop postSave(AbstractIngredient bean) throws ServiceException {
		// TODO Auto-generated method stub
		return (Hop) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Hop merge(AbstractIngredient elt, AbstractIngredient savedOne) {
		// TODO Auto-generated method stub
		return (Hop) savedOne;
	}

}
