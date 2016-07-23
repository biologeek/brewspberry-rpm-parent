package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.exceptions.ServiceException;

public interface ISpecificStockService {

	public List<StockCounter> getWholeStockForProduct(Stockable arg0);
	public List<RawMaterialCounter> getStockForPrimaryMaterials();
	public List<FinishedProductCounter> getStockForFinishedProducts();
	
	StockCounter toogleStockCounterForProduct(double valueToDecrease, Stockable arg0, CompteurType type)
			throws StockException, ServiceException;
	
	
	public List<StockCounter> getStockCountersByTypes (List<CompteurType> ar0);

}
