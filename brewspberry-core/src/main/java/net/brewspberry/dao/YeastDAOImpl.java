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
import net.brewspberry.business.beans.Levure;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.business.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class YeastDAOImpl implements IGenericDao<Levure> {


	@Autowired
	SessionFactory sessionFactory;


	@Override
	public void deleteElement(long arg0) {

		sessionFactory.getCurrentSession().delete((Levure) sessionFactory.getCurrentSession().get(Levure.class, arg0));

		

	}

	@Override
	public void deleteElement(Levure arg0) {
		sessionFactory.getCurrentSession().delete(arg0);

		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getAllDistinctElements() {

		List<Levure> result = new ArrayList<Levure>();

		result = sessionFactory.getCurrentSession().createQuery("from Levure group by ing_desc").list();

		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Levure> getAllElements() {
		

		long resultId;
		List<Levure> result = new ArrayList<Levure>();
		try {

			result = (List<Levure>) sessionFactory.getCurrentSession().createQuery("from Levure").list();

			

		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return result;
	}

	@Override
	public Levure getElementById(long arg0) {
		Levure lev = (Levure) sessionFactory.getCurrentSession().get(Levure.class, arg0);

		
		return lev;
	}

	@Override
	public Levure save(Levure arg0) throws DAOException {
		
		Levure result = new Levure();
		try {

			long resultId = (long) sessionFactory.getCurrentSession().save(arg0);
			result = (Levure) sessionFactory.getCurrentSession().get(Levure.class, resultId);
			
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return result;
	}

	@Override
	public Levure update(Levure arg0) {
		

		Levure result = new Levure();

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
	public Levure getElementByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}