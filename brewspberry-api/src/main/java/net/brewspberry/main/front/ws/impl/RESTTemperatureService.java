package net.brewspberry.main.front.ws.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificActionerService;
import net.brewspberry.main.business.ISpecificTemperatureMeasurementService;
import net.brewspberry.main.business.beans.Actioner;
import net.brewspberry.main.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.PalierType;
import net.brewspberry.main.business.beans.TemperatureMeasurement;
import net.brewspberry.main.business.beans.TheoreticalTemperatureMeasurement;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.front.ws.beans.dto.TemperatureMeasurementDTO;
import net.brewspberry.main.front.ws.beans.responses.MergedTemperatureMeasurement;
import net.brewspberry.main.front.ws.beans.responses.MergedTemperatureMeasurementsForChart;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.LogManager;

@RequestMapping("/temperatureService")
@RestController
@CrossOrigin
public class RESTTemperatureService {

	@Autowired
	@Qualifier("temperatureMeasurementServiceImpl")
	IGenericService<ConcreteTemperatureMeasurement> tmesService;
	@Autowired
	@Qualifier("temperatureMeasurementServiceImpl")
	ISpecificTemperatureMeasurementService tmesSpecService;
	@Autowired
	@Qualifier("etapeServiceImpl")
	IGenericService<Etape> stepService;
	private Etape currentStep;
	
	
	@Autowired
	ISpecificActionerService actionerSpecService;

	Logger logger = LogManager.getInstance(RESTTemperatureService.class.getName());

	@GetMapping("/step/{stepId}/act/{actUUID}")
	public MergedTemperatureMeasurementsForChart initTemperatures(@PathVariable("stepId") Long stepId,
			@PathVariable("actUUID") String actionerUUID) throws ServiceException {
		Etape step = stepService.getElementById(stepId);

		System.out.println(step);
		List<ConcreteTemperatureMeasurement> tmes = tmesSpecService.getTemperaturesByStepAndUUID(step, actionerUUID,
				0L);

		System.out.println(tmes.size());
		return new TemperatureMeasurementDTO().convertToMergedAPIObject(tmes);
	}

	@GetMapping("/step/{stepId}/act/{actUUID}/last/{lastID}")
	public MergedTemperatureMeasurementsForChart updateTemperatures(@PathVariable("stepId") Long stepId,
			@PathVariable("actUUID") String actionerUUID, @PathVariable("lastID") Long lastID) throws ServiceException {
		Etape step = stepService.getElementById(stepId);

		System.out.println(step);
		List<ConcreteTemperatureMeasurement> tmes = tmesSpecService.getTemperaturesByStepAndUUID(step, actionerUUID,
				lastID);

		for(ConcreteTemperatureMeasurement tm : tmes){
			
			if (tm.getTmes_id() < lastID){
				
				tmes.remove(tm);
			}
		}
		return new TemperatureMeasurementDTO().convertToMergedAPIObject(tmes);
	}

	@GetMapping("/initTemperatures/uuid/{uuid}/sid/{sid}/maxPts/{maxPts}/delay/{delay}")
	/**
	 * Initiates temperature list with all available results
	 * 
	 */
	public MergedTemperatureMeasurement initTemperatureForStep(@PathVariable("uuid") String uuid,
			@PathVariable("sid") long stepID, @PathVariable("maxPts") int maxPointsNumber,
			@PathVariable("delay") float delayToDisplayInSeconds) throws Exception {

		JSONObject json = new JSONObject();
		JSONObject thResult = null;
		JSONObject result = null;
		int limit = 0;

		List<ConcreteTemperatureMeasurement> filteredList = new ArrayList<ConcreteTemperatureMeasurement>();

		if (stepID > 0) {

			currentStep = stepService.getElementById(stepID);

			List<ConcreteTemperatureMeasurement> tmesList = tmesSpecService
					.getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay(currentStep, uuid, maxPointsNumber,
							delayToDisplayInSeconds);

			List<TheoreticalTemperatureMeasurement> thList = null;

			String theoreticalParam = ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES,
					"params.chart.display.theoreticalTemperatures");

			if (theoreticalParam.equals("true")) {
				// Display theoretical temperatures

				thList = this.generateTheoreticalTmesListFromConstant(filteredList, currentStep.getEtp_palier_type());

			}

			return new MergedTemperatureMeasurement().concrete(tmesList).theoretical(thList);

		}

