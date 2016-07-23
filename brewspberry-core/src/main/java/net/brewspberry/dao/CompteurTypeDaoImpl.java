package net.brewspberry.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.stock.CompteurType;
import net.brewspberry.exceptions.DAOException;

@Repository
public class CompteurTypeDaoImpl implements IGenericDao<CompteurType> {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public CompteurType save(CompteurType arg0) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompteurType update(CompteurType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompteurType getElementById(long id) {
		return (CompteurType) sessionFactory.getCurrentSession().get(CompteurType.class, id);
		
	}

	@Override
	public CompteurType getElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
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
