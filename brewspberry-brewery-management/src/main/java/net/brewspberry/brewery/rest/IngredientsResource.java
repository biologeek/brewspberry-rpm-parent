package net.brewspberry.brewery.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.brewery.exceptions.ElementNotFoundException;
import net.brewspberry.brewery.exceptions.ServiceException;
import net.brewspberry.brewery.mappers.AdditiveMapper;
import net.brewspberry.brewery.mappers.HopMapper;
import net.brewspberry.brewery.mappers.MaltMapper;
import net.brewspberry.brewery.mappers.SpiceMapper;
import net.brewspberry.brewery.mappers.YeastMapper;
import net.brewspberry.brewery.model.Additive;
import net.brewspberry.brewery.model.Hop;
import net.brewspberry.brewery.model.Malt;
import net.brewspberry.brewery.model.Spice;
import net.brewspberry.brewery.model.Yeast;
import net.brewspberry.brewery.service.IngredientBusinessService;

@RestController
@RequestMapping("/ingredients")
public class IngredientsResource {

	@Autowired
	private IngredientBusinessService<Malt> maltService;
	@Autowired
	private IngredientBusinessService<Hop> hopService;
	@Autowired
	private IngredientBusinessService<Yeast> yeastService;
	@Autowired
	private IngredientBusinessService<Spice> spiceService;
	@Autowired
	private IngredientBusinessService<Additive> additiveService;
	@Autowired
	private MaltMapper maltMapper;
	@Autowired
	private HopMapper hopMapper;
	@Autowired
	private YeastMapper yeastMapper;
	@Autowired
	private SpiceMapper spiceMapper;
	@Autowired
	private AdditiveMapper additiveMapper;

	@GetMapping("/malts")
	public ResponseEntity<List<net.brewspberry.brewery.api.Malt>> getMalts() {
		try {
			return new ResponseEntity<>(maltMapper.toDto(maltService.getAll()), HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/hops")
	public ResponseEntity<List<net.brewspberry.brewery.api.Hop>> getHops() {
		try {
			return new ResponseEntity<List<net.brewspberry.brewery.api.Hop>>(hopMapper.toDto(hopService.getAll()),
					HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/yeasts")
	public ResponseEntity<List<net.brewspberry.brewery.api.Yeast>> getYeasts() {
		try {
			return new ResponseEntity<List<net.brewspberry.brewery.api.Yeast>>(yeastMapper.toDto(yeastService.getAll()),
					HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/spices")
	public ResponseEntity<List<net.brewspberry.brewery.api.Spice>> getSpices() {
		try {
			return new ResponseEntity<List<net.brewspberry.brewery.api.Spice>>(spiceMapper.toDto(spiceService.getAll()),
					HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/additives")
	public ResponseEntity<List<net.brewspberry.brewery.api.Additive>> getAdditives() {
		try {
			return new ResponseEntity<List<net.brewspberry.brewery.api.Additive>>(
					additiveMapper.toDto(additiveService.getAll()), HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
