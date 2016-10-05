package net.brewspberry.front.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IBrewProcessingRESTService;
import net.brewspberry.front.ws.beans.dto.BrassinDTO;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;

@Path("/brewService")
public class BrewProcessingRestService implements IBrewProcessingRESTService {

	@Autowired
	@Qualifier("etapeServiceImpl")
	private IGenericService<Etape> stepService;
	@Autowired
	@Qualifier("brassinServiceImpl")
	private IGenericService<Brassin> brassinGenService;
	@Autowired
	private ISpecificEtapeService specStepService;

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
	 * @param etape step ID to stop
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
	public Response getAllActiveBrews() {
		
		
		
		return null;
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllBrews() {
		
		List<Brassin> brewList = null;
		try {
			brewList = brassinGenService.getAllElements();
		} catch(Exception e){			
			return Response.status(500).entity(e).build();
		}
		 ArrayList<SimpleBrewResponse> convertedBrewList = new ArrayList<SimpleBrewResponse>();
		 
		 // Using a stream to convert each element of the list to DTO 
		 brewList.stream().forEach(x -> {
			 convertedBrewList.add(BrassinDTO.getInstance().toSimpleBrewResponse(x)); 
		 });
		
		return Response.status(200).entity(convertedBrewList).build();
	}

}
