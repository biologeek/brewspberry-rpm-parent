package net.brewspberry.business;

import java.util.List;

import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;

public interface ISpecificStockDao {

	


	public List<StockCounter> getWholeStockForProduct(Stockable arg0);

	public List<StockCounter> getStockForPrimaryMaterials();

	public List<StockCounter> getStockForFinishedProducts();
	
	public StockCounter getStockCounterByProductAndType (Stockable arg0, CompteurType arg1); 
	
	public List<StockCounter> getStockCountersByTypes (List<CompteurType> ar0);
}
