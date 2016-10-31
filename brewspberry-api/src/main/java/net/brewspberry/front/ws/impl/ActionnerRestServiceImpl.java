package net.brewspberry.front.ws.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.RaspiPin;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificActionerService;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.business.beans.GenericActionner;
import net.brewspberry.business.beans.RaspiPins;
import net.brewspberry.business.validation.GenericActionnerValidator;
import net.brewspberry.business.validation.Validator;
import net.brewspberry.front.ws.beans.dto.ActionnerDTO;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse.ActionerType;
import net.brewspberry.util.Constants;

@RequestMapping("/actionner")
@RestController
public class ActionnerRestServiceImpl {

	
	

	@Autowired
	@Qualifier("actionerServiceImpl")
	private IGenericService<Actioner> actionnerService;
	

	@Autowired
	@Qualifier("genericActionnerServiceImpl")
	private IGenericService<GenericActionner> genericActionnerService;
	
	@Autowired
	@Qualifier("actionerServiceImpl")
	private ISpecificActionerService actionnerSpecService;

	@GetMapping("/available")
	@ResponseBody
	public List<ActionnerResponse> getAvailableActionners(){
		
		return new ActionnerDTO().toRawActionnerResponse(actionnerSpecService.getAllGenericActionners()).buildAPI();	
	}
	
	
	@PostMapping("/save")
	@ResponseBody
	public ActionnerResponse saveGenericActionner(@RequestBody ActionnerResponse arg0) throws Exception{
		
		Validator<GenericActionner> validator = new GenericActionnerValidator();
		
		GenericActionner genAct = new ActionnerDTO().toBusinessObject(arg0);
		
		ActionnerResponse result = null;
		
		if (validator.validate(genAct) == null || validator.validate(genAct).isEmpty()){
			
			 result = new ActionnerDTO().toRawActionnerResponse(genericActionnerService.save(genAct));
			
		}
		
		return result;
	}
	
	@GetMapping("/types")
	public List<ActionerType> getActionnerTypes(){
		
		return Arrays.asList(ActionerType.values());
	}
	

	
	@GetMapping("/pins")
	public List<String> getPins(){
		
		return Arrays.asList(RaspiPins.getRealPins());
	}
}
