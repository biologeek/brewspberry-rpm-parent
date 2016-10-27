package net.brewspberry.front.ws.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Actioner;
import net.brewspberry.front.ws.beans.dto.ActionnerDTO;
import net.brewspberry.front.ws.beans.responses.ActionnerResponse;

@RequestMapping("/actionner")
@RestController
public class ActionnerRestServiceImpl {

	
	
	
	@Autowired
	@Qualifier("actionerServiceImpl")
	private IGenericService<Actioner> actionnerService;

	@GetMapping("/available")
	@ResponseBody
	public List<ActionnerResponse> getAvailableActionners(){
		
		return new ActionnerDTO().toRawActionnerResponse(actionnerService.getAllDistinctElements());
		
	}
}
