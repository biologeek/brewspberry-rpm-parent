package net.brewspberry.front.ws.stock;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface IStockRESTService {

	public Response getStockForProduct(@PathParam("p") long productID);

	public Response getStockForFinishedProducts();

	public Response getStockForIngredients();

	public Response modifyStockForCounter(@PathParam("p") long productID, @PathParam("v") double stockMotion,
			@PathParam("t") long counterTypeID);
}
