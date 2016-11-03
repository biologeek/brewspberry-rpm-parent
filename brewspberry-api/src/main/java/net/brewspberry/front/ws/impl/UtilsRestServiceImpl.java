package net.brewspberry.front.ws.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.front.ws.beans.StockUnitLight;
import net.brewspberry.front.ws.beans.dto.UnitsDTO;

@RestController
@RequestMapping("/utils")
public class UtilsRestServiceImpl {

	
	
	@GetMapping("/units")
	public List<StockUnitLight> getStockUnits (){
		
		
		return new UnitsDTO().toFrontObjectList(Arrays.asList(StockUnit.values()));
		
	}
}
