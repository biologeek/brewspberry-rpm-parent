package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.Houblon;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class HopDaoImpl implements IGenericDao<Houblon> {

	@Autowired
	SessionFactory sessionFactory;

	
	@Override
	public void deleteElement(long arg0) {

		sessionFactory.getCurrentSession().delete((Houblon) sessionFactory.getCurrentSession().get(Houblon.class, arg0));
		
	}

	@Override
	public void deleteElement(Houblon arg0) {
		sessionFactory.getCurrentSession().delete(arg0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getAllDistinctElements() {

		List<Houblon> result = new ArrayList<Houblon>();

		result = sessionFactory.getCurrentSession().createQuery("from Houblon group by ing_desc").list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getAllElements() {
		return (List<Houblon>) sessionFactory.getCurrentSession().createQuery("from Houblon").list();
	}

	@Override
	public Houblon getElementById(long arg0) {
		Houblon result = new Houblon();

		result = (Houblon) sessionFactory.getCurrentSession().get(Houblon.class, arg0);
		
		return result;
	}

	@Override
	public Houblon save(Houblon arg0) throws DAOException {
		
		Houblon result = new Houblon();
		try {

			long resultId = (long) sessionFactory.getCurrentSession().save(arg0);
			result = (Houblon) sessionFactory.getCurrentSession().get(Houblon.class, resultId);
			
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return result;
	}

	@Override
	public Houblon update(Houblon arg0) {
		

		Houblon result = new Houblon();

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
	public Houblon getElementByName(String name) {

		Houblon result = (Houblon) sessionFactory.getCurrentSession().createQuery(
				"from Houblon where hbl_variete = '" + name + "'")
				.uniqueResult();

		
		return result;

	}
}