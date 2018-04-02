package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.monitoring.MultiActionnerTemperatures;

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
	
	public List<ConcreteTemperatureMeasurement> getTemperaturesByStepAndUUID(
			Etape stepID, String uuid, Long lastID);
	public MultiActionnerTemperatures getTemperaturesForActionners(List<Actioner> actionners);

}