package net.brewspberry.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockDao;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.AbstractFinishedProduct;
import net.brewspberry.business.beans.AbstractIngredient;
import net.brewspberry.business.beans.stock.CompteurType;
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
		// TODO Auto-generated method stub
		return null;
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
		
		if (arg0 != null){
			
			res = this.specDAO.getWholeStockForProduct(arg0);
			
		}
		
		return res;
	}


	@Override
	public List<StockCounter> getStockForPrimaryMaterials() {
		

		List<StockCounter> res = new ArrayList<StockCounter>();


			res = this.specDAO.getStockForPrimaryMaterials();
			
		
		return res;
	}

	@Override
	public List<StockCounter> getStockForFinishedProducts() {

		List<StockCounter> res = new ArrayList<StockCounter>();
		

		res = this.specDAO.getStockForFinishedProducts();
			
		
		return res;
	}

	@Override
	/**
	 * 
	 * Method used for decrementing or incrementing stock for a product and and counter type
	 * 
	 * @param valueToDecrease : value that will be removed from stock counter, negative if stock lowers, positive if stock added
	 * @param arg0 : the product that has its stock decremented
	 * @param type : type of counter that will be decremented
	 * 
	 * @returns the stock counter with its new value
	 */
	public StockCounter toogleStockCounterForProduct(double valueToDecrease, Stockable arg0, CompteurType type) throws StockException, ServiceException {
		
		StockCounter cptToDecrease = this.specDAO.geStockCounterByProductAndType(arg0, type);
		
		if (cptToDecrease.getCpt_value() <= 0){
			
			throw new StockException("Stock must be positive to be decremented");
			
		} else {
			
			cptToDecrease.setCpt_value(cptToDecrease.getCpt_value() - valueToDecrease);
			cptToDecrease.setCpt_date_maj(new Date());
			
			
			try {
				this.genericDAO.save(cptToDecrease);
			} catch (DAOException e) {
				throw new ServiceException("Error during saving of counter");
			}
		}
		
		
		return null;
	}

	@Override
	public List<StockCounter> getStockCountersByTypes(List<CompteurType> ar0) {

		if (ar0 != null && ar0.size() > 0){
			
			specDAO.getStockCountersByTypes(ar0);
			
		}
		
		return null;
	}
	
	
	
}
