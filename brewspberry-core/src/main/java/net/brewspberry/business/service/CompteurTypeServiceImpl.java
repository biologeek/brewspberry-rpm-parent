package net.brewspberry.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.exceptions.ServiceException;

public class CompteurTypeServiceImpl implements IGenericService<CounterType> {

	
	@Autowired
	@Qualifier("compteurTypeDaoImpl")
	private IGenericDao<CounterType> genericDao;

	@Override
	public CounterType save(CounterType arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CounterType update(CounterType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CounterType getElementById(long id) throws ServiceException {
		
		if (id > 0){
			
			return genericDao.getElementById(id);
		}
		
		throw new ServiceException("ID must be > 0");
	}

	@Override
	public List<CounterType> getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(CounterType arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CounterType> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CounterType getElementByName(String name) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
