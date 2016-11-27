package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.Malt;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class MaltDAOImpl implements IGenericDao<Malt> {


	@Autowired
	SessionFactory sessionFactory;


	@Override
	public Malt save(Malt arg0) throws DAOException {
		

		long resultId;
		Malt result = new Malt();
		try {
			resultId = (long) sessionFactory.getCurrentSession().save(arg0);
			result = (Malt) sessionFactory.getCurrentSession().get(Malt.class, resultId);
			

		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return result;
	}

	@Override
	public Malt update(Malt arg0) {
		

		Malt result = new Malt();

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
	public Malt getElementById(long id) {

		Malt malt = (Malt) sessionFactory.getCurrentSession().get(Malt.class, id);

		
		return malt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Malt> getAllElements() {

		long resultId;
		List<Malt> result = new ArrayList<Malt>();

		result = (List<Malt>) sessionFactory.getCurrentSession().createQuery("from Malt").list();

		

		return result;
	}

	@Override
	public void deleteElement(long id) {
		

		try {
			Malt malt = (Malt) sessionFactory.getCurrentSession().get(Malt.class, id);
			sessionFactory.getCurrentSession().delete(malt);
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
	}

	@Override
	public void deleteElement(Malt arg0) {
		sessionFactory.getCurrentSession().delete(arg0);

		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Malt> getAllDistinctElements() {

		List<Malt> result = new ArrayList<Malt>();
		result = sessionFactory.getCurrentSession().createQuery("from Malt group by ing_desc").list();

		

		return result;
	}

	@Override
	public Malt getElementByName(String name) {
		
		return null;
	}

}