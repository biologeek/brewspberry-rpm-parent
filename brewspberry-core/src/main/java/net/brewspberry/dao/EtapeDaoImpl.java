package net.brewspberry.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import net.brewspberry.business.IGenericDao;
import net.brewspberry.business.beans.Etape;
import net.brewspberry.exceptions.DAOException;
import net.brewspberry.util.HibernateUtil;

@Repository
public class EtapeDaoImpl implements IGenericDao<Etape> {

	private Session session = HibernateUtil.getSession();
	private StatelessSession statelessSession = HibernateUtil.getStatelessSession();
	
	
	@Override
	public Etape save(Etape arg0) throws DAOException {
		Transaction tx = session.beginTransaction();

		try {

			long etape = (long) session.save(arg0);

			tx.commit();

			arg0.setEtp_id(etape);

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			arg0 = new Etape();
		} finally {
			HibernateUtil.closeSession();
		}

		return arg0;
	}

	@Override
	public Etape update(Etape arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Etape getElementById(long id) {
		Etape result;

		result = (Etape) session.get(Etape.class, id);
		HibernateUtil.closeSession();

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Etape> getAllElements() {
		List<Etape> result;
		try {
			result = (List<Etape>) session.createQuery("from Etape").list();
		} finally {
			HibernateUtil.closeSession();
		}
		return result;
	}

	@Override
	public void deleteElement(long id) {
		
		Transaction tx = session.beginTransaction();
		
		try {
			
			Etape toDel = (Etape) session.get(Etape.class, id);
			session.delete(toDel);
			tx.commit();			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
		
	}

	@Override
	public void deleteElement(Etape arg0) {

		Transaction tx = session.beginTransaction();
		
		try {
			session.delete(arg0);
			tx.commit();			
		} catch (HibernateException e){
			e.printStackTrace();
			tx.rollback();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Etape> getAllDistinctElements() {

		List<Etape> result = new ArrayList<Etape>();
		result = session.createQuery("from Etape group by etp_nom").list();

		HibernateUtil.closeSession();

		return result;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@Override
	public Etape getElementByName(String name) {
		Etape result = (Etape) session.createCriteria(Etape.class).add(Restrictions.eq("etp_nom", name)).uniqueResult();
		return result;
	}

}