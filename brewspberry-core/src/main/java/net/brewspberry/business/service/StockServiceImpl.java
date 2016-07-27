package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.beans.stock.FinishedProductCounter;
import net.brewspberry.business.beans.stock.RawMaterialCounter;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.StockException;
import net.brewspberry.exceptions.DAOException;
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

}
