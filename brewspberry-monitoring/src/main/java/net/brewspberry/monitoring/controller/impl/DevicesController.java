package net.brewspberry.monitoring.controller.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.converter.DeviceConverter;
import net.brewspberry.monitoring.exceptions.ServiceException;
import net.brewspberry.monitoring.model.AbstractDevice;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.services.DeviceService;

@RequestMapping("/devices")
@RestController
public class DevicesController {

	@Autowired
	private DeviceService<AbstractDevice> deviceServices;
	@Autowired
	private DeviceService<TemperatureSensor> temperatureSensorServices;
	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public Set<DeviceDto> getAllDevices(){
		return new DeviceConverter().toApi(deviceServices.listAllDevices());
	}

	@RequestMapping(path = "/temperature", method = RequestMethod.GET)
	public ResponseEntity<Set<DeviceDto>> listTemperatureSensors() {
		Set<TemperatureSensor> sensors = temperatureSensorServices.listPluggedDevices();

		if (sensors == null || sensors.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new DeviceConverter().toApiFromSensors(sensors), HttpStatus.OK);
	}

	@RequestMapping(path = "/temperature/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<Void> savePluggedSensor(@PathVariable("uuid") String uuid) throws ServiceException {
		TemperatureSensor sensor = temperatureSensorServices.getDeviceByUUID(uuid);

		if (sensor == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		temperatureSensorServices.saveDevice(sensor);
				
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
