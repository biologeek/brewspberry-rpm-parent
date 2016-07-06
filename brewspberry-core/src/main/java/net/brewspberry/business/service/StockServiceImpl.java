package net.brewspberry.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.ISpecificStockService;
import net.brewspberry.business.beans.stock.StockCounter;
import net.brewspberry.business.beans.stock.Stockable;


/**
 * 
 * 
 */
@Service
public class StockServiceImpl implements ISpecificStockService, IGenericService<StockCounter> {
	
	
	@Autowired
	IGenericDao<StockCounter> genericDAO;
	
	
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

		return null;
	}

}
