package net.brewspberry.front.ws.impl;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.front.ws.IStepRESTService;
import net.brewspberry.front.ws.beans.dto.StepDTO;
import net.brewspberry.front.ws.beans.requests.CompleteStep;
import net.brewspberry.front.ws.beans.responses.SimpleStepResponse;

@RestController
@RequestMapping("/stepService")
public class StepRESTServiceImpl implements IStepRESTService {

	@Autowired
	@Qualifier("brassinServiceImpl")
	IGenericService<Brassin> genBrewService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	IGenericService<Etape> genStepService;

	@Override
	@PostMapping("/add")
	public CompleteStep add(@RequestBody SimpleStepResponse step) throws Exception {

		Brassin attachedBrew;

		if (step != null) {

			if (step.getId() == null) {
				Assert.assertNotNull(genBrewService);
				
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

	@Override
	@GetMapping("/{id}")
	public CompleteStep get(@PathVariable("id") long stepID) throws Exception {


		if (stepID > 0){
			return new StepDTO().toCompleteStep(genStepService.getElementById(stepID));
		} else 
			throw new Exception("id must be > 0");
		
	}

	/*
	 * TODO : add get, getStepsByBrew, ... methods
	 */
}
