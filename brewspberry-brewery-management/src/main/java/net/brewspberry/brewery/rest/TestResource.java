package net.brewspberry.brewery.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestResource {

	
	@GetMapping("")
	public ResponseEntity<String> test(){
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
}
