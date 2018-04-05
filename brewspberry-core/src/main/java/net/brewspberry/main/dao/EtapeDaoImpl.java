package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
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
	EntityManager em;

	
	
	@Override
	public Etape save(Etape arg0) throws DAOException {
		

		try {
			em.persist(arg0);
		} catch (Exception e) {
			e.printStackTrace();
			arg0 = new Etape();
		} 

		return arg0;
	}

	@Override
	public Etape update(Etape arg0) {
		em.persist(arg0);
		
		return this.getElementById(arg0.getEtp_id());
	}

	@Override
	public Etape getElementById(long id) {
		Etape result;

		result = (Etape) em.find(Etape.class, id);
		

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Etape> getAllElements() {
		List<Etape> result;
		try {
			result = (List<Etape>) em.createQuery("from Etape").getResultList();
		} finally {
			
		}
		return result;
	}

	@Override
	public void deleteElement(long id) {
		
		
		
		try {
			
			Etape toDel = (Etape) em.find(Etape.class, id);
			em.remove(toDel);
						
		} catch (HibernateException e){
			e.printStackTrace();
			
		}
		
	}

	@Override
	public void deleteElement(Etape arg0) {

		
		
		try {
			em.remove(arg0);
						
		} catch (HibernateException e){
			e.printStackTrace();
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Etape> getAllDistinctElements() {

		List<Etape> result = new ArrayList<Etape>();
		result = em.createQuery("from Etape group by etp_nom").getResultList();

		

		return result;
	}

	@Override
	public Etape getElementByName(String name) {
		Etape result = (Etape) em.createQuery("from Etape where etp_nom = "+ name).getSingleResult();
		return result;
	}

}