package net.brewspberry.front.ws;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.requests.IngredientRequest;

public interface IProductRESTService {
	
	
	public Response addIngredient(IngredientRequest request);
	public Response getIngredient(String type, long id);
	public Response deleteIngredient(IngredientRequest request);
	Response updateIngredient(long id, IngredientRequest req);
	
	
	
	
}
