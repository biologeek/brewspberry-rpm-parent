package net.brewspberry.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.ServiceException;

@Service
@Transactional
public class StockableServiceImpl implements IGenericService<Stockable> {

	@Autowired
	@Qualifier("stockableDaoImpl")
	private IGenericDao<Stockable> genericDao;

	@Override
	public Stockable save(Stockable arg0) throws Exception {
		
		return null;
	}

	@Override
	public Stockable update(Stockable arg0) {
		
		return null;
	}

	@Override
	public Stockable getElementById(long id) throws ServiceException {
		
		return genericDao.getElementById(id);
	}

	@Override
	public List<Stockable> getAllElements() {
		
		return null;
	}

	@Override
	public void deleteElement(long id) {
		

	}

	@Override
	public void deleteElement(Stockable arg0) {
		

	}

	@Override
	public List<Stockable> getAllDistinctElements() {
		
		return null;
	}

	@Override
	public Stockable getElementByName(String name) throws ServiceException {
		
		return null;
	}

}
