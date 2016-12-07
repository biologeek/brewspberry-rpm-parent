package net.brewspberry.main.business.service;

import net.brewspberry.main.business.beans.AbstractFinishedProduct;
import net.brewspberry.main.business.beans.AbstractIngredient;
import net.brewspberry.main.business.beans.builders.StockCounterBuilder;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;

public class FinishedProductStockCounterBuilder extends StockCounterBuilder<FinishedProductCounter> {


	
	public FinishedProductStockCounterBuilder() {
		counter = new FinishedProductCounter();
	}

	public FinishedProductStockCounterBuilder type(CounterType a){
		counter.setCpt_counter_type(a);
		return this;
	}

	public FinishedProductStockCounterBuilder value(double a){		
		counter.setCpt_value(a);		
		return this;	
	}

	public FinishedProductStockCounterBuilder unit(StockUnit a){		
		counter.setCpt_unit(a);	
		return this;		
	}	
	
	public FinishedProductStockCounterBuilder product(AbstractFinishedProduct e) {
		((FinishedProductCounter) counter).setCpt_product(e);
		return this;
	}

	@Override
	public FinishedProductCounter build() {
		return (FinishedProductCounter) counter;
	}


}
