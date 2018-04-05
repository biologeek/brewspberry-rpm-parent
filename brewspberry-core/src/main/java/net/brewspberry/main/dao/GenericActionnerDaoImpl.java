package net.brewspberry.main.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.monitoring.GenericActionner;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class GenericActionnerDaoImpl implements IGenericDao<GenericActionner> {

	@Autowired
	EntityManager em;
	
	@Override
	public GenericActionner save(GenericActionner arg0) throws DAOException {

		em.persist(arg0);
		return arg0;
	}

	@Override
	public GenericActionner update(GenericActionner arg0) {
		em.persist(arg0);
		return arg0;
	}

	@Override
	public GenericActionner getElementById(long id) {
		return (GenericActionner) em.find(GenericActionner.class, id);
	}

	@Override
	public GenericActionner getElementByName(String name) {
		return (GenericActionner) em.createQuery("from GenericActionner where act_name = "+name).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GenericActionner> getAllElements() {
		return em.createQuery("from GenericActionner").getResultList();
	}

	@Override
	public void deleteElement(long id) {
		em.remove(this.getElementById(id));
	}

	@Override
	public void deleteElement(GenericActionner arg0) {
		em.remove(arg0);
	}

	@Override
	public List<GenericActionner> getAllDistinctElements() {
		return this.getAllElements();
	}

}

