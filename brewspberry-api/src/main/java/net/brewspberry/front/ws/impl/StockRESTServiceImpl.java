package net.brewspberry.front.ws.impl;

import java.util.List;
import java.util.zip.DataFormatException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

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
import net.brewspberry.front.ws.beans.StockCounterIngredientRequest;

@Controller
@Path("/stockService")
public class StockRESTServiceImpl implements IStockRESTService {

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
	
	@Override
	@GET
	@Path("/stock/product/{p}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Returns stock for product by product ID
	 * @param productID
	 * @return
	 */
	public List<StockCounter> getStockForProduct(@PathParam("p") long productID) {

		
		List<StockCounter> result = null;
		
		if (productID > 0) {
			Stockable stockable = null;
			try {
				stockable = genericStockableService.getElementById(productID);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			result = specificStockService.getWholeStockForProduct(stockable);

		} else {
			try {
				throw new DataFormatException();
			} catch (DataFormatException e) {
				
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	@GET
	@Path("/stock/products")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Method used for getting finished products stock counters such as beer, ...
	 * Will return stock counters for every finished product stored in DB
	 */
	public List<FinishedProductCounter> getStockForFinishedProducts() {


		List<FinishedProductCounter> result =  (List<FinishedProductCounter>) specificStockService.getStockForFinishedProducts();
		
		return result;
	}

	@Override
	@GET
	@Path("/stock/ingredients")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RawMaterialCounter> getStockForIngredients() {
		List<RawMaterialCounter> result = (List<RawMaterialCounter>) specificStockService.getStockForPrimaryMaterials();
		return result;
	}

	@Override
	@POST
	@Path("/stock/product/modifyStockForProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Will update stock currently stored in DB with stockMotion value for stock counter of type counterTypeID
	 */
	public Response modifyStockForCounter(StockCounterIngredientRequest request){//@PathParam("productID") long productID, double stockMotion, long counterTypeID) {
		
		Stockable stockable;
		CounterType counterType;
		StockCounter stockCounterAfterMotion = null;
		if (request.getProductID() > 0){
			
			StockCounter stockCounter;
			try {
				stockCounter = genericStockService.getElementById(request.getProductID());
			} catch (ServiceException e1) {
				return Response.status(500).entity(e1).build();

			}
			
			if (stockCounter instanceof RawMaterialCounter){
				
				stockable = ((RawMaterialCounter) stockCounter).getCpt_product();
			} else if (stockCounter instanceof FinishedProductCounter) {
				
				stockable = ((FinishedProductCounter) stockCounter).getCpt_product();
				
			} else {
				
				try {
					throw new DataFormatException("Type "+stockCounter.getClass()+" not yet implemented. Blame the developper !");
				} catch (DataFormatException e) {
					return Response.status(500).entity(e).build();
				}
			}

			if (request.getCounterTypeID() > 0){
				
				try {
					
					// Getting counter type
					counterType = genericCompteurTypeService.getElementById(request.getCounterTypeID());
					
					/*
					 * Processing stock motion
					 */
					stockCounterAfterMotion = specificStockService.toogleStockCounterForProduct(request.getStockMotion(), stockable, counterType);
				} catch (StockException | ServiceException e) {
					return Response.status(500).entity(e).build();
				}
			}
		}
		
		return Response.status(200).entity(stockCounterAfterMotion).build();
	}

}
