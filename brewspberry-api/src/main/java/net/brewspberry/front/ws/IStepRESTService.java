package net.brewspberry.front.ws;

import net.brewspberry.front.ws.beans.requests.CompleteStep;

public interface IStepRESTService {
	
	public CompleteStep add(CompleteStep step) throws Exception; 

}
