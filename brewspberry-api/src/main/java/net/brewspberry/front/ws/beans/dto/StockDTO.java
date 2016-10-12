package net.brewspberry.front.ws.beans.dto;

import java.util.List;

import javax.xml.crypto.Data;

import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.CounterTypeConstants;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.front.ws.DTO;
import net.brewspberry.front.ws.beans.requests.StockCounterRequest;

public class StockDTO implements DTO<StockCounter, StockCounterRequest> {

	
	private List<CounterType> list;

	public StockDTO(List<CounterType> list){
		
	}
	
	
	@Override
	public StockCounter toBusinessObject(StockCounterRequest cplObj) {
		StockCounter counter = new StockCounter();
		
		counter.setCpt_id(cplObj.getId());
		counter.setCpt_counter_type(CounterTypeConstants.valueOf(cplObj.getType()).toDBCouter(list));

		return null;
	}

	@Override
	public List<StockCounter> toBusinessObjectList(List<StockCounterRequest> cplLst) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockCounterRequest toFrontObject(StockCounter cplObj) {
		StockCounterRequest res = new StockCounterRequest();
		
		res.setId(cplObj.getCpt_id());
		res.setCreation(cplObj.getCpt_date_cre().getTime());
		res.setUpdate(cplObj.getCpt_date_maj().getTime());
		res.setType(cplObj.getCpt_counter_type().getCty_libelle());
		res.setUnit(cplObj.getCpt_unit().name());
		res.setValue(cplObj.getCpt_value());
		return res;
	}

	@Override
	public List<StockCounterRequest> toFrontObjectList(List<StockCounter> cplLst) {
		// TODO Auto-generated method stub
		return null;
	}

}
