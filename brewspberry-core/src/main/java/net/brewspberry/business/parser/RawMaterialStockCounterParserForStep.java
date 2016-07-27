package net.brewspberry.business.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.beans.stock.StockUnit;
import net.brewspberry.exceptions.ServiceException;
import net.brewspberry.util.LogManager;
import net.brewspberry.util.StockMotionValidator;

public class RawMaterialStockCounterParserForStep implements Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> {

	@Autowired
	@Qualifier("counterTypeServiceImpl")
	IGenericService<CounterType> counterTypeService;
	private Logger logger = LogManager.getInstance(RawMaterialStockCounterParserForStep.class.getName());

	@Override
	/**
	 * Parses a step to extract stock counters from
	 */
	public List<RawMaterialCounter> parse(Etape objectToBeParsed) {

		try {
			return parseIngredients(objectToBeParsed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	/**
	 * Parses a list of steps to extract stock counters
	 */
	public List<RawMaterialCounter> parseList(List<Etape> listOfObjectsToBeParsed) {

		if (listOfObjectsToBeParsed.size() > 0) {

			for (Etape objectToParse : listOfObjectsToBeParsed) {

				this.parse(objectToParse);

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
	 * Returns a list of stock motions
	 */
	public List<RawMaterialStockMotion> compareTwoObjectsAndExtractStockMotions(Etape oldObject, Etape newObject) {

		List<RawMaterialStockMotion> stockMotionsResult = new ArrayList<RawMaterialStockMotion>();
		List<RawMaterialCounter> oldParsedObject = new ArrayList<RawMaterialCounter>();
		List<RawMaterialCounter> newParsedObject = parse(newObject);

		if (oldObject != null) {

			oldParsedObject = parse(oldObject);

			for (RawMaterialCounter oldObj : oldParsedObject) {

				for (RawMaterialCounter newObj : newParsedObject) {

					RawMaterialStockMotion currentStockMotion = new RawMaterialStockMotion();
					if (oldObj.getCpt_id() == newObj.getCpt_id()) {

						currentStockMotion.setStm_motion_date(new Date());
						currentStockMotion.setStr_product(newObj.getCpt_product());
						currentStockMotion.setStm_counter_from(newObj.getCpt_counter_type());
						currentStockMotion.setStm_counter_to(CounterType.STOCK_EN_FAB);

						/*
						 * Reminder : I had decided to use 5 kg of malt for this
						 * recipe. In fact I put only 3 kg in kettle for the
						 * recipe (by modifying the step). It results 2 kg must
						 * go back in stock (negative)
						 */

						double stockMotionValue = newObj.getCpt_value() - oldObj.getCpt_value();
						
						// VALIDATING STOCK MOTION AND ADDING IT TO LIST
						if (StockMotionValidator.checkIfStockMotionSatisfiesRules(
								currentStockMotion.getStm_counter_from(), currentStockMotion.getStm_counter_to())) {
							stockMotionsResult.add(currentStockMotion);
						}
					}
				}

			}
		} else {

			/*
			 * If oldObject is null, step is being created so motions are full
			 * from step
			 */
			for (RawMaterialCounter newObj : newParsedObject) {

				RawMaterialStockMotion currentStockMotion = new RawMaterialStockMotion();

				currentStockMotion.setStm_motion_date(new Date());
				currentStockMotion.setStr_product(newObj.getCpt_product());
				currentStockMotion.setStm_counter_from(CounterType.STOCK_DISPO_FAB);
				currentStockMotion.setStm_counter_to(CounterType.STOCK_EN_FAB);

				currentStockMotion.setStm_value(newObj.getCpt_value());
				stockMotionsResult.add(currentStockMotion);
			}

		}

		return stockMotionsResult;
	}

	/**
	 * Parses ingredients of step and returns quantities as stockCounters
	 * 
	 * @param objectToBeParsed
	 * @return
	 * @throws Exception
	 */
	private List<RawMaterialCounter> parseIngredients(Etape objectToBeParsed) throws Exception {

		List<RawMaterialCounter> result = new ArrayList<RawMaterialCounter>();

		Calendar date = Calendar.getInstance();
		date.setTime(objectToBeParsed.getEtp_debut());
		CounterType counterType = CounterType.STOCK_EN_FAB;

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

		return result;
	}

}
