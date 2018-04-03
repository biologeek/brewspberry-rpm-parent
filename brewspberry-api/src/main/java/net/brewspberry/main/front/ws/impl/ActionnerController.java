package net.brewspberry.main.front.ws.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.RaspiPin;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificActionerLauncherService;
import net.brewspberry.main.business.ISpecificActionerService;
import net.brewspberry.main.business.beans.monitoring.Actioner;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;
import net.brewspberry.main.business.beans.monitoring.RaspiPins;
import net.brewspberry.main.business.exceptions.ValidationException;
import net.brewspberry.main.business.validation.BusinessError;
import net.brewspberry.main.business.validation.GenericActionnerValidator;
import net.brewspberry.main.business.validation.Validator;
import net.brewspberry.main.front.ws.beans.dto.ActionnerDTO;
import net.brewspberry.main.front.ws.beans.responses.ActionnerResponse;
import net.brewspberry.main.front.ws.beans.responses.ActionnerResponse.ActionerType;
import net.brewspberry.main.util.Constants;

@RequestMapping("/device")
@RestController

public class ActionnerController {

	@Autowired
	@Qualifier("actionerServiceImpl")
	private IGenericService<Actioner> actionnerService;

	@Autowired
	@Qualifier("genericActionnerServiceImpl")
	private IGenericService<GenericActionner> genericActionnerService;

	@Autowired
	@Qualifier("actionerServiceImpl")
	private ISpecificActionerService actionnerSpecService;
	
	@Autowired
	ISpecificActionerLauncherService batchLauncherService;

	@GetMapping("/available")
	@ResponseBody
	public List<net.brewspberry.main.front.ws.beans.responses.GenericActionner> getAvailableActionners() {
		ActionnerDTO dto = new ActionnerDTO();
		return dto.new GenericActionnerDTO().toRawActionnerResponse(actionnerSpecService.getAllGenericActionners(), false);
	}

	@PostMapping("/save")
	@ResponseBody
	public net.brewspberry.main.front.ws.beans.responses.GenericActionner saveGenericActionner(
			@RequestBody net.brewspberry.main.front.ws.beans.responses.GenericActionner arg0) throws Exception {
		ActionnerDTO dto = new ActionnerDTO();
		Validator<GenericActionner> validator = new GenericActionnerValidator();

		GenericActionner genAct = dto.new GenericActionnerDTO().toBusinessObject(arg0);

		net.brewspberry.main.front.ws.beans.responses.GenericActionner result = null;
		
		
		/*
		 * Validating against recorded actionners
		 */
		List<GenericActionner> actionnersList = genericActionnerService.getAllElements();

		validator.setOTherParameters(actionnersList);
		List<BusinessError> errs = validator.validate(genAct);
		
		if (errs == null || errs.isEmpty()) {

			result = dto.new GenericActionnerDTO().toRawActionnerResponse(genericActionnerService.save(genAct), false);

		} else {
			throw new ValidationException(errs);
		}

		return result;
	}

	@GetMapping("/types")
	public List<ActionerType> getActionnerTypes() {
		return Arrays.asList(ActionerType.values());
	}

	@GetMapping("/pins")
	public List<String> getPins() {
		return Arrays.asList(RaspiPins.getRealPins());
	}

	@GetMapping("/")
	public List<net.brewspberry.main.front.ws.beans.responses.GenericActionner> getAllActionners() {
		ActionnerDTO dto = new ActionnerDTO();
		return dto.new GenericActionnerDTO().toRawActionnerResponse(genericActionnerService.getAllElements(), false);
	}
	
	
	
	@PostMapping("/activate/{actId}")
	/**
	 * Method used for activating an actionner. This starts actionner monitoring and stores the action in database.
	 * 
	 * For temperature sensors, it starts temperature measurements
	 * For pumps, engines, ... it starts device and records start time
	 * 
	 * @param actionerId the ID of the actionner to start
	 * 
	 * @return empty response if OK
	 */
	public ResponseEntity<String> activateActioner(@PathVariable("actId") Long actionerId){
		
		if (actionerId != null && actionerId > 0){
			
			try {
				batchLauncherService.startAction(actionerId);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body("Could not start action due to internal error");
			}
		}
		
		return ResponseEntity.ok().body("");		
	}
}
