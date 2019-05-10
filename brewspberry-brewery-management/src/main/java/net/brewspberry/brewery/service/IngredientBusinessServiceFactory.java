package net.brewspberry.brewery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.brewspberry.brewery.model.AbstractIngredient;

@Service
public class IngredientBusinessServiceFactory {

	@Autowired
	ApplicationContext ctx;

	@SuppressWarnings("unchecked")
	public <T extends AbstractIngredient> IngredientBusinessService<T> getBusinessService(Class<T> clazz) {
		return (IngredientBusinessService<T>) ctx.getBean("default" + clazz.getSimpleName() + "BusinessService");
	}
}
