package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.IngredientJSONRequest;

public interface IProductRESTService {
	
	
	public Response addIngredient(IngredientJSONRequest request);
	
	
	
}
