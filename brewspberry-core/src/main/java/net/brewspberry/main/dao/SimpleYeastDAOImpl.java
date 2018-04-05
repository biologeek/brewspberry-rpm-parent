package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.SimpleLevure;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class SimpleYeastDAOImpl implements IGenericDao<SimpleLevure> {

	@Autowired
	EntityManager em;

	@Override
	public void deleteElement(long arg0) {

		em.remove((SimpleLevure) em.find(SimpleLevure.class, arg0));

	}

	@Override
	public void deleteElement(SimpleLevure arg0) {
		em.remove(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleLevure> getAllDistinctElements() {
		List<SimpleLevure> result = new ArrayList<SimpleLevure>();

		result = em.createQuery("from SimpleLevure group by ing_desc").getResultList();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleLevure> getAllElements() {

		List<SimpleLevure> result = new ArrayList<SimpleLevure>();
		result = (List<SimpleLevure>) em.createQuery("from SimpleLevure").getResultList();

		return result;
	}

	@Override
	public SimpleLevure getElementById(long arg0) {
		SimpleLevure lev = (SimpleLevure) em.find(SimpleLevure.class, arg0);

		return lev;
	}

	@Override
	public SimpleLevure save(SimpleLevure arg0) throws DAOException {

		SimpleLevure result = new SimpleLevure();
		try {

			em.persist(arg0);
			
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {

		}
		return arg0;
	}

	@Override
	public SimpleLevure update(SimpleLevure arg0) {

		SimpleLevure result = new SimpleLevure();

		if (arg0.getStb_id() != 0) {
			try {
				em.persist(arg0);

				result = arg0;

			} catch (HibernateException e) {
				e.printStackTrace();

			} finally {

			}
		} else {

			try {
				result = this.save(arg0);
			} catch (HibernateException | DAOException e) {
				e.printStackTrace();

			} finally {

			}
		}
		return result;
	}

	@Override
	public SimpleLevure getElementByName(String name) {

		SimpleLevure result = (SimpleLevure) em
				.createQuery("from SimpleLevure where ing_desc = '" + name + "'").getSingleResult();

		return result;
	}

}