package net.brewspberry.front.ws;

import net.brewspberry.front.ws.beans.requests.CompleteStep;
import net.brewspberry.front.ws.beans.responses.SimpleStepResponse;

public interface IStepRESTService {
	
	public CompleteStep add(SimpleStepResponse step) throws Exception; 
	public CompleteStep get(long stepID) throws Exception; 

}
