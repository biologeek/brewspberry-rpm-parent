package net.brewspberry.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.stock.Stockable;
import net.brewspberry.business.exceptions.DAOException;

@Repository
public class StockableDaoImpl implements IGenericDao<Stockable> {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Stockable save(Stockable arg0) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stockable update(Stockable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stockable getElementById(long id) {
		
		return (Stockable) sessionFactory.getCurrentSession().get(Stockable.class, id);
		
	}

	@Override
	public Stockable getElementByName(String name) {
		
		Assert.fail("Not implemented for stockable");
		return null;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stockable> getAllElements() {
		// TODO Auto-generated method stub
		return (List<Stockable>) sessionFactory.getCurrentSession().createQuery("from Stockable").list();
	}

	@Override
	public void deleteElement(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteElement(Stockable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Stockable> getAllDistinctElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
