package net.brewspberry.main.front.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificBrassinService;
import net.brewspberry.main.business.ISpecificEtapeService;
import net.brewspberry.main.business.beans.brewing.Brassin;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.exceptions.BusinessException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.exceptions.ValidationException;
import net.brewspberry.main.business.validation.BrewValidator;
import net.brewspberry.main.business.validation.BusinessError;
import net.brewspberry.main.business.validation.Validator;
import net.brewspberry.main.front.ws.IBrewingProcessController;
import net.brewspberry.main.front.ws.IStepProcessController;
import net.brewspberry.main.front.ws.beans.dto.BrassinDTO;
import net.brewspberry.main.front.ws.beans.dto.StepDTO;
import net.brewspberry.main.front.ws.beans.requests.CompleteStep;
import net.brewspberry.main.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.main.front.ws.beans.responses.SimpleBrewResponse;
import net.brewspberry.main.front.ws.beans.responses.SimpleBrewResponse.BrewResponseType;
import net.brewspberry.main.util.LogManager;



@RestController
@RequestMapping("/brewService")
public class ProcessController implements IBrewingProcessController, IStepProcessController {

	@Autowired
	@Qualifier("etapeServiceImpl")
	private IGenericService<Etape> stepService;
	@Autowired
	@Qualifier("brassinServiceImpl")
	private IGenericService<Brassin> brassinGenService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	private ISpecificEtapeService specStepService;
	private ISpecificBrassinService specBrewService;
	private Logger logger;
	

	@Autowired
	@Qualifier("brassinServiceImpl")
	IGenericService<Brassin> genBrewService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	IGenericService<Etape> genStepService;


