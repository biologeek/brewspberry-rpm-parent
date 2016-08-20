package net.brewspberry.business.beans.builders;

import java.util.Date;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.business.beans.stock.Stockable;

public abstract class StockCounterBuilder<T> {

	
	
	protected StockCounter counter;

	public abstract StockCounterBuilder<T> ingredient (AbstractIngredient e);
	public abstract StockCounterBuilder<T> product (AbstractFinishedProduct e);
	public StockCounterBuilder() {

	}
	public StockCounterBuilder<T> type(CounterType a){
		
		counter.setCpt_counter_type(a);
		
		
		return this;	
		
	}

	public StockCounterBuilder<T> value(double a){
		
		counter.setCpt_value(a);
		
		
		return this;	
		
	}
	

	public StockCounterBuilder<T> unit(StockUnit a){
		
		counter.setCpt_unit(a);
		
		
		return this;	
		
	}
	
	
	public abstract T build();
}
