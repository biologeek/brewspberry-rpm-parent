package net.brewspberry.main.business.beans.builders;

import java.util.Date;

import net.brewspberry.main.business.beans.brewing.AbstractFinishedProduct;
import net.brewspberry.main.business.beans.brewing.AbstractIngredient;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.beans.stock.Stockable;

public abstract class StockCounterBuilder<T> {

	protected StockCounter counter;

	public StockCounterBuilder() {

	}
	public abstract T build();
}
