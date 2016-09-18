package net.brewspberry.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.stock.CounterType;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.util.LogManager;

@Repository
public class CompteurTypeDaoImpl implements IGenericDao<CounterType> {

	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = LogManager.getInstance(CompteurTypeDaoImpl.class.getName());

	@Override
	public CounterType save(CounterType arg0) throws DAOException {

		CounterType result = null;
		long id = (long) sessionFactory.getCurrentSession().save(arg0);

		if (id > 0) {

			result = this.getElementById(id);

		}

		return result;
	}

	@Override
	public CounterType update(CounterType arg0) {

		try {
			sessionFactory.getCurrentSession().update(arg0);
			return arg0;

		} catch (Exception e) {
			return null;

		}

	}

	@Override
	public CounterType getElementById(long id) {
		return (CounterType) sessionFactory.getCurrentSession().get(
				CounterType.class, id);

	}

	@Override
	public CounterType getElementByName(String name) {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CounterType> getAllElements() {
		logger .info("Size of counter type list : "
				+ sessionFactory.getCurrentSession()
						.createQuery("from CounterType").list().size());
		
		
		return sessionFactory.getCurrentSession()
				.createQuery("from CounterType").list();
	}

	@Override
	public void deleteElement(long id) {
		

	}

	@Override
	public void deleteElement(CounterType arg0) {
		

	}

	@Override
	public List<CounterType> getAllDistinctElements() {
		
		return null;
	}

}
