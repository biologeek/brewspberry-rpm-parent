package net.brewspberry.main.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.brewspberry.main.business.IGenericDao;
import net.brewspberry.main.business.beans.brewing.Biere;
import net.brewspberry.main.business.beans.brewing.Malt;
import net.brewspberry.main.business.exceptions.DAOException;
import net.brewspberry.main.data.ISpecificBiereDAO;
import net.brewspberry.main.util.HibernateUtil;

@Repository
public class BiereDaoImpl implements IGenericDao<Biere>, ISpecificBiereDAO {


	@Autowired
	SessionFactory sessionFactory;


	@Override
	public Biere save(Biere arg0) throws DAOException {
		

		try {
			long id = (long) sessionFactory.getCurrentSession().save(arg0);
			

			arg0.setStb_id(id);
			return arg0;
		} catch (HibernateException e) {
			
			return new Biere();
		} finally {
			
		}
	}

	@Override
	public Biere update(Biere arg0) {
		
		return null;
	}

	@Override
	public Biere getElementById(long id) {

		Biere result = (Biere) sessionFactory.getCurrentSession().get(Biere.class, id);
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Biere> getAllElements() {

		return (List<Biere>) sessionFactory.getCurrentSession().createQuery("from Biere").list();
	}

	@Override
	public void deleteElement(long id) {

		Biere beer = this.getElementById(id);
		

		try {

			sessionFactory.getCurrentSession().delete(beer);

			

		} catch (HibernateException e) {

			
			e.printStackTrace();

		}
	}

	@Override
	public void deleteElement(Biere arg0) {

		

		try {

			sessionFactory.getCurrentSession().delete(arg0);

			

		} catch (HibernateException e) {

			
			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Biere> getAllDistinctElements() {

		List<Biere> result = new ArrayList<Biere>();
		result = sessionFactory.getCurrentSession().createQuery("from Biere group by ing_desc").list();

		

		return result;
		
	}

	@Override
	public Biere getElementByName(String name) {

		return (Biere) sessionFactory.getCurrentSession().createQuery("from Biere where act_nom = "+name).list();
		
	}

}