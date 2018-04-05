package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Houblon;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class HopDaoImpl implements IGenericDao<Houblon> {

	@Autowired
	EntityManager em;

	
	@Override
	public void deleteElement(long arg0) {

		em.remove((Houblon) em.find(Houblon.class, arg0));
		
	}

	@Override
	public void deleteElement(Houblon arg0) {
		em.remove(arg0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getAllDistinctElements() {

		List<Houblon> result = new ArrayList<Houblon>();

		result = em.createQuery("from Houblon group by ing_desc").getResultList();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Houblon> getAllElements() {
		return (List<Houblon>) em.createQuery("from Houblon").getResultList();
	}

	@Override
	public Houblon getElementById(long arg0) {
		Houblon result = new Houblon();

		result = (Houblon) em.find(Houblon.class, arg0);
		
		return result;
	}

	@Override
	public Houblon save(Houblon arg0) throws DAOException {
		
		Houblon result = new Houblon();
		try {

			Houblon resultId = em.merge(arg0);
			result = (Houblon) em.find(Houblon.class, resultId);
			
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
				em.persist(arg0);
				
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

		Houblon result = (Houblon) em.createQuery(
				"from Houblon where hbl_variete = '" + name + "'")
				.getSingleResult();

		
		return result;

	}
}