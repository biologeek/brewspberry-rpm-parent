package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;

public interface ISpecificStockService {

	public List<StockCounter> getWholeStockForProduct(Stockable arg0);
	
	
	
}
