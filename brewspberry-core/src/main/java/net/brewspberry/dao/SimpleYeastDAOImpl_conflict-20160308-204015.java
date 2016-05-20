package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.SimpleLevure;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

public class SimpleYeastDAOImpl implements IGenericDao<SimpleLevure> {

	Session session = HibernateUtil.getSession();
	StatelessSession statelessSession = HibernateUtil.getStatelessSession();

	@Override
	public void deleteElement(long arg0) {

		session.delete((SimpleLevure) session.get(SimpleLevure.class, arg0));

		HibernateUtil.closeSession();

	}

	@Override
	public void deleteElement(SimpleLevure arg0) {
		session.delete(arg0);

		HibernateUtil.closeSession();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleLevure> getAllDistinctElements() {
		List<SimpleLevure> result = new ArrayList<SimpleLevure>();

		result = session.createQuery("from SimpleLevure group by ing_desc")
				.list();
		HibernateUtil.closeSession();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleLevure> getAllElements() {

		long resultId;
		List<SimpleLevure> result = new ArrayList<SimpleLevure>();
		result = (List<SimpleLevure>) session.createQuery("from SimpleLevure")
				.list();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public SimpleLevure getElementById(long arg0) {
		SimpleLevure lev = (SimpleLevure) session.get(SimpleLevure.class, arg0);

		HibernateUtil.closeSession();
		return lev;
	}

	@Override
	public SimpleLevure save(SimpleLevure arg0) throws DAOException {
		Transaction tx = session.beginTransaction();
		SimpleLevure result = new SimpleLevure();
		try {

			long resultId = (long) session.save(arg0);
			result = (SimpleLevure) session.get(SimpleLevure.class, resultId);
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtil.closeSession();
		}
		return result;
	}

	@Override
	public SimpleLevure update(SimpleLevure arg0) {
		Transaction tx = session.beginTransaction();

		SimpleLevure result = new SimpleLevure();

		if (arg0.getIng_id() != 0) {
			try {
				session.update(arg0);
				tx.commit();
				result = arg0;

			} catch (HibernateException e) {
				e.printStackTrace();
				tx.rollback();
			} finally {
				HibernateUtil.closeSession();
			}
		} else {

			try {
				result = this.save(arg0);
			} catch (HibernateException | DAOException e) {
				e.printStackTrace();
				tx.rollback();
			} finally {
				HibernateUtil.closeSession();
			}
		}
		return result;
	}

	@Override
	public SimpleLevure getElementByName(String name) {

		SimpleLevure result = (SimpleLevure) session.createQuery(
				"from SimpleLevure where ing_desc = '" + name + "'")
				.uniqueResult();

		HibernateUtil.closeSession();
		return result;
	}

}
