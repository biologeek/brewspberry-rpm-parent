package net.brewspberry.main.front.ws;

import net.brewspberry.main.front.ws.beans.requests.CompleteStep;
import net.brewspberry.main.front.ws.beans.responses.SimpleStepResponse;

public interface IStepProcessController {
	
	public CompleteStep add(CompleteStep step) throws Exception; 
	public CompleteStep get(long stepID) throws Exception; 

}
