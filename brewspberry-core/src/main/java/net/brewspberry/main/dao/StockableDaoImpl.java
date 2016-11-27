package net.brewspberry.main.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.stock.Stockable;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class StockableDaoImpl implements IGenericDao<Stockable> {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public Stockable save(Stockable arg0) throws DAOException {
		
		return null;
	}

	@Override
	public Stockable update(Stockable arg0) {
		
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
		
		return (List<Stockable>) sessionFactory.getCurrentSession().createQuery("from Stockable").list();
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

}
