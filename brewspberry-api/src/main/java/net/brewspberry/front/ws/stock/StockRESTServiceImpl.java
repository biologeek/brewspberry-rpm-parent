package net.brewspberry.front.ws.stock;

import java.util.List;
import java.util.zip.DataFormatException;

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
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.exceptions.ServiceException;

@Controller
@Path("/stockService")
public class StockRESTServiceImpl implements IStockRESTService {

	@Autowired
	@Qualifier("stockServiceImpl")
	IGenericService<StockCounter> genericStockService;
	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	IGenericService<CompteurType> genericCompteurTypeService;
	@Autowired
	ISpecificStockService specificStockService;
	
	@Override
	@GET
	@Path("/stock/product/{p}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStockForProduct(@PathParam("p") long productID) {

		if (productID > 0) {

		}

		return null;
	}

	@Override
	@GET
	@Path("/stock/product/{p}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStockForFinishedProducts() {


		List<FinishedProductCounter> result =  (List<FinishedProductCounter>) specificStockService.getStockForFinishedProducts();
		
		return Response.status(200).entity(result).build();
	}

	@Override
	@GET
	@Path("/stock/product/{p}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStockForIngredients() {
		List<RawMaterialCounter> result = (List<RawMaterialCounter>) specificStockService.getStockForPrimaryMaterials();
	}

	@Override
	@POST
	@Path("/stock/product/{p}/s/{s}/t/{t}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modifyStockForCounter(long productID, double stockMotion, long counterTypeID) {
		
		Stockable stockable;
		CompteurType counterType;
		StockCounter stockCounterAfterMotion = null;
		if (productID > 0){
			
			StockCounter stockCounter;
			try {
				stockCounter = genericStockService.getElementById(productID);
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

			if (counterTypeID > 0){
				
				try {
					
					// Getting counter type
					counterType = genericCompteurTypeService.getElementById(counterTypeID);
					
					/*
					 * Processing stock motion
					 */
					stockCounterAfterMotion = specificStockService.toogleStockCounterForProduct(stockMotion, stockable, counterType);
				} catch (StockException | ServiceException e) {
					return Response.status(500).entity(e).build();
				}
			}
		}
		
		return Response.status(200).entity(stockCounterAfterMotion).build();
	}

}
