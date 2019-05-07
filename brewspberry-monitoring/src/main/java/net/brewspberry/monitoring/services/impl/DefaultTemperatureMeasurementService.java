package net.brewspberry.monitoring.services.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.repositories.TemperatureMeasurementRepository;
import net.brewspberry.monitoring.services.TemperatureMeasurementService;
import net.brewspberry.monitoring.services.TemperatureSensorService;

@Service
public class DefaultTemperatureMeasurementService implements TemperatureMeasurementService {

	@Autowired
	private TemperatureSensorService temperatureSensorService;
	@Autowired
	private TemperatureMeasurementRepository temperatureMeasurementRepo;
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public List<TemperatureMeasurement> getMeasureMentsForUuidAndPeriod(String uuid, LocalDateTime begin,
			LocalDateTime end) throws ElementNotFoundException {
		if (this.temperatureSensorService.getDeviceByUUID(uuid) == null){
			throw new ElementNotFoundException();
		}

		List<TemperatureMeasurement> measurements = null;

		if (begin != null && end != null) {
			measurements = this.temperatureMeasurementRepo.findAllByUuidBetween(uuid, begin, end);
		} else if (begin == null && end != null) {
			measurements = temperatureMeasurementRepo.findAllByUuidBefore(uuid, end);
		} else if (begin != null && end == null) {
			measurements = temperatureMeasurementRepo.findAllByUuidAfter(uuid, begin);
		} else {
			begin = LocalDateTime.now().minus(Duration.ofHours(2L));
			measurements = temperatureMeasurementRepo.findAllByUuidAfter(uuid, begin);
		}

		if (measurements == null) {
			logger.fine("Could not find device " + uuid);
			throw new ElementNotFoundException();
		}
		return measurements;
	}

}
