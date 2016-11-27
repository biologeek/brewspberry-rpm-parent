package net.brewspberry.main.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.IGenericService;
import net.brewspberry.main.business.beans.GenericActionner;
import net.brewspberry.main.business.exceptions.ServiceException;

@Service
@Transactional
public class GenericActionnerServiceImpl implements IGenericService<GenericActionner> {

	@Autowired
	@Qualifier("genericActionnerDaoImpl")
	private IGenericDao<GenericActionner> genericDao;

	@Override
	public GenericActionner save(GenericActionner arg0) throws Exception {

		return genericDao.save(arg0);
	}

	@Override
	public GenericActionner update(GenericActionner arg0) {

		return this.genericDao.update(arg0);
	}

	@Override
	public GenericActionner getElementById(long id) throws ServiceException {
		return genericDao.getElementById(id);
	}

	@Override
	public GenericActionner getElementByName(String name) throws ServiceException {
		return getElementByName(name);
	}

	@Override
	public List<GenericActionner> getAllElements() {
		return genericDao.getAllElements();
	}

	@Override
	public void deleteElement(long id) {
		genericDao.deleteElement(id);
	}

	@Override
	public void deleteElement(GenericActionner arg0) {
		genericDao.deleteElement(arg0);
	}

	@Override
	public List<GenericActionner> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
