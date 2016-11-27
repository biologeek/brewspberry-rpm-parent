package net.brewspberry.main.front.ws;

import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.web.bind.annotation.PathVariable;

import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.exceptions.StockException;
import net.brewspberry.main.front.ws.beans.requests.StockCounterIngredientRequest;
import net.brewspberry.main.front.ws.beans.requests.StockCounterRequest;

public interface IStockRESTService {

	public List<StockCounter> getStockForProduct(@PathVariable("p") long productID) throws DataFormatException, ServiceException;

	public List<FinishedProductCounter> getStockForFinishedProducts();

	public List<RawMaterialCounter> getStockForIngredients();

	public StockCounterRequest modifyStockForCounter(StockCounterIngredientRequest request) throws ServiceException, DataFormatException, StockException;
}
