package net.brewspberry.brewery.service;

import java.util.List;

import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;

public interface IngredientBusinessService<T extends AbstractIngredient> {
	

	public T preSave(AbstractIngredient bean) throws ValidationException, ServiceException;
	
	public T postSave(AbstractIngredient bean) throws ServiceException;

	public void validate(AbstractIngredient toSave) throws ValidationException;

	public T merge(AbstractIngredient elt, AbstractIngredient savedOne);

	public List<T> getAll() throws ServiceException, ElementNotFoundException;

}
