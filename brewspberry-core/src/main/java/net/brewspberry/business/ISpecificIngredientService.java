package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.AbstractIngredient;

public interface ISpecificIngredientService {
	<T extends AbstractIngredient> List<T>  getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix);

}
