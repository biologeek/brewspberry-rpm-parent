package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.front.ws.beans.requests.CompleteStep;

public interface IStepRESTService {
	
	public Response add(CompleteStep step); 

}
