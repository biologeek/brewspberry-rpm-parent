package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.DAOException;

public interface ISpecificStockDao {

	


	public List<StockCounter> getWholeStockForProduct(Stockable arg0);

	public List<RawMaterialCounter> getStockForPrimaryMaterials();

	public List<FinishedProductCounter> getStockForFinishedProducts();
	
	public StockCounter getStockCounterByProductAndType (Stockable arg0, CounterType arg1) throws DAOException; 
	
	public List<StockCounter> getStockCountersByTypes (List<CounterType> ar0);
	
	public List<StockCounter> batchSaveStockCounter(List<StockCounter> listOfStockCounters);
}
