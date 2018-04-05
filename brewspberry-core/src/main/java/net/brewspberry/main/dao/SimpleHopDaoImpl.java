package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.SimpleHoublon;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class SimpleHopDaoImpl implements IGenericDao<SimpleHoublon> {


	@Autowired
	EntityManager em;

	@Override
	public void deleteElement(long arg0) {

		em.remove((SimpleHoublon) em.find(SimpleHoublon.class, arg0));
		
	}

	@Override
	public void deleteElement(SimpleHoublon arg0) {
		em.remove(arg0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleHoublon> getAllDistinctElements() {

		List<SimpleHoublon> result = new ArrayList<SimpleHoublon>();

		result = em.createQuery("from SimpleHoublon group by ing_desc")
				.getResultList();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleHoublon> getAllElements() {
		List<SimpleHoublon> result = (List<SimpleHoublon>) em.createQuery("from SimpleHoublon")
				.getResultList();
		
		return result;
	}

	@Override
	public SimpleHoublon getElementById(long arg0) {
		SimpleHoublon result = new SimpleHoublon();
		result = (SimpleHoublon) em.find(SimpleHoublon.class, arg0);
		
		return result;
	}

	@Override
	public SimpleHoublon save(SimpleHoublon arg0) throws DAOException {
		
		SimpleHoublon result = new SimpleHoublon();
		try {

			em.persist(arg0);
			result = (SimpleHoublon) em.find(SimpleHoublon.class, arg0.getStb_id());
			
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
	public SimpleHoublon getElementByName(String name) {

		SimpleHoublon result = (SimpleHoublon) em.createQuery(
				"from SimpleHoublon where ing_desc = '" + name + "'")
				.getSingleResult();

		
		return result;

	}
}