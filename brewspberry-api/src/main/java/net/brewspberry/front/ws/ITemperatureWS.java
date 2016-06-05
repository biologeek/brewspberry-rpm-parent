package net.brewspberry.front.ws;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;

@WebService
@SOAPBinding(style=Style.RPC)
public interface ITemperatureWS {

	
	@WebMethod ConcreteTemperatureMeasurement getTemperatureMeasurementByProbe (String uuidOrname, Boolean uuid);
	@WebMethod List<ConcreteTemperatureMeasurement> getAllTemperatureMeasurements(
			List<String> uuidOrname, Boolean uuid) throws Exception;
	
	@WebMethod ConcreteTemperatureMeasurement getCSVTemperatureMeasurementByProbe (String uuidOrname, Boolean uuid);
	@WebMethod List<ConcreteTemperatureMeasurement> getAllCSVTemperatureMeasurements(
			List<String> uuidOrname, Boolean uuid) throws Exception;
	
}
