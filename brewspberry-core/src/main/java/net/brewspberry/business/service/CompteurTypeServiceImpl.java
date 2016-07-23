package net.brewspberry.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.IGenericService;
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.exceptions.ServiceException;

public class CompteurTypeServiceImpl implements IGenericService<CompteurType> {

	
	@Autowired
	@Qualifier("compteurTypeDaoImpl")
	private IGenericDao<CompteurType> genericDao;

	@Override
	public CompteurType save(CompteurType arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompteurType update(CompteurType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompteurType getElementById(long id) throws ServiceException {
		
		if (id > 0){
			
			return genericDao.getElementById(id);
		}
		
		throw new ServiceException("ID must be > 0");
	}

	@Override
	public List<CompteurType> getAllElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(CompteurType arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CompteurType> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
