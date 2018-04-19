package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Levure;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class YeastDAOImpl implements IGenericDao<Levure> {

	@Autowired
	EntityManager em;

	@Override
	public void deleteElement(long arg0) {

		em.remove((Levure) em.find(Levure.class, arg0));

	}

	@Override
	public void deleteElement(Levure arg0) {
		em.remove(arg0);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getAllDistinctElements() {

		List<Levure> result = new ArrayList<Levure>();

		result = em.createQuery("from Levure group by ing_desc").getResultList();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getAllElements() {

		long resultId;
		List<Levure> result = new ArrayList<Levure>();
		try {

			result = (List<Levure>) em.createQuery("from Levure").getResultList();

		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {

		}
		return result;
	}

	@Override
	public Levure getElementById(long arg0) {
		Levure lev = (Levure) em.find(Levure.class, arg0);

		return lev;
	}

	@Override
	public Levure save(Levure arg0) throws DAOException {

		try {

			em.persist(arg0);
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {

		}
		return arg0;
	}

	@Override
	public Levure update(Levure arg0) {

		try {
			em.persist(arg0);
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return arg0;
	}

	@Override
	public Levure getElementByName(String name) {

		return null;
	}

}