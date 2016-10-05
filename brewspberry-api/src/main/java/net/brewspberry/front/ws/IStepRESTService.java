package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.requests.SimpleStepRequest;

public interface IStepRESTService {
	
	public Response add(long brewID, SimpleStepRequest step); 

}
