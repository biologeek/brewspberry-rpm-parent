package net.brewspberry.dao;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class MaltDAOImpl implements IGenericDao<Malt> {

	private Session session = HibernateUtil.getSession();
	private StatelessSession statelessSession = HibernateUtil.getStatelessSession();

	@Override
	public Malt save(Malt arg0) throws DAOException {
		Transaction tx = session.beginTransaction();

		long resultId;
		Malt result = new Malt();
		try {
			resultId = (long) session.save(arg0);
			result = (Malt) session.get(Malt.class, resultId);
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
	public Malt update(Malt arg0) {
		Transaction tx = session.beginTransaction();

		Malt result = new Malt();

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
	public Malt getElementById(long id) {

		Malt malt = (Malt) session.get(Malt.class, id);

		HibernateUtil.closeSession();
		return malt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Malt> getAllElements() {

		long resultId;
		List<Malt> result = new ArrayList<Malt>();

		result = (List<Malt>) session.createQuery("from Malt").list();

		HibernateUtil.closeSession();

		return result;
	}

	@Override
	public void deleteElement(long id) {
		Transaction tx = session.beginTransaction();

		try {
			Malt malt = (Malt) session.get(Malt.class, id);
			session.delete(malt);
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void deleteElement(Malt arg0) {
		session.delete(arg0);

		HibernateUtil.closeSession();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Malt> getAllDistinctElements() {

		List<Malt> result = new ArrayList<Malt>();
		result = session.createQuery("from Malt group by ing_desc").list();

		HibernateUtil.closeSession();

		return result;
	}

	@Override
	public Malt getElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}