package net.brewspberry.monitoring.controller.impl;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.api.request.TemperatureBatchRunRequestBody;
import net.brewspberry.monitoring.converter.DeviceConverter;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.services.TemperatureSensorService;

@RestController
@RequestMapping("/temperature")
public class TemperatureSensorController {

	@Autowired
	private TemperatureSensorService deviceService;

	@RequestMapping(path = "/sensors", method = RequestMethod.GET)
	public ResponseEntity<Set<DeviceDto>> listDevices() {
		Set<TemperatureSensor> sensors = deviceService.listPluggedDevices();

		if (sensors == null || sensors.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new DeviceConverter().toApiFromSensors(sensors), HttpStatus.OK);
	}

	@RequestMapping(path = "/sensor/{id}/{action}", method = RequestMethod.PUT)
	/**
	 * Will "activate" given device. This function must be used as a kind of
	 * pre-measuring step. Does not actually measure anything
	 * 
	 * @param action
	 *            if UP, activates device, if DOWN, deactivates it
	 * @param id
	 *            technical ID of the deviceT
	 * @return
	 */
	public ResponseEntity<DeviceDto> lightOnOrOffDevice(@PathVariable("action") SwitchStatus action,
			@PathVariable("id") Long id) {
		TemperatureSensor result = null;

		if (action == SwitchStatus.UP)
			result = deviceService.switchOnDevice(id);
		else if (action == SwitchStatus.DOWN)
			result = deviceService.switchOffDevice(id);
		else
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(new DeviceConverter().toApi(result), HttpStatus.OK);
	}

	/**
	 * Runs a temperature measurements batch <br>
	 * <br>
	 * Parameters to give :<br>
	 * - <b>duration</b> in ms<br>
	 * - <b>frequency</b> in ms<br>
	 * - <b>devices</b> : if list is null or empty, all devices are monitored. Else only
	 * devices with given UUIDs will be measured<br>
	 * - <b>externalId</b> : eventually the object to which temperature measurements are
	 * related to. It may be a brew or a step of a brew for example
	 * 
	 * @param body
	 *            contains batch parameters. Some are mandatory such as frequency
	 *            and duration and given in milliseconds
	 * @return
	 */
	@RequestMapping(path = "/run")
	public ResponseEntity<Void> launchTemperatureMeasurement(@RequestBody TemperatureBatchRunRequestBody body) {
		deviceService.runRegularTemperatureMeasurementStr(body.getDevices(), bodyToParameters(body));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private Map<String, Object> bodyToParameters(TemperatureBatchRunRequestBody body) {
		Map<String, Object> params = new HashMap<>();
		params.put(TemperatureSensor.DURATION, Duration.ofMillis(body.getDuration()));
		params.put(TemperatureSensor.EXTERNAL_ID, body.getExternalId());
		params.put(TemperatureSensor.FREQUENCY, Duration.ofMillis(body.getFrequency()));
		return params;
	}

}