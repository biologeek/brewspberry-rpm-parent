package net.brewspberry.front.ws.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

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

import net.brewspberry.business.BrewValidator;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificBrassinService;
import net.brewspberry.business.ISpecificEtapeService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.exceptions.BusinessException;
import net.brewspberry.business.exceptions.ServiceException;
import net.brewspberry.business.exceptions.ValidationException;
import net.brewspberry.business.validation.BusinessErrors;
import net.brewspberry.business.validation.Validator;
import net.brewspberry.front.ws.IBrewProcessingRESTService;
import net.brewspberry.front.ws.IBrewRESTService;
import net.brewspberry.front.ws.beans.dto.BrassinDTO;
import net.brewspberry.front.ws.beans.responses.ComplexBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse;
import net.brewspberry.front.ws.beans.responses.SimpleBrewResponse.BrewResponseType;
import net.brewspberry.util.LogManager;



@RestController
@RequestMapping("/brewService")
public class BrewProcessingRestService implements IBrewProcessingRESTService, IBrewRESTService {

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

	public BrewProcessingRestService() {
		this.logger = LogManager.getInstance(BrewProcessingRestService.class.getName());
	}
	@PostMapping("/start/{etapeID}")
	@ResponseBody
	/**
	 * 
	 */
	public Etape startStep(@PathVariable("etapeID") long etape) throws Exception {
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
		return stepAfterStateUpdate;
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


	
	
	@PostMapping("/add")
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

				List<BusinessErrors> errs = val.validate(brew);

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

				List<BusinessErrors> errs = val.validate(brew);

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
