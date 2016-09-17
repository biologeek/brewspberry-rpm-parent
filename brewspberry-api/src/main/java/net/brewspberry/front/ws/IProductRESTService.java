package net.brewspberry.front.ws;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.IngredientJSONRequest;

public interface IProductRESTService {
	
	
	public Response addIngredient(IngredientJSONRequest request);
	public Response getIngredient(String type, long id);
	public Response deleteIngredient(IngredientJSONRequest request);
	Response updateIngredient(long id, IngredientJSONRequest req);
	
	
	
	
}
