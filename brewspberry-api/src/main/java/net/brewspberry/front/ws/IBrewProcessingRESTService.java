package net.brewspberry.front.ws;

import javax.ws.rs.core.Response;

import net.brewspberry.business.beans.Etape;

public interface IBrewProcessingRESTService {
	

	public Response startStep (long etape) throws Exception;
	public Response endStep (long etape);
	public Response getAllActiveBrews();
	public Response getAllBrews();
	

}
