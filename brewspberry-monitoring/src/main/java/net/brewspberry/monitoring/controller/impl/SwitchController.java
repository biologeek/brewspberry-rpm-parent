package net.brewspberry.monitoring.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.DeviceDto;
import net.brewspberry.monitoring.converter.DeviceConverter;
import net.brewspberry.monitoring.exceptions.DefaultRuntimeException;
import net.brewspberry.monitoring.exceptions.StateChangeException;
import net.brewspberry.monitoring.services.BinarySwitchService;

@RestController
@RequestMapping("/switch")
public class SwitchController {
	
	@Autowired
	private BinarySwitchService switchService;
	@Autowired
	private DeviceConverter deviceConverter;

	@PutMapping("/{id}")
	public ResponseEntity<DeviceDto> switchPin(@PathVariable("id") Long id){
		
		try {
			return new ResponseEntity<>(this.deviceConverter.toApi(this.switchService.toggleSwitch(id)), HttpStatus.OK);
		} catch (StateChangeException e) {
			throw new DefaultRuntimeException(e.getMessage(), "");
		}
	}

	
	
}
