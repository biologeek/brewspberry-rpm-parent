package net.brewspberry.front.ws.impl;

import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.front.ws.IProductRESTService;
import net.brewspberry.front.ws.beans.IngredientDTO;
import net.brewspberry.front.ws.beans.IngredientJSONRequest;
import net.brewspberry.util.LogManager;

public class ProductRESTServiceImpl implements IProductRESTService {

	

	@Autowired
	IGenericService<Malt> maltService;

	@Autowired
	IGenericService<Houblon> hopService;

	@Autowired
	IGenericService<Levure> levService;
	
	Logger logger;
	
	
	public ProductRESTServiceImpl() {
		logger = LogManager.getInstance(this.getClass().getName());
	}
	
	
	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIngredient(IngredientJSONRequest request) {

		AbstractIngredient businessIngredient = null;

		if (request != null) {

			if (isThereOnlyOneIngredientInRequest(request)) {

				switch (request.getType().toLowerCase()) {

				case "malt":

					businessIngredient = new IngredientDTO().toMalt(request);
					
					break;
				case "hop":

					businessIngredient = new IngredientDTO().toHop(request);

					break;
				case "yeast":

					businessIngredient = new IngredientDTO().toYeast(request);

					break;
				default :
					logger.severe("This type of ingredient does not exist : "+request.getType());
					break;
				}
				
			
			}

		} else {
			return Response.status(500).entity("request is null").build();
		}

		return null;
	}

	private boolean isThereOnlyOneIngredientInRequest(IngredientJSONRequest request) {

		return true;
	}

}
