package net.brewspberry.brewery.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.model.AbstractIngredient;

@Service
public class IngredientBusinessServiceFactory {

	@Autowired
	ApplicationContext ctx;

	@SuppressWarnings("unchecked")
	public <T extends AbstractIngredient> IngredientBusinessService<T> getBusinessService(Class<T> clazz) {
		return (IngredientBusinessService<T>) ctx.getBean("default" + clazz.getSimpleName() + "BusinessService");
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractIngredient> IngredientBusinessService<T> getBusinessService(String clazz) throws ServiceException {
		try {
			return (IngredientBusinessService<T>) ctx
				.getBean("default" + clazz.substring(0, 1).toUpperCase() + clazz.substring(1) + "BusinessService");
		} catch (BeansException e) {
			throw new ServiceException(e.getMessage(), "ibsfacotry.nobean");
		}
	}
}