		throw new Exception("Id must be > 0");
	}

	@GetMapping("/initTemperatures/e/{e}")
	/**
	 * 
	 * @param stepID
	 * @return
	 * @throws JSONException
	 */
	public List<ConcreteTemperatureMeasurement> initTemperature(@PathVariable("e") long stepID) throws Exception {

		if (stepID > 0) {

			try {
				currentStep = stepService.getElementById(stepID);
			} catch (ServiceException e) {

				e.printStackTrace();
			}

			List<ConcreteTemperatureMeasurement> tmesList = tmesSpecService
					.getTemperatureMeasurementByEtape(currentStep);

			return tmesList;
		}

		throw new Exception("Id must be > 0");
	}

	@GetMapping("/updateTemperatures/uuid/{uuid}/sid/{sid}/maxPts/{maxPts}/delay/{delay}/lastID/{lastID}")
	/**
	 * Returns tempreatures for stepID step, eventually probe with UUID uuid or
	 * all for all probes since last ID in graph (lastID)
	 * 
	 * @param stepID
	 * @param uuid
	 * @param lastID
	 * @param delayInMinutes
	 * @return
	 */
	public MergedTemperatureMeasurement updateTemperatureForStep(@PathVariable("uuid") String uuid,
			@PathVariable("sid") long stepID, @PathVariable("maxPts") int maxPointsNumber,
			@PathVariable("delay") float delayToDisplayInSeconds, @PathVariable("lastID") long lastID)
			throws JSONException {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		JSONObject jsonResult = null;
		List<TheoreticalTemperatureMeasurement> thResult = null;

		if (lastID > 0) {

			Etape etape = new Etape();

			try {
				etape = stepService.getElementById(stepID);
			} catch (ServiceException e) {

				e.printStackTrace();
			}

			if (maxPointsNumber <= 0) {
				maxPointsNumber = Integer.parseInt(
						ConfigLoader.getConfigByKey(Constants.CONFIG_PROPERTIES, "params.chart.maxNumberOfPoints"));
			}
			result = tmesSpecService.getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(etape, uuid, maxPointsNumber,
					lastID, delayToDisplayInSeconds);
			jsonResult = this.convertListToJSONObject(result);

			thResult = this.generateTheoreticalTmesListFromConstant(result, etape.getEtp_palier_type());

		} else {

			logger.warning("You are trying to update with ID 0");
		}

		return new MergedTemperatureMeasurement().concrete(result).theoretical(thResult);
	}

	/**
	 * Method used to return most recent measured temperature for step and
	 * eventually UUID.
	 * 
	 * If you want to get temperatures for all available
	 * 
	 * @param stepID
	 * @param uuid
	 * @return
	 * @throws JSONException
	 */
	@GetMapping("/lastTemperatureValue")
	public List<ConcreteTemperatureMeasurement> getLastTemperatureValue(@PathVariable("e") long stepID,
			@PathVariable("u") String uuid) throws JSONException {

		List<ConcreteTemperatureMeasurement> response = new ArrayList<ConcreteTemperatureMeasurement>();

		if (stepID > 0 && uuid != null) {

			Etape step = null;
			try {
				step = stepService.getElementById(stepID);
			} catch (ServiceException e) {

				e.printStackTrace();
			}

			response = tmesSpecService.getLastTemperatureByStepAndUUID(step, uuid);

			if (!uuid.equalsIgnoreCase("all") && response.size() == 1) {
				logger.fine("Got " + response.size() + " for UUID " + uuid + " and step " + stepID);
			}

		}

		return response;
	}
	
	
	@GetMapping("/actionners/available")
	/**
	 * 
	 * @return
	 */
	public List<MergedTemperatureMeasurementsForChart> getTemperaturesForActiveActionners(){
		
		List<Actioner> actionners = actionerSpecService.getAllActiveActionners();
		
		return new TemperatureMeasurementDTO().convertToMergedAPIObject(
					tmesSpecService.getTemperaturesForActionners(actionners)
				);
	}

	/*
	 * **************************************************************** USELESS
	 ***************************************************************/

	@GetMapping("/test")
	public String testWS() {

		return "Test OK";

	}

	@SuppressWarnings("unchecked")
	public JSONObject convertListToJSONObject(List<? extends TemperatureMeasurement> toConvert) throws JSONException {
		JSONObject json = new JSONObject();

		/*
		 * JSON structure : ConcreteTemperature [ uuid : [ { id : "", uuid : "",
		 * ... }, ... ], ... ], TheoreticalTemperature [ { id : "" , ... }, ...
		 * ]
		 */

		if (toConvert != null && toConvert.size() > 0) {

			Iterator<TemperatureMeasurement> it = (Iterator<TemperatureMeasurement>) toConvert.iterator();

			if (toConvert.get(0) instanceof ConcreteTemperatureMeasurement) {

				List<String> actionerUUIDs = this.returnActionerUUIDs((List<ConcreteTemperatureMeasurement>) toConvert);

				Map<String, List<TemperatureMeasurement>> result = new HashMap<String, List<TemperatureMeasurement>>();

				while (it.hasNext()) {

					ConcreteTemperatureMeasurement next = (ConcreteTemperatureMeasurement) it.next();

					/*
					 * Here, temperature measurements are sorted by UUID : If
					 * UUID is not in the Map, we add new entry else add new
					 * element to List
					 * 
					 */
					if (result.keySet().contains(next.getTmes_probeUI())) {
						result.get(next.getTmes_probeUI()).add(next);
					} else {
						result.put(next.getTmes_probeUI(), new ArrayList<TemperatureMeasurement>());
						result.get(next.getTmes_probeUI()).add(next);
					}

				}

				// Object to JSON parser
				json = json.put(ConcreteTemperatureMeasurement.class.getSimpleName(),
						this.buildJSON(result, ConcreteTemperatureMeasurement.class));

			} else if (toConvert.get(0) instanceof TheoreticalTemperatureMeasurement) {

				// No need to sort temperatures by UUID, so putting null as UUID
				Map<String, List<TemperatureMeasurement>> result2 = new HashMap<String, List<TemperatureMeasurement>>();
				List<TemperatureMeasurement> tmesList = new ArrayList<TemperatureMeasurement>();

				while (it.hasNext()) {

					tmesList.add((TheoreticalTemperatureMeasurement) it.next());

				}
				result2.put("null", tmesList);

				json.put(TheoreticalTemperatureMeasurement.class.getSimpleName(),
						this.buildJSON(result2, TheoreticalTemperatureMeasurement.class));

			}

		}

		return json;
	}

	/**
	 * UUID : [ { id : "", ... } ]
	 * 
	 * Building JSON from a Map
	 * 
	 * @param listTemperatures
	 * @param class1
	 * @return
	 * @throws JSONException
	 */
	private JSONObject buildJSON(Map<String, List<TemperatureMeasurement>> listTemperatures,
			Class<? extends TemperatureMeasurement> class1) throws JSONException {
		JSONObject res = new JSONObject();

		Set<String> keySet = listTemperatures.keySet();

		for (String key : keySet) {
			JSONArray array = new JSONArray();

			for (TemperatureMeasurement tmes : listTemperatures.get(key)) {

				array.put(this.convertToJSONObject(tmes));
			}

			res.put(key, array);
		}
		return res;
	}

	private List<String> returnActionerUUIDs(List<ConcreteTemperatureMeasurement> toConvert) {

		List<String> actioners = new ArrayList<String>();

		for (ConcreteTemperatureMeasurement tmes : toConvert) {

			if (!actioners.contains(tmes)) {

				actioners.add(tmes.getTmes_probeUI());

			}
		}

		return actioners;
	}

	/**
	 * Puts object attributes in JSON object
	 * 
	 * @param tmes
	 * @return
	 * @throws JSONException
	 */
	JSONObject convertToJSONObject(TemperatureMeasurement tmes) throws JSONException {
		JSONObject json = null;

		if (tmes != null) {

			json = new JSONObject();

			if (tmes instanceof ConcreteTemperatureMeasurement) {

				ConcreteTemperatureMeasurement cTmes = (ConcreteTemperatureMeasurement) tmes;
				json.put("id", cTmes.getTmes_id());
				json.put("uuid", cTmes.getTmes_probeUI());
				json.put("date", cTmes.getTmes_date());
				json.put("name", cTmes.getTmes_probe_name());
				json.put("brew", cTmes.getTmes_brassin().getBra_id());
				json.put("step", cTmes.getTmes_etape().getEtp_id());
				json.put("temp", cTmes.getTmes_value());

			} else if (tmes instanceof TheoreticalTemperatureMeasurement) {

				TheoreticalTemperatureMeasurement thMes = (TheoreticalTemperatureMeasurement) tmes;

				json.put("id", thMes.getThmes_id());
				json.put("uuid", "");
				json.put("date", thMes.getThmes_date());
				json.put("name", "");
				json.put("brew", thMes.getThmes_brassin().getBra_id());
				json.put("step", thMes.getThmes_etape().getEtp_id());
				json.put("temp", thMes.getThmes_value());

			}
		}

		return json;
	}

	public List<TheoreticalTemperatureMeasurement> generateTheoreticalTmesListFromConstant(
			List<ConcreteTemperatureMeasurement> tmesList, PalierType palier) {

		List<TheoreticalTemperatureMeasurement> result = new ArrayList<TheoreticalTemperatureMeasurement>();
		int i = 0;
		for (ConcreteTemperatureMeasurement tmes : tmesList) {

			TheoreticalTemperatureMeasurement currentThmes = new TheoreticalTemperatureMeasurement();

			currentThmes.setThmes_id((long) i);
			currentThmes.setThmes_brassin(tmes.getTmes_brassin());
			currentThmes.setThmes_etape(tmes.getTmes_etape());
			currentThmes.setThmes_actioner(tmes.getTmes_actioner());
			currentThmes.setThmes_date(tmes.getTmes_date());
			currentThmes.setThmes_value((float) palier.getPlt_temperature());
			i++;
		}

		return result;
	}

	public JSONArray mergeTwoJSONObjects(JSONObject result2, JSONObject thResult2) {

		JSONArray result = new JSONArray();
		result.put(result2);

		result.put(thResult2);

		return result;

	}
}