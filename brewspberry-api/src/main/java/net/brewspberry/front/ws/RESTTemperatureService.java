package net.brewspberry.front.ws;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificTemperatureMeasurementService;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.PalierType;
import net.brewspberry.business.beans.TemperatureMeasurement;
import net.brewspberry.business.beans.ConcreteTemperatureMeasurement;
import net.brewspberry.business.beans.TheoreticalTemperatureMeasurement;
import net.brewspberry.business.service.EtapeServiceImpl;
import net.brewspberry.business.service.TemperatureMeasurementServiceImpl;
import net.brewspberry.util.ConfigLoader;
import net.brewspberry.util.Constants;
import net.brewspberry.util.LogManager;

@Path("/")
public class RESTTemperatureService {

	IGenericService<ConcreteTemperatureMeasurement> tmesService;
	ISpecificTemperatureMeasurementService tmesSpecService;
	IGenericService<Etape> stepService;
	private Etape currentStep;

	Logger logger = LogManager.getInstance(RESTTemperatureService.class
			.getName());

	@GET
	@Path("/initTemperatures/uuid/{uuid}/sid/{sid}/maxPts/{maxPts}/delay/{delay}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Initiates temperature list with all available results
	 *  
	 */
	public Response initTemperatureForStep(@PathParam("uuid") String uuid,
			@PathParam("sid") long stepID,
			@PathParam("maxPts") int maxPointsNumber,
			@PathParam("delay") float delayToDisplayInSeconds) throws Exception {

		JSONObject json = new JSONObject();
		JSONObject thResult = null;
		JSONObject result = null;
		int limit = 0;
		tmesService = new TemperatureMeasurementServiceImpl();
		tmesSpecService = new TemperatureMeasurementServiceImpl();
		stepService = new EtapeServiceImpl();
		
		List<ConcreteTemperatureMeasurement> filteredList = new ArrayList<ConcreteTemperatureMeasurement>();

		if (stepID > 0) {

			currentStep = stepService.getElementById(stepID);

			List<ConcreteTemperatureMeasurement> tmesList = tmesSpecService
					.getLastTemperatureMeasurementByStepUUIDNumberOfPointsAndDelay(
							currentStep, uuid, maxPointsNumber,
							delayToDisplayInSeconds);
			logger.fine("List size : " + tmesList.size());

			result = this.convertListToJSONObject(tmesList);
			// List filtered in service, so just need to add theoretical
			// temperatures

			List<TheoreticalTemperatureMeasurement> thList = null;

			String theoreticalParam = ConfigLoader.getConfigByKey(
					Constants.CONFIG_PROPERTIES,
					"params.chart.display.theoreticalTemperatures");

			if (theoreticalParam.equals("true")) {
				// Display theoretical temperatures

				thList = this.generateTheoreticalTmesListFromConstant(
						filteredList, currentStep.getEtp_palier_type());
				thResult = this.convertListToJSONObject(thList);

			}

		}
		return Response.status(200)
				.entity(this.mergeTwoJSONObjects(result,  thResult).toString())
				.build();
	}

	@GET
	@Path("/initTemperatures/e/{e}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 * @param stepID
	 * @return
	 * @throws JSONException
	 */
	public Response initTemperature(@PathParam("e") long stepID)
			throws JSONException {

		JSONObject json = new JSONObject();
		JSONObject result = null;

		tmesService = new TemperatureMeasurementServiceImpl();
		tmesSpecService = new TemperatureMeasurementServiceImpl();
		stepService = new EtapeServiceImpl();
		if (stepID > 0) {

			currentStep = stepService.getElementById(stepID);

			List<ConcreteTemperatureMeasurement> tmesList = tmesSpecService
					.getTemperatureMeasurementByEtape(currentStep);

			result = this.convertListToJSONObject(tmesList);

		}

		return Response.status(200).entity(result.toString()).build();

	}

	@GET
	@Path("/updateTemperatures/uuid/{uuid}/sid/{sid}/maxPts/{maxPts}/delay/{delay}/lastID/{lastID}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Returns tempreatures for stepID step, eventually probe with UUID uuid or all for all probes
	 * since last ID in graph (lastID)
	 * @param stepID
	 * @param uuid
	 * @param lastID
	 * @param delayInMinutes
	 * @return
	 */
	public Response updateTemperatureForStep(@PathParam("uuid") String uuid,
			@PathParam("sid") long stepID,
			@PathParam("maxPts") int maxPointsNumber,
			@PathParam("delay") float delayToDisplayInSeconds,
			@PathParam("lastID") long lastID) throws JSONException {

		List<ConcreteTemperatureMeasurement> result = new ArrayList<ConcreteTemperatureMeasurement>();
		JSONObject jsonResult = null;
		JSONObject thResult = null;

		tmesService = new TemperatureMeasurementServiceImpl();
		tmesSpecService = new TemperatureMeasurementServiceImpl();
		stepService = new EtapeServiceImpl();
		
		if (lastID > 0) {

			Etape etape = new Etape();

			etape = stepService.getElementById(stepID);

			if (maxPointsNumber <= 0) {
				maxPointsNumber = Integer.parseInt(ConfigLoader.getConfigByKey(
						Constants.CONFIG_PROPERTIES,
						"params.chart.maxNumberOfPoints"));
			}
			result = tmesSpecService
					.getTemperatureMeasurementsAfterIDForStepUUIDAndDelay(
							etape, uuid, maxPointsNumber, lastID,
							delayToDisplayInSeconds);
			jsonResult = this.convertListToJSONObject(result);

			thResult = this.convertListToJSONObject(this.generateTheoreticalTmesListFromConstant(result,
					etape.getEtp_palier_type()));

		} else {

			logger.warning("You are trying to update with ID 0");
		}

		return Response
				.status(200)
				.entity(this.mergeTwoJSONObjects(jsonResult, thResult)
						.toString())
				.build();
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
	@GET
	@Path("/lastTemperatureValue")
	public Response getLastTemperatureValue(@PathParam("e") long stepID,
			@PathParam("u") String uuid) throws JSONException {

		List<ConcreteTemperatureMeasurement> response = new ArrayList<ConcreteTemperatureMeasurement>();
		JSONObject jsonResult = null;

		tmesService = new TemperatureMeasurementServiceImpl();
		tmesSpecService = new TemperatureMeasurementServiceImpl();
		stepService = new EtapeServiceImpl();

		if (stepID > 0 && uuid != null) {

			Etape step = stepService.getElementById(stepID);

			response = tmesSpecService.getLastTemperatureByStepAndUUID(step,
					uuid);

			if (!uuid.equalsIgnoreCase("all") && response.size() == 1) {
				logger.fine("Got " + response.size() + " for UUID " + uuid
						+ " and step " + stepID);
			}

		}

		jsonResult = this.convertListToJSONObject(response);

		return Response.status(200).entity(jsonResult.toString()).build();
	}

	@GET
	@Path("/test")
	public Response testWS() {

		return Response.status(200).entity("Test OK").build();

	}

	@SuppressWarnings("unchecked")
	public JSONObject convertListToJSONObject(
			List<? extends TemperatureMeasurement> toConvert)
			throws JSONException {
		JSONObject json = new JSONObject();

		/*
		 * JSON structure : ConcreteTemperature [ uuid : [ { id : "", uuid : "",
		 * ... }, ... ], ... ], TheoreticalTemperature [ { id : "" , ... }, ...
		 * ]
		 */

		if (toConvert != null && toConvert.size() > 0) {

			Iterator<TemperatureMeasurement> it = (Iterator<TemperatureMeasurement>) toConvert
					.iterator();

			if (toConvert.get(0) instanceof ConcreteTemperatureMeasurement) {

				List<String> actionerUUIDs = this
						.returnActionerUUIDs((List<ConcreteTemperatureMeasurement>) toConvert);

				Map<String, List<TemperatureMeasurement>> result = new HashMap<String, List<TemperatureMeasurement>>();
				
				
				while (it.hasNext()) {

					ConcreteTemperatureMeasurement next = (ConcreteTemperatureMeasurement) it
							.next();

					/*
					 * Here, temperature measurements are sorted by UUID :
					 * If UUID is not in the Map, we add new entry
					 * else add new element to List
					 * 
					 */
					if (result.keySet().contains(next.getTmes_probeUI())) {
						result.get(next.getTmes_probeUI()).add(next);
					} else{
						result.put(next.getTmes_probeUI(), new ArrayList<TemperatureMeasurement>());
						result.get(next.getTmes_probeUI()).add(next);
					}

				}
				
				// Object to JSON parser
				json = json.put(ConcreteTemperatureMeasurement.class.getSimpleName(), this.buildJSON(result,
							ConcreteTemperatureMeasurement.class));

			} else if (toConvert.get(0) instanceof TheoreticalTemperatureMeasurement) {

				// No need to sort temperatures by UUID, so putting null as UUID
				Map<String, List<TemperatureMeasurement>> result2 = new HashMap<String, List<TemperatureMeasurement>>();
				List<TemperatureMeasurement> tmesList = new ArrayList<TemperatureMeasurement>();
				
				
				while (it.hasNext()) {

					tmesList.add((TheoreticalTemperatureMeasurement) it.next());
					
				}
				result2.put("null", tmesList);

				json.put(TheoreticalTemperatureMeasurement.class.getSimpleName(), this.buildJSON(result2, TheoreticalTemperatureMeasurement.class));

			}

		}

		return json;
	}

	
	/**
	 * UUID : [
	 * 				{
	 * 					id : "", 
	 * 					...
	 * 				}
	 * ]
	 * 
	 * Building JSON from a Map 
	 * @param listTemperatures
	 * @param class1
	 * @return
	 * @throws JSONException
	 */
	private JSONObject buildJSON(
			Map<String, List<TemperatureMeasurement>> listTemperatures,
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

	private List<String> returnActionerUUIDs(
			List<ConcreteTemperatureMeasurement> toConvert) {

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
	JSONObject convertToJSONObject(TemperatureMeasurement tmes)
			throws JSONException {
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