package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Malt;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class MaltDAOImpl implements IGenericDao<Malt> {

	@Autowired
	EntityManager em;

	@Override
	public Malt save(Malt arg0) throws DAOException {
		try {
			em.persist(arg0);
			return arg0;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DAOException();
		}
	}

	@Override
	public Malt update(Malt arg0) {

		Malt result = new Malt();

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
	public Malt getElementById(long id) {

		Malt malt = (Malt) em.find(Malt.class, id);

		return malt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Malt> getAllElements() {

		long resultId;
		List<Malt> result = new ArrayList<Malt>();

		result = (List<Malt>) em.createQuery("from Malt").getResultList();

		return result;
	}

	@Override
	public void deleteElement(long id) {

		try {
			Malt malt = (Malt) em.find(Malt.class, id);
			em.remove(malt);
		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {

		}
	}

	@Override
	public void deleteElement(Malt arg0) {
		em.remove(arg0);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Malt> getAllDistinctElements() {
		return em.createQuery("from Malt group by ing_desc").getResultList();
	}

	@Override
	public Malt getElementByName(String name) {

		return null;
	}

}