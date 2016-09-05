package net.brewspberry.front.ws.impl;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.exceptions.DataTransferException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IProductRESTService;
import net.brewspberry.front.ws.beans.IngredientDTO;
import net.brewspberry.front.ws.beans.IngredientJSONRequest;
import net.brewspberry.util.LogManager;

public class ProductRESTServiceImpl implements IProductRESTService {

	@Autowired
	@Qualifier("maltServiceImpl")
	IGenericService<Malt> maltService;

	@Autowired
	@Qualifier("hopServiceImpl")
	IGenericService<Houblon> hopService;

	@Autowired
	@Qualifier("yeastServiceImpl")
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
				try {

					switch (request.getType().toLowerCase()) {
					case "malt":

						businessIngredient = new IngredientDTO().toMalt(request);
						maltService.save((Malt) businessIngredient);

						break;
					case "hop":

						businessIngredient = new IngredientDTO().toHop(request);
						hopService.save((Houblon) businessIngredient);
						break;
					case "yeast":

						businessIngredient = new IngredientDTO().toYeast(request);
						levService.save((Levure) businessIngredient);
						break;
					default:
						logger.severe("This type of ingredient does not exist : " + request.getType());
						break;
					}

				} catch (Exception e) {
					return Response.status(500).entity("Error during service call").build();
				}

			}

		} else {
			return Response.status(500).entity("request is null").build();
		}

		try {
			return Response.status(200).entity(new IngredientDTO().toServiceObject(businessIngredient)).build();
		} catch (DataTransferException e) {
			// TODO Auto-generated catch block
			return Response.status(500).entity(e).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response updateIngredient(IngredientJSONRequest req) {

		return null;

	}

	@Override
	public Response deleteIngredient(IngredientJSONRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isThereOnlyOneIngredientInRequest(IngredientJSONRequest request) {

		return true;
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredient(@PathParam("type") String type, @PathParam("id") long id) {

		IngredientJSONRequest result = null;

		if (id > 0) {

			try {
				switch (type.toLowerCase()) {
				case "malt":

					Malt malt = maltService.getElementById(id);

					result = new IngredientDTO().toServiceObject(malt);

					break;
				case "hop":

					Houblon houblon = hopService.getElementById(id);
					result = new IngredientDTO().toServiceObject(houblon);

					break;

				case "yeast":

					Levure levure = levService.getElementById(id);
					result = new IngredientDTO().toServiceObject(levure);

					break;
				}
			} catch (ServiceException | DataTransferException e) {

				return Response.status(500).entity(e).build();
				
			}
		}
		return Response.status(200).entity(result).build();
	}
}
