package net.brewspberry.monitoring.controller.impl;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.Temperature;
import net.brewspberry.monitoring.api.request.TemperatureBatchRunRequestBody;
import net.brewspberry.monitoring.converter.TemperatureConverter;
import net.brewspberry.monitoring.model.TemperatureMeasurement;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.services.TemperatureSensorService;

@RestController
@RequestMapping("/temperature")
public class TemperatureSensorController {

	private static final String SENSOR_IDS_SEPARATOR = ".";
	@Autowired
	private TemperatureSensorService temperatureSensorService;

	/**
	 * Runs a temperature measurements batch <br>
	 * <br>
	 * Parameters to give :<br>
	 * - <b>duration</b> in ms<br>
	 * - <b>frequency</b> in ms<br>
	 * - <b>devices</b> : if list is null or empty, all devices are monitored. Else
	 * only devices with given UUIDs will be measured<br>
	 * - <b>externalId</b> : eventually the object to which temperature measurements
	 * are related to. It may be a brew or a step of a brew for example
	 * 
	 * @param body
	 *            contains batch parameters. Some are mandatory such as frequency
	 *            and duration and given in milliseconds
	 * @return
	 */
	@RequestMapping(path = "/run")
	public ResponseEntity<Void> launchTemperatureMeasurement(@RequestBody TemperatureBatchRunRequestBody body) {
		temperatureSensorService.runRegularTemperatureMeasurementStr(body.getDevices(), bodyToParameters(body));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(path = "/sensors", method = RequestMethod.GET)
	public ResponseEntity<List<Temperature>> getTemperaturesForSensors(@PathVariable("sensors") String sensors) {
		List<Long> deviceIds = processSensors(sensors);
		List<TemperatureMeasurement> measurements = temperatureSensorService.getTemperatureForDevices(deviceIds);
		return new ResponseEntity<List<Temperature>>(new TemperatureConverter().toApi(measurements), HttpStatus.OK);
	}
	
	private Map<String, Object> bodyToParameters(TemperatureBatchRunRequestBody body) {
		Map<String, Object> params = new HashMap<>();
		params.put(TemperatureSensor.DURATION, Duration.ofMillis(body.getDuration()));
		params.put(TemperatureSensor.EXTERNAL_ID, body.getExternalId());
		params.put(TemperatureSensor.FREQUENCY, Duration.ofMillis(body.getFrequency()));
		return params;
	}

	/**		
	 * Translates sensor IDs into Longs. Default seperator is "."
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