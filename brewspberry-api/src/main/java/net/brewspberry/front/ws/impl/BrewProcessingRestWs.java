package net.brewspberry.front.ws.impl;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IBrewProcessing;

@Path("/brew")
public class BrewProcessingRestWs implements IBrewProcessing {

	@Autowired
	@Qualifier("etapeServiceImpl")
	private IGenericService<Etape> stepService;
	@Autowired
	private ISpecificEtapeService specStepService;

	@Override
	@Path("startStep")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response startStep(long etape) throws Exception {
		Etape stepFromDataSource = null;
		Etape stepAfterStateUpdate = null;

		if (etape >= 0) {

			try {

				stepFromDataSource = stepService.getElementById(etape);

			} catch (ServiceException e) {

				return Response
						.status(500)
						.entity(new BusinessException(
								"Error during step retrieving")).build();

			}

			if (stepFromDataSource != null) {

				stepAfterStateUpdate = specStepService
						.startStepForReal(stepFromDataSource);

			} else {
				return Response
						.status(500)
						.entity(new BusinessException(
								"No step found in database")).build();
			}

		} else {
			throw new Exception("ID null or negative");
		}
		return Response.status(200).entity(stepAfterStateUpdate).build();
	}

	@Override
	public Response endStep(long etape) {
		Etape stepFromDataSource = null;
		Etape stepAfterStateUpdate = null;

		if (etape >= 0) {

			try {

				stepFromDataSource = stepService.getElementById(etape);

			} catch (ServiceException e) {

				return Response
						.status(500)
						.entity(new BusinessException(
								"Error during step retrieving")).build();

			}

			if (stepFromDataSource != null) {

				stepAfterStateUpdate = specStepService
						.stopStepForReal(stepFromDataSource);

			} else {
				return Response
						.status(500)
						.entity(new BusinessException(
								"No step found in database")).build();
			}

		} else {
			return Response
					.status(500)
					.entity(new Exception("ID null or negative")).build();
		}
		return Response.status(200).entity(stepAfterStateUpdate).build();
		}

}
