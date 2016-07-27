package net.brewspberry.util;

import java.util.Arrays;

import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.StockMotionTypeRules;

public class StockMotionValidator {
	
	/**
	 * Checks if stock motion respects rules
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean checkIfStockMotionSatisfiesRules(CounterType from, CounterType to){
		
		
		if (from == null){
			from = CounterType.NONE;
		}
		if (to == null){
			to = CounterType.NONE;
		}
		
		for (StockMotionTypeRules rule : StockMotionTypeRules.values()){
			
			if (rule.getSmt_authorized_stock_counter_from().equals(from)){
				if(Arrays.asList(rule.getSmt_authorized_stock_counter_to()).contains(to))
					return true;
			}
		}
		
		
		return false;		
	}

}
