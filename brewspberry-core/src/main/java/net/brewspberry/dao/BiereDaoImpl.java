package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.ISpecificBiereDAO;
import net.brewspberry.business.beans.Biere;
import net.brewspberry.business.beans.Malt;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

public class BiereDaoImpl implements IGenericDao<Biere>, ISpecificBiereDAO {

	private Session session = HibernateUtil.getSession();

	@Override
	public Biere save(Biere arg0) throws DAOException {
		Transaction tx = session.beginTransaction();

		try {
			long id = (long) session.save(arg0);
			tx.commit();

			arg0.setBeer_id(id);
			return arg0;
		} catch (HibernateException e) {
			tx.rollback();
			return new Biere();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public Biere update(Biere arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Biere getElementById(long id) {

		Biere result = (Biere) session.get(Biere.class, id);
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Biere> getAllElements() {

		return (List<Biere>) session.createQuery("from Biere").list();
	}

	@Override
	public void deleteElement(long id) {

		Biere beer = this.getElementById(id);
		Transaction tx = session.beginTransaction();

		try {

			session.delete(beer);

			tx.commit();

		} catch (HibernateException e) {

			tx.rollback();
			e.printStackTrace();

		}
	}

	@Override
	public void deleteElement(Biere arg0) {

		Transaction tx = session.beginTransaction();

		try {

			session.delete(arg0);

			tx.commit();

		} catch (HibernateException e) {

			tx.rollback();
			e.printStackTrace();

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Biere> getAllDistinctElements() {

		List<Biere> result = new ArrayList<Biere>();
		result = session.createQuery("from Biere group by ing_desc").list();

		HibernateUtil.closeSession();

		return result;
		
	}

	@Override
	public Biere getElementByName(String name) {

		return (Biere) session.createQuery("from Biere where act_nom = "+name).list();
		
	}

}
