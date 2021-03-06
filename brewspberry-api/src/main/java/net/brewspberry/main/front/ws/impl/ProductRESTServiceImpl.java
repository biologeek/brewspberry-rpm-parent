package net.brewspberry.main.front.ws.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.beans.brewing.AbstractIngredient;
import net.brewspberry.main.business.beans.brewing.Houblon;
import net.brewspberry.main.business.beans.brewing.Levure;
import net.brewspberry.main.business.beans.brewing.Malt;
import net.brewspberry.main.business.beans.brewing.SimpleHoublon;
import net.brewspberry.main.business.beans.brewing.SimpleLevure;
import net.brewspberry.main.business.beans.brewing.SimpleMalt;
import net.brewspberry.main.business.exceptions.BusinessException;
import net.brewspberry.main.business.exceptions.DataTransferException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.front.ws.IProductRESTService;
import net.brewspberry.main.front.ws.beans.dto.IngredientDTO;
import net.brewspberry.main.front.ws.beans.dto.MaltDTO;
import net.brewspberry.main.front.ws.beans.dto.SimpleHopDTO;
import net.brewspberry.main.front.ws.beans.dto.SimpleMaltDTO;
import net.brewspberry.main.front.ws.beans.dto.SimpleYeastDTO;
import net.brewspberry.main.front.ws.beans.dto.YeastDTO;
import net.brewspberry.main.front.ws.beans.requests.IngredientRequest;
import net.brewspberry.main.front.ws.beans.responses.StandardResponse;
import net.brewspberry.main.util.LogManager;


@RequestMapping("/ingredient")
@RestController
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


	@Autowired
	@Qualifier("simpleMaltServiceImpl")
	IGenericService<SimpleMalt> simpleMaltService;

	@Autowired
	@Qualifier("simpleHopServiceImpl")
	IGenericService<SimpleHoublon> simpleHopService;

	@Autowired
	@Qualifier("simpleYeastServiceImpl")
	IGenericService<SimpleLevure> simpleYeastService;

	Logger logger;

	public ProductRESTServiceImpl() {
		logger = LogManager.getInstance(this.getClass().getName());
	}
	
	
	
	@GetMapping("/")
	public List<IngredientRequest> getAllIngredients(){
		
		List<IngredientRequest> result = new ArrayList<>();
		result.addAll(new SimpleMaltDTO().toFrontObjectList(simpleMaltService.getAllElements()));
		result.addAll(new SimpleHopDTO().toFrontObjectList(simpleHopService.getAllElements()));
		result.addAll(new SimpleYeastDTO().toFrontObjectList(simpleYeastService.getAllElements()));
		
		return result;
	}
	
	
	@PostMapping("/save")
	public IngredientRequest addIngredient(@RequestBody IngredientRequest request) throws Exception {

		AbstractIngredient businessIngredient = null;

		if (request != null) {

			if (isThereOnlyOneIngredientInRequest(request)) {
				try {

					switch (request.getType()) {
					case MALT:

						businessIngredient = new SimpleMaltDTO().toBusinessObject(request);
						simpleMaltService.save((SimpleMalt) businessIngredient);
						break;
					case HOP:

						businessIngredient = new SimpleHopDTO().toBusinessObject(request);
						simpleHopService.save((SimpleHoublon) businessIngredient);
						break;
					case YEAST:

						businessIngredient = new SimpleYeastDTO().toBusinessObject(request);
						simpleYeastService.save((SimpleLevure) businessIngredient);
						break;
					default:
						logger.severe("This type of ingredient does not exist : " + request.getType());
						break;
					}

				} catch (Exception e) {
					throw new Exception("Error during service call");
				}

			}

		} else {
			new DataFormatException("request is null");
		}

		return new IngredientDTO().toServiceObject(businessIngredient);

	}

	@PutMapping("/update/{id}")
	public IngredientRequest updateIngredient(@PathVariable("id") long id, IngredientRequest req) {

		return null;

	}

	@DeleteMapping("/delete")
	public StandardResponse deleteIngredient(IngredientRequest request) {

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
	private boolean isThereOnlyOneIngredientInRequest(IngredientRequest request) {

		if (((request.getCereal() != null || request.getMaltType() != null || request.getColor() != 0)
				&& ((request.getAlphaAcid() != 0 || request.getHopType() != null || request.getVariety() != null)
						|| (request.getFoculation() != null && request.getSpecie() != null)))
				|| ((request.getAlphaAcid() != 0 || request.getHopType() != null || request.getVariety() != null)
						&& ((request.getCereal() != null || request.getMaltType() != null || request.getColor() != 0)
								|| (request.getFoculation() != null && request.getSpecie() != null)))
				|| ((request.getFoculation() != null && request.getSpecie() != null)
						&& ((request.getAlphaAcid() != 0 || request.getHopType() != null || request.getVariety() != null)
								|| (request.getCereal() != null || request.getMaltType() != null
										|| request.getColor() != 0)))) {
			return false;
		}
		return true;
	}

	@GetMapping("{id}/{type}")
	public IngredientRequest getIngredient(@PathVariable("type") String type, @PathVariable("id") long id) throws DataTransferException, ServiceException {

		IngredientRequest result = null;

		if (id > 0) {

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
			 
		}
		return result;
	}
}
