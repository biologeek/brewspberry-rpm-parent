package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;

public interface ISpecificTemperatureMeasurementService {
	
	
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByBrassin(Brassin bid);
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementByEtape(Etape etape);
	
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByUUID (String uuid) throws Exception;
	public List<ConcreteTemperatureMeasurement> getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay (Etape etapeID, String uuid, int numberOfPoints, float delay) throws Exception;
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementByName(String name) throws Exception;
	public List<ConcreteTemperatureMeasurement> getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(Etape etape, String uuid, int NumberOfPoints, long tmesID, float delayInSeconds);
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurements(List<String> uuidOrName, Boolean uuid) throws Exception;
	
	public List<ConcreteTemperatureMeasurement> getAllLastTemperatureMeasurementsFromCSV (List<String> uuidOrName, Boolean uuid) throws Exception;
	public ConcreteTemperatureMeasurement getLastTemperatureMeasurementsByNameFromCSV (String uuidOrName, Boolean uuid) throws Exception;
	public List<ConcreteTemperatureMeasurement> getLastTemperatureByStepAndUUID(
			Etape stepID, String uuid);

}