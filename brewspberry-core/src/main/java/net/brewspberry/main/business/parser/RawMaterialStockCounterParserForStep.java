package net.brewspberry.main.business.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.beans.Etape;
import net.brewspberry.main.business.beans.Houblon;
import net.brewspberry.main.business.beans.Levure;
import net.brewspberry.main.business.beans.Malt;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.util.ConfigLoader;
import net.brewspberry.main.util.Constants;
import net.brewspberry.main.util.DateManipulator;
import net.brewspberry.main.util.LogManager;
import net.brewspberry.main.util.StockMotionValidator;

@Component
public class RawMaterialStockCounterParserForStep implements Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> {

	@Autowired
	@Qualifier("compteurTypeServiceImpl")
	IGenericService<CounterType> counterTypeService;

	private Logger logger = LogManager.getInstance(RawMaterialStockCounterParserForStep.class.getName());

	private List<CounterType> list;

	@Override
	/**
	 * Parses a step to extract stock counters from ingredients
	 */
	public List<RawMaterialCounter> parse(Etape objectToBeParsed, CounterType counterType) {

		try {
			return parseIngredients(objectToBeParsed, counterType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	/**
	 * Parses a list of steps to extract stock counters
	 */
	public List<RawMaterialCounter> parseList(List<Etape> listOfObjectsToBeParsed, CounterType counterType) {

		if (listOfObjectsToBeParsed.size() > 0) {

			for (Etape objectToParse : listOfObjectsToBeParsed) {

				this.parse(objectToParse, counterType);

			}
		}

		return null;
	}

	@Override
	/**
	 * Compares 2 steps to extract stock motions between old and new object
	 * oldObject being null means step is being created while if not null, it is
	 * only modified
	 *
	 * @return a list of stock motions
	 */
	public List<RawMaterialStockMotion> compareTwoObjectsAndExtractStockMotions(Etape oldObject, Etape newObject,
			CounterType counterTypeFrom) {

		list = getList();

		// Array list containing stock motions during step

		List<RawMaterialStockMotion> stockMotionsResult = new ArrayList<RawMaterialStockMotion>();
		List<RawMaterialCounter> oldParsedObject = new ArrayList<RawMaterialCounter>();

		// Searching for Stock counters in
		List<RawMaterialCounter> newParsedObject;

		if (oldObject != null && newObject != null) {

			/*
			 * If both objects are not null, step is being updated
			 */
			newParsedObject = parse(newObject, counterTypeFrom);
			oldParsedObject = parse(oldObject, counterTypeFrom);

			for (RawMaterialCounter oldObj : oldParsedObject) {

				for (RawMaterialCounter newObj : newParsedObject) {

					RawMaterialStockMotion currentStockMotion = new RawMaterialStockMotion();
					if (oldObj.getCpt_id() == newObj.getCpt_id()) {

						currentStockMotion.setStm_motion_date(new Date());
						currentStockMotion.setStr_product(newObj.getCpt_product());
						currentStockMotion.setStm_counter_from(newObj.getCpt_counter_type());
						if (isStepBeginningSoonSoStockIsInFab(newObject)) {

							currentStockMotion.setStm_counter_to(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list));

						} else {
							currentStockMotion
									.setStm_counter_to(CounterTypeConstants.STOCK_RESERVE_FAB.toDBCouter(list));

						}

						double stockMotionValue = newObj.getCpt_value() - oldObj.getCpt_value();

						currentStockMotion.setStm_value(stockMotionValue);
						// VALIDATING STOCK MOTION AND ADDING IT TO LIST
						if (StockMotionValidator.checkIfStockMotionSatisfiesRules(
								currentStockMotion.getStm_counter_from().toConstant(),
								currentStockMotion.getStm_counter_to().toConstant())) {
							stockMotionsResult.add(currentStockMotion);
						}
					}
				}

			}
		} else if (newObject != null && oldObject == null) {

			/*
			 * If oldObject is null, step is being created so motions are full
			 * from step
			 */
			newParsedObject = parse(newObject, counterTypeFrom);

			for (RawMaterialCounter newObj : newParsedObject) {

				RawMaterialStockMotion currentStockMotion = new RawMaterialStockMotion();

				currentStockMotion.setStm_motion_date(new Date());
				currentStockMotion.setStr_product(newObj.getCpt_product());
				currentStockMotion.setStm_counter_from(CounterTypeConstants.STOCK_DISPO_FAB.toDBCouter(list));
				currentStockMotion.setStm_counter_to(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list));

				currentStockMotion.setStm_value(newObj.getCpt_value());
				// VALIDATING STOCK MOTION AND ADDING IT TO LIST
				if (StockMotionValidator.checkIfStockMotionSatisfiesRules(
						currentStockMotion.getStm_counter_from().toConstant(),
						currentStockMotion.getStm_counter_to().toConstant())) {
					stockMotionsResult.add(currentStockMotion);
				}
			}

		} else if ((newObject == null || newObject.equals(new Etape())) && oldObject != null) {
			/*
			 * If updated step is null or new, step is being terminated so
			 * removing stock in fab
			 */
			newObject = new Etape();

			oldParsedObject = parse(oldObject, counterTypeFrom);
			newParsedObject = parse(newObject, null);

			// TODO generate reverse stock motions to delete stock in
			// fabrication when step is terminated

			for (RawMaterialCounter c : oldParsedObject) {

				if (c != null && c.getCpt_value() > 0) {

					RawMaterialStockMotion currentStockMotion = new RawMaterialStockMotion();

					currentStockMotion.setStm_motion_date(new Date());
					currentStockMotion.setStr_product(c.getCpt_product());

					currentStockMotion.setStm_counter_from(CounterTypeConstants.STOCK_EN_FAB.toDBCouter(list));

					currentStockMotion.setStm_counter_to(CounterTypeConstants.NONE.toDBCouter(list));

					currentStockMotion.setStm_unit(currentStockMotion.getStm_unit());

					// Setting value to positive value as motion has direction
					currentStockMotion.setStm_value(c.getCpt_value());

					stockMotionsResult.add(currentStockMotion);
				}

			}
		}

		return stockMotionsResult;
	}

	private List<CounterType> getList() {

		return counterTypeService.getAllElements();
	}

	/**
	 * Method checking if step is beginnning soon (depends on parameter
	 * param.stock.delay.limitToStockInFab.minutes)
	 * 
	 * @param etp
	 *            step to check
	 * @return true if step starts soon. Otherwise false
	 */
	private boolean isStepBeginningSoonSoStockIsInFab(Etape etp) {
		float delayToPutStockInFabInsteadOfReservingIt = Float.parseFloat(ConfigLoader
				.getConfigByKey(Constants.CONFIG_PROPERTIES, "param.stock.delay.limitToStockInFab.minutes"));

		if (delayToPutStockInFabInsteadOfReservingIt > 0)
			delayToPutStockInFabInsteadOfReservingIt = -delayToPutStockInFabInsteadOfReservingIt;

		if (etp.getEtp_debut_reel() != null) {
			/*
			 * Brew step has already started
			 */
			return true;

		} else {
			Date date = DateManipulator.getInstance().getDateFromDateAndDelay(etp.getEtp_debut(),
					delayToPutStockInFabInsteadOfReservingIt, "MINUTES");

			// If date - delay < now means step starts soon
			if (date.before(new Date()))
				return true;
			else
				return false;
		}
	}

	/**
	 * Parses ingredients of step and returns quantities as stockCounters
	 * 
	 * @param objectToBeParsed
	 * @return
	 * @throws Exception
	 */
	private List<RawMaterialCounter> parseIngredients(Etape objectToBeParsed, CounterType counterType)
			throws Exception {

		List<RawMaterialCounter> result = new ArrayList<RawMaterialCounter>();

		Calendar date = Calendar.getInstance();
		date.setTime((objectToBeParsed.getEtp_debut() == null)? new Date() : objectToBeParsed.getEtp_debut());

		if (objectToBeParsed.getEtp_malts() != null) {
			for (Malt malt : objectToBeParsed.getEtp_malts()) {

				if (malt.getIng_quantite() > 0) {

					RawMaterialCounter currentCounter = new RawMaterialCounter();

					currentCounter.setCpt_date_cre(new Date());
					currentCounter.setCpt_date_maj(new Date());
					currentCounter.setCpt_product(malt);
					currentCounter.setCpt_unit(StockUnit.KILO);
					currentCounter.setCpt_value(malt.getIng_quantite());
					currentCounter.setCpt_counter_type(counterType);

					result.add(currentCounter);
				}

			}
		}

		if (objectToBeParsed.getEtp_houblons() != null) {
			for (Houblon hop : objectToBeParsed.getEtp_houblons()) {

				if (hop.getIng_quantite() > 0) {

					RawMaterialCounter currentCounter = new RawMaterialCounter();

					currentCounter.setCpt_date_cre(new Date());
					currentCounter.setCpt_date_maj(new Date());
					currentCounter.setCpt_product(hop);
					currentCounter.setCpt_unit(StockUnit.GRAMME);
					currentCounter.setCpt_value(hop.getIng_quantite());
					currentCounter.setCpt_counter_type(counterType);

					result.add(currentCounter);
				}

			}
		}

		if (objectToBeParsed.getEtp_levures() != null) {

			for (Levure lev : objectToBeParsed.getEtp_levures()) {

				if (lev.getIng_quantite() > 0) {

					RawMaterialCounter currentCounter = new RawMaterialCounter();

					currentCounter.setCpt_date_cre(new Date());
					currentCounter.setCpt_date_maj(new Date());
					currentCounter.setCpt_product(lev);
					currentCounter.setCpt_unit(StockUnit.GRAMME);
					currentCounter.setCpt_value(lev.getIng_quantite());
					currentCounter.setCpt_counter_type(counterType);

					result.add(currentCounter);
				}

			}
		}
		return result;
	}

}
