package net.brewspberry.front.ws.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.front.ws.IStepRESTService;
import net.brewspberry.front.ws.beans.dto.StepDTO;
import net.brewspberry.front.ws.beans.requests.CompleteStep;

@RequestMapping("/stepService")
@RestController
public abstract class StepRESTServiceImpl implements IStepRESTService {

	@Autowired
	@Qualifier("brassinServiceImpl")
	IGenericService<Brassin> genBrewService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	IGenericService<Etape> genStepService;

	@Override
	@PostMapping("/add")
	public CompleteStep add(CompleteStep step) throws Exception {

		Brassin attachedBrew;

		if (step != null) {

			if (step.getId() == null) {

				attachedBrew = genBrewService.getElementById(step.getBrewID());

				if (attachedBrew != null) {

					Etape stepToPersist = new StepDTO().toBusinessObject(step, attachedBrew);

					Etape persisted = genStepService.save(stepToPersist);

					return new StepDTO().toCompleteStep(persisted);

				}
			} else {

				throw new ResourceAccessException("Wrong method");
			}
		}

		throw new Exception("Null object sent");
	}

	/*
	 * TODO : add get, getStepsByBrew, ... methods
	 */
}
