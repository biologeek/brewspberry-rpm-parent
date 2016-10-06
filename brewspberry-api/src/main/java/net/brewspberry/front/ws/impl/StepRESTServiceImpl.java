package net.brewspberry.front.ws.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.client.ResourceAccessException;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IStepRESTService;
import net.brewspberry.front.ws.beans.dto.StepDTO;
import net.brewspberry.front.ws.beans.requests.CompleteStep;

@Path("/stepService")
public class StepRESTServiceImpl implements IStepRESTService {

	@Autowired
	IGenericService<Brassin> genBrewService;
	IGenericService<Etape> genStepService;

	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/add")
	public Response add(CompleteStep step) {

		Brassin attachedBrew;

		if (step != null) {

			if (step.getId() == null) {

				try {
					attachedBrew = genBrewService.getElementById(step.getBrewID());

				} catch (ServiceException e) {

					return Response.status(500).entity(e).build();
				}

				if (attachedBrew != null) {

					try {
						Etape stepToPersist = new StepDTO().toBusinessObject(step, attachedBrew);

						Etape persisted = genStepService.save(stepToPersist);

						return Response.status(200).entity(persisted).build();

					} catch (ServiceException e) {
						return Response.status(500).entity(e).build();
					} catch (Exception e) {
						return Response.status(500).entity(e).build();
					}

				}
			} else {

				throw new ResourceAccessException("Wrong method");
			}
		}

		return Response.status(500).entity(new Exception("Null object sent")).build();
	}

	/*
	 * TODO : add get, getStepsByBrew, ... methods
	 */
}
