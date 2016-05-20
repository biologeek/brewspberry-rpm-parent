package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

public class HopDaoImpl implements IGenericDao<Houblon> {

	private Session session = HibernateUtil.getSession();
	private StatelessSession statelessSession = HibernateUtil.getStatelessSession();
	
	@Override
	public void deleteElement(long arg0) {

		session.delete((Houblon) session.get(Houblon.class, arg0));
		HibernateUtil.closeSession();
	}

	@Override
	public void deleteElement(Houblon arg0) {
		session.delete(arg0);
		HibernateUtil.closeSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getAllDistinctElements() {

		List<Houblon> result = new ArrayList<Houblon>();

		result = session.createQuery("from Houblon group by ing_desc").list();
		HibernateUtil.closeSession();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getAllElements() {
		return (List<Houblon>) session.createQuery("from Houblon").list();
	}

	@Override
	public Houblon getElementById(long arg0) {
		Houblon result = new Houblon();

		result = (Houblon) session.get(Houblon.class, arg0);
		HibernateUtil.closeSession();
		return result;
	}

	@Override
	public Houblon save(Houblon arg0) throws DAOException {
		Transaction tx = session.beginTransaction();
		Houblon result = new Houblon();
		try {

			long resultId = (long) session.save(arg0);
			result = (Houblon) session.get(Houblon.class, resultId);
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
	public Houblon update(Houblon arg0) {
		Transaction tx = session.beginTransaction();

		Houblon result = new Houblon();

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
	public Houblon getElementByName(String name) {

		Houblon result = (Houblon) session.createQuery(
				"from Houblon where hbl_variete = '" + name + "'")
				.uniqueResult();

		HibernateUtil.closeSession();
		return result;

	}
}
