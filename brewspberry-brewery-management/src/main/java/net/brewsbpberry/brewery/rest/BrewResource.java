package net.brewsbpberry.brewery.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewsbpberry.brewery.mappers.BrewMapper;
import net.brewsbpberry.brewery.service.BrewService;
import net.brewspberry.brewery.api.BrewDto;

@RestController
@RequestMapping("/brew")
public class BrewResource {

	
	@Autowired
	private BrewService brewService;
	@Autowired
	private BrewMapper brewMapper;

	public List<BrewDto> getAllBrews(){
		return this.brewMapper.toDto(this.brewService.getAllBrews());
	}
}
