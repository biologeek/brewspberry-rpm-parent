package net.brewspberry.front.ws;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.front.ws.beans.StockCounterIngredientRequest;

public interface IStockRESTService {

	public List<StockCounter> getStockForProduct(@PathParam("p") long productID);

	public List<FinishedProductCounter> getStockForFinishedProducts();

	public List<RawMaterialCounter> getStockForIngredients();

	public Response modifyStockForCounter(StockCounterIngredientRequest request);
}
