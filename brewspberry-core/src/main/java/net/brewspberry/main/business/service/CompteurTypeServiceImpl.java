package net.brewspberry.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.exceptions.ServiceException;

@Service("compteurTypeServiceImpl")
@Transactional
public class CompteurTypeServiceImpl implements IGenericService<CounterType> {

	
	@Autowired
	@Qualifier("compteurTypeDaoImpl")
	private IGenericDao<CounterType> genericDao;

	@Override
	public CounterType save(CounterType arg0) throws Exception {
		
		return genericDao.save(arg0);
	}

	@Override
	public CounterType update(CounterType arg0) {
		
		return genericDao.update(arg0);
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
		
		return genericDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		genericDao.deleteElement(id);

	}

	@Override
	public void deleteElement(CounterType arg0) {
		genericDao.deleteElement(arg0);

	}

	@Override
	public List<CounterType> getAllDistinctElements() {
		
		return genericDao.getAllDistinctElements();
	}

	@Override
	public CounterType getElementByName(String name) throws ServiceException {
		
		return genericDao.getElementByName(name);
	}

}
