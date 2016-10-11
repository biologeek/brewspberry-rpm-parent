package net.brewspberry.front.ws;

import java.util.List;


import net.brewspberry.front.ws.beans.requests.BrewRequest;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;

public interface IBrewRESTService {

	
	
	//public Response getCompleteBrew(long brewID);
//	public Response getSimpleBrew(long brewID);
	public List<? extends SimpleBrewResponse> getAllBrews(String lightOrFull) throws Exception;	
	//	public Response getAllActiveBrews(String lightOrFull);
	//public Response addBrew(BrewRequest req);
	//public Response updateBrew(BrewRequest req);
}
