package net.brewspberry.main.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.stock.CounterType;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.util.LogManager;

@Repository
public class CompteurTypeDaoImpl implements IGenericDao<CounterType> {

	@Autowired
	private EntityManager em;
	private Logger logger = LogManager.getInstance(CompteurTypeDaoImpl.class.getName());

	@Override
	public CounterType save(CounterType arg0) throws DAOException {

		CounterType result = null;
		em.persist(arg0);

		if (arg0.getCty_id() > 0) {
			return result;
		}
		throw new DAOException("save.counter.type.error");
	}

	@Override
	public CounterType update(CounterType arg0) throws DAOException {

		try {
			em.persist(arg0);
			return arg0;

		} catch (Exception e) {
			throw new DAOException("update.counter.type.error");
		}

	}

	@Override
	public CounterType getElementById(long id) {
		return (CounterType) em.find(CounterType.class, id);

	}

	@Override
	public CounterType getElementByName(String name) {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CounterType> getAllElements() {
		logger.info("Size of counter type list : " + em.createQuery("from CounterType").getResultList().size());

		return em.createQuery("from CounterType").getResultList();
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
