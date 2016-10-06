package net.brewspberry.front.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.ws.rs.Consumes;
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
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.business.BrewValidator;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.business.validation.BusinessErrors;
import net.brewspberry.business.validation.Validator;
import net.brewspberry.dao.BrassinDaoImpl;
import net.brewspberry.front.ws.IBrewProcessingRESTService;
import net.brewspberry.front.ws.IBrewRESTService;
import net.brewspberry.front.ws.beans.dto.BrassinDTO;
import net.brewspberry.front.ws.beans.requests.BrewRequest;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;

@Path("/brewService")
@RestController
public class BrewProcessingRestService implements IBrewProcessingRESTService, IBrewRESTService {

	@Autowired
	@Qualifier("etapeServiceImpl")
	private IGenericService<Etape> stepService;
	@Autowired
	@Qualifier("brassinServiceImpl")
	private IGenericService<Brassin> brassinGenService;
	@Autowired
	private ISpecificEtapeService specStepService;
	private IGenericService<Brassin> genBrewService;

	// TODO : test of methods
	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/start/{etapeID}")
	/**
	 * 
	 */
	public Response startStep(@PathParam("etapeID") long etape) throws Exception {
		Etape stepFromDataSource = null;
		Etape stepAfterStateUpdate = null;

		if (etape >= 0) {

			try {

				stepFromDataSource = stepService.getElementById(etape);

			} catch (ServiceException e) {

				return Response.status(500).entity(new BusinessException("Error during step retrieving")).build();

			}

			if (stepFromDataSource != null) {

				stepAfterStateUpdate = specStepService.startStepForReal(stepFromDataSource);

			} else {
				return Response.status(500).entity(new BusinessException("No step found in database")).build();
			}

		} else {
			throw new Exception("ID null or negative");
		}
		return Response.status(200).entity(stepAfterStateUpdate).build();
	}

	@Override
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/stop/{etapeID}")
	/**
	 * Ends step when called
	 * 
	 * @param etape
	 *            step ID to stop
	 * @return step after state update
	 */
	public Response endStep(@PathParam("etapeID") long etape) {
		Etape stepFromDataSource = null;
		Etape stepAfterStateUpdate = null;

		if (etape >= 0) {

			try {

				stepFromDataSource = stepService.getElementById(etape);

			} catch (ServiceException e) {

				return Response.status(500).entity(new BusinessException("Error during step retrieving")).build();

			}

			if (stepFromDataSource != null) {

				stepAfterStateUpdate = specStepService.stopStepForReal(stepFromDataSource);

			} else {
				return Response.status(500).entity(new BusinessException("No step found in database")).build();
			}

		} else {
			return Response.status(500).entity(new Exception("ID null or negative")).build();
		}
		return Response.status(200).entity(stepAfterStateUpdate).build();
	}

	@Override
	public Response getAllActiveBrews(@PathParam("type") String lightOrFull) {

		return null;
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{type}")
	public Response getAllBrews(@PathParam("type") String lightOrFull) {

		List<Brassin> brewList = null;
		try {
			brewList = brassinGenService.getAllElements();
		} catch (Exception e) {
			return Response.status(500).entity(e).build();
		}

		if (lightOrFull.equals("light")) {
			ArrayList<SimpleBrewResponse> convertedBrewList = new ArrayList<SimpleBrewResponse>();
			// Using a stream to convert each element of the list to DTO
			brewList.stream().forEach(x -> {
				convertedBrewList.add(BrassinDTO.getInstance().toSimpleBrewResponse(x));
			});

			return Response.status(200).entity(convertedBrewList).build();

		} else {
			ArrayList<ComplexBrewResponse> convertedBrewList = new ArrayList<ComplexBrewResponse>();
			// Using a stream to convert each element of the list to DTO
			brewList.stream().forEach(x -> {
				convertedBrewList.add(BrassinDTO.getInstance().toComplexBrewResponse(x));
			});

			return Response.status(200).entity(convertedBrewList).build();

		}
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/full")
	/**
	 * Returns Full Brew object with steps and actionners
	 * 
	 * @param brewID
	 * @return
	 */
	public Response getCompleteBrew(@PathParam("id") long brewID) {

		if (brewID > 0) {

			Brassin brew = new Brassin();

			try {
				brew = genBrewService.getElementById(brewID);
			} catch (ServiceException e) {
				return Response.status(500).entity(e).build();
			}

			ComplexBrewResponse response = BrassinDTO.getInstance().toComplexBrewResponse(brew);

			return Response.status(200).entity(response).build();

		}
		return Response.status(500).entity(new DataFormatException("Wrong ID " + brewID)).build();
	}

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
	/**
	 * Adds a newly created brew in DB
	 * 
	 * @param req
	 *            Brew form object
	 * @return newly created ComplexBrewResponse
	 */
	public Response addBrew(BrewRequest req) {

		if (req != null) {

			try {
				Brassin brew = BrassinDTO.getInstance().toBusinessObject(req);

				Validator<Brassin> val = new BrewValidator();

				List<BusinessErrors> errs = val.validate(brew);

				if (errs != null && !errs.isEmpty()) {

					try {
						return Response.status(200)
								.entity(BrassinDTO.getInstance().toComplexBrewResponse(genBrewService.save(brew)))
								.build();
					} catch (Exception e) {
						Response.status(500).entity(e).build();
					}

				} else {
					Response.status(500).entity(errs).build();
				}

			} catch (ServiceException e) {
				return Response.status(500).entity(e).build();
			}

		}

		return Response.status(500).entity(null).build();
	}

	@Override
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBrew(BrewRequest req) {
		

		if (req != null) {

			try {
				Brassin brew = BrassinDTO.getInstance().toBusinessObject(req);

				Validator<Brassin> val = new BrewValidator();

				List<BusinessErrors> errs = val.validate(brew);

				if (errs != null && !errs.isEmpty()) {

					try {
						return Response.status(200)
								.entity(BrassinDTO.getInstance().toComplexBrewResponse(genBrewService.update(brew)))
								.build();
					} catch (Exception e) {
						Response.status(500).entity(e).build();
					}

				} else {
					Response.status(500).entity(errs).build();
				}

			} catch (ServiceException e) {
				return Response.status(500).entity(e).build();
			}

		}

		return Response.status(500).entity(null).build();
	}

	@Override
	/**
	 * Retrieves simple brew by its ID
	 * 
	 * @param brewID
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/light")
	public Response getSimpleBrew(@PathParam("id") long brewID) {

		if (brewID > 0) {

			Brassin brew = new Brassin();

			try {
				brew = genBrewService.getElementById(brewID);
			} catch (ServiceException e) {
				return Response.status(500).entity(e).build();
			}

			ComplexBrewResponse response = BrassinDTO.getInstance().toComplexBrewResponse(brew);

			return Response.status(200).entity(response).build();

		}
		return Response.status(500).entity(new DataFormatException("Wrong ID " + brewID)).build();
	
	}

}
