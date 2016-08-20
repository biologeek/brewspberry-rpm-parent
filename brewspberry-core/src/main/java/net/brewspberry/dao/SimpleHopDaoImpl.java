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
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class SimpleHopDaoImpl implements IGenericDao<SimpleHoublon> {


	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void deleteElement(long arg0) {

		sessionFactory.getCurrentSession().delete((SimpleHoublon) sessionFactory.getCurrentSession().get(SimpleHoublon.class, arg0));
		
	}

	@Override
	public void deleteElement(SimpleHoublon arg0) {
		sessionFactory.getCurrentSession().delete(arg0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleHoublon> getAllDistinctElements() {

		List<SimpleHoublon> result = new ArrayList<SimpleHoublon>();

		result = sessionFactory.getCurrentSession().createQuery("from SimpleHoublon group by ing_desc")
				.list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleHoublon> getAllElements() {
		List<SimpleHoublon> result = (List<SimpleHoublon>) sessionFactory.getCurrentSession().createQuery("from SimpleHoublon")
				.list();
		
		return result;
	}

	@Override
	public SimpleHoublon getElementById(long arg0) {
		SimpleHoublon result = new SimpleHoublon();
		result = (SimpleHoublon) sessionFactory.getCurrentSession().get(SimpleHoublon.class, arg0);
		
		return result;
	}

	@Override
	public SimpleHoublon save(SimpleHoublon arg0) throws DAOException {
		
		SimpleHoublon result = new SimpleHoublon();
		try {

			long resultId = (long) sessionFactory.getCurrentSession().save(arg0);
			result = (SimpleHoublon) sessionFactory.getCurrentSession().get(SimpleHoublon.class, resultId);
			
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return result;
	}

	@Override
	public SimpleHoublon update(SimpleHoublon arg0) {
		

		SimpleHoublon result = new SimpleHoublon();

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
	public SimpleHoublon getElementByName(String name) {

		SimpleHoublon result = (SimpleHoublon) sessionFactory.getCurrentSession().createQuery(
				"from SimpleHoublon where ing_desc = '" + name + "'")
				.uniqueResult();

		
		return result;

	}
}