package net.brewspberry.main.front.ws.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.front.ws.beans.StockUnitLight;
import net.brewspberry.main.front.ws.beans.dto.UnitsDTO;

@RestController
@RequestMapping("/utils")
public class UtilsRestServiceImpl {

	
	
	@GetMapping("/units")
	public List<StockUnitLight> getStockUnits (){
		
		
		return new UnitsDTO().toFrontObjectList(Arrays.asList(StockUnit.values()));
		
	}
}
