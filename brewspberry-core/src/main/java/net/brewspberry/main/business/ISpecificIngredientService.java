package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.brewing.AbstractIngredient;

public interface ISpecificIngredientService {
	<T extends AbstractIngredient> List<T>  getIngredientFromArrayId(String[] array, String[] arrayQte, String[] arrayPrix);

}
