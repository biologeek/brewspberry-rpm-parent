package net.brewspberry.brewery.service.impl;

import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.model.Malt;
import net.brewspberry.brewery.service.IngredientBusinessService;

@Service
public class DefaultMaltBusinessService implements IngredientBusinessService<Malt> {

	@Override
	public Malt preSave(AbstractIngredient bean) throws ValidationException, ServiceException {
		// TODO Auto-generated method stub
		return (Malt) bean;
	}

	@Override
	public Malt postSave(AbstractIngredient bean) throws ServiceException {
		// TODO Auto-generated method stub
		return (Malt) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Malt merge(AbstractIngredient elt, AbstractIngredient savedOne) {
		// TODO Auto-generated method stub
		return (Malt) savedOne;
	}

}
