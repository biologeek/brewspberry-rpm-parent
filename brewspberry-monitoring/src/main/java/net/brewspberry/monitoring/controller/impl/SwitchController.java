package net.brewspberry.monitoring.controller.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.monitoring.api.Temperature;
import net.brewspberry.monitoring.converter.TemperatureConverter;
import net.brewspberry.monitoring.exceptions.ElementNotFoundException;
import net.brewspberry.monitoring.exceptions.ElementNotFoundRuntimeException;
import net.brewspberry.monitoring.services.BinarySwitchService;

@RestController
@RequestMapping("/switch")
public class SwitchController {

	@Autowired
	private BinarySwitchService switchService;

	@Autowired
	private TemperatureConverter temperatureConverter;

	@GetMapping("/sensors/uuid/{uuid}/measurements")
	public ResponseEntity<List<Temperature>> getSwitchStatesForDates(//
			@PathVariable("uuid") String uuid//
			, @RequestParam(value = "begin", required = false) LocalDateTime begin//
			, @RequestParam(value = "end", required = false) LocalDateTime end) {

		try {
			return new ResponseEntity<>(this.temperatureConverter.toApiSwitch(//
					this.switchService.getSwitchStatesForDates(uuid, begin, end)//
			), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundRuntimeException();
		}
	}

}
