package net.brewspberry.main.util;

import java.util.Arrays;

import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.StockMotionTypeRules;

public class StockMotionValidator {
	
	/**
	 * Checks if stock motion respects rules
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean checkIfStockMotionSatisfiesRules(CounterTypeConstants from, CounterTypeConstants to){
		
		
		if (from == null){
			from = CounterTypeConstants.NONE;
		}
		if (to == null){
			to = CounterTypeConstants.NONE;
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
