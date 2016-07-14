package net.brewspberry.front.ws;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

public interface IRESTStockWS {
	
	
	public Response getStockForProduct(@PathParam("p") long productID);
	public Response getStockForFinishedProducts();
	
}
