package net.brewspberry.front.ws.beans.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.StockUnitLight;

public class UnitsDTO implements DTO<StockUnit, StockUnitLight>{

	@Override
	public StockUnit toBusinessObject(StockUnitLight cplObj) {
		
		return StockUnit.valueOf(cplObj.getObj());
	}

	@Override
	public List<StockUnit> toBusinessObjectList(List<StockUnitLight> cplLst) {
		
		List<StockUnit> units = new ArrayList<>();
		if (cplLst.isEmpty())
			return Collections.emptyList();
		
		for(StockUnitLight cpl : cplLst){
			units.add(this.toBusinessObject(cpl));			
		}
		return units;
	}

	@Override
	public StockUnitLight toFrontObject(StockUnit cplObj) {
		
			return new StockUnitLight().obj(cplObj.name()).trad(cplObj.getStu_value());
	}

	@Override
	public List<StockUnitLight> toFrontObjectList(List<StockUnit> cplLst) {

		List<StockUnitLight> units = new ArrayList<>();
		if (cplLst.isEmpty())
			return Collections.emptyList();
		
		for(StockUnit cpl : cplLst){
					
			units.add(this.toFrontObject(cpl));
		}
		
		return units;
	}

	
	
}
