package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.builders.IngredientStockCounterBuilder;
import net.brewspberry.business.beans.stock.AbstractStockMotion;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.FinishedProductStockMotion;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.exceptions.ServiceException;

/**
 * 
 * 
 */
@Service
@Transactional
public class StockServiceImpl implements ISpecificStockService, IGenericService<StockCounter> {

	@Autowired
	IGenericDao<StockCounter> genericDAO;
	@Autowired
	ISpecificStockDao specDAO;

	@Override
	public StockCounter save(StockCounter arg0) throws Exception {

		return this.genericDAO.save(arg0);
	}

	@Override
	public StockCounter update(StockCounter arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StockCounter getElementById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockCounter> getAllElements() {
		List<StockCounter> res = new ArrayList<StockCounter>();

		res = genericDAO.getAllElements();
		return res;
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(StockCounter arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<StockCounter> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockCounter> getWholeStockForProduct(Stockable arg0) {

		List<StockCounter> res = new ArrayList<StockCounter>();

		if (arg0 != null) {

			res = this.specDAO.getWholeStockForProduct(arg0);

		}

		return res;
	}

	@Override
	/**
	 * Method used to return all stock counters for primary material such as
	 * amlt, hops and yeasts
	 */
	public List<RawMaterialCounter> getStockForPrimaryMaterials() {

		List<RawMaterialCounter> res = new ArrayList<RawMaterialCounter>();

		res = this.specDAO.getStockForPrimaryMaterials();

		return res;
	}

	@Override
	/**
	 * Method used to return all stock counters for finished products
	 */
	public List<FinishedProductCounter> getStockForFinishedProducts() {

		List<FinishedProductCounter> res = new ArrayList<FinishedProductCounter>();

		res = this.specDAO.getStockForFinishedProducts();

		return res;
	}

	@Override
	/**
	 * 
	 * Method used for decrementing or incrementing stock for a product and and
	 * counter type
	 * 
	 * @param valueToDecrease
	 *            : value that will be removed from stock counter, negative if
	 *            stock lowers, positive if stock added
	 * @param arg0
	 *            : the product that has its stock decremented
	 * @param type
	 *            : type of counter that will be decremented
	 * 
	 * @returns the stock counter with its new value
	 */
	public StockCounter toogleStockCounterForProduct(double valueToDecrease, Stockable arg0, CounterType type)
			throws StockException, ServiceException {

		StockCounter cptToDecrease = specDAO.getStockCounterByProductAndType(arg0, type);

		boolean isAnIngredient = ((cptToDecrease instanceof RawMaterialCounter) ? true : false);

		if (cptToDecrease.getCpt_value() <= 0) {

			throw new StockException("Stock must be positive to be decremented");

		} else if (isAnIngredient) {

			/*
			 * Checking validity for an ingredient
			 */
			AbstractIngredient stockCounterIngredient = ((RawMaterialCounter) cptToDecrease).getCpt_product();
			AbstractIngredient userIngredient = null;

			try {
				userIngredient = (AbstractIngredient) arg0;
			} catch (Exception e) {

				throw new StockException("Ingredients are not the same. You cannot modify stock for : "
						+ stockCounterIngredient.getIng_desc());
			}

			if (stockCounterIngredient == null || userIngredient == null
					|| !stockCounterIngredient.equals(userIngredient)) {

				throw new StockException("Either " + stockCounterIngredient + " or " + userIngredient + " is null or "
						+ stockCounterIngredient + " != " + userIngredient);

			}

		} else if (!isAnIngredient) {

			/*
			 * Checking validity for a finished product
			 */
			AbstractFinishedProduct stockCounterIngredient = ((FinishedProductCounter) cptToDecrease).getCpt_product();
			AbstractFinishedProduct userIngredient = null;

			try {
				userIngredient = (AbstractFinishedProduct) arg0;
			} catch (Exception e) {

				throw new StockException("Ingredients are not the same. You cannot modify stock for : "
						+ stockCounterIngredient.getStb_id());
			}

			if (stockCounterIngredient == null || userIngredient == null
					|| !stockCounterIngredient.equals(userIngredient)) {

				throw new StockException("Either " + stockCounterIngredient + " or " + userIngredient + " is null or "
						+ stockCounterIngredient + " != " + userIngredient);

			}

		}

		// Decreasing stock value
		cptToDecrease.setCpt_value(cptToDecrease.getCpt_value() - valueToDecrease);
		cptToDecrease.setCpt_date_maj(new Date());

		if (cptToDecrease.getCpt_value() < 0) {
			throw new StockException("Stock is < 0 after motion !");
		}

		return this.genericDAO.update(cptToDecrease);

	}

	@Override
	/**
	 * Gets stockCounters from list provided
	 */
	public List<StockCounter> getStockCountersByTypes(List<CounterType> ar0) {

		List<StockCounter> result = null;

		if (ar0 != null && ar0.size() > 0) {

			result = specDAO.getStockCountersByTypes(ar0);

		}

		return result;
	}

	@Override
	public StockCounter getElementByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * From the stock motions list, retrieves corresponding stock counters and
	 * updates stock value
	 */
	public void processStockMotionsForUpdatingStockCounters(List<AbstractStockMotion> motions) throws ServiceException {

		if (motions.size() > 0) {

			for (AbstractStockMotion stockMotion : motions) {
				Stockable reference = null;

				// Getting either ingredient or product
				if (stockMotion instanceof RawMaterialStockMotion) {
					reference = ((RawMaterialStockMotion) stockMotion).getStr_product();
				} else if (stockMotion instanceof FinishedProductStockMotion) {

					reference = ((FinishedProductStockMotion) stockMotion).getStf_product();

				}

				// Retrieving stock counters in datasource
				List<StockCounter> currentStockCountersForProduct = this.getWholeStockForProduct(reference);

				StockCounter isStockCounterFromExistingInDB = checkIfStockCountersListContainsCounterType(
						currentStockCountersForProduct, stockMotion.getStm_counter_from());
				StockCounter isStockCounterToExistingInDB = checkIfStockCountersListContainsCounterType(
						currentStockCountersForProduct, stockMotion.getStm_counter_to());

				isStockCounterFromExistingInDB = createOrUpdateStockCounterWithStockValue(stockMotion,
						isStockCounterFromExistingInDB);
				isStockCounterToExistingInDB = createOrUpdateStockCounterWithStockValue(stockMotion,
						isStockCounterToExistingInDB);

				// Deleting unnecessary counters

				try {
					if (isStockCounterFromExistingInDB.getCpt_value() == 0) {
						this.deleteElement(isStockCounterFromExistingInDB);
					} else {

						this.saveOrUpdate(isStockCounterFromExistingInDB);
					}
					if (isStockCounterToExistingInDB.getCpt_value() == 0) {
						this.deleteElement(isStockCounterToExistingInDB);
					} else {

						this.saveOrUpdate(isStockCounterToExistingInDB);

					}
				} catch (Exception e) {

					throw new ServiceException("Could not save stock counter");

				}

			}
		}

	}

	/**
	 * Saves stockCounter if does not have an ID, updates if ID > 0
	 * 
	 * @param isStockCounterToExistingInDB
	 * @return
	 * @throws Exception
	 */
	private StockCounter saveOrUpdate(StockCounter isStockCounterToExistingInDB) throws Exception {
		StockCounter result = null;

		if (isStockCounterToExistingInDB.getCpt_id() > 0) {
			result = this.update(isStockCounterToExistingInDB);
		} else {
			result = this.save(isStockCounterToExistingInDB);
		}

		return result;

	}

	private StockCounter createOrUpdateStockCounterWithStockValue(AbstractStockMotion stockMotion,
			StockCounter isStockCounterExistingInDB) {
		if (isStockCounterExistingInDB != null) {

			/*
			 * if stock counter exists updating value
			 */
			updateStockValueWithStockMotion(isStockCounterExistingInDB, stockMotion);

		} else {

			/*
			 * if stock counter does not exist create it
			 */
			if (stockMotion instanceof RawMaterialStockMotion) {
				isStockCounterExistingInDB = (StockCounter) new IngredientStockCounterBuilder()
						.type(stockMotion.getStm_counter_from()).unit(stockMotion.getStm_unit())
						.value(-stockMotion.getStm_value()).build();
			}

		}
		return isStockCounterExistingInDB;
	}

	private void updateStockValueWithStockMotion(StockCounter isStockCounterFromExistingInDB,
			AbstractStockMotion stockMotion) {

		if (isStockCounterFromExistingInDB.getCpt_value() > Math.abs(stockMotion.getStm_value())) {
			double newStock = 0.0D;

			/*
			 * Stock motion from counter1 to counter2 with value 2 means "I
			 * transfer 2 quantities from counter1 to counter2 Thus, decrease
			 * counter1 and increase counter2
			 */
			if (isStockCounterFromExistingInDB.getCpt_counter_type().equals(stockMotion.getStm_counter_from())) {

				newStock = isStockCounterFromExistingInDB.getCpt_value() - stockMotion.getStm_value();

			} else if (isStockCounterFromExistingInDB.getCpt_counter_type().equals(stockMotion.getStm_counter_to())) {

				newStock = isStockCounterFromExistingInDB.getCpt_value() + stockMotion.getStm_value();

			}
			isStockCounterFromExistingInDB.setCpt_date_maj(new Date());

		}

	}

	private StockCounter checkIfStockCountersListContainsCounterType(List<StockCounter> list, CounterType type) {

		for (StockCounter stk : list) {

			if (stk.getCpt_counter_type().equals(type)) {
				return stk;
			}
		}

		return null;

	}

}
