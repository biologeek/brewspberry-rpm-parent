package net.brewspberry.front.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebMethod;
import javax.jws.WebService;

import net.brewspberry.business.ISpecificTemperatureMeasurementService;
import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.business.service.TemperatureMeasurementServiceImpl;

@WebService
public class TemperatureWSImpl implements ITemperatureWS {

	ISpecificTemperatureMeasurementService specTMesService = new TemperatureMeasurementServiceImpl();

	String uuidRegex = "[0-9a-f]{2}-[0-9a-f]{12}";

	@Override
	@WebMethod
	public ConcreteTemperatureMeasurement getTemperatureMeasurementByProbe(
			String uuidOrname, Boolean uuid) {

		
		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();

		if (uuidOrname != null && uuid != null) {

			if (uuid) {
				// Search by UUID

				Pattern p = Pattern.compile(uuidRegex);
				Matcher m = p.matcher(uuidOrname);

				System.out.println(uuidOrname);
				if (m.matches()) {

					try {
						result = specTMesService
								.getLastTemperatureMeasurementByUUID(uuidOrname);
					} catch (Exception e) {
						
						e.printStackTrace();
					}

				} else {
					System.out.println("Input doesn't match !!!");
				}

			} else {
				try {
					result = specTMesService
							.getLastTemperatureMeasurementByName(uuidOrname);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	@WebMethod
	public List<ConcreteTemperatureMeasurement> getAllTemperatureMeasurements(
			List<String> uuidOrname, Boolean uuid) throws Exception {
		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();

		try {
			result = specTMesService.getAllLastTemperatureMeasurements(
					uuidOrname, uuid);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		if (result.size() <= 0) {

			throw new Exception("Result list empty");

		}

		return result;
	}

	@Override
	public ConcreteTemperatureMeasurement getCSVTemperatureMeasurementByProbe(
			String uuidOrname, Boolean uuid) {
		ConcreteTemperatureMeasurement result = new ConcreteTemperatureMeasurement();
		
		if (uuid != null){
			
			try {
				
				result = specTMesService.getLastTemperatureMeasurementsByNameFromCSV(uuidOrname,uuid);
				
				
			} catch (Exception e ){
				
				e.printStackTrace();
				
			}			
		}	
		
		return result;
	}

	@Override
	public List<ConcreteTemperatureMeasurement> getAllCSVTemperatureMeasurements(
			List<String> uuidOrname, Boolean uuid) throws Exception {
		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		
		if (uuid != null){
			
			try {
				
				result = specTMesService.getAllLastTemperatureMeasurementsFromCSV(uuidOrname,uuid);
				
				
			} catch (Exception e ){
				
				e.printStackTrace();
				
			}
			
		}
		
		
		return result;
	}

}
