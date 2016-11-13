package net.brewspberry.front.ws.impl;

import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.front.ws.IStockRESTService;
import net.brewspberry.front.ws.beans.dto.StockDTO;
import net.brewspberry.front.ws.beans.requests.StockCounterIngredientRequest;
import net.brewspberry.front.ws.beans.requests.StockCounterRequest;

@RestController
@RequestMapping("/stockService")
public abstract class StockRESTServiceImpl implements IStockRESTService {

	@Autowired
	@Qualifier("stockServiceImpl")
	IGenericService<StockCounter> genericStockService;
	@Autowired
	@Qualifier("stockableServiceImpl")
	IGenericService<Stockable> genericStockableService;
	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	IGenericService<CounterType> genericCompteurTypeService;
	@Autowired
	ISpecificStockService specificStockService;
	private List<CounterType> list;
	
	
	public StockRESTServiceImpl() {
		this.list = genericCompteurTypeService.getAllElements();
	}

	@Override
	@GetMapping("/stock/product/{p}")
	/**
	 * Returns stock for product by product ID
	 * 
	 * @param productID
	 * @return
	 */
	public List<StockCounter> getStockForProduct(@PathVariable("p") long productID)
			throws DataFormatException, ServiceException {

		List<StockCounter> result = null;

		if (productID > 0) {
			Stockable stockable = null;
			stockable = genericStockableService.getElementById(productID);

			result = specificStockService.getWholeStockForProduct(stockable);

		} else {

			throw new DataFormatException();
		}

		return result;
	}

	@Override
	@GetMapping("/stock/products")
	/**
	 * Method used for getting finished products stock counters such as beer,
	 * ... Will return stock counters for every finished product stored in DB
	 */
	public List<FinishedProductCounter> getStockForFinishedProducts() {

		List<FinishedProductCounter> result = (List<FinishedProductCounter>) specificStockService
				.getStockForFinishedProducts();

		return result;
	}

	@Override
	@GetMapping("/stock/ingredients")
	public List<RawMaterialCounter> getStockForIngredients() {
		List<RawMaterialCounter> result = (List<RawMaterialCounter>) specificStockService.getStockForPrimaryMaterials();
		return result;
	}

	@Override
	@PostMapping("/stock/product/modifyStockForProduct")
	/**
	 * Will update stock currently stored in DB with stockMotion value for stock
	 * counter of type counterTypeID
	 */
	public StockCounterRequest modifyStockForCounter(StockCounterIngredientRequest request) throws ServiceException, DataFormatException, StockException {

		Stockable stockable;
		CounterType counterType;
		StockCounter stockCounterAfterMotion = null;
		if (request.getProductID() > 0) {

			StockCounter stockCounter;
			stockCounter = genericStockService.getElementById(request.getProductID());

			if (stockCounter instanceof RawMaterialCounter) {

				stockable = ((RawMaterialCounter) stockCounter).getCpt_product();
			} else if (stockCounter instanceof FinishedProductCounter) {

				stockable = ((FinishedProductCounter) stockCounter).getCpt_product();

			} else {

				throw new DataFormatException(
						"Type " + stockCounter.getClass() + " not yet implemented. Blame the developper !");

			}

			if (request.getCounterTypeID() > 0) {

				// Getting counter type
				counterType = genericCompteurTypeService.getElementById(request.getCounterTypeID());

				/*
				 * Processing stock motion
				 */
				stockCounterAfterMotion = specificStockService.toogleStockCounterForProduct(request.getStockMotion(),
						stockable, counterType);

			}
		}

		
		return new StockDTO(list).toFrontObject(stockCounterAfterMotion);
	}

}
