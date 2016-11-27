package net.brewspberry.main.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.GenericActionner;
import net.brewspberry.main.business.exceptions.DAOException;

@Repository
public class GenericActionnerDaoImpl implements IGenericDao<GenericActionner> {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public GenericActionner save(GenericActionner arg0) throws DAOException {

		Long id = (Long) sessionFactory.getCurrentSession().save(arg0);
		return this.getElementById(id);
	}

	@Override
	public GenericActionner update(GenericActionner arg0) {
		sessionFactory.getCurrentSession().update(arg0);
		return this.getElementById(arg0.getGact_id());
	}

	@Override
	public GenericActionner getElementById(long id) {
		return (GenericActionner) sessionFactory.getCurrentSession().get(GenericActionner.class, id);
	}

	@Override
	public GenericActionner getElementByName(String name) {
		return (GenericActionner) sessionFactory.getCurrentSession().createCriteria(GenericActionner.class)
				.add(Restrictions.eq("act_name", name)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GenericActionner> getAllElements() {
		return sessionFactory.getCurrentSession().createCriteria(GenericActionner.class).list();
	}

	@Override
	public void deleteElement(long id) {
		sessionFactory.getCurrentSession().delete(this.getElementById(id));
	}

	@Override
	public void deleteElement(GenericActionner arg0) {
		sessionFactory.getCurrentSession().delete(arg0);
	}

	@Override
	public List<GenericActionner> getAllDistinctElements() {
		return this.getAllElements();
	}

}