	public ProcessController() {
		this.logger = LogManager.getInstance(ProcessController.class.getName());
	}
	
	
	@Override
	@PostMapping("/add")
	public CompleteStep add(@RequestBody CompleteStep step) throws Exception {

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
	
	
	
	@PostMapping("/start/{etapeID}")
	@ResponseBody
	/**
	 * 
	 */
	public CompleteStep startStep(@PathVariable("etapeID") long etape) throws Exception {
		Etape stepFromDataSource = null;
		Etape stepAfterStateUpdate = null;

		if (etape >= 0) {

			try {

				stepFromDataSource = stepService.getElementById(etape);

			} catch (ServiceException e) {

				throw new BusinessException("Error during step retrieving");
				
			}

			if (stepFromDataSource != null) {

				stepAfterStateUpdate = specStepService.startStepForReal(stepFromDataSource);

			} else {
				throw new BusinessException("No step found in database");
			}

		} else {
			throw new Exception("ID null or negative");
		}
		return new StepDTO().toCompleteStep(stepAfterStateUpdate);
	}


	@GetMapping("/stop/{etapeID}")
	@ResponseBody
	/**
	 * Ends step when called
	 * 
	 * @param etape
	 *            step ID to stop
	 * @return step after state update
	 */
	public Etape endStep(@PathVariable("etapeID") long etape) throws Exception {
		Etape stepFromDataSource = null;
		Etape stepAfterStateUpdate = null;

		if (etape >= 0) {

			try {

				stepFromDataSource = stepService.getElementById(etape);

			} catch (ServiceException e) {

				throw new BusinessException("Error during step retrieving");

			}

			if (stepFromDataSource != null) {

				stepAfterStateUpdate = specStepService.stopStepForReal(stepFromDataSource);

			} else {
				throw new BusinessException("No step found in database");
			}

		} else {
			throw new Exception("ID null or negative");
		}
		return stepAfterStateUpdate;
	}

	@Override
	public <T extends SimpleBrewResponse> List<T> getAllActiveBrews(@PathVariable("type") String lightOrFull) {

		Class<T> clazz;

		List<T> result = new ArrayList<T>();
		
		List<Brassin> brews = specBrewService.getActiveBrews();
		if (BrewResponseType.valueOf(lightOrFull).equals(BrewResponseType.full)){
			
			clazz = (Class<T>) ComplexBrewResponse.class;
			

			for (Brassin brew : brews){
				result.add(clazz.cast(BrassinDTO.getInstance().toComplexBrewResponse(brew)));	
			}
			
		} else if (BrewResponseType.valueOf(lightOrFull).equals(BrewResponseType.light)){
			
			clazz = (Class<T>) SimpleBrewResponse.class;
			

			for (Brassin brew : brews){
				result.add(clazz.cast(BrassinDTO.getInstance().toSimpleBrewResponse(brew)));	
			}
		}
		
		return result;
	}



	@GetMapping("/{type}")
	@ResponseBody
	public List<? extends SimpleBrewResponse> getAllBrews(@PathVariable("type") String lightOrFull) throws Exception {

		List<Brassin> brewList = null;
		try {
			brewList = brassinGenService.getAllElements();
		} catch (Exception e) {
			throw new Exception(e);
		}

		if (lightOrFull.equals("light")) {
			ArrayList<SimpleBrewResponse> convertedBrewList = new ArrayList<SimpleBrewResponse>();
			// Using a stream to convert each element of the list to DTO
			brewList.stream().forEach(x -> {
				convertedBrewList.add(BrassinDTO.getInstance().toSimpleBrewResponse(x));
			});

			return convertedBrewList;

		} else {
			ArrayList<ComplexBrewResponse> convertedBrewList = new ArrayList<ComplexBrewResponse>();
			// Using a stream to convert each element of the list to DTO
			brewList.stream().forEach(x -> {
				convertedBrewList.add(BrassinDTO.getInstance().toComplexBrewResponse(x));
			});

			return convertedBrewList;

		}
	}


	@GetMapping("/{id}/full")
	@Override
	/**
	 * Returns Full Brew object with steps and actionners
	 * 
	 * @param brewID
	 * @return
	 */
	public ComplexBrewResponse getCompleteBrew(@PathVariable("id") long brewID) throws BusinessException, DataFormatException {

		if (brewID > 0) {

			Brassin brew = new Brassin();

			try {
				brew = brassinGenService.getElementById(brewID);
			} catch (ServiceException e) {
				throw new BusinessException(e.getMessage());
			}

			ComplexBrewResponse response = BrassinDTO.getInstance().toComplexBrewResponse(brew);

			return response;

		}
		throw new DataFormatException("Wrong ID " + brewID);
	}



	
	@PostMapping("/add/simple")
	/**
	 * Adds a newly created brew in DB
	 * 
	 * @param req
	 *            Brew form object
	 * @return newly created ComplexBrewResponse
	 */
	public SimpleBrewResponse addBrew(@RequestBody SimpleBrewResponse req) throws BusinessException, ValidationException {

		if (req != null) {

			try {
				Brassin brew = BrassinDTO.getInstance().toBusinessObject(req);

				Validator<Brassin> val = new BrewValidator();

				List<BusinessError> errs = val.validate(brew);

				if (errs == null || errs.isEmpty()) {

					try {
						return BrassinDTO.getInstance().toSimpleBrewResponse(brassinGenService.save(brew));
					} catch (Exception e) {
						throw new BusinessException(e.getMessage());
					}

				} else {
					logger.severe(val.computeErrors(errs, ", "));
					throw new ValidationException(errs);
				}

			} catch (ServiceException e) {
				throw new BusinessException(e.getMessage());
			}

		}

		return null;
	}
	

	
	@PostMapping("/add/complex")
	/**
	 * Adds a newly created brew in DB
	 * 
	 * @param req
	 *            Brew form object
	 * @return newly created ComplexBrewResponse
	 */
	public ComplexBrewResponse addBrew(@RequestBody ComplexBrewResponse req) throws BusinessException, ValidationException {

		if (req != null) {

			try {
				Brassin brew = BrassinDTO.getInstance().toBusinessObject(req);

				Validator<Brassin> val = new BrewValidator();

				List<BusinessError> errs = val.validate(brew);

				if (errs == null || errs.isEmpty()) {

					try {
						return BrassinDTO.getInstance().toComplexBrewResponse(brassinGenService.save(brew));
					} catch (Exception e) {
						throw new BusinessException(e.getMessage());
					}

				} else {
					logger.severe(val.computeErrors(errs, ", "));
					throw new ValidationException(errs);
				}

			} catch (ServiceException e) {
				throw new BusinessException(e.getMessage());
			}

		}

		return null;
	}

	@Override
	@PutMapping("/update")
	public ComplexBrewResponse updateBrew(@RequestBody ComplexBrewResponse req) throws BusinessException, ValidationException {
		

		if (req != null) {

			try {
				Brassin brew = BrassinDTO.getInstance().toBusinessObject(req);

				Validator<Brassin> val = new BrewValidator();

				List<BusinessError> errs = val.validate(brew);

				if (errs == null || errs.isEmpty()) {

					try {
						return BrassinDTO.getInstance().toComplexBrewResponse(brassinGenService.update(brew));
					} catch (Exception e) {
						throw new BusinessException(e.getMessage());
					}

				} else {
					logger.severe(val.computeErrors(errs, ", "));
					throw new ValidationException(errs);
				}

			} catch (ServiceException e) {
				throw new BusinessException(e.getMessage());
			}

		}

		return null;
	}

	@Override
	/**
	 * Retrieves simple brew by its ID
	 * 
	 * @param brewID
	 * @return
	 */
	@GetMapping("/{id}/light")
	public SimpleBrewResponse getSimpleBrew(@PathVariable("id") long brewID) throws DataFormatException, BusinessException {

		if (brewID > 0) {

			Brassin brew = new Brassin();

			try {
				brew = brassinGenService.getElementById(brewID);
			} catch (ServiceException e) {
				throw new BusinessException(e.getMessage());
			}

			ComplexBrewResponse response = BrassinDTO.getInstance().toComplexBrewResponse(brew);

			return response;

		}
		throw new DataFormatException("Wrong ID " + brewID);
	
	}

}
