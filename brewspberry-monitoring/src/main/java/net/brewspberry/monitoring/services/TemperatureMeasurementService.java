package net.brewspberry.monitoring.services;

import java.time.LocalDateTime;
import java.util.List;

import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;

public interface TemperatureMeasurementService {

	List<TemperatureMeasurement> getMeasureMentsForUuidAndPeriod(String uuid, LocalDateTime begin, LocalDateTime end) throws ElementNotFoundException;

}
