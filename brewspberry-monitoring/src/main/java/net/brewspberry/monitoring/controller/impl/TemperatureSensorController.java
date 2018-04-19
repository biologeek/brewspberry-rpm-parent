package net.brewspberry.monitoring.controller.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.ActionnerDto;
import net.brewspberry.monitoring.converter.Converter;
import net.brewspberry.monitoring.model.SwitchStatus;
import net.brewspberry.monitoring.model.TemperatureSensor;
import net.brewspberry.monitoring.services.DeviceService;

@RestController
public class TemperatureSensorController {

	@Autowired
	private DeviceService<TemperatureSensor> deviceService;

	public ResponseEntity<Set<ActionnerDto>> listDevices() {
		Set<TemperatureSensor> sensors = deviceService.listPluggedDevices();

		if (sensors == null || sensors.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(new Converter().toActionerApi(sensors), HttpStatus.OK);
	}
	
	@RequestMapping(path="/{action}/{id}", method=RequestMethod.PUT)
	public ResponseEntity<ActionnerDto> lightOnOrOffDevice(@PathVariable("action") SwitchStatus action, @PathVariable("id") Long id){
		TemperatureSensor result = null;
		
		if (action == SwitchStatus.UP)
			result = deviceService.switchOnDevice(id);
		else if (action == SwitchStatus.DOWN)
			result = deviceService.switchOffDevice(id);
		else 
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		return new ResponseEntity<>(new Converter().toActionerApi(result), HttpStatus.OK);
	}
	
}