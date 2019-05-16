package net.brewspberry.brewery.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.brewery.exceptions.ValidationException;
import net.brewspberry.brewery.model.AbstractIngredient;
import net.brewspberry.brewery.service.AbstractIngredientService;

@RestController
@RequestMapping("/ingredients")
public class IngredientsResource {

	private AbstractIngredientService ingredientService; 
	
	@GetMapping("/{type}")
	public ResponseEntity<List<AbstractIngredient>> getIngredientsByType(@PathVariable("type") String type){
		try {
			ingredientService.getIngredientsByType(type);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		
	}
}
