package net.brewspberry.main.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.ISpecificStockService;
import net.brewspberry.main.business.beans.brewing.AbstractFinishedProduct;
import net.brewspberry.main.business.beans.brewing.AbstractIngredient;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.beans.builders.IngredientStockCounterBuilder;
import net.brewspberry.main.business.beans.stock.AbstractStockMotion;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.beans.stock.CounterTypeConstants;
import net.brewspberry.main.business.beans.stock.FinishedProductCounter;
import net.brewspberry.main.business.beans.stock.FinishedProductStockMotion;
import net.brewspberry.main.business.beans.stock.MotionDirection;
import net.brewspberry.main.business.beans.stock.RawMaterialCounter;
import net.brewspberry.main.business.beans.stock.RawMaterialStockMotion;
import net.brewspberry.main.business.beans.stock.StockCounter;
import net.brewspberry.main.business.beans.stock.StockUnit;
import net.brewspberry.main.business.beans.stock.Stockable;
import net.brewspberry.main.business.exceptions.BusinessException;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.business.exceptions.ServiceException;
import net.brewspberry.main.business.exceptions.StockException;
import net.brewspberry.main.business.parser.Parser;
import net.brewspberry.main.data.ISpecificStockDao;
import net.brewspberry.main.util.LogManager;
import net.brewspberry.main.util.StockUnitUtils;

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

	@Autowired
	Parser<RawMaterialCounter, Etape, RawMaterialStockMotion> stepParserForRawMaterial;

	Logger logger;

	public StockServiceImpl() {
		super();

		logger = LogManager.getInstance(this.getClass().getName());
	}

	public IGenericDao<StockCounter> getGenericDAO() {
		return genericDAO;
	}

	public void setGenericDAO(IGenericDao<StockCounter> genericDAO) {
		this.genericDAO = genericDAO;
	}

	public ISpecificStockDao getSpecDAO() {
		return specDAO;
	}

	public void setSpecDAO(ISpecificStockDao specDAO) {
		this.specDAO = specDAO;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Override
	public StockCounter save(StockCounter arg0) throws Exception {

		return this.genericDAO.save(arg0);
	}

	@Override
	public StockCounter update(StockCounter arg0) {

		return genericDAO.update(arg0);
	}

	@Override
	public StockCounter getElementById(long id) {
		
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
		

	}

	@Override
	public void deleteElement(StockCounter arg0) {
		

	}

	@Override
	public List<StockCounter> getAllDistinctElements() {
		
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

		StockCounter cptToDecrease;
		try {
			cptToDecrease = specDAO.getStockCounterByProductAndType(arg0, type);
		} catch (DAOException e1) {
			throw new ServiceException(e1.getMessage());
		}

		boolean isAnIngredient = ((arg0 instanceof AbstractIngredient) ? true : false);

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

			if (stockCounterIngredient == null) {

				throw new StockException(stockCounterIngredient + " is null ");
			} else if (userIngredient == null) {
				throw new StockException(userIngredient + " is null ");

			} else if (!stockCounterIngredient.equals(userIngredient)) {

				throw new StockException(stockCounterIngredient + " != " + userIngredient);

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
		cptToDecrease.setCpt_value(cptToDecrease.getCpt_value() + valueToDecrease);
		cptToDecrease.setCpt_date_maj(new Date());

		if (cptToDecrease.getCpt_value() < 0) {
			throw new StockException("Stock is < 0 after motion !");
		}

		try {
			return this.saveOrUpdate(cptToDecrease);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;

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
		
		return null;
	}

	@Override
	/**
	 * From the stock motions list, retrieves corresponding stock counters and
	 * updates stock value
	 */
	public List<StockCounter> processStockMotionsForUpdatingStockCounters(List<? extends AbstractStockMotion> motions)
			throws ServiceException {

		List<StockCounter> result = new ArrayList<StockCounter>();
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

				logger.info("WARNING : " + currentStockCountersForProduct);
				logger.info("WARNING : " + stockMotion.getStm_counter_from());

				StockCounter savedCounter = null;
				/*
				 * stock counter type being null or NONE means stock is created
				 * or removed from other counter, so updating only non-null
				 * counter
				 */
				try {
					if (stockMotion.getStm_counter_from() != null
							|| stockMotion.getStm_counter_from().toConstant().equals(CounterTypeConstants.NONE)) {

						StockCounter isStockCounterFromExistingInDB = checkIfStockCountersListContainsCounterType(
								currentStockCountersForProduct, stockMotion.getStm_counter_from());

						isStockCounterFromExistingInDB = createOrUpdateStockCounterWithStockValue(stockMotion,
								isStockCounterFromExistingInDB, MotionDirection.FROM);
						if (isStockCounterFromExistingInDB.getCpt_value() == 0) {
							this.deleteElement(isStockCounterFromExistingInDB);
						} else {

							savedCounter = this.saveOrUpdate(isStockCounterFromExistingInDB);

							result.add(savedCounter);
						}
					}

					// Counter To is null means stock is neither modified nor
					// created but deleted. Thus no stock
					// counter To is updated
					if (stockMotion.getStm_counter_to() != null
							|| stockMotion.getStm_counter_to().toConstant().equals(CounterTypeConstants.NONE)) {
						StockCounter isStockCounterToExistingInDB = checkIfStockCountersListContainsCounterType(
								currentStockCountersForProduct, stockMotion.getStm_counter_to());

						isStockCounterToExistingInDB = createOrUpdateStockCounterWithStockValue(stockMotion,
								isStockCounterToExistingInDB, MotionDirection.TO);

						if (isStockCounterToExistingInDB.getCpt_value() == 0) {
							this.deleteElement(isStockCounterToExistingInDB);
						} else {

							savedCounter = this.saveOrUpdate(isStockCounterToExistingInDB);

							result.add(savedCounter);

						}
					}
				} catch (Exception e) {

					throw new ServiceException("Could not save stock counter");

				}

			}
		}

		return result;

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

	public StockCounter createOrUpdateStockCounterWithStockValue(AbstractStockMotion stockMotion,
			StockCounter isStockCounterExistingInDB, MotionDirection direction) throws BusinessException {
		if (isStockCounterExistingInDB != null) {

			/*
			 * if stock counter exists updating value
			 */
			isStockCounterExistingInDB = updateStockValueWithStockMotion(isStockCounterExistingInDB, stockMotion,
					direction);

		} else {

			/*
			 * if stock counter does not exist create it
			 */

			if (stockMotion instanceof FinishedProductStockMotion) {
				if (direction.equals(MotionDirection.FROM)) {

					isStockCounterExistingInDB = (StockCounter) (new FinishedProductStockCounterBuilder()
							.type(stockMotion.getStm_counter_from()).unit(stockMotion.getStm_unit()))
									.product(((FinishedProductStockMotion) stockMotion).getStf_product()).build();

				} else if (direction.equals(MotionDirection.TO)) {

					isStockCounterExistingInDB = (StockCounter) (new FinishedProductStockCounterBuilder()
							.type(stockMotion.getStm_counter_to()).unit(stockMotion.getStm_unit()))
									.product(((FinishedProductStockMotion) stockMotion).getStf_product()).build();

				}
				isStockCounterExistingInDB = updateStockValueWithStockMotion(isStockCounterExistingInDB, stockMotion,
						direction);
			} else if (stockMotion instanceof RawMaterialStockMotion) {
				if (direction.equals(MotionDirection.FROM)) {

					isStockCounterExistingInDB = (StockCounter) (new IngredientStockCounterBuilder()
							.type(stockMotion.getStm_counter_from()).unit(stockMotion.getStm_unit()))
									.ingredient(((RawMaterialStockMotion) stockMotion).getStr_product()).build();

				} else if (direction.equals(MotionDirection.TO)) {

					isStockCounterExistingInDB = (StockCounter) (new IngredientStockCounterBuilder()
							.type(stockMotion.getStm_counter_to()).unit(stockMotion.getStm_unit()))
									.ingredient(((RawMaterialStockMotion) stockMotion).getStr_product()).build();
					;

				}
				isStockCounterExistingInDB = updateStockValueWithStockMotion(isStockCounterExistingInDB, stockMotion,
						direction);

			} else {

				throw new BusinessException("No suitable type found for motion " + stockMotion);
			}

		}
		return isStockCounterExistingInDB;
	}

	/**
	 * Updates a stock counter using a stock motion object 2 rules :
	 * 
	 * 
	 * - stock counters are stored in standard unit
	 * 
	 * - stock motions can be in any compatible unit. They are converted before
	 * stock update
	 * 
	 * @param isStockCounterFromExistingInDB
	 * @param stockMotion
	 * @param direction
	 * @return
	 */
	public StockCounter updateStockValueWithStockMotion(StockCounter isStockCounterFromExistingInDB,
			AbstractStockMotion stockMotion, MotionDirection direction) {

		double newStock = 0.0D;

		stockMotion = this.standardizeMotionUnit(stockMotion);
		

		/*
		 * 
		 * Stock motion from counter1 to counter2 with value 2 means "I transfer
		 * 2 quantities from counter1 to counter2" Thus, decrease counter1 and
		 * increase counter2
		 */
		if (direction.equals(MotionDirection.FROM)) {

			newStock = isStockCounterFromExistingInDB.getCpt_value() - stockMotion.getStm_value();

		} else if (direction.equals(MotionDirection.TO)) {

			newStock = isStockCounterFromExistingInDB.getCpt_value() + stockMotion.getStm_value();

		}
		isStockCounterFromExistingInDB.setCpt_value(newStock);
		isStockCounterFromExistingInDB.setCpt_date_maj(new Date());

		return isStockCounterFromExistingInDB;

	}

	private AbstractStockMotion standardizeMotionUnit(AbstractStockMotion stockMotion) {

		stockMotion.setStm_value(
				StockUnitUtils.convertToStandardUnit(stockMotion.getStm_value(), stockMotion.getStm_unit()));
		stockMotion.setStm_unit(StockUnitUtils.getStandardUnitFromNonStandardUnit(stockMotion.getStm_unit()));

		return stockMotion;
	}

	private StockCounter checkIfStockCountersListContainsCounterType(List<StockCounter> list, CounterType type) {

		for (StockCounter stk : list) {

			if (stk.getCpt_counter_type().equals(type)) {
				return stk;
			}
		}

		return null;

	}

	@Override

	/**
	 * Compares old and new step and extract stock motions. From stock motions,
	 * stock counters are updated
	 * 
	 * @param oldEtape
	 *            step before stock modifications
	 * @param newEtape
	 *            step after modifications
	 * @param counterTypeFrom
	 *            stock counter From
	 * @param counterTypeTo
	 *            stock counter To
	 * @return updated stock counters
	 */
	public List<StockCounter> compareOldAndNewStepToExtractStockMotionsAndUpdateStockCounters(Etape oldEtape,
			Etape newEtape, CounterType counterTypeFrom, CounterType counterTypeTo) {

		List<StockCounter> result = null;

		// STEP 1 - Extract stock motions

		List<? extends AbstractStockMotion> motions = stepParserForRawMaterial
				.compareTwoObjectsAndExtractStockMotions(oldEtape, newEtape, counterTypeFrom);

		// STEP 2 - Process stock motions and update stock counters

		try {
			result = this.processStockMotionsForUpdatingStockCounters(motions);
		} catch (ServiceException e) {
			
			e.printStackTrace();
		}

		return result;
	}

}
