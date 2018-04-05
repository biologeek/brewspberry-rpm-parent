package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.SimpleMalt;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class SimpleMaltDAOImpl implements IGenericDao<SimpleMalt> {


	@Autowired
	EntityManager em;


	@Override
	public SimpleMalt save(SimpleMalt arg0) throws DAOException {
		
		try {
			em.persist(arg0);
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
		return arg0;
	}

	@Override
	public SimpleMalt update(SimpleMalt arg0) {
		

		SimpleMalt result = new SimpleMalt();

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
	public SimpleMalt getElementById(long id) {

		SimpleMalt malt = (SimpleMalt) em.find(SimpleMalt.class, id);

		
		return malt;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleMalt> getAllElements() {

		long resultId;
		List<SimpleMalt> result = new ArrayList<SimpleMalt>();

		result = (List<SimpleMalt>) em.createQuery("from SimpleMalt")
				.getResultList();
		
		return result;
	}

	@Override
	public void deleteElement(long id) {
		

		try {
			SimpleMalt malt = (SimpleMalt) em.find(SimpleMalt.class, id);
			em.remove(malt);
		} catch (HibernateException e) {
			e.printStackTrace();
			
		} finally {
			
		}
	}

	@Override
	public void deleteElement(SimpleMalt arg0) {
		em.remove(arg0);

		

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleMalt> getAllDistinctElements() {

		List<SimpleMalt> result = new ArrayList<SimpleMalt>();
		result = em.createQuery("from SimpleMalt group by ing_desc")
				.getResultList();

		
		return result;
	}

	@Override
	public SimpleMalt getElementByName(String name) {

		SimpleMalt result = (SimpleMalt) em.createQuery(
				"from SimpleMalt where ing_desc = '" + name + "'")
				.getSingleResult();

		
		return result;

	}
}