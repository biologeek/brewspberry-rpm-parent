package net.brewspberry.front.ws.impl;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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
import net.brewspberry.front.ws.beans.dto.IngredientDTO;
import net.brewspberry.front.ws.beans.requests.IngredientRequest;
import net.brewspberry.util.LogManager;

@Path("/productService")
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Response addIngredient(IngredientRequest request) {

		AbstractIngredient businessIngredient = null;

		if (request != null) {

			if (isThereOnlyOneIngredientInRequest(request)) {
				try {

					switch (request.getType().toLowerCase()) {
					case "malt":

						businessIngredient = new IngredientDTO()
								.toMalt(request);
						maltService.save((Malt) businessIngredient);

						break;
					case "hop":

						businessIngredient = new IngredientDTO().toHop(request);
						hopService.save((Houblon) businessIngredient);
						break;
					case "yeast":

						businessIngredient = new IngredientDTO()
								.toYeast(request);
						levService.save((Levure) businessIngredient);
						break;
					default:
						logger.severe("This type of ingredient does not exist : "
								+ request.getType());
						break;
					}

				} catch (Exception e) {
					return Response.status(500)
							.entity("Error during service call").build();
				}

			}

		} else {
			return Response.status(500).entity("request is null").build();
		}

		try {
			return Response
					.status(200)
					.entity(new IngredientDTO()
							.toServiceObject(businessIngredient)).build();
		} catch (DataTransferException e) {
			
			return Response.status(500).entity(e).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	@Path("/update/{id}")
	public Response updateIngredient(@PathParam("id") long id,
			IngredientRequest req) {

		return null;

	}

	@Override
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public Response deleteIngredient(IngredientRequest request) {
		
		return null;
	}

	/**
	 * Testing if required field are filled for one ingredient only. It tests if
	 * at least one of required fields is filled for two ingredients
	 * 
	 * @param request
	 *            DTO object to check
	 * @return true if only one ingredient is filled. False if not.
	 */
	private boolean isThereOnlyOneIngredientInRequest(
			IngredientRequest request) {

		if (((request.getCereal() != null || request.getMaltType() != null || request
				.getColor() != 0) && ((request.getAlphaAcid() != 0
				|| request.getHopType() != 0 || request.getVariety() != null) || (request
				.getFoculation() != null && request.getSpecie() != null)))
				|| ((request.getAlphaAcid() != 0 || request.getHopType() != 0 || request
						.getVariety() != null) && ((request.getCereal() != null
						|| request.getMaltType() != null || request.getColor() != 0) || (request
						.getFoculation() != null && request.getSpecie() != null)))
				|| ((request.getFoculation() != null && request.getSpecie() != null) && ((request
						.getAlphaAcid() != 0 || request.getHopType() != 0 || request
						.getVariety() != null) || (request.getCereal() != null
						|| request.getMaltType() != null || request.getColor() != 0)))) {
			return false;
		}
		return true;
	}

	@Override
	@GET
	@Path("/get/{type}/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIngredient(@PathParam("type") String type,
			@PathParam("id") long id) {

		IngredientRequest result = null;

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
