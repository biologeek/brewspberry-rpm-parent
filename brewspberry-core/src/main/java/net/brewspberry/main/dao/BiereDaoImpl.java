package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificBiereDAO;

@Repository
public class BiereDaoImpl implements IGenericDao<Biere>, ISpecificBiereDAO {

	@Autowired
	EntityManager em;

	@Override
	public Biere save(Biere arg0) throws DAOException {
		em.persist(arg0);
		return arg0;
	}

	@Override
	public Biere update(Biere arg0) {
		em.persist(arg0);
		return arg0;
	}

	@Override
	public Biere getElementById(long id) {
		return (Biere) em.find(Biere.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Biere> getAllElements() {
		return (List<Biere>) em.createQuery("from Biere").getResultList();
	}

	@Override
	public void deleteElement(long id) {
		Biere beer = this.getElementById(id);
		em.remove(beer);
	}

	@Override
	public void deleteElement(Biere arg0) {
		em.remove(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Biere> getAllDistinctElements() {
		List<Biere> result = new ArrayList<Biere>();
		result = em.createQuery("from Biere group by ing_desc").getResultList();
		return result;
	}

	@Override
	public Biere getElementByName(String name) {
		return (Biere) em.createQuery("from Biere where act_nom = " + name).getResultList();
	}

}