package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.ISpecificBrassinDAO;
import net.brewspberry.main.business.beans.Biere;
import net.brewspberry.main.business.beans.Brassin;
import net.brewspberry.main.business.beans.BrewStatus;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.util.HibernateUtil;
import net.brewspberry.main.util.LogManager;

@Repository
public class BrassinDaoImpl implements IGenericDao<Brassin>,
		ISpecificBrassinDAO {

	static final Logger logger = LogManager.getInstance(BrassinDaoImpl.class
			.getName());

	@Autowired
	SessionFactory sessionFactory;


	@Override
	public Brassin save(Brassin arg0) throws DAOException {
		

		try {
			long id = (long) sessionFactory.getCurrentSession().save(arg0);
			
			logger.info("Saved ID : " + id);
			arg0.setBra_id(id);
			return arg0;
		} catch (HibernateException e) {
			
			return new Brassin();
		} finally {
			
		}
	}

	@Override
	public Brassin update(Brassin arg0) {
		

		if (arg0.getBra_id() != null) {
			try {
				sessionFactory.getCurrentSession().update(arg0);
				
				return arg0;
			} catch (HibernateException e) {
				
				return new Brassin();
			}finally {
				
			}
		}
		return new Brassin();
		
	}

	@Override
	public Brassin getElementById(long id) {
		Brassin result = new Brassin();

		result = (Brassin) sessionFactory.getCurrentSession().get(Brassin.class, id);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brassin> getAllElements() {

		List<Brassin> result = null;

		result = (List<Brassin>) sessionFactory.getCurrentSession().createQuery("from Brassin").list();

		

		return result;
	}

	@Override
	public void deleteElement(long id) {
		
		
		Brassin brassin = this.getElementById(id);
		
		try{
			sessionFactory.getCurrentSession().delete(brassin);
			
		} catch (HibernateException e){
			
			e.printStackTrace();
		}

	}

	@Override
	public void deleteElement(Brassin arg0) {
		

		try {
			sessionFactory.getCurrentSession().delete(arg0);
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Brassin> getAllDistinctElements() {

		List<Brassin> result = new ArrayList<Brassin>();
		result = sessionFactory.getCurrentSession().createQuery("from Brassin group by ing_desc").list();

		

		return result;
	}

	@Override
	public Brassin getBrassinByBeer(Biere beer) {
		Brassin result = (Brassin) sessionFactory.getCurrentSession().createCriteria(Brassin.class).add(Restrictions.eq("bra_beer", beer)).uniqueResult();
		return result;
	}

	@Override
	public Brassin getElementByName(String name) {
		Brassin result = (Brassin) sessionFactory.getCurrentSession().createCriteria(Brassin.class).add(Restrictions.eq("bra_nom", name)).uniqueResult();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * Returns brews depending on statuses given
	 * 
	 * @param statuses
	 * @return
	 */
	public List<Brassin> getBrewByStates(List<BrewStatus> statuses) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Brassin.class);
		
		
		crit.add(Restrictions.in("bra_statut", statuses));
		
		return crit.list();
	}

}