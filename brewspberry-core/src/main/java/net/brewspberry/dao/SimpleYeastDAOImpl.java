package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class SimpleYeastDAOImpl implements IGenericDao<SimpleLevure> {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void deleteElement(long arg0) {

		sessionFactory.getCurrentSession()
				.delete((SimpleLevure) sessionFactory.getCurrentSession().get(SimpleLevure.class, arg0));

	}

	@Override
	public void deleteElement(SimpleLevure arg0) {
		sessionFactory.getCurrentSession().delete(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleLevure> getAllDistinctElements() {
		List<SimpleLevure> result = new ArrayList<SimpleLevure>();

		result = sessionFactory.getCurrentSession().createQuery("from SimpleLevure group by ing_desc").list();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleLevure> getAllElements() {

		List<SimpleLevure> result = new ArrayList<SimpleLevure>();
		result = (List<SimpleLevure>) sessionFactory.getCurrentSession().createQuery("from SimpleLevure").list();

		return result;
	}

	@Override
	public SimpleLevure getElementById(long arg0) {
		SimpleLevure lev = (SimpleLevure) sessionFactory.getCurrentSession().get(SimpleLevure.class, arg0);

		return lev;
	}

	@Override
	public SimpleLevure save(SimpleLevure arg0) throws DAOException {

		SimpleLevure result = new SimpleLevure();
		try {

			long resultId = (long) sessionFactory.getCurrentSession().save(arg0);
			result = (SimpleLevure) sessionFactory.getCurrentSession().get(SimpleLevure.class, resultId);

		} catch (HibernateException e) {
			e.printStackTrace();

		} finally {

		}
		return result;
	}

	@Override
	public SimpleLevure update(SimpleLevure arg0) {

		SimpleLevure result = new SimpleLevure();

		if (arg0.getStb_id() != 0) {
			try {
				sessionFactory.getCurrentSession().update(arg0);

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

		SimpleLevure result = (SimpleLevure) sessionFactory.getCurrentSession()
				.createQuery("from SimpleLevure where ing_desc = '" + name + "'").uniqueResult();

		return result;
	}

}