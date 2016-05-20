package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

public class SimpleMaltDAOImpl implements IGenericDao<SimpleMalt> {

	private Session session = HibernateUtil.getSession();

	@Override
	public SimpleMalt save(SimpleMalt arg0) throws DAOException {
		Transaction tx = session.beginTransaction();

		long resultId;
		SimpleMalt result = new SimpleMalt();
		try {
			resultId = (long) session.save(arg0);
			result = (SimpleMalt) session.get(SimpleMalt.class, resultId);
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
	public SimpleMalt update(SimpleMalt arg0) {
		Transaction tx = session.beginTransaction();

		SimpleMalt result = new SimpleMalt();

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
	public SimpleMalt getElementById(long id) {

		SimpleMalt malt = (SimpleMalt) session.get(SimpleMalt.class, id);

		HibernateUtil.closeSession();
		return malt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleMalt> getAllElements() {

		long resultId;
		List<SimpleMalt> result = new ArrayList<SimpleMalt>();

		result = (List<SimpleMalt>) session.createQuery("from SimpleMalt")
				.list();
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public void deleteElement(long id) {
		Transaction tx = session.beginTransaction();

		try {
			SimpleMalt malt = (SimpleMalt) session.get(SimpleMalt.class, id);
			session.delete(malt);
		} catch (HibernateException e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void deleteElement(SimpleMalt arg0) {
		session.delete(arg0);

		HibernateUtil.closeSession();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleMalt> getAllDistinctElements() {

		List<SimpleMalt> result = new ArrayList<SimpleMalt>();
		result = session.createQuery("from SimpleMalt group by ing_desc")
				.list();

		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public SimpleMalt getElementByName(String name) {

		SimpleMalt result = (SimpleMalt) session.createQuery(
				"from SimpleMalt where ing_desc = '" + name + "'")
				.uniqueResult();

		HibernateUtil.closeSession();
		return result;

	}

}
