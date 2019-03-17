package net.brewspberry.monitoring.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.RaspberryPinDto;
import net.brewspberry.monitoring.converter.PinConverter;
import net.brewspberry.monitoring.services.PinServices;

@RestController
@RequestMapping("/raspberry")
public class RaspberryController {

	@Autowired
	private PinConverter pinMapper;
	
	@Autowired
	private PinServices pinServices;

	@GetMapping("/pins")
	public ResponseEntity<List<RaspberryPinDto>> getPins() {
		return new ResponseEntity<>(this.pinMapper.toDto(this.pinServices.getAllPins()), HttpStatus.OK);
	}
}
