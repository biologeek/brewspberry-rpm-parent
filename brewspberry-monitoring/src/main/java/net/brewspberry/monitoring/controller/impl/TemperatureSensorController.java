package net.brewspberry.monitoring.controller.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.Temperature;
import net.brewspberry.monitoring.converter.TemperatureConverter;
import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.services.TemperatureMeasurementService;
import net.brewspberry.monitoring.services.TemperatureSensorService;

@RestController
@RequestMapping("/temperature")
public class TemperatureSensorController {

	private static final String SENSOR_IDS_SEPARATOR = ".";
	private static final String SENSOR_UUIDS_SEPARATOR = ";";
	@Autowired
	private TemperatureSensorService temperatureSensorService;
	@Autowired
	private TemperatureConverter temperatureConverter;
	@Autowired
	private TemperatureMeasurementService temperatureMeasurementService;

	@RequestMapping(path = "/sensors/{sensors}", method = RequestMethod.GET)
	public ResponseEntity<List<Temperature>> getTemperaturesForSensors(@PathVariable("sensors") String sensors) {
		List<Long> deviceIds = processSensors(sensors);
		List<TemperatureMeasurement> measurements = temperatureSensorService.getTemperatureForDevices(deviceIds);
		return new ResponseEntity<List<Temperature>>(temperatureConverter.toApi(measurements), HttpStatus.OK);
	}

	@RequestMapping(path = "/sensors/uuid/{sensors}", method = RequestMethod.GET)
	public ResponseEntity<List<Temperature>> getTemperaturesForSensorsUuid(@PathVariable("sensors") String sensors) {
		List<String> deviceUuids = processSensorsUuids(sensors);
		List<TemperatureMeasurement> measurements = temperatureSensorService.getTemperatureForDevicesUuids(deviceUuids,
				true);
		return new ResponseEntity<List<Temperature>>(temperatureConverter.toApi(measurements), HttpStatus.OK);
	}

	@GetMapping("/sensors/uuid/{uuid}/measurements")
	public ResponseEntity<List<Temperature>> getTemperaturesForSensorAndPeriod(//
			@PathVariable("uuid") String uuid//
			, @RequestParam(value = "begin", required = false) LocalDateTime begin//
			, @RequestParam(value = "end", required = false) LocalDateTime end) {
		List<TemperatureMeasurement> measurements = null;
		try {
			measurements = this.temperatureMeasurementService.getMeasureMentsForUuidAndPeriod(uuid, begin, end);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(temperatureConverter.toApi(measurements), HttpStatus.OK);
	}

	/*
	 * Helper methods
	 */

	private List<String> processSensorsUuids(String sensors) {
		return Arrays.asList(sensors.split(SENSOR_UUIDS_SEPARATOR)).stream().collect(Collectors.toList());
	}

	/**
	 * Translates sensor IDs into Longs. Default seperator is "."
	 * 
	 * @param sensors
	 * @return
	 */
	private List<Long> processSensors(String sensors) {
		return Arrays.asList(sensors.split(SENSOR_IDS_SEPARATOR)).stream().map(new Function<String, Long>() {
			@Override
			public Long apply(String t) {
				return Long.valueOf(t);
			}
		}).collect(Collectors.toList());
	}
	
}