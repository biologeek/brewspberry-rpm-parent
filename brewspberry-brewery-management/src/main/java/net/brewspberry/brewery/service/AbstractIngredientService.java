package net.brewspberry.brewery.service;

import java.util.List;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;

/**
 * High level service handling common operations over ingredients.<br>
 * <br>
 * For ingredient-specific business rules, refer to
 * {@link IngredientBusinessService}
 * 
 * @author xavier
 *
 */
public interface AbstractIngredientService {

	<T extends AbstractIngredient> T save(T toSave) throws ValidationException, ServiceException;
	
	<T extends AbstractIngredient> T getIngredient(Class<T> clazz, Long id);

	<T extends AbstractIngredient> List<T> updateAll(List<T> toSave) throws ServiceException, ValidationException;

}
