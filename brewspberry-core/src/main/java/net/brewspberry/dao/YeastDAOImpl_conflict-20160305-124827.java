package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

public class YeastDAOImpl implements IGenericDao<Levure> {

	Session session = HibernateUtil.getSession();
	StatelessSession statelessSession = HibernateUtil.getStatelessSession();

	@Override
	public void deleteElement(long arg0) {

		session.delete((Levure) session.get(Levure.class, arg0));

		HibernateUtil.closeSession();

	}

	@Override
	public void deleteElement(Levure arg0) {
		session.delete(arg0);

		HibernateUtil.closeSession();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getAllDistinctElements() {

		List<Levure> result = new ArrayList<Levure>();

		result = session.createQuery("from Levure group by ing_desc").list();

		HibernateUtil.closeSession();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getAllElements() {
		Transaction tx = session.beginTransaction();

		long resultId;
		List<Levure> result = new ArrayList<Levure>();
		try {

			result = (List<Levure>) session.createQuery("from Levure").list();

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
	public Levure getElementById(long arg0) {
		Levure lev = (Levure) session.get(Levure.class, arg0);

		HibernateUtil.closeSession();
		return lev;
	}

	@Override
	public Levure save(Levure arg0) throws DAOException {
		Transaction tx = session.beginTransaction();
		Levure result = new Levure();
		try {

			long resultId = (long) session.save(arg0);
			result = (Levure) session.get(Levure.class, resultId);
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
	public Levure update(Levure arg0) {
		Transaction tx = session.beginTransaction();

		Levure result = new Levure();

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
	public Levure getElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
