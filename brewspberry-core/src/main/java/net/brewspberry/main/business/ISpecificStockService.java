package net.brewspberry.main.business;

import java.util.List;

import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.stock.AbstractStockMotion;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.Stockable;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.exceptions.StockException;

public interface ISpecificStockService {

	public List<StockCounter> getWholeStockForProduct(Stockable arg0);
	public List<RawMaterialCounter> getStockForPrimaryMaterials();
	public List<FinishedProductCounter> getStockForFinishedProducts();
	
	StockCounter toogleStockCounterForProduct(double valueToDecrease, Stockable arg0, CounterType type)
			throws StockException, ServiceException;
	
	
	public List<StockCounter> getStockCountersByTypes (List<CounterType> ar0);

	
	public List<StockCounter> processStockMotionsForUpdatingStockCounters(List<? extends AbstractStockMotion> motions) throws ServiceException;
	
	/**
	 * Compares old and new step and extract stock motions. 
	 * From stock motions, stock counters are updated 
	 * @param oldEtape step before stock modifications
	 * @param newEtape step after modifications
	 * @param counterTypeFrom stock counter From
	 * @param counterTypeTo stock counter To
	 * @return updated stock counters
	 */
	public List<StockCounter> compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(Etape oldEtape, Etape newEtape, CounterType counterTypeFrom, CounterType counterTypeTo);
}
