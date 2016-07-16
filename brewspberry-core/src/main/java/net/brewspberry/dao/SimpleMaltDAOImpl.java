package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.SimpleHoublon;
import net.brewspberry.business.beans.SimpleMalt;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class SimpleMaltDAOImpl implements IGenericDao<SimpleMalt> {


	@Autowired
	SessionFactory sessionFactory;


	@Override
	public SimpleMalt save(SimpleMalt arg0) throws DAOException {
		

		long resultId;
		SimpleMalt result = new SimpleMalt();
		try {
			Session session = sessionFactory.getCurrentSession();
			resultId = (long) session.save(arg0);
			result = (SimpleMalt) sessionFactory.getCurrentSession().get(SimpleMalt.class, resultId);
			

		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return result;
	}

	@Override
	public SimpleMalt update(SimpleMalt arg0) {
		

		SimpleMalt result = new SimpleMalt();

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
	public SimpleMalt getElementById(long id) {

		SimpleMalt malt = (SimpleMalt) sessionFactory.getCurrentSession().get(SimpleMalt.class, id);

		
		return malt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleMalt> getAllElements() {

		long resultId;
		List<SimpleMalt> result = new ArrayList<SimpleMalt>();

		result = (List<SimpleMalt>) sessionFactory.getCurrentSession().createQuery("from SimpleMalt")
				.list();
		
		return result;
	}

	@Override
	public void deleteElement(long id) {
		

		try {
			SimpleMalt malt = (SimpleMalt) sessionFactory.getCurrentSession().get(SimpleMalt.class, id);
			sessionFactory.getCurrentSession().delete(malt);
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
	}

	@Override
	public void deleteElement(SimpleMalt arg0) {
		sessionFactory.getCurrentSession().delete(arg0);

		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleMalt> getAllDistinctElements() {

		List<SimpleMalt> result = new ArrayList<SimpleMalt>();
		result = sessionFactory.getCurrentSession().createQuery("from SimpleMalt group by ing_desc")
				.list();

		
		return result;
	}

	@Override
	public SimpleMalt getElementByName(String name) {

		SimpleMalt result = (SimpleMalt) sessionFactory.getCurrentSession().createQuery(
				"from SimpleMalt where ing_desc = '" + name + "'")
				.uniqueResult();

		
		return result;

	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}