package net.brewspberry.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificIngredientService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.Brassin;
import net.brewspberry.business.beans.DurationBO;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.stock.AbstractStockMotion;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.parser.Parser;
import net.brewspberry.business.service.EtapeServiceImpl;
import net.brewspberry.business.service.HopServiceImpl;
import net.brewspberry.business.service.MaltServiceImpl;
import net.brewspberry.business.service.YeastServiceImpl;
import net.brewspberry.exceptions.ServiceException;
import net.brewspberry.util.DateManipulator;
import net.brewspberry.util.LogManager;


@Component
public class StepProcessor implements Processor<Object> {

	@Autowired
	@Qualifier("etapeServiceImpl")
	IGenericService<Etape> etapeService;
	@Autowired
	ISpecificIngredientService maltIngSpecService;

	@Autowired
	@Qualifier("maltServiceImpl")
	IGenericService<Malt> maltService;
	@Autowired
	@Qualifier("hopServiceImpl")
	IGenericService<Houblon> hopService;
	@Autowired
	@Qualifier("yeastServiceImpl")
	IGenericService<Levure> yeastService;
	@Autowired
	ISpecificIngredientService hopIngSpecService;
	@Autowired
	ISpecificIngredientService levureIngSpecService;

	@Autowired
	Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> stepParserForRawMaterial;

	@Autowired
	ISpecificStockService specStockService;

	private Logger logger = LogManager.getInstance(StepProcessor.class.getName());

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

	@Override
	@SuppressWarnings("unchecked")
	public boolean record(Object parent, HttpServletRequest request) {

		List<RawMaterialStockMotion> stockMotionsDuringStep;
		request.getAttribute("step_id");
		Etape currentStep = new Etape();

		if (parent instanceof Etape) {

			/*
			 * If parent object parameter is Etape, parent brew is already
			 * attached. It's an update of parent step
			 */

			currentStep = (Etape) parent;
		}

		else if (parent instanceof Brassin) {

			/*
			 * else, we're creating a new Step, it's necesary to attach brew
			 */
			Brassin parentBrew = (Brassin) parent;

			// Attaching brew to step
			if (parent != null && parentBrew.getBra_id() != null && !parentBrew.getBra_id().equals(new Brassin())) {

				logger.fine("Attaching to brew " + parentBrew);
				currentStep.setEtp_brassin(parentBrew);
			}

		}

		// Building step 
		parseServletRequestForStepParameters(request, currentStep);

		// Recording stock motions :
		if (parent instanceof Etape) {
			stockMotionsDuringStep = stepParserForRawMaterial.compareTwoObjectsAndExtractStockMotions((Etape) parent,
					currentStep);
		} else {
			stockMotionsDuringStep = stepParserForRawMaterial.compareTwoObjectsAndExtractStockMotions(null,
					currentStep);
		}
		/*
		 * Updating stock counters in datasource :
		 * 
		 */

		try {
			specStockService.processStockMotionsForUpdatingStockCounters(
					(List<AbstractStockMotion>) (List<?>) stockMotionsDuringStep);
		} catch (ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Recording step
		try {
			if (currentStep.getEtp_id() != null && currentStep.getEtp_id() > 0) {

				currentStep = etapeService.update(currentStep);
				logger.info("Updating Step with ID " + currentStep.getEtp_id());

			} else {
				currentStep = etapeService.save(currentStep);
				logger.info("Saving Step with ID " + currentStep.getEtp_id());
			}
			return true;
		} catch (Exception e) {

			e.printStackTrace();
			logger.severe("Got an error while saving step...");

			return false;
		}

	}

	private void parseServletRequestForStepParameters(HttpServletRequest request, Etape currentStep) {

		String currentStepLabel;
		String currentStepDuration;
		String currentStepTemperature;
		String currentStepComment;
		Date currentStepEndDate;
		String currentStepNumber;
		Date currentStepBeginningDate;

		// Step beginning date
		if (request.getParameter("step_beginning") != null) {

			currentStepBeginningDate = DateManipulator
					.formatDateFromVariousPatterns(request.getParameter("step_beginning")).getTime();

			if (currentStep.getEtp_debut() == null) {

				try {
					currentStepBeginningDate = sdf.parse(request.getParameter("step_beginnging"));
					currentStep.setEtp_debut(currentStepBeginningDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.severe(request.getParameter("step_beginnging")
							+ " cannot be converted to Date. Considering now ()");
					currentStep.setEtp_debut(new Date());
				}

			}

		}

		// Step end date
		if (request.getParameter("step_end") != null) {

			try {

				currentStepEndDate = sdf.parse(request.getParameter("step_end"));

				currentStep.setEtp_fin(currentStepEndDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.severe(request.getParameter("step_end") + " cannot be converted to Date. Considering now ()");
				currentStep.setEtp_fin(new Date());
			}

		}

		// Step Label

		if (request.getParameter("step_label") != null) {

			currentStepLabel = request.getParameter("step_label");

			currentStep.setEtp_nom(currentStepLabel);

		}

		// Step duration

		if (request.getParameter("step_duration") != null) {

			currentStepDuration = request.getParameter("step_duration");

			DurationBO duration = new DurationBO();

			duration.setMinute(Long.parseLong(currentStepDuration));

			currentStep.setEtp_duree(duration);

		}

		// Step theoretical temperature

		if (request.getParameter("step_temperature") != null) {

			currentStepTemperature = request.getParameter("step_temperature");

			try {
				currentStep.setEtp_temperature_theorique(Double.parseDouble(currentStepTemperature));
			} catch (Exception e) {

				logger.severe("Couldn't convert temperature " + currentStepTemperature);
			}
		}

		// Température théorique de l'étape

		if (request.getParameter("step_comment") != null) {

			currentStepComment = request.getParameter("step_comment");

			currentStep.setEtp_remarque(currentStepComment);

		}

		// Numéro de l'étape

		if (request.getParameter("step_number") != null) {

			currentStepNumber = request.getParameter("step_number");

			currentStep.setEtp_numero(Integer.parseInt(currentStepNumber));

		}
	}

	public IGenericService<Etape> getEtapeService() {
		return etapeService;
	}

	public void setEtapeService(IGenericService<Etape> etapeService) {
		this.etapeService = etapeService;
	}

	public ISpecificIngredientService getMaltIngSpecService() {
		return maltIngSpecService;
	}

	public void setMaltIngSpecService(ISpecificIngredientService maltIngSpecService) {
		this.maltIngSpecService = maltIngSpecService;
	}

	public IGenericService<Malt> getMaltService() {
		return maltService;
	}

	public void setMaltService(IGenericService<Malt> maltService) {
		this.maltService = maltService;
	}

	public IGenericService<Houblon> getHopService() {
		return hopService;
	}

	public void setHopService(IGenericService<Houblon> hopService) {
		this.hopService = hopService;
	}

	public IGenericService<Levure> getYeastService() {
		return yeastService;
	}

	public void setYeastService(IGenericService<Levure> yeastService) {
		this.yeastService = yeastService;
	}

	public ISpecificIngredientService getHopIngSpecService() {
		return hopIngSpecService;
	}

	public void setHopIngSpecService(ISpecificIngredientService hopIngSpecService) {
		this.hopIngSpecService = hopIngSpecService;
	}

	public ISpecificIngredientService getLevureIngSpecService() {
		return levureIngSpecService;
	}

	public void setLevureIngSpecService(ISpecificIngredientService levureIngSpecService) {
		this.levureIngSpecService = levureIngSpecService;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
}