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
		return (Hop) bean;
	}

	@Override
	public Hop postSave(AbstractIngredient bean) throws ServiceException {
		return (Hop) bean;
	}

	@Override
	public void validate(AbstractIngredient toSave) throws ValidationException {
		
	}

	@Override
	public Hop merge(AbstractIngredient elt, AbstractIngredient savedOne) {
		Hop eltH = (Hop) elt;
		Hop savedOneH = (Hop) savedOne;
		savedOneH.setAlphaAcid(eltH.getAlphaAcid());
		savedOneH.setType(eltH.getType());
		return (Hop) savedOne;
	}

}
