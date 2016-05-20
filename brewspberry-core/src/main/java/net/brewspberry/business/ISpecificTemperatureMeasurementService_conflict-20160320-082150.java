package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.TemperatureMeasurement;

public interface ISpecificTemperatureMeasurementService {
	
	
	public List<TemperatureMeasurement> getTemperatureMeasurementByBrassin(Brassin bid);
	public List<TemperatureMeasurement> getTemperatureMeasurementByEtape(Etape etape);
	
	public TemperatureMeasurement getLastTemperatureMeasurementByUUID (String uuid) throws Exception;
	public TemperatureMeasurement getLastTemperatureMeasurementByName(String name) throws Exception;
	public List<TemperatureMeasurement> getTemperatureMeasurementsAfterID(Etape etape, String uuid, long tmesID, int modulo);
	public List<TemperatureMeasurement> getAllLastTemperatureMeasurements(List<String> uuidOrName, Boolean uuid) throws Exception;
	
	public List<TemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV (List<String> uuidOrName, Boolean uuid) throws Exception;
	public TemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV (String uuidOrName, Boolean uuid) throws Exception;
	public List<TemperatureMeasurement> getLastTemperatureByStepAndUUID(
			Etape stepID, String uuid);

}
