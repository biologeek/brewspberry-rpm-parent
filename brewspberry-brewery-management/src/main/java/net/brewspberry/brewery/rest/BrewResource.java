package net.brewspberry.brewery.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.brewery.api.BrewDto;
import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.mappers.BrewMapper;
import net.brewspberry.brewery.model.Brew;
import net.brewspberry.brewery.service.BrewService;

@RestController
@RequestMapping("/brew")
public class BrewResource {

	@Autowired
	private BrewService brewService;
	@Autowired
	private BrewMapper brewMapper;

	@GetMapping("")
	public ResponseEntity<List<BrewDto>> getAllBrews() {
		return new ResponseEntity<>(this.brewMapper.toDto(this.brewService.getAllBrews()), HttpStatus.OK);
	}

	@GetMapping("/current")
	public ResponseEntity<BrewDto> getCurrentBrew() {
		return new ResponseEntity<>(this.brewMapper.toDto(this.brewService.getCurrentBrew()), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<BrewDto> createBrew(@RequestBody BrewDto brew) {

		Brew model;
		try {
			model = this.brewService.createBrew(brewMapper.toModel(brew));
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (ValidationException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<BrewDto>(this.brewMapper.toDto(model), HttpStatus.OK);
	}
}
