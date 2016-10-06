package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.requests.BrewRequest;

public interface IBrewRESTService {

	
	
	public Response getCompleteBrew(long brewID);
	public Response getSimpleBrew(long brewID);
	public Response getAllBrews(String lightOrFull);	
	public Response getAllActiveBrews(String lightOrFull);
	public Response addBrew(BrewRequest req);
	public Response updateBrew(BrewRequest req);
}
