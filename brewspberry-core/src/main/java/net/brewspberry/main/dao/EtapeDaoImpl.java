package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Etape;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
@Transactional
public class EtapeDaoImpl implements IGenericDao<Etape> {


	@Autowired
	SessionFactory sessionFactory;

	
	
	@Override
	public Etape save(Etape arg0) throws DAOException {
		

		try {

			long etape = (long) sessionFactory.getCurrentSession().save(arg0);

			arg0.setEtp_id(etape);

		} catch (Exception e) {
			e.printStackTrace();
			
			arg0 = new Etape();
		} 

		return arg0;
	}

	@Override
	public Etape update(Etape arg0) {
		sessionFactory.getCurrentSession().update(arg0);
		
		return this.getElementById(arg0.getEtp_id());
	}

	@Override
	public Etape getElementById(long id) {
		Etape result;

		result = (Etape) sessionFactory.getCurrentSession().get(Etape.class, id);
		

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Etape> getAllElements() {
		List<Etape> result;
		try {
			result = (List<Etape>) sessionFactory.getCurrentSession().createQuery("from Etape").list();
		} finally {
			
		}
		return result;
	}

	@Override
	public void deleteElement(long id) {
		
		
		
		try {
			
			Etape toDel = (Etape) sessionFactory.getCurrentSession().get(Etape.class, id);
			sessionFactory.getCurrentSession().delete(toDel);
						
		} catch (HibernateException e){
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void deleteElement(Etape arg0) {

		
		
		try {
			sessionFactory.getCurrentSession().delete(arg0);
						
		} catch (HibernateException e){
			e.printStackTrace();
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Etape> getAllDistinctElements() {

		List<Etape> result = new ArrayList<Etape>();
		result = sessionFactory.getCurrentSession().createQuery("from Etape group by etp_nom").list();

		

		return result;
	}

	@Override
	public Etape getElementByName(String name) {
		Etape result = (Etape) sessionFactory.getCurrentSession().createCriteria(Etape.class).add(Restrictions.eq("etp_nom", name)).uniqueResult();
		return result;
	}

}